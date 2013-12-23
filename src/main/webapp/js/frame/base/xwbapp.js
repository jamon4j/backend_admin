/*!
 * X weibo JavaScript Library v1.1
 * http://x.weibo.com/
 * 
 * Copyright 2010 SINA Inc.
 * Date: 2010/10/28 21:22:06
 */


/**
 * @class Xwb
 * 鏈被鏄墍鏈塜weibo JavaScript浜や簰搴旂敤鐨勫懡鍚嶇┖闂存牴鐩綍銆�
 * @extends Xwb.ax.Eventable
 */
 
if(!window.Xwb)
    Xwb = {};

(function(X, $){

var 
    undefined,
    doc  = document,
    CFG = X.cfg,
	// 閫氱敤鐨刣isabled鏍峰紡
    disabledCS = 'general-btn-disabled';
    
var String = window.String,
    trimReg = new RegExp("(?:^\\s*)|(?:\\s*$)", "g");

//debug寮€鍏�
window.__debug = !!window.__debug;

var _uid = 0, ds = doc.selection;

/**
 * @class Xwb.util
 * 瀹炵敤鍑芥暟闆�
 * @singleton
 */
var Util = X.util = {

       /**
        * @param {String} templateString
        * @param {Object} dataMap
        * @param {Boolean} [urlencode] encodeURIComponent for value
        * @param {Boolean} [cascade] cascade apply template from value
        example:
        <pre>
            <code>
                var name = templ('My name is {name}', {name:'xweibo'});
                var url  = templ('http://www.server.com/getName?name={name}', {name:'寰崥'}, true);
            </code>
        </pre>
        */
       templ : function(str, map, urlencode, cascade){
            return str.replace(/\{([\w_$]+)\}/g, function(s, s1){
                var v = map[s1];
                if(cascade && typeof v === 'string')
                    v = argument.callee(v, map, urlencode, cascade);
                
                if(v === undefined || v === null) 
                    return '';
                return urlencode?encodeURIComponent(v) : v;
            });
       },
       /**
        * @param {Object} target
        * @param {Object} source
        */
        extendIf : function(des, src) {
            if(!des)
              des = {};
        
            if(src)
              for(var i in src){
                if(des[i] === undefined)
                  des[i] = src[i];
              }
        
            return des;
        },
       
        /**
         * 灏嗘簮瀵硅薄鐨勬墍鏈夊睘鎬у鍒跺埌鐩爣瀵硅薄
         * @param {Object} target 鐩爣瀵硅薄,鍙负绌�
         * @param {Object} source 婧愬璞�
         * @param {Boolean} [override]
         * @return {Object} target
         */
        extend : function(target, src, override){
          if(!target)
            target = {};
          if(src){
            for(var i in src)
                if(target[i]===undefined || override)
                    target[i] = src[i];
          }
          return target;
        },
        
        /**
         * 绉婚櫎瀛楃涓叉渶宸︿笌鏈€鍙宠竟鐨勭┖鏍�
         * @param {String} string
         * @return {String}
         */
        trim : function(s){
            return s.replace(trimReg, "");
        },

        /**
         * 杩斿洖瀵硅薄鏌ヨ瀛楃涓茶〃绀哄舰寮�.
         * <pre><code>
           var obj = {name:'xweibo', age:'25'};

           //鏄剧ず name=xweibo&age=25
           alert(queryString(obj));
           // 涔熷彲浠ュ弻閲嶉敭鍊煎褰㈠紡
           {"doAction":"interView","extra_params":{"interview_id":"27"}}
           => 'doAction=interView&extra_params['interview_id']=27'
         * </code></pre>
         * 鎻愬彇url鍙傛暟杞负JS瀵硅薄鏂规硶鍙傝{@link #dequery}<br/>
         * 鑾峰緱琛ㄥ崟鍐呮墍鏈夊厓绱犳彁浜ゅ弬鏁板瓧绗︿覆琛ㄧず鏂规硶鍙傝{@link #formQuery}
         * @param {Object} obj
         * @return {String} 瀵硅薄鐨勬煡璇㈠瓧绗︿覆琛ㄧず褰㈠紡
         */
        queryString : function(obj) {
            if(!obj)
                return '';
            var arr = [];
            for(var k in obj){
                var ov = obj[k], k = encodeURIComponent(k);
                var type = typeof ov;
                if(type === 'undefined'){
                    arr.push(k, "=&");
                }else if(type != "function" && type != "object"){
                    arr.push(k, "=", encodeURIComponent(ov), "&");
                }else if(ov instanceof Array){
                    if (ov.length) {
                        for(var i = 0, len = ov.length; i < len; i++) {
                            arr.push(k, "=", encodeURIComponent(ov[i] === undefined ? '' : ov[i]), "&");
                        }
                    } else {
                        arr.push(k, "=&");
                    }
                }else if(type === 'object'){
                    // 渚嬪"extra_params":{"interview_id":"27"}褰㈠紡
                    for(var kk in ov){
                        arr.push(k,'[',kk,']','=', encodeURIComponent(ov[kk]),'&');
                    }
                }
            }
            arr.pop();
            return arr.join("");
        },
        
        /**
         * 濡傛灉浠呬粎鎯冲垏鎹his鑼冨洿锛岃€屽張浣夸唬鐞嗗嚱鏁板弬鏁颁笌鍘熸潵鍙傛暟涓€鑷寸殑锛屽彲浣跨敤鏈柟娉曘€�
         * @param {Function} sourceFunction
         * @param {Object} scope scope.func()
         * @return {Function}
         */
        bind : function(fn, scope){
          return function() {
              return fn.apply(scope, arguments);
          };
        },

        /**
         * @class Xwb.ax.AjaxConfig
         * {@link Xwb.util#ajax}鏂规硶鐨勮姹傚弬鏁�
         */
         /**
          * @cfg {String} url 璇锋眰鐩爣URL
          */
         /**
          * @cfg {String} method 璇锋眰鏂规硶 POST/GET
          */
         /**
          * @cfg {String} encoding 鍙戦€佸唴瀹圭殑瀛楃缂栫爜锛屾湭璁剧疆閲囩敤榛樿
          */
         /**
          * @cfg {String} dt dataType锛岃繑鍥炲唴瀹圭被鎹被鍨嬶紝text鎴杍son锛岄粯璁や负json锛岀郴缁熸牴鎹绫诲瀷浼犻€掑搴旂被鍨嬬殑鏁版嵁鍒板洖璋冩柟娉曠殑鍙傛暟涓€�
          */
         /**
          * @cfg {String|Object} data 璇锋眰鏃朵紶閫掔殑鏁版嵁锛屽彲涓哄瓧绗︿覆锛屼篃鍙负閿€煎銆�
          */
          /**
           * @cfg {Boolean} cache 璇锋眰鏃舵槸鍚﹀簲鐢ㄧ紦瀛橈紝榛樿蹇界暐缂撳瓨
           */
         /**
          * @cfg {Object} scope 鍙寚瀹氬洖璋冩柟娉曡皟鐢ㄦ椂鐨則his瀵硅薄
          */
         /**
          * @cfg {Function} success 璇锋眰鎴愬姛鍚庡洖璋冩柟娉�
          * @param {Mixed} data 鏍规嵁璁惧畾鐨勬暟鎹被鍨嬩紶閫掍笉鍚岀殑绫诲瀷鏁版嵁
          * @param {XMLHttpRequest} ajax 
          */
         /**
          * @cfg {Function} failure 璇锋眰澶辫触鍚庡洖璋冩柟娉�
          * @param {String} responseText 鏍规嵁璁惧畾鐨勬暟鎹被鍨嬩紶閫掍笉鍚岀殑绫诲瀷鏁版嵁
          * @param {XMLHttpRequest} ajax 
          */
          
        /**
         * @class Xwb.util
         */
        /**
         * 鍙戣捣涓€涓猘jax璇锋眰.
         * @param {Xwb.ax.AjaxConfig} param 璇锋眰鍙傛暟
         * @return {XMLHttpRequest}
         */
        ajax : function(param){
            var ajax, url = param.url;
            
            if (window.XMLHttpRequest) {
                ajax = new XMLHttpRequest();
            } else {
                if (window.ActiveXObject) {
                    try {
                        ajax = new ActiveXObject("Msxml2.XMLHTTP");
                    } catch (e) {
                        try {
                            ajax = new ActiveXObject("Microsoft.XMLHTTP");
                        } catch (e) { }
                        }
                    }
            }
            
            
            if(ajax){
                param.method = param.method ? param.method.toUpperCase() : 'GET';
                // setup param

                var ps = param.data, ch = !param.cache;
                if(ps || ch){
                    var isQ = url.indexOf('?') >= 0;
                    if(ch){
                        if (isQ)
                            url = url + '&_=' + (+new Date());
                        else
                            url = url + '?_=' + (+new Date());
                    }
                    
                    // append data to url or parse post data to string
                    if(ps){
                        if(typeof ps === 'object')
                            ps = Util.queryString(ps);
                        if(param.method === 'GET'){
                            if(!isQ && !ch)
                                url = url+'?';
            
                            url = url + '&' + ps;
                        }
                    }
                }
                ajax.open(param.method, url, true);
                
                if (param.method === 'POST')
                    ajax.setRequestHeader('Content-type', 'application/x-www-form-urlencoded; charset='+(param.encoding?param.encoding:''));
                
                ajax.onreadystatechange = function(){
                    if (ajax.readyState === 4) {
                        var ok = (ajax.status === 200);
                        if(ok && param.success){
                            try{
                                var data = (!param.dt || param.dt === 'json') ? eval("("+ajax.responseText+");") : ajax.responseText;
                            }catch(e){
                                if( __debug ) console.error('鏈嶅姟鍣ㄨ繑鍥濲SON鏍煎紡鏈夎锛岃妫€鏌ャ€俓n',e,'\n', ajax.responseText);
                                ok = false;
                            }
                            if (ok)
                                param.success.call(param.scope||this, data, ajax);
                        }
                        
                        if(!ok && param.failure){
                            param.failure.call(param.scope||this, ajax.responseText, ajax);
                        }
                    }
                };
                
                // send POST data
                ajax.send("POST" === param.method ? ps : undefined);
                
                return ajax;
            }
        },
        
        /**
         * @class Xwb.ax.JSONPConfig
         * {@link Xwb.util#jsonp}鏂规硶鐨勮姹傚弬鏁�
         */
         
         /**
          * @cfg {String} url 璇锋眰鐩爣URL
          */
         /**
          * @cfg {DOMElement} doc 鍙互鎸囧畾鐢熸垚JSONP鑴氭湰鎵€鍦ㄧ殑document
          */
         
         /**
          * @cfg {Object} scope 鍙寚瀹氬洖璋冩柟娉曡皟鐢ㄦ椂鐨則his瀵硅薄
          */
          
         /**
          * @cfg {Object} data 浣滀负鎻愪氦鍙傛暟鐨勯敭鍊煎
          */
          
          /**
           * @cfg {String} charset JSONP鑴氭湰瀛楃缂栫爜
           */
           /**
            * @cfg {Object} script 杩涜JSONP璇锋眰鐨剆cript鏍囩鐨勫睘鎬ч泦锛屽湪璇锋眰鍓嶈灞炴€ч泦灏嗚澶嶅埗鍒皊cript鏍囩涓�
            */
         /**
          * @cfg {Function} success 璇锋眰鎴愬姛鍚庡洖璋冩柟娉�
          * @param {Object} data 鏍规嵁璁惧畾鐨勬暟鎹被鍨嬩紶閫掍笉鍚岀殑绫诲瀷鏁版嵁
          * @param {XMLHttpRequest} ajax
          */
         /**
          * @cfg {Function} failure 璇锋眰澶辫触鍚庡洖璋冩柟娉�
          * @param {String} responseText 鏍规嵁璁惧畾鐨勬暟鎹被鍨嬩紶閫掍笉鍚岀殑绫诲瀷鏁版嵁
          * @param {XMLHttpRequest} ajax 
          */
          /**
           * @cfg {String} jsonp 鎸囧畾JSONP璇锋眰鏍囪瘑鍙傛暟鐨勫悕绉帮紝榛樿涓�'jsonp'
           */

        /**
         * @class Xwb.util
         */
         /**
          * 鍙戣捣涓€涓狫SONP璇锋眰
         * @param {String} url 鐩爣鍦板潃
         * @param {Xwb.ax.JSONPConfig} param 璇锋眰鍙傛暟
         * @return {HTMLElement} scriptElement
         */
        jsonp : function(param){
            var fn  = 'jsonp_' + (+new Date()),
                doc = param.doc || document, 
                url = param.url,
                script = doc.createElement('script'),
                hd = doc.getElementsByTagName("head")[0],
                success;
            
            if(typeof param == 'function'){
                success = param;
                param = {};
            }else success = param.success;
            
            
            script.type = 'text/javascript';
            param.charset && (script.charset = param.charset);
            param.deffer  && (script.deffer  = param.deffer);
            
            url = url + ( url.indexOf('?')>=0 ? '&' + ( param.jsonp || 'jsonp')+'='+fn : '?'+( param.jsonp || 'jsonp')+'='+fn);
            
            if(param.data)
                url += '&'+Util.queryString(param.data);
            
            if(param.script){
                Util.extend(script, param.script);
                delete param.script;
            }
            
            script.src = url;

            var cleaned = false;
            
            function clean(){
                if(!cleaned){
                    try {
                        delete window[fn];
                        script.parentNode.removeChild(script);
                        script = null;
                    }catch(e){}
                    cleaned = true;
                }
            }
            
            window[fn] = function(){
                clean();
                if(success)
                  success.apply(param.scope||this, arguments);
            };

            script.onreadystatechange = script.onload = function(){
                var rs = this.readyState;
                // 
                if( !cleaned && (!rs || rs === 'loaded' || rs === 'complete') ){
                    clean();
                    if(param.failure)
                        param.failure.call(param.scope||this);
                }
            };
            
            hd.appendChild(script);
            
            return script;
        },
    
    /**
     * @property ie6
     * 褰撳墠鏄惁IE6娴忚鍣�
     * @type Boolean
     */
    // ie6
    
    /**
     * 绉婚櫎鏁扮粍涓嬫爣鍏冪礌
     * <pre><code>
     var arr = ['a',2,'string'];
     Xwb.util.arrayRemove(arr, 2);
 </code></pre>
     * @param {Array} array
     * @param {Number} index
     */
    arrayRemove : function(arr, idx){
        arr.splice(idx, 1)[0];
    },
/**
 *  鑾峰緱鍏冪礌鍦ㄦ暟缁勪腑鐨勪笅鏍囷紝鏃犲垯杩斿洖-1锛岄€氳繃===姣旇緝涓や釜鍏冪礌鏄惁鐩哥瓑銆�
 * @param {Array} array
 * @param {Object} object
 * @return {Number} index, 鎴� -1
 */
    arrayIndexOf : function(arr, obj){
        for(var i=0,len=arr.length;i<len;i++)
            if(arr[i] === obj)
                return i;
        return -1;
    },
/**
 *  鍒╃敤JavaScript涓師鍨嬬壒鎬у垱寤洪潰鍚戝璞′腑鐨勨€滅被鈥濄€�
 *  鎵€鏈夊埄鐢ㄨ鏂规硶鍒涘缓鐨勭被閮借瀹炵幇鎴栬€呰瀛樺湪{@link #init}鍒濆鏂规硶銆�
  <pre><code>
  function Base(){
  }
  
  Base.prototype = {
    init : function(){
        alert('init');
    },
    say : function(){
        alert('Base');
    },
    
    eat : function(){
        alert('eat');
    }
  };
  
  var A = Xwb.util.create(Base, {
        say : function(){
            Base.prototype.say.apply(this, arguments);
            alert('A');
        }
  });
  
  var a = new A();
  a.eat();
  a.say();
 </code></pre>
 * @param {String} [namespace] 鍚嶇О锛屽寘鍚懡鍚嶇┖闂达紝鍙€�
 * @param {Function} superclass 鐖剁被锛屾棤鐖剁被鍙疆绌�
 * @param {Object} attributes 绫�(鍘熷瀷)灞炴€э紝鏂规硶闆�
 * @return {Function} 鏂扮被
 */
    create : function(){
          var clazz = (function() {
            this.init.apply(this, arguments);
          });

          if (arguments.length === 0)
            return clazz;

          var absObj, base, type, ags = $.makeArray( arguments );

          if (typeof ags[0] === 'string') {
            type = ags[0];
            base = ags[1];
            ags.shift();
          } else base = ags[0];
          
          ags.shift();

          if (base)
            base = base.prototype;
          
          if (base) {
            function Bridge(){};
            Bridge.prototype = base;
            clazz.prototype = absObj = new Bridge();
          }

          if (type) {
            absObj.type = type;
            Util.ns(type, clazz);
          }
          
          for(var i=0,len=ags.length;i<len;i++)
            absObj = $.extend(absObj, typeof ags[i] === 'function' ? ags[i]( base ):ags[i]);
          
          absObj.constructor = clazz;
          return clazz;
    },
/**
 * 鍦―OM鏍戜腑鍚戜笂鏌ユ壘绗﹀悎鎸囧畾閫夋嫨鍣ㄧ殑鍏冪礌銆�
 * @param {HTMLElement} fromElement 寮€濮嬪厓绱�(鍖呭惈)
 * @param {HTMLElement} toElement 缁撴潫鍏冪礌(涓嶅寘鍚�)锛屾湭璁剧疆鏃朵负document.body鍏冪礌
 */    
    domUp : function(el, selector, end){
        end = end || doc.body;
        var isStr = typeof selector === 'string';
        while(el){
            if(isStr){
                if($(el).is(selector))
                    return el; 
            }else if(selector(el)){
                return el;
            }
            el = el.parentNode;
            if(el === end)
                return null;
        }
        return el;        
    },
/**
 *  鏍规嵁瀛楃涓插垱寤哄懡鍚嶇┖闂淬€�
  <pre><code>
  Xwb.util.ns('Xwb.ux', {});
 </code></pre>
 * @param {String} namespace
 * @param {Object} object
 */
    ns : function(ns, v){
        var routes = ns.split('.'),p=window,key;
        for(var k=0,len=routes.length - 1;k<len;k++){
            key = routes[k];
            if(!p[key])
                p[key] = {};
            p = p[key];
        }
        p[routes[k]] = v;
    },
/**
 *  璁＄畻鏂囨湰鍓╀綑瀛楁暟锛岄粯璁ゆ渶澶ч暱搴︿负寰崥鏈€澶ч暱搴︺€�
 *  濡傛灉宸茶秴鍑烘渶澶ч檺鍒跺瓧鏁帮紝杩斿洖璐熷€笺€�
 * @param {String} text
 * @param {Number} [max] 鍙€夛紝鏈€澶у瓧鏁帮紙涓€涓瓧涓や釜瀛楃锛�
 * @return {Number} 
 */
    calWbText : function(text, max){
        if(max === undefined)
            max = 140;
        var cLen=0;
        var matcher = text.match(/[^\x00-\xff]/g),
            wlen  = (matcher && matcher.length) || 0;
        return Math.floor((max*2 - text.length - wlen)/2);
    },
    
/**
 *  杩斿洖鍗犵敤瀛楄妭闀垮害锛堜竴涓瓧涓や釜瀛楄妭锛�
 * @param {String} text
 * @return {Number}
 */
    byteLen : function(text){
        var len = text.length;
        var matcher = text.match(/[^\x00-\xff]/g);
        if(matcher)
            len += matcher.length;
        return len;
    },
    
    /**
     * 浠ュ瓧鑺備负闀垮害璁＄畻鍗曚綅鎴彇瀛楃涓诧紝涓€涓瓧涓や釜瀛楄妭
     * @param {String} text
     * @param {Number} length
     * @return {String} cutString
     */
    byteCut : function(str, length) {
      var wlen = Util.byteLen(str);
      if(wlen>length){
          // 鎵€鏈夊瀛楃敤&&浠ｆ浛
          var c = str.replace(/&/g, " ")
                     .replace(/[^\x00-\xff]/g, "&&");
          // c.slice(0, length)杩斿洖鎴煭瀛楃涓蹭綅
          str = str.slice(0, c.slice(0, length)
                    // 鐢变綅瀹借浆涓篔S char瀹�
                    .replace(/&&/g, " ")
                    // 闄ゅ幓鎴簡鍗婁釜鐨勫浣�
                    .replace(/&/g, "").length
                );
      }
      return str;
    },
    /**
     *  鏇挎崲婧愬瓧绗︿覆涓煇娈典负鎸囧畾鍚戝瓧绗︿覆
     * @param {String} source
     * @param {String} replacement
     * @param {Number} fromIndex
     * @param {Number} toIndex
     * @return {String} newString
     */
    stringReplace : function(source, text, from, to){
        return source.substring(0, from) + text + source.substring(to);
    },

/**
 *  灏嗗厜鏍囧畾浣嶈嚦鎸囧畾涓嬫爣锛屽涓嬫爣鏈缃紝瀹氫綅鑷虫枃鏈湯灏�
 * @param {HTMLElement} inputor
 * @param {Number} index
 */
    focusEnd : function(inputor, num){
        inputor.focus();
        if(num === undefined)
            num = inputor.value.length;
        if(doc.selection) {
            var cr = inputor.createTextRange();
            cr.collapse();
            cr.moveStart('character', num);
            cr.moveEnd('character', num);
            cr.select();
        }else {
            inputor.selectionStart = inputor.selectionEnd = num;
        }
    },
    
    selectionStart : function(elem){
        if(!ds)
            return elem.selectionStart;
        var range = ds.createRange(), 
            s, 
            bdyRange = doc.body.createTextRange();
            
            bdyRange.moveToElementText(elem);
            try{
                for(s=0;bdyRange.compareEndPoints("StartToStart", range) < 0;s++)
                    bdyRange.moveStart('character', 1);
            }catch(e){
                s = this.getCursorPos(elem);
            }
         return s;
    },
    
    selectionBefore : function(elem){
        return elem.value.slice(0, this.selectionStart(elem));
    },
/**
 * 閫夋嫨杈撳叆妗嗗唴鎸囧悜鑼冨洿鍐呯殑鏂囨湰
 * @param {HTMLElement} inputor
 * @param {Number} startIndex
 * @param {Number} endIndex
 */
    selectText : function(elem, start, end){
        elem.focus();
        if(!ds){
            elem.setSelectionRange(start, end);
            return;
        }
        
        var range = elem.createTextRange();
        range.collapse(1);
        range.moveStart('character', start);
        range.moveEnd('character', end - start);
        range.select();
    },
/**
 *  妫€娴嬫槸褰撳墠娴忚鍣ㄦ槸鍚︽槸鏀寔W3C鏍囧噯閫夋嫨鑼冨洿鐨勬祻瑙堝櫒
 * @return {Boolean}
 */
    hasSelectionSupport : function(){
      return !$.browser.msie;
    },
    
/**
 * 鑾峰緱杈撳叆妗嗗唴鍏夋爣涓嬫爣
 * @param {HTMLElement} element
 * @return {Number} index
 */
    getCursorPos : function(elem){
        var pos = 0;
        if(!this.hasSelectionSupport()){
            try{
                elem.focus();
                var range = null;
                range = ds.createRange();
                var tmpRange = range.duplicate();
                tmpRange.moveToElementText(elem);
                tmpRange.setEndPoint("EndToEnd", range);
                elem.selectionStart = tmpRange.text.length - range.text.length;
                elem.selectionEnd = elem.selectionStart + range.text.length;
                pos = elem.selectionStart;
            }catch(e) {};
        }else{
            if( elem.selectionStart || elem.selectionStart == '0' )
                pos = elem.selectionStart;
        }
        
        return pos;
    },
/**
 * 鑾峰緱杈撳叆妗嗗唴閫夋嫨鏂囨湰
 * @param {HTMLElement} element
 * @return {String} text
 */
    getSelectionText : function(elem){
        var selectedText = '';
        if(window.getSelection){
            selectedText = (function () {
                if (elem.selectionStart != undefined && elem.selectionEnd != undefined) {
                    return elem.value.substring(elem.selectionStart, elem.selectionEnd)
                }
                else {
                    return ""
                }
            })(elem);
        }else selectedText = ds.createRange().text;
        
        return selectedText;
    },
    
    setCursor : function(elem, pos, coverLen){
        pos = pos == null ? elem.value.length : pos;
        coverLen = coverLen == null ? 0 : coverLen;
        elem.focus();
        if(elem.createTextRange){
            var range = elem.createTextRange();
            range.move("character", pos);
            range.moveEnd("character", coverLen);
            range.select();
        }else {
            elem.setSelectionRange(pos, pos + coverLen);
        }
    },
    
    replaceSelection : function(elem, text){
        elem.focus();
        var start = this.selectionStart(elem),
            end   = this.getSelectionText(elem).length,
            val   = elem.value;
        end = end === 0 ? start : start+end;
        elem.value = Util.stringReplace(val, text, start, end);
        this.setCursor(elem, start+text.length);
        return start;
    },
/**
 * 杞箟鏂囨湰涓殑HTML瀛楃
 * @param {String} html
 * @return {String} escapedHtml
 */
    escapeHtml : function(html){
        return html?html.replace(/</g, '&lt;').replace(/>/g, '&gt;'):'';
    },
/** 
 *  鍒╃敤CSS鏍峰紡绂佺敤鎴栨仮澶嶆寚瀹歨tml鍏冪礌銆�
 * @param {HTMLElement} element
 * @param {Boolean} disabled
 * @param {String} [disabledCss] 鍙€夛紝鍙嚜瀹氫箟鑰屼笉鍒╃敤榛樿鏍峰紡
 */
    disable : function(el , disabled, cs){
        disabled ? $(el).addClass(cs||disabledCS) : $(el).removeClass(cs||disabledCS);
    },
/**
 *  杩斿洖this涓烘寚瀹氬璞＄殑鍑芥暟锛岃鏂规硶浼氱敓鎴愪竴涓睘鎬ф寚瀹氭柊鍑芥暟锛岄伩鍏嶄簡姣忔璋冪敤鏃堕兘浜х敓鏂扮殑鍑芥暟銆�
  <pre><code>
  var a = {
     say : function(){ alert('say'); }
  };
  
  var wrappedSay = Xwb.util.getBind(a, 'say');
  // alert say
  wrappedSay();
  
  // 绗簩娆¤皟鐢ㄦ椂锛岃繑鍥炲師鏉ュ凡鍒涘缓鐨勯棴鍖呭嚱鏁�, true
  alert(wrappedSay === Xwb.util.getBind(a, 'say'))
 </code></pre>
 * @param {Object} object, this scope object
 * @param {String} functionName 鍑芥暟鍚�
 * @return {Function} closuredFunction
 */
    getBind : function(obj, funcName){
        var k = '_xwbbnd_'+funcName;
        var m = obj[k];
        if(!m)
           m = obj[k] = Util.bind(obj[funcName], obj);
        return m;
    },
    
/**
 *  杩斿洖杩愯鏃跺敮涓€ID
 * @return {Number}
 */
    uniqueId : function(){
    	return ++_uid;
    },
    
    /**
     * 鑾峰緱涓€涓〃鍗曟墍鏈夎〃鍗曞厓绱犵殑鏁版嵁,骞惰繑鍥炶〃鍗曠殑鏌ヨ瀛楃涓茶〃绀恒€�
     * <br/>
     <code>
       &lt;form id=&quot;f&quot;&gt;
         &lt;input type=&quot;text&quot; name=&quot;username&quot; value=&quot;rock&quot;/&gt;
         &lt;input type=&quot;text&quot; name=&quot;password&quot; value=&quot;123&quot;/&gt;
       &lt;/form&gt;
       &lt;script&gt;
         //&gt;: username=rock&amp;password=123
         alert(formQuery('f'));
       &lt;/script&gt;
       </code>
     * @param {FormElement|String|jQuery} f form鎴杅orm鐨刬d
     * @return {String} 鎵€鏈夎〃鍗曞厓绱犵殑鏌ヨ瀛楃涓茶〃绀�
     */
    formQuery: function(f) {
        var formData = "", elem = "", f = $(f)[0], qid;
        var elements = f.elements;
        var length = elements.length;
        for (var s = 0; s < length; s++) {
            elem = elements[s];
            if (elem.tagName === 'INPUT') {
                if (elem.type === 'radio' || elem.type === 'checkbox') {
                    if (!elem.checked) {
                        continue;
                    }
                }
            }
            
            qid = elem.name||elem.id;
            
            if(qid){
	            if (formData != "") {
	                formData += "&";
	            }
	            formData += encodeURIComponent(elem.name||elem.id) + "=" + encodeURIComponent(elem.value);
            }
        }
        return formData;
    },
        
/**
 * 寰€URL杩藉姞鎻愪氦鍙傛暟
 * @param {String} url
 * @param {Object|String} param
 * @return {String}
 */
    appendParam : function(url, param){
        var qs = typeof param !== 'string' ? this.queryString(param):param;
        return url + ( url.indexOf('?') !== -1 ? '&'+qs : '?'+qs );
    },
/**
 * 鍒嗚ВURL涓殑鍙傛暟鍒癑S瀵硅薄銆�<br/>
 * 灏咼S瀵硅薄缁勮涓哄弬鏁板瓧绗︿覆鏂规硶鍙傝{@link #queryString}<br/>
 * 鑾峰緱琛ㄥ崟鍐呮墍鏈夊厓绱犳彁浜ゅ弬鏁板瓧绗︿覆琛ㄧず鏂规硶鍙傝{@link #formQuery}
 * @param {String} url
 * @return {Object} 姘歌繙涓嶄細涓虹┖
 */
    dequery : function(url){
        var param = {};
        url = url.substr(url.indexOf('?')+1);
        if(url){
            url = url.split('&');
            for(var i=0,len=url.length;i<len;i++){
                var arr = url[i].split('=');
                param[arr[0]] = decodeURIComponent(arr[1]);
            }
        }
        return param;
    },
/**
 * 浠庤矾寰勪腑鎻愬彇鏂囦欢鍚�
 * @param {String} pathName
 * @return {String}
 */
    getFileName : function(str, len){
		if (str.indexOf('\\')) {
			var parts = str.split('\\');
			str = parts.pop();
		}
			
		if (str.length > len) {
			str = str.substr(0, len-4) + '..' + str.substr(str.length-4);
		}
		return str;
    },
/**
 *  鎷嗗垎瀛楃涓蹭负鏁扮粍銆�
 * @param {String} string
 * @param {String} splitChar 鍒嗛殧瀛楃
 * @param {String} escapeChar杞箟瀛楃
 * @param {Array}  array
 */
    split : function(str, splitChar, escChar){
        var c, arr = [], tmp = [];
        if(!escChar)
            escChar = '\\';
    
        for(var i=0,len=str.length;i<len;i++){
            c = str.charAt(i);
            if(c === splitChar){
                arr.push(tmp.join(''));
                tmp.length = 0;
                continue;
            }
            else if(c === escChar && str.charAt(i+1) === splitChar){
                c = splitChar;
                i++;
            }
            tmp[tmp.length] = c;
        }
        if(tmp.length)
            arr.push(tmp.join(''));
        return arr;
    },
/**
 * 瑙ｉ噴閿€煎瀛楃涓蹭负JS瀵硅薄锛屾渶甯哥敤娉曟槸瑙ｆ瀽html涓璻el灞炴€с€�
 * @param {String} string
 * @return {Object} map
 */
    parseKnV : function(strRel){
        var map = {}, kv, kvs = this.split(strRel||'', ',');
        try {
            for( var i=0,len=kvs.length;i<len;i++){
                // if not contains ':'
                // set k = true
                if(kvs[i].indexOf(':') === -1){
                    map[kvs[i]] = true;
                }else {
                    // split : to k and v
                    kv = Util.split(kvs[i], ':');
                    // escape value
                    map[kv[0]] = kv[1];
                }
            }
        }catch(e) { 
            if(__debug) console.trace();
            throw 'Syntax Error:rel瀛楃涓叉牸寮忓嚭閿欍€�' + strRel; 
        }
    
        return map;
    },
/**
 * 娴嬭瘯涓や釜HTML鍏冪礌闂存槸鍚﹀瓨鍦ㄥ寘鍚叧绯�
 * @param {HTMLElement} parentElement
 * @param {HTMLElement} childElement
 * @return {Boolean}
 */
    ancestorOf :function(v, a, depth){
      if (v.contains && !$.browser.webkit) {
         return v.contains(a);
      }else if (v.compareDocumentPosition) {
         return !!(v.compareDocumentPosition(a) & 16);
      }
    
      if(depth === undefined)
        depth = 65535;
      var p = a.parentNode, bd = doc.body;
      while(p!= bd && depth>0 && p !== null){
        if(p == v)
          return true;
        p = p.parentNode;
        depth--;
      }
      return false;
    },
    
/**
 * 鏍规嵁鏍峰紡绫诲悕寰楀埌鏍峰紡瀹炰綋
 *  @param {String|Array} sname
 *  @param {String|Array} cssName 鎸囧畾鏍峰紡琛ㄥ悕绉�
 *  @return {Object}
 */
    getClassByName : function(sname,cssName){
    	var get = function(n){
    		var j = sname.length;
//		    console.log(sname)
//			console.log(sname.length);
    		for(var i=0;i<j;i++){
    			if(n.toLowerCase() == sname[i]){
//    				console.log(j);
//	    			j = sname.length;
//    				sname.splice(i,1);
    				return true;
    			}
    		}
    		return false;
    	};
    	
    	var isCss = function(href){
    		if(typeof cssName == 'string'){
    			return ( href.indexOf(cssName) < 0 )? false : true;
    		}else{

    			for (var j=0;j<cssName.length;j++) {
    				if(href.indexOf(cssName[j]) > -1){
    					return true;
    				}
    			}
    			return false;
    		}
    	};
    	var result = {};
    	for (var i=0;i<document.styleSheets.length;i++) {
    		var rules;
    		if(cssName){
    			if(!document.styleSheets[i].href || !isCss(document.styleSheets[i].href)) continue;
    		}
    		if (document.styleSheets[i].cssRules) {
    			rules = document.styleSheets[i].cssRules;
    		} else {
    			rules = document.styleSheets[i].rules;
    		}
    		if(typeof sname == 'string'){
        		for (var j=0;j<rules.length;j++) {
        			if (rules[j].selectorText && rules[j].selectorText.toLowerCase() == sname) {
        				result = rules[j].style;
        			}
        		}
    		}else{
	    		for (var j=0,k=rules.length;j<k;j++) {
	    			var n = (rules[j].selectorText&&rules[j].selectorText.toLowerCase())||'';
		    		if(n && get(n)){
		    			result[n] = rules[j].style;
		    		}
		    	}
    		}
    	}
	    return result;
    },
    
/**
 *  淇敼鏍峰紡锛坈lass锛夊睘鎬�
 *  @param {String|Array} sname
 *  @param {Object} attrObj 瑕佷慨鏀圭殑灞炴€у璞�
 *  @param {String|Array} cssName 鎸囧畾鏍峰紡琛ㄥ悕绉�
 */   
    setClassAttr : function(sname,attrObj,cssName){
    	var tempSname = sname.slice(0);
    	var cls = this.getClassByName(sname,cssName);
    	if(cls){
    		if(typeof sname == 'string'){
	    		for(var v in attrObj){
	    			cls[v] = attrObj[v];
	    		}
    		}else{
    			for(var i=0,j=sname.length;i<j;i++){
    				if(cls[sname[i]]){
    		    		for(var v in attrObj[i]){
    		    			cls[sname[i]][v] = attrObj[i][v];
    		    		}
    				}
    			}
    		}
    	}
    },
    
    //鑾峰彇鏌愬瓧绗﹀湪textarea鎴杋nput涓殑鍧愭爣
    getCursorOffset : (function(){
        var font = "Tahoma,瀹嬩綋";
        
        var isCss1 = false;
        
        if ($.browser.msie && $.browser.version < 8) {
            isCss1 = true
        }
        
        function format(h) {
            var a = /<|>|\'|\"|&|\\|\r\n|\n| /gi;
            var hash = {
                "<": "&lt;",
                ">": "&gt;",
                '"': "&quot;",
                "\\": "&#92;",
                "&": "&amp;",
                "'": "&#039;",
                "\r": "",
                "\n": "<br>",
                " ": !isCss1 ? "<span style='white-space:pre-wrap;'> </span>" : "<pre style='overflow:hidden;display:inline;word-wrap:break-word;'> </pre>"
            };
            
            return h.replace(a, function (m) {
                return hash[m]
            });
        }
        
        //鐢熸垚涓€涓€忔槑闀滃儚
        function mirror($element) {
            this.ele = $element;
            
            this.init();
        }
        mirror.prototype = {
            //panel
            $p : null,
            
            //瑕佹祴璇曠殑瀵硅薄鐨勪綅缃�
            $f : null,
            
            css : ["overflowY", "height", "width", "paddingTop", "paddingLeft", "paddingRight", "paddingBottom", "marginTop", "marginLeft", "marginRight", "marginBottom"
                ,'fontFamily', 'borderStyle', 'borderWidth', 'wordWrap', 'fontSize', 'lineHeight', 'overflowX'],
            
            init : function() {
                var $p = this.$p = $('<div></div>');
                
                var css = {opacity: 0, position: 'absolute', left: 0, top:0, zIndex: 20000}, 
                    $ele = this.ele;
                /*
                css = $.extend({
                    fontFamily: font,
                    borderStyle: "solid",
                    borderWidth: "0px",
                    wordWrap: "break-word",
                    fontSize: "14px",
                    lineHeight: "18px",
                    overflowX: "hidden"
                }, css);
                */
                $.each(this.css, function(i, p){
                    css[p] = $ele.css(p);
                });
                
                $p.css(css);
                $('body').append($p);
            },
            
            setContent : function(front, flag, end) {
                var $p = this.$p, $flag;
                $p.html('<span>' + format(front) + '</span>');
                this.$f = $flag = $('<span>' + format(flag) + '</span>');
                $p.append($flag);
                $p.append('<span>' + format(end) + '</span>');
            },
            
            getPos : function() {
                return this.$f.position();
            }
        }
        
        return function(textElem, flagCharAt) {
            var $textElem = $(textElem);
            if (!$textElem.data('mirror')) {
                $textElem.data('mirror', new mirror($textElem));
            }
            
            var $mirror = $textElem.data('mirror');
            
            if (!$mirror) {
                return {};
            }
            
            var text = $textElem.val(),
            frontContent = text.substring(0, flagCharAt),
            flag = text.charAt(flagCharAt),
            lastContent = text.substring(flagCharAt+1);
            $mirror.setContent(frontContent, flag, lastContent);
            
            return $mirror.getPos();
        }
    })()
    
};


if(window.__debug){

/**
 * @class console
 * 绯荤粺鎺у埗鍙�,濡傛灉瀛樺湪firebug,鍒╃敤firebug杈撳嚭璋冭瘯淇℃伅,鍚﹀垯蹇界暐.
 * 鍦╢irbug涓彲鐩存帴杩涜瀵规煇涓璞¤繘琛岀洃瑙�,
 * 鏃燾onsole鏃跺氨涓虹┖璋冪敤,鍙噸鍐欒嚜瀹氳緭鍑�.
 * @singleton
 */
if(!window.console)
      window.console = {};

Util.extendIf(console,{
      /**
       * %o琛ㄧず鍙傛暟涓轰竴涓璞�
       * console.log('This an string "%s",this is an object , link %o','a string', CC);
       *@param {arguments} 绫讳技C璇█涓璸rintf璇硶
       *@method
       */
    debug : $.noop,

/**
 * @method trace
 */
    trace : $.noop,
/**
 * @method log
 */
    log : $.noop,
/**
 * @method warn
 */
    warn : $.noop,
/**
 * @method error
 */
    error : $.noop,

/**
 * @method group
 */
    group:$.noop,
/**
 * @method groupEnd
 */
    groupEnd:$.noop
});

}


if ( $.browser.msie && $.trim($.browser.version) == "6.0" ){
    Util.ie6 = true;
    doc.execCommand("BackgroundImageCache", false, true);
}

/**
 * @class Xwb.ax
 */
X.ax = {};

/**
 * @class Xwb.ax.Tpl
 * <p>HTML妯℃澘绫伙紝鐢ㄤ簬瑙ｆ瀽瀛楃涓睭TML骞剁敓鎴愬搴旂殑DOM鍏冪礌銆�</p>
 * <p>HTML妯℃澘瀛楃瑙ｆ瀽渚濊禆涓や釜鏁版嵁锛�
    <ul><li>鏈В鏋愮殑htmls瀛楃涓�</li>
    <li>map(key,value)瀵硅薄鏁版嵁</li>
    </ul>
    htmls瀛楃涓叉槸鍘熷鐨勬暟鎹紝閫氳繃{@link #parse}鏂规硶瑙ｆ瀽銆�
   </p>
   <pre>
    妯℃澘鏂囨硶鎻忚堪锛�
    
    鍏ュ彛锛�
    parse(entry, dataMap)
    鍙傛暟锛�
    dataMap: {  key : value, 鈥
    key : JavaScript鏍囪瘑绗�
    value:entry
    entry: html 鏂囨湰锛�.keyFromDataMap锛宬eyFromTemplates
    html鏂囨湰: &lt;tag attribute="{.keyFromDataMap}"&gt;{keyFromTemplates}&lt;/tag&gt;, IfTest, ...
    IfTest : [?keyFromDataMap?html鏂囨湰?],鍙栧弽锛歔?!keyFromDataMap?html鏂囨湰?]
    Templates : {key : html鏂囨湰}
    
    渚嬪瓙锛�
    var map = { name:'Xweibo' };
    var templates = {
         Header : '&lt;h2&gt;{.name}&lt;/h2&gt;',
         Box:'{Header}&lt;div&gt;鍚嶇О鏄瘂.name}&lt;/div&gt;'
    };
    alert( parse('Box', map) );
    缁撴灉鏄細&lt;h2&gt;Xweibo&lt;/h2&gt;&lt;div&gt;鍚嶇О鏄疿weibo&lt;/div&gt;
    </pre>
  * @singleton
  */


var tplRegIf = /\[\?(!?)\.([\w_$]+?)\?([\S\s]*?)\?\]/g,
    tplReg   = /\{(\.?[\w_$]+)\}/g;

var T = X.ax.Tpl = {
    // 妯℃澘html缂撳瓨
    tpls:{},
    
/**
 * 閿煡鎵捐繃绋嬶細妯℃澘 --> 瀵硅薄 --> 妯℃澘
 <pre><code>
    var map = { name:'Xweibo' };
    var templates = {
         Header : 鈥�&lt;h2&gt;{.name}&lt;/h2&gt;',
         Box:'{Header}&lt;div&gt;鍚嶇О鏄瘂.name}&lt;/div&gt;'
    };
    alert( parse(鈥楤ox', map) );
    缁撴灉鏄細&lt;h2&gt;Xweibo&lt;/h2&gt;&lt;div&gt;鍚嶇О鏄疿weibo&lt;/div&gt;
 </code></pre>
 * @param {String} htmls 妯℃澘瀛楃涓�
 * @param {Object} map 鍊奸敭瀵�
 */    
    parse : function(htmls, map){
        if(!map)
            map = {};
        if(htmls.charAt(0) !== '<'){
            var tmp = T.tpls[htmls];
            if(tmp) 
                htmls = tmp;
        }
        
        // [?test?<img src="{src}">],褰搕est缃€兼椂搴旂敤鍐呭閮ㄤ唤
        // example : [?right?output value {right}?]the left
        htmls = htmls.replace(tplRegIf, function(s, s0, s1, s2){
            if(s0 === '!')
                return !map[s1] ? s2:'';
    
            return map[s1] === undefined ? '' : s2;
        });
        
        return htmls.replace(tplReg, function(s, k){
            var v = k.charAt(0) === '.' ? map[k.substr(1)] : T.tpls[k];
            if(v === undefined || v === null)
                return '';
                
            // html text
            if(v.toString().charAt(0) === '<')
                return T.parse(v, map);
            
            // key of Tpl?
            if(T.tpls[v])
                return T.parse(T.tpls[v], map);
                
            return v;
        });
    },
   /**
    * 鏍规嵁html妯℃澘鍒涘缓HTML鍏冪礌
    <pre>
        <code>
            var iframeElement = forNode(
              '&lt;{tag} class="{cls}" frameBorder="no" scrolling="auto" hideFocus=""&gt;&lt;/iframe&gt;',
              {tag:'iframe', cls:'ui-frame'}
            );
        </code>
    </pre>
    * @param {String} htmls
    * @param {Object|Array} map
    * @return {HTMLElement}
    */
    forNode : function(htmls, map){
        if(map)
            htmls = this.parse(htmls, map);
        return $(htmls).get(0);
    },
/**
 *  鏍规嵁妯℃澘鍚嶇О鑾峰緱妯℃澘瀛楃涓层€�
 * @param {String} templateName
 * @return {String}
 */
    get : function(type){
        return this.tpls[type];
    },
    /**
     * 娉ㄥ唽HTML妯℃澘
     * @param {Object} htmlTemplateMap
     */
    reg : function(map){
        $.extend(this.tpls, map);
    }
};


/**
 * @class Xwb.ax.Cache
 * 缂撳瓨绫伙紝鍙互灏嗕竴浜涘父鐢ㄩ噸鐢ㄧ殑鏁版嵁绾冲叆鏈被绠＄悊銆�<br/>
 * 鍐呴儴鏁版嵁缁撴瀯涓�:<br>
 * <pre>
 * // 鏁版嵁鐩存帴鏀惧湪绫讳笅锛屽悕绉颁笉瑕佷笌鏂规硶鍐茬獊浜嗗摝锛�
 * Cache[key] = [dataObjectArray||null, generator];
 * dataObjectArray[0] = 棰勭暀浣�,淇濆瓨璇ey鏁版嵁鏈€澶х紦瀛樹釜鏁�, 榛樿涓�3.
 * generator = 鐢熸垚鏁版嵁鍥炶皟
 * </pre>
 * @singleton
 */
var Cache = X.ax.Cache = {

    /**@cfg {Number} MAX_ITEM_SIZE 瀵规瘡涓被鍒缃殑鏈€澶х紦瀛樻暟閲忥紝榛樿涓�3.*/
    MAX_ITEM_SIZE: 3,

/**
 * 娉ㄥ唽鏁版嵁浜х敓鏂瑰紡鍥炶皟鍑芥暟,鍙噸澶嶈祴鍊�,鍑芥暟杩斿洖閿搴旂殑鏁版嵁.
 * @param {Object} key
 * @param {Function} callback
 * @param {Number} [max] 缂撳瓨璇ユ暟鎹殑鏈€澶у€�
 */
    reg: function(k, callback, max) {
       if(!this[k])
        this[k] = [null, callback];
       else this[k][1] = callback;

       if(max !== undefined)
        this.sizeFor(k, max);
    },
/**
 * 鏍规嵁閿幏寰楀搴旂殑缂撳瓨鏁版嵁.
 * @param {String} key
 * @return {Object}
 */
    get: function(k) {
        var a = this[k];
        if(a === undefined)
            return null;
        var b = a[1];
        a = a[0];

        if(a === null){
          return b();
        }
        //0浣嶉鐣�
        if(a.length > 1)
            return a.pop();
        if(b)
            return b();

        return null;
    },
/**
 * 缂撳瓨閿€兼暟鎹�.
 * @param {Object} key
 * @param {Object} value
 */
    put: function(k, v) {
        var a = this[k];
        if(!a){
            this[k] = a = [[this.MAX_ITEM_SIZE, v]];
            return;
        }
        var c = a[0];
        if(!c)
          a[0] = c = [this.MAX_ITEM_SIZE];

        if (c.length - 1 >= c[0]) {
            return ;
        }

        c.push(v);
    },

/**
 * 绉婚櫎缂撳瓨.
 * @param {Object} key 閿€�
 */
    remove : function(k){
      var a = this[k];
      if(a){
        delete this[k];
      }
    },
/**
 * 璁剧疆鎸囧畾閿€肩紦瀛樻暟鎹殑鏈€澶у€�.
 * @param {Object} key
 * @param {Number} max
 */
    sizeFor : function( k, max ) {
        var a = this[k];
        if(!a)
          this[k] = a = [[]];
        if(!a[0])
          a[0] = [];
        a[0][0] = max;
    }
};

/**
 * 缂撳瓨DIV缁撶偣.
 * <pre><code>
   var divNode = Xwb.Cache.get('div');
 * </code></pre>
 * @property div
 * @type DOMElement
 */
Cache.reg('div', function() {
    return doc.createElement('DIV');
});


/**
 * @class Xwb
 */

/**
 * 鏍规嵁閰嶇疆鍚嶇О鑾峰緱鍏ㄥ眬閰嶇疆鍊�
 * @param {String} key
 * @return {Object}
 * @type Function
 */
X.getCfg     = function(key){
	return X.cfg && X.cfg[key]; 
};

/**
 * 杩斿洖绔欑偣鐢ㄦ埛ID
 * @return {String} siteId
 * @type Function
 */
X.getSiteUid = function(){ return parseInt(X.getCfg('siteUid'));};

/**
 * 杩斿洖鐧诲綍鐢ㄦ埛ID
 * @return {String} userId
 * @type Function
 */
X.getUid     = function(){
	var uid = X.getCfg('uid'); 
	return uid !== '0' && uid;
};

/**
 *  鏍规嵁寰崥ID杩斿洖缂撳瓨涓殑寰崥鏁版嵁
 * @type Function
 * @return {Object}
 */
X.getWb = function(id) {
	var wbList = X.getCfg('wbList');
	return id? wbList && wbList[id]: wbList;
};

/**
 *  缂撳瓨寰崥鏁版嵁鍒板叏灞€缂撳瓨
 * @param {String} wid
 * @param {Object} wbData
 * @type Function
 */
X.setWb = function(id, data) {
	X.cfg.wbList && (X.cfg.wbList[id] = data);
};

//
//  浠ョ缉鐣ュ悕娉ㄥ唽绫�
//
$.extend(X, {
	
	_cls : {},
	/**
	 * 涓烘寚瀹氱被鎴栧璞℃敞鍐屼竴涓煭鍛藉悕鏍囪瘑锛屼究浜庡湪鍏跺畠鍦版柟閫氳繃{@link use}鏂规硶杩斿洖璇ョ被瀹炰緥鎴栬瀵硅薄銆�<br/>
	 * @param {String} shortcut 鏍囪瘑锛屽嵆璇ョ被鐨勭缉鐣ュ悕
	 * @param {Function|Object} target 绫绘垨瀵硅薄
	 * @param {Boolean} [override] 榛樿濡傛灉宸插瓨鍦ㄥ悓鍚嶅璞′細鎶涘嚭寮傚父锛屼絾閫氳繃璁剧疆鏈爣璁板彲寮哄埗閲嶅畾涔夌被
	 * @return clazz
	 */
	reg : function(n, cls, override){
		if(this._cls[n] !== undefined && !override){
			if(__debug) console.trace();
			throw '宸插畾涔夌被' + n;
		}
		this._cls[n] = cls;
		return cls;
	},
	
	/**
	 * 浣跨敤鐭悕绫伙紝鍙互灏嗘煇涓被鎴栫被瀹炰緥閫氳繃{@link #reg}鏂规硶鏀惧埌Xwb瀵硅薄缂撳瓨涓紝
	 * 鍦ㄤ换浣曞湴鏂硅皟鐢ㄦ湰鏂规硶鑾峰緱宸茬紦瀛樺璞°€�
	 * @param {Object} name 鏍规嵁閿煡鎵剧紦瀛樺璞�
	 * @param {Object} [config] 鍋囧缂撳瓨鍊间负涓€涓被(Function)锛屼互config涓哄弬鏁板疄渚嬪寲璇ョ被
	 * @return {Object} 濡傛灉缂撳瓨鐨勬槸涓€涓被(Function)锛岃繑鍥炶绫诲疄渚嬶紝鍚﹀垯鐩存帴杩斿洖缂撳瓨瀵硅薄
	 */
	use : function(n){
		// instance( type, config )
		var cls = this._cls[n];
		if (cls) {
		    // object only
		    if(typeof cls === 'object')
		        return cls;
		    // instance class
		    var cfg = arguments[1];
		    if( typeof cfg === 'function' )
		        return new cls(cfg(cls.prototype));
		    return new cls(cfg);
		}
		return null;
	},
	
/**
 * 妫€娴嬪綋鍓嶉〉闈㈡槸鍚︿负鎸囧畾妯″潡鐨勯〉闈€�
 * 閫氳繃BigPipe瀹炵幇妯″潡鍔犺浇鍚庤鏂规硶宸茶搴熷純銆�
 * @param {String} page
 * @param {Boolean}
 */
	isModule : function(name){
	    return this.getModule() === name;
	},
	
/**
 * 杩斿洖褰撳墠椤甸潰妯″潡鍚嶇О
 * @return {String}
 */
	getModule : function(){
	    if(this._mod === undefined)
	        this._mod = this.getCfg('page')||'';
	    return this._mod;
	}
});

/**
 * @class Xwb.ax
 */

/**
 * @class Xwb.ax.Uploader
 * 寮傛鏂囦欢涓婁紶绫伙紝璇ョ被閫氳繃绫讳技JSONP鏂瑰紡鐨勯殣钘廔FRAME涓婁紶鏂囦欢銆侷FRAME鐢辩被鑷姩鍒涘缓銆�
 <pre><code>
    X.use('Uploader', {
       form:$('#fileForm'),
       // 鎴愬姛鍚庡洖璋� 
       onload:function(e){
            if(e.isOk()){
                alert('uploaded!');
            }else {
                alert(e.getCode());
            }
       }
    });
    </code></pre>
 * @constructor
 * @param {Object} config 閰嶇疆淇℃伅
 */

/**
 * @cfg {String} [action] 濡傛灉action鏈厤缃紝鍒欎粠form閲屾彁鍙朼ction锛屽鏋渇orm鏈疆璁綼ction锛岄粯璁ction涓篨wb.request.apiUrl('action', 'upload_pic')
 */
/**
 * @cfg {Function} onload 涓婁紶鍚庣殑鍥炶皟鍑芥暟锛屽弬鏁颁负Xwb.ax.ResponseDefinition瀹炰緥
 */
X.ax.Uploader = X.reg('Uploader', function(cfg){
    this.init(cfg);
});

X.ax.Uploader.prototype = {
    
    init : function( cfg ){
        $.extend( this, cfg );
        
        var form = this.form;
        var formEl = this.formEl = $(form)[0];
        var name = 'xwb_upload_frame_' + Util.uniqueId();
        this.iframe = T.forNode('<iframe src="about:blank" style="display:none;" id="'+name+'" name="'+name+'"></iframe>');
        
        //娣诲姞callback鍙傛暟 ?? 涓轰粈涔堣繕瑕佹坊鍔�
        $('<input type="hidden" name="callback"/>').appendTo(form);
        
        $(this.iframe).appendTo( doc.body );
        
        formEl.target = name;
        
        if(!this.action)
            this.action = formEl.action || X.request.apiUrl('action', 'upload_pic');
    },
    
/**
 * 鍙噸缃產ction鍊�
 * @param {String} action
 */
    setAction : function(action){
        this.action = action;
        return this;
    },
/**
 * 鏄惁鍔犺浇涓�
 */
    isLoading : function(){
        return !!this.jsonpFn;
    },
    
/**
 * @cfg {Function} beforeUpload 寮€濮嬩笂浼犲墠璋冪敤锛岃繑鍥瀎alse鍙栨秷涓婁紶锛屽彲瀹炵幇璇ユ柟娉曚互鍦ㄤ笂浼犲墠妫€娴嬭〃鍗曘€傚弬鏁颁负 beforeUpload(jqForm)銆�
 */
    beforeUpload : $.noop,
    
/**
 *  寮€濮嬩笂浼�
 * @param {Function} [callback]
 */
    upload : function( callback ){
        if(this.beforeUpload(this.form) !== false){
            if(this.isLoading())
                this.abort();
            
            var self = this,
                fn = this.jsonpFn = 'jsonp' + new Date().getTime();
            
            window[fn] = function(){
                window[fn] = null;
                delete self.jsonpFn;
                var e = X.request.parseProtocol(arguments[0]);
                (callback||self.onload).call(self, e);
                // fix a bug in IE7
                delete self.jsonpFn;
            };
            
            this.formEl.action = Util.appendParam(this.action, {callback:'parent.'+fn, '_':Util.uniqueId()});
            this.formEl.submit();
        }
    },

    onload : $.noop,
    
    abort : function(){
        if(this.isLoading()){
            var fn = this.jsonpFn;
            window[fn] = function(){
                window[fn] = null;
            };
        }
    }
};

})(Xwb, $);
(function(X, $){

var Util = X.util;
var undefined;
var T = X.ax.Tpl;
var doc = document;
    
// hidden style class
var hidCls   = 'hidden';
var jqWin = $(window);
var jqDoc = $(doc);
/**
 * @class Xwb.ui
 */
var ui   = X.ui = {
    Base : Util.create()
};

var Base = ui.Base;

/**
 * UI鍩虹被锛屾彁渚涘浣曠敓鎴愬拰鎺у埗UI鍐匟TML鍏冪礌鐨勫熀鏈柟娉曘€�<br/>
 * 鎯充簡瑙ｇ敓鎴怳I瑙嗗浘鐨凥TML濡備綍缁勭粐锛屽弬瑙亄@link Xwb.util.Tpl}绫诲拰{@link #view}灞炴€с€�<br/>
 * @class Xwb.ui.Base
 * @constructor
 * @param {Object} config  璇ラ厤缃俊鎭皢琚畬鍏ㄥ湴澶嶅埗鍒板綋鍓嶅疄渚嬩腑锛屽苟浼氳鐩栫浉鍚屽睘鎬у悕鐨勯粯璁ゅ€笺€�
 */
/**
 * @cfg {Boolean} closeable
 * 鎸囩ず鎺т欢鏄惁鍙叧闂紝鍙叧闂殑鎺т欢涓婂瓨鍦ㄤ竴涓獅@link #clsNode}缁撶偣锛�
 * 褰撶偣鍑昏鍏冪礌鏃跺叧闂帶浠讹紝杩欒繃绋嬬敱鎺т欢瀹炵幇銆�
 */

/**
 * @cfg {String} titleNode 鏍囬缁撶偣閫夋嫨鍣紝濡傛灉鎺т欢瀛樺湪鈥滄爣棰樷€濓紝
 * 璋冪敤{@link #setTitle}鏂规硶鏃朵細鐢ㄦ爣棰樼粨鐐归€夋嫨鍣ㄨ幏寰楁爣棰樼粨鐐癸紝
 * 骞跺湪璇ョ粨鐐逛笂鎵撳嵃鏍囬銆傞粯璁ゅ€间负"#xwb_title"
 */

/**
 * @cfg {String} clsNode
 * 鐢ㄨ閫夋嫨鍣ㄦ煡鎵惧叧闂簨浠惰Е鍙戠粨鐐广€傞粯璁ゅ€间负"#xwb_cls"
 */
 
/**
 * @cfg {Function} onViewReady
 * 杩欐槸涓帴鍙ｆ柟娉曪紝褰撴帶浠秇tml鍏冪礌鐢熸垚鍚庡洖璋冭鎺ュ彛锛屽弬鏁颁紶閫掓帶浠秇tml鍏冪礌銆�
 
* <pre><code>
    {
        onViewReady : function(viewElement){
            alert(viewElement.tagName);
        }
    }
 </code></pre>

 */
 
/**
 * @cfg {String} title 瀛樺湪鏍囬鍊兼椂锛屾帶浠跺垵濮嬪寲鏃朵細璋冪敤{@link #setTitle}鏂规硶杈撳嚭鏍囬
 */

/**
 * @cfg {String|HTMLElement|HtmlTemplate} view
 * 鎸囧畾鎺т欢鐢熸垚html瑙嗗浘鐨勬ā鏉匡紝瀹冨彲浠ユ槸涓€娈礖TML瀛楃涓诧紝涔熷彲浠ユ槸HTML妯℃澘鍚嶇О锛屼篃鍙负涓€涓猦tml鍏冪礌銆�<br/>
 * 濡傛灉view鏉ヨ嚜瀛楃涓叉ā鏉匡紝灏嗚皟鐢▄@link Xwb.util.Tpl.parse}鏂规硶瑙ｆ瀽妯℃澘銆�<br/>
 * 濡傛灉鏈寚瀹歿@link #tplData}鏁版嵁锛屽皢鎺т欢鑷韩鏁版嵁浣滀负妯℃澘瑙ｆ瀽鐨刱ey->value鏁版嵁銆�
 */
 
/**
 * @cfg {Boolean} actionMgr
 * 鎸囩ず鏄惁鍚敤{@link Xwb.ax.ActionMgr}鍔ㄤ綔澶勭悊锛�
 * 鍒濆鍖栧悗actionMgr灞炴€ф寚鍚戝疄渚嬪寲鐨剓@link Xwb.ax.ActionMgr}绫诲疄渚嬨€�
 */
/**
 * @cfg {Function} onactiontrig 鎺ュ彛鏂规硶锛屽紑濮媨@link #actionMgr}鍚庯紝鍙疄鐜拌鎺ュ彛缁熶竴澶勭悊鏉ヨ嚜{@link #actionMgr}瀹炰緥鐨勫悇绉嶅姩浣�
 
* <pre><code>
    {
        actionMgr : true,
        onactiontrig : function(act){
            switch(act.data.e){
                case 'sd' :
                // do something
                break;
                // ...
            }
        }
    }
 </code></pre>

 */
 
/**
 * @cfg {Boolean} destroyOnClose 褰撳叧闂帶浠舵椂锛�
 * 鏄惁閿€姣�({@link #destroy})鎺т欢锛屽惁鍒欏彧闅愯棌鎺т欢銆傞粯璁ゅ€糵alse锛屽彧闅愯棌銆�
 */

/**
 * @cfg {Function} onclose 鎺ュ彛鏂规硶锛屽叧闂帶浠�({@link #close})鏃讹紝灏嗗洖璋冭鏂规硶锛屽鏋滄柟娉曡繑鍥瀎alse鍊煎彇娑堝叧闂�
 */

/**
 * @cfg {Boolean} contexted 鎸囩ず鎺т欢鏄剧ず鍚庢槸鍚﹀叿鏈夆€滃唴澶栧垏鎹⑩€濈被鑿滃崟鐨勬晥鏋溿€傗€滃唴澶栧垏鎹⑩€濆嵆鐐瑰嚮鎺т欢鍐呴儴鍖哄煙鏃朵竴鍒囨甯革紝浣嗙偣鍑婚潪鎺т欢鍖哄煙鏃跺氨鍏抽棴鎺т欢銆�
 */

/**
 * @cfg {Boolean} hidden 鍒濆鍖栨椂鏄惁鏄剧ず鎺т欢锛岄粯璁ゆ樉绀�
 */

/**
 * @cfg {Boolean} autoRender
 * 鎸囩ず鏄惁鍦ㄧ被鍒濆鍖栨椂鐢熸垚瑙嗗浘鍏冪礌锛岄粯璁alse锛屾寜闇€鎵嶇敓鎴愩€�<br/>
 * 濡傛灉璇ラ」涓篺alse锛屽嵆鎺т欢鍦ㄥ垵濮嬪寲鍚庯紝骞舵湭绔嬪嵆鐢熸垚瑙嗗浘缁撶偣锛岃Е鍙憑@link #onViewReady}鍥炶皟锛�
 * 鑰屾槸鐩磋嚦鐩存帴鎴栭棿鎺ヨ皟鐢▄@link #getView}鏂规硶鍚庣敓鎴愶紝鍗虫寜闇€鏃舵墠鐢熸垚HTML瑙嗗浘銆�<br/>
 * 濡傛灉鏈敓鎴愯鍥剧粨鐐癸紝{@link #onViewReady}鏂规硶鏄笉浼氳Е鍙戠殑锛�
 * 浣嗘湁鏃跺€欓渶瑕佹彁鍓嶇敓鎴愶紝姝ゆ椂灏卞彲浠ヨ缃椤逛负true锛屽嵆鍦ㄥ垵濮嬪寲鏃跺氨鐢熸垚HTML瑙嗗浘锛�
 * 浠庤€岃Е鍙憑@link #onViewReady}銆�
 */

/**
 * @property cacheId
 * 鎺т欢鍏ㄥ眬鍞竴ID
 * @type String
 */
 
// 璇ユ柟娉曚細瑕嗙洊鍘熸湁getView()褰搗iew缁撶偣鐢熸垚鍚庛€�
function getReadyView(){
    return this.view;
}

ui.Base.prototype = {
        
        autoRender : false,
        
        titleNode : '#xwb_title',
        
        // 绫诲垵濮嬪寲鍏ュ彛
        init : function(opt){
            this.cacheId = 'c' + Util.uniqueId();
            opt && $.extend(this, opt);
            // UI鍒濆鍖栧叆鍙�
            this.initUI();
            
            if(this.autoRender)
                this.getView();
        },
        
        // 鎺ュ彛鏂规硶
        initUI : $.noop,
        
        clsNode : '#xwb_cls',
        
        // 缁戝畾鍏抽棴浜嬩欢
        initClsBehave : function(cls){
            this.jq(this.clsNode).click(Util.bind(this.onClsBtnClick, this));
            this.setCloseable(cls);
        },
        
        /**
         *  璁剧疆鏄惁鍙叧闂�
         * @param {Boolean} closable
         */
        setCloseable : function(cls){
            $(this.clsNode).cssDisplay(cls);
            this.closeable = cls;
        },
        
        onClsBtnClick : function(){
            this.close(true);
            return false;
        },
/**
 * 鍏抽棴鎺т欢锛屽叧闂墠瑙﹀彂{@link #onclose}鍥炶皟锛屽鏋滃紑濮媨@link #destroyOnClose}锛屽叧闂悗閿€姣�({@link #destroy})璇ユ帶浠�
 */
        close : function(){
            if(!this.onclose || this.onclose() !== false){
                if(this.destroyOnClose)
                    this.destroy();
                else this.display(false);
            }
        },
/**
 *  @cfg {Object} tplData 榛樿鎺т欢鐨勬ā鏉挎暟鎹潵鑷帶浠惰嚜韬紝涔熷彲浠ラ€氳繃璇ュ睘鎬ч噸瀹氫箟妯℃澘鏁版嵁
 */
        tplData : false,
        
/**
 * 鐢県tml鍒涘缓鎴栭€夋嫨鍣ㄥ畾浣峌I鍥捐缁撶偣
 * @private
 */
        createView : function(){
            var v = this.view;
            if(typeof v === 'string'){
                v = this.view = T.forNode(T.get(v)||v, this.tplData || this, true);
            }else this.view = v = doc.createElement('DIV');
            return $(v)[0];
        },

/**
 * 瀛愮被搴旈噸杞借鏂规硶鏇夸唬onViewReady
 * @private
 */
        innerViewReady : $.noop,

/**
 * 璁剧疆鎺т欢鏍囬銆�
 * 鏌ユ壘鏍囬鎵€鍦ㄧ粨鐐瑰弬瑙亄@link #titleNode}閰嶇疆銆�
 * @param {String} title
 * @return this
 */
        setTitle : function(tle){
            this.jq(this.titleNode).html(tle);
            return this;
        },
        
    /**
     * 鑾峰緱绐楀彛瑙嗗浘DOM缁撶偣锛屽鏋滅粨鐐规湭鍒涘缓锛岀珛鍗冲垱寤哄苟杩斿洖缁撶偣锛屽垱寤哄悗瑙﹀彂{@link #onViewReady}鎺ュ彛鍥炶皟銆�
     * 瀛愮被涓嶈兘閲嶅啓璇ユ柟娉曘€�
     * @return {HTMLElement}
     */ 
        getView : function(){
          var v = this.view;
          
          // 鏈垱寤�
          if(!v || !v.tagName)
            v = this.createView();

          // 閲嶅啓锛屼笅杞借皟鐢ㄦ椂灏辩洿鎺ヨ繑鍥炵粨鐐�
          this.getView = getReadyView;
          
          if(this.appendTo){
              $(this.appendTo).append(v);
              delete this.appendTo;
          }
          
          if(this.closeable !== undefined)
              this.initClsBehave(this.closeable);

          if(this.actionMgr){
              this.actionMgr = X.use('ActionMgr', this.actionMgr);
              this.actionMgr.bind( v );
              if(this.onactiontrig){
                  var self = this;
                  this.actionMgr.addFilter(function(e){
                      return self.onactiontrig(e);
                  });
              }
          }
          
          // interval method
          this.innerViewReady(v);
          
          // config method锛屽洖璋冩帴鍙ｆ柟娉�
          this.onViewReady && this.onViewReady(v);
          
          // 灏哾isplay鏀惧埌onViewReady涔嬪悗锛�
          // 姝ｅ父鎵ц
          if(this.hidden !== undefined){
              var tmp = this.hidden;
              this.hidden = undefined;
              this.display(!tmp);
          }
          return v;
        },
/**
 * 灏嗗厓绱犵粦瀹氬埌鏈被锛屽揩閫熷埄鏈被鏂规硶澶勭悊鍏冪礌銆�
 * @param {HTMLElement|jQuery} view
 * @return this
 */
        fly : function(view){
            this.view = (view && view[0]) || view;
            return this;
        },

/**
 * @cfg {Function} beforeShow 鎺ュ彛鏂规硶锛屽湪鎺т欢鏄剧ず鍓嶅洖璋冿紝濡傛灉瀛愮被瀹炵幇璇ユ柟娉曪紝璇风‘淇濊皟鐢ㄥ洖鐖剁被鏂规硶銆�<br/>
 * 鍥炶皟鏂规硶鏃讹紝瑙嗗浘鐘舵€佸凡鏄� visibility:'hidden', display:true锛屾墍浠ュ鏋滆鍥惧凡鍦―OM涓婏紝鎺т欢鍦―OM娴佷笂鍗犱綅锛�
 * 鍙互姝ｇ‘鑾峰緱鎺т欢瀹介珮鍜屼綅缃瓑绛変俊鎭€�
 */
        beforeShow : $.noop,
/**
 * @cfg {Function} afterShow 鎺ュ彛鏂规硶锛屾帶浠舵樉绀哄悗鍥炶皟锛屽鏋滃瓙绫诲疄鐜拌鏂规硶锛岃纭繚璋冪敤鍥炵埗绫绘柟娉�
 */
        afterShow  : $.noop,

/**
 * @cfg {Function} beforeHide 鎺ュ彛鏂规硶锛屾帶浠堕殣钘忓墠鍥炶皟锛屽鏋滃瓙绫诲疄鐜拌鏂规硶锛岃纭繚璋冪敤鍥炵埗绫绘柟娉�
 */
        beforeHide : $.noop,

/**
 * @cfg {Function} afterHide 鎺ュ彛鏂规硶锛屾帶浠堕殣钘忓悗鍥炶皟锛屽鏋滃瓙绫诲疄鐜拌鏂规硶锛岃纭繚璋冪敤鍥炵埗绫绘柟娉�
 */        
        afterHide : $.noop,

/**
 * 浠ラ€氱敤JavaScript鍙橀噺鍛藉悕椋庢牸鐢熸垚ID鍏冪礌瀵瑰簲鐨刯q瀵硅薄銆�
 * 濡� 
* <pre><code>
     this.jqExtra('inputor', 'title');
     alert( this.jqInputor );
     alert( this.jqTitle );
 </code></pre>
 * @param {Array} args 鍙傛暟鍒楄〃锛屽畠骞朵笉鏄竴涓暟缁勶紝鑰屾槸鍙彉鍙傛暟鍒楄〃锛屽 jqExtra('a','b','c',...)
 * @return this
 */
        jqExtra : function(ids){
            for(var args = arguments,i=0,len=args.length;i<len;i++){
                var k    = args[i];
                var jqEl = this.jq('#'+k);
                if( jqEl ){
                    // 棣栧瓧姣嶅ぇ鍐�
                    k = k.charAt(0).toUpperCase() + k.substring(1);
                    this['jq'+k] = jqEl;
                }
            }
            return this;
        },
/**@cfg {Boolean} contextable 璁剧疆鏄惁寮€鍚樉绀哄悗鐐瑰嚮UI鍖哄煙澶栧悗闅愯棌UI*/
/**
 * 鏄剧ず/闅愯棌鎴栬幏寰桿I鐨勬樉绀烘垨闅愯棌灞炴€�
 * @param {Object} show
 * @return this
 */
        display : function(b){
            var j = this.jq();

            if(b === undefined)
                return !j.hasClass(hidCls);
            b = !b;
            if(this.hidden !== b){
                if(!b) {
                    this.hidden = b;
                    j.css('visibility', 'hidden').removeClass(hidCls);
                    this.beforeShow();
                    j.css('visibility', '');
                    if( this.contextable && !this.contexted)
                        this.setContexted(true);

                    this.afterShow();
                }else {
                    if(this.beforeHide() !== false){
                        this.hidden = b;
                        j.addClass(hidCls);
                        this.afterHide && this.afterHide();
                        // release contexted on hide
                        if(this.contexted)
                           this.setContexted(false);
                    }
                }
            }
			return this;
        },
        
        onContextRelease : function(){
            this.display(false);    
        },
/**
 * 鎺т欢瑙嗗浘杩藉姞鍒板湪鎸囧畾jQuery瀵硅薄涓�
 * @param {jQuery} jQuery瀵硅薄
 */
        appendAt : function(a){
            $(a).append(this.getView());
            return this;
        },
        
    /**
     * 鏄惁鍖呭惈鏌愬厓绱�
     * @param {Xwb.ui.Base|HTMLElement} target
     * @param {Number} [depth] 鎼滅储娣卞害
     */
        ancestorOf :function(a, depth){
          a = a.view || a;
          return Util.ancestorOf(this.view, a, depth);
        },
/**
 * 鑾峰緱鎺т欢鎴栨帶浠跺瓙鍏冪礌鐨刯Query瀵硅薄
 * @param {jQuery} selector jQuery閫夋嫨鍣紝鏈缃椂杩斿洖鎺т欢瑙嗗浘鐨刯Query瀵硅薄
 * @return {jQuery}
 */
        jq : function(selector){
            return selector === undefined ? $(this.getView()) : $(this.getView()).find(selector);
        },
/**
 * 閿€姣佹帶浠讹紝灏嗚鍥句粠DOM涓婄Щ闄�
 */
        destroy : function(){
            this.display(false);
            this.jq().remove();
        },

/**
 * 娣诲姞DOM浜嬩欢澶勭悊锛屽ぇ閮ㄤ唤浜嬩欢鍙洿鎺ヨ皟鐢╦Query鏂规硶澶勭悊锛屼絾mousedown鏄緥澶栫殑锛屽鏋滄兂鐩戝惉mousedown浜嬩欢锛岃璋冪敤鏈柟娉曡繘琛岀粦瀹氥€�<br/>
 * 涓轰粈涔坢ousedown浜嬩欢鏄緥澶栫殑锛熷弬瑙亄@link Xwb.ax.contextMgr}
 * @param {String} eventName
 * @param {Function} handler
 * @param {String|HTMLElement} [childElementToBind] 濡傛灉鎸囧畾璇ラ」锛屼簨浠跺皢缁戝畾鍒拌椤瑰厓绱犱笂
 */
        domEvent : function(evt, fn, child){
            if(evt === 'mousedown'){
                var comp = this;
                var wrapper = function(e){
    	           	if(!comp.contexted)
    					X.use('contextMgr').releaseAll(e);
    			    return fn.apply(comp, arguments);
                };
                
                if(!this._mousedownFns)
                    this._mousedownFns = {};
                this._mousedownFns[fn] = wrapper;
                
                this.jq(child).bind(evt, wrapper);
            }else this.jq(child).bind(evt, fn);
        },
/***
 * 绉婚櫎鐢眥@link #domEvent}缁戝畾鐨勪簨浠�
 * @param {String} eventName
 * @param {Function} handler
 * @param {String|HTMLElement} [childElementToBind]
 */
        unDomEvent : function(evt, fn, child){
            if(evt === 'mousedown'){
                var wrapper = this._mousedownFns[fn];
                this.jq(child).unbind(evt, wrapper);
                delete this._mousedownFns[fn];
            }else this.jq(child).unbind(evt, fn);
        },
     
        /**
         * 娣诲姞涓婁笅鏂囧垏鎹㈡晥鏋�,褰撶偣鍑绘帶浠跺尯鍩熶互澶栫殑鍦版柟鏃堕殣钘忔帶浠躲€�
         * @see #onContextRelease
         * @return {Xwb.ui.Base} this
         */
        setContexted : function(set){
        	if(this.contexted !== set)
        		set ? X.use('contextMgr').context(this) : 
        		      X.use('contextMgr').release(this);
        	return this;
        },
        /**
         * 鎵归噺瀵瑰瓙鍏冪礌搴旂敤innerHTML
         * @param {Object} childSelectorHtmlMap 缁撶偣涓簕childSelector:strHtml}
         
        * <pre><code>
            var data = {
                '#text_a':'瀛楃涓睞',
                '#text_b':'瀛楃涓睟',
                '#text_c':'瀛楃涓睠'
            };
         </code></pre>

         */
        templ : function(obj){
            for(var selector in obj){
                this.jq(selector).html(obj[selector]);
            }
            return this;
        },
		
		offset : function(){
			if(arguments.length){
				this.jq().css(arguments[0]);
				return this;
			}
			return this.jq().offset();
		},
		
    /**
     * 寰楀埌鐩稿鐩爣鍏冪礌鐨勫亸绉婚噺锛屽亣濡傚弬鏁颁负window鍏冪礌锛屽緱鍒扮浉瀵瑰鎴峰尯瑙嗗浘鍋忕Щ閲�
     * @param {DOMElement|jQuery} offsetToTarget
     * @return [offsetX, offsetY]
     */
        offsetsTo : function(tar){
            var tar = tar[0]||tar;
            var e;
            if(tar == window)
                e = {left:jqDoc.scrollLeft(),top:jqDoc.scrollTop()};
            else e = $(tar).offset();
            var o = this.jq().offset();
            return [o.left-e.left,o.top-e.top];
        },

/**
 * 婊氬姩鎺т欢鍒版寚瀹氳鍥�
 * @param {DOMElement|Xwb.ui.Base|jQuery} ct 鎸囧畾婊氬姩鍒拌鍥剧殑缁撶偣
 * @param {Boolean} hscroll 鏄惁姘村钩婊氬姩,榛樿鍙瀭鐩存粴鍔�
 * @return {Object} this
 */
    scrollIntoView : function(ct, hscroll){
      var c = ct?ct.view||ct[0]||ct:doc.body;
        var off = this.getHiddenAreaOffsetVeti(c);
        if(off !== false)
          c.scrollTop = off;
        //c.scrollTop = c.scrollTop;

        if(hscroll){
          off = this.getHiddenAreaOffsetHori(ct);
          if(off !== false)
          c.scrollLeft = off;
        }

        return this;
    },
    
      /**
       * 妫€娴嬪厓绱犳槸鍚﹀湪鏌愪釜瀹瑰櫒鐨勫彲瑙佸尯鍩熷唴.
       * <br>濡傛灉鍦ㄥ彲瑙佸尯鍩熷唴,杩斿洖false,
       * 鍚﹀垯杩斿洖鍏冪礌鍋忕瀹瑰櫒鐨剆crollTop,鍒╃敤璇crollTop鍙皢瀹瑰櫒鍙鑼冨洿婊氬姩鍒板厓绱犲銆�
* <pre><code>
    html:
    &lt;body&gt;
        &lt;input type=&quot;button&quot; onclick=&quot;b.scrollIntoView($('#a')[0], true);&quot; value=&quot;
M&iuml;&Aacute;&quot; /&gt;&lt;span id=&quot;t&quot;&gt;&lt;/span&gt;
        &lt;div id=&quot;a&quot; style=&quot;position:absolute;height:200px;width:300px;background:red;left:100px;top:200px;overflow:auto;&quot;&gt;
            &lt;div id=&quot;b&quot; style=&quot;position:absolute;height:50px;width:200px;background:black;left:120px;top:210px;&quot;&gt;
            &lt;/div&gt;
            &lt;div style=&quot;position:absolute;height:800px;width:2000px;left:20px;top:420px;&quot;&gt;
            &lt;/div&gt;
        &lt;/div&gt;
    &lt;/body&gt;
    script:
    var b = new Xwb.ui.Base({view:$('#b')[0]});
    var t = $('#t');
    $('#a').scroll(function(){
        var r = b.getHiddenAreaOffsetVeti(this);
        var text = (r === false?'鍨傜洿鍙':'鍨傜洿閬僵');
        r = b.getHiddenAreaOffsetHori(this);
        text += (r === false?',姘村钩鍙':',姘村钩閬僵');
        t.text(text);
    });
 </code></pre>
       * @param {DOMElement|Xwb.ui.Base|jQuery} container
       * @return {Boolean}
       */
      getHiddenAreaOffsetVeti : function(ct){
            var c = ct.view||ct[0]||ct;
            var el = this.view;

            var o = this.offsetsTo(c),
                ct = parseInt(c.scrollTop, 10),
                //鐩稿ct鐨�'offsetTop'
                t = o[1] + ct,
                eh = el.offsetHeight,
                //鐩稿ct鐨�'offsetHeight'
                b = t+eh,
    
                ch = c.clientHeight,
                //scrollTop鑷冲鍣ㄥ彲瑙佸簳楂樺害
                cb = ct + ch;
            if(eh > ch || t < ct){
              return t;
            }else if(b > cb){
                b -= ch;
                if(ct != b){
                    return b;
                }
            }
    
        return false;
      },
      /**
       * 妫€娴嬪厓绱犳槸鍚﹀湪鏌愪釜瀹瑰櫒鐨勫彲瑙佸尯鍩熷唴.
       * <br>濡傛灉鍦ㄥ彲瑙佸尯鍩熷唴锛岃繑鍥瀎alse,
       * 鍚﹀垯杩斿洖鍏冪礌鍋忕瀹瑰櫒鐨剆crollLeft,鍒╃敤璇crollLeft鍙皢瀹瑰櫒鍙鑼冨洿婊氬姩鍒板厓绱犲銆�
* <pre><code>
    html:
    &lt;body&gt;
        &lt;input type=&quot;button&quot; onclick=&quot;b.scrollIntoView($('#a')[0], true);&quot; value=&quot;
M&iuml;&Aacute;&quot; /&gt;&lt;span id=&quot;t&quot;&gt;&lt;/span&gt;
        &lt;div id=&quot;a&quot; style=&quot;position:absolute;height:200px;width:300px;background:red;left:100px;top:200px;overflow:auto;&quot;&gt;
            &lt;div id=&quot;b&quot; style=&quot;position:absolute;height:50px;width:200px;background:black;left:120px;top:210px;&quot;&gt;
            &lt;/div&gt;
            &lt;div style=&quot;position:absolute;height:800px;width:2000px;left:20px;top:420px;&quot;&gt;
            &lt;/div&gt;
        &lt;/div&gt;
    &lt;/body&gt;
    script:
    var b = new Xwb.ui.Base({view:$('#b')[0]});
    var t = $('#t');
    $('#a').scroll(function(){
        var r = b.getHiddenAreaOffsetVeti(this);
        var text = (r === false?'鍨傜洿鍙':'鍨傜洿閬僵');
        r = b.getHiddenAreaOffsetHori(this);
        text += (r === false?',姘村钩鍙':',姘村钩閬僵');
        t.text(text);
    });
 </code></pre>
       * @param {DOMElement|CC.Base} [container]
       * @return {Boolean}
       */
      getHiddenAreaOffsetHori : function(ct){
        var c = ct.view||ct[0]||ct;
        var el = this.view;
            var cl = parseInt(c.scrollLeft, 10),
            o = this.offsetsTo(c),
                l = o[0] + cl,
                ew = el.offsetWidth,
                cw = c.clientWidth,
                r = l+ew,
                cr = cl + cw;
        if(ew > cw || l < cl){
            return l;
        }else if(r > cr){
            r -= cw;
            if(r != cl){
              return r;
             }
        }
        return false;
      },
      
      
    
		// pa:t,b,c, pb:l,c,r
		/**
		 * 瀹氫綅鎺т欢鍒版寚瀹氬厓绱犺竟娌裤€�<br/>
		 * 瀹氫綅鏂逛綅鍒嗕负涓婏紝涓紝涓嬶紝宸︼紝鍙筹紝鐢ㄥ瓧姣嶅垎鍒〃杈総,c,b,l,r銆�<br/>
		 * 璁＄畻椤哄簭涓猴細涓婁腑涓�
        * <pre><code>
            // 灏嗘帶浠跺畾浣嶄簬鍏冪礌鐨勪笂鏂瑰苟灞呬腑
            component.anchor(anchorElement, 'lc');
         </code></pre>
         * 渚嬪瓙鍙弬瑙乨emo/anchor.html
		 * @param {HTMLElement|jQuery} anchorElement 浣滀负閿氱偣鐨勫厓绱�
		 * @param {String} direction 瀵归綈鏂瑰悜锛屼笂涓嬶紝宸﹀彸涓庝腑闂寸殑缁撳悎锛屼笂涓嬶細t,b锛涘乏鍙砽,r锛屼腑闂碿
		 * @param {Function} [prehandler] 璁＄畻鍑哄榻愭暟鎹悗鑷冲簲鐢ㄦ暟鎹墠鍙€氳繃璇ユ柟娉曞鏁版嵁杩涜浜屾澶勭悊
		 * @param {Boolean} [restrictIntoView] 濡傛灉鎺т欢瓒呭嚭鍙鍖哄煙锛屾槸鍚﹁皟鏁村埌鍙鍖哄煙鍐�
		 */
		anchor : function(targetEl, pos, prehandler, intoView){
		    var jqT  = $(targetEl), jq = this.jq();
		    var toff = jqT.offset(),
		        tw   = jqT.width(),
		        th   = jqT.height(),
		        sw   = jq.width(),
		        sh   = jq.height();
		    var pa = pos.charAt(0), pb = pos.charAt(1);

		    var l = toff.left, t = toff.top;
		    switch(pa){
		        case 't' :
		            t-=sh;
		        break;
		        case 'b':
		            t+=th;
		        break;
		        case 'c':
		            t+= Math.floor((th-sh)/2);
		        break;
		    }
		    
		    switch(pb){
		        case 'c' :
		            l+= Math.floor((tw-sw)/2);
		        break;
		        case 'r':
		            l+=tw-sw;
		        break;
		    }
		    
		    if(prehandler){
		        var ret = ret = [l, t];
		        prehandler(ret, sw, sh);
		        l = ret[0];
		        t = ret[1];
		    }
		    // 闄愬埗瀹藉湪鍙鑼冨洿鍐�
		    if(intoView){
		        if(t<0) t=0;
		        else {
    		        var vph = jqWin.height();
    		        if(t+sh-jqDoc.scrollTop()>vph){
    		            t = vph-sh+jqDoc.scrollTop();
    		        }
		        }
		        if(l<0)
		            l=0;
		        else {
    		        var vpw = jqWin.width();
    		        if(l+sw-jqDoc.scrollLeft()>vpw){
    		            l = vpw-sw+jqDoc.scrollLeft();
    		        }
		        }
		    }
		    
		    jq.css('left', l+'px')
		      .css('top', t+'px');
		},
		/**
		 * 鎺т欢鐩稿褰撳墠鏂囨。瑙嗗浘灞呬腑
		 */
        center : function(){
          var jq  = this.jq(),
              sz  = [jqWin.width(), jqWin.height()],
              dsz = [jq.width(), jq.height()],
              off = (sz[1] - dsz[1]) * 0.8;
          this.view.style.left = Math.max((((sz[0] - dsz[0]) / 2) | 0), 0) + jqDoc.scrollLeft() + 'px';
          this.view.style.top  = Math.max(off - off/2|0, 0)+jqDoc.scrollTop() + 'px';
          return this;
        },
        
        /**
         * 鎺т欢瑙嗗浘浠庡綋鍓岲OM鐖跺厓绱犺劚绂伙紝澶栧寘涓€灞侱IV鍏冪礌锛岀粷瀵瑰畾浣嶇疆浜巇ocument.body灞傦紝浣嗘帶浠剁殑浣嶇疆闀垮害淇濇寔涓嶅彉銆�<br/>
         * 鎺т欢瑙嗗浘鍙兘鍦ㄥ鍖匘IV灞傚唴娲诲姩锛孌IV灞俤isplay:hidden锛屾墍浠ヨ秴鍑篋IV灞傛帶浠惰鍥鹃儴浠藉皢琚壀鍒囥€�<br/>
         * 璇ュ嚱鏁颁富瑕佷綔鐢ㄦ槸锛屾柟渚跨浉瀵瑰鍖匘IV灞傚唴鎺у埗鎺т欢鐨勪綅缃紝浣垮緱鎺т欢婊戝姩鏃跺叿鏈夊壀鍒囨晥鏋溿€�<br/>
         * 涓洪槻闂儊锛岃皟鐢ㄥ嚱鏁板墠鍙厛璁剧疆view鐨剉isiblity灞炴€т负闅愯棌(hidden)锛�<br/>
         * style.display涓烘樉绀猴紝璋冪敤鍚巚iew澶栧眰鍏冪礌鐨剉isiblity灞炴€ф槸闅愯棌(hidden)鐨勩€�
         */
        clip : function(){
            if(!Base.CLIP_WRAPPER_CSS){
                Base.CLIP_WRAPPER_CSS = {
                    position:'absolute',
                    clear : 'both',
                    overflow:'hidden'
                };
                Base.CLIPPER_CSS = {
                    position:'absolute',
                    left:0,
                    top:0
                };
            }
            // 闃叉澶氭璋冪敤鏃朵骇鐢熷灞傚寘瑁�
            if(!this.jqClipWrapper){
                var jqWrap = $(X.ax.Cache.get('div')),
                    v      = this.getView(),
                    jq     = this.jq(),
                    pNode  = v.parentNode,
                    voff   = jq.offset();
                    
                jqWrap.css(Base.CLIP_WRAPPER_CSS)
                      .css(voff)
                      .css('width', jq.width()+'px')
                      .css('height', jq.height()+'px')
                      .css('z-index', jq.css('z-index'))
                      .append(v);
    
                // 淇濆瓨鐘舵€侊紝clip缁撴潫鎭㈠
                var tmpCps = this._tmpClipedCss = {};
                for(var k in Base.CLIPPER_CSS){
                    tmpCps[k] = v.style[k];
                }
                jq.css(Base.CLIPPER_CSS);
                
                pNode && jqWrap.appendTo(pNode);
                this.jqClipWrapper = jqWrap;
            }
            return this.jqClipWrapper;
        },
        /**
         *  鎭㈠{@link #clip}鍓嶆帶浠剁姸鎬�
         */
        unclip : function(){
            if(this.jqClipWrapper){
                var wr = this.jqClipWrapper[0],
                    wrst = wr.style,
                    jq = this.jq(),
                    st = jq[0].style;
                
                for(var k in Base.CLIP_WRAPPER_CSS)
                    wrst[k] = '';
    
                this.jqClipWrapper
                    .css('overflow','')
                    .css('width','')
                    .css('height','');
                
                var tmpCps = this._tmpClipedCss;
                for(k in tmpCps)
                    st[k] = tmpCps[k];
                delete this._tmpClipedCss;
                
                wr.removeChild(jq[0]);
                if(wr.parentNode)
                    this.jqClipWrapper.replaceWith(jq);
                X.ax.Cache.put('div', wr);
                delete this.jqClipWrapper;
           }
        },
        
        /**
         * 婊戝姩鏄剧ず鎴栭殣钘忋€�<br/>
         * 濡傛灉鏄殣钘忥紝璋冪敤鍓嶈璁剧疆鎺т欢鐨剉isiblity:hidden,display:NOT HIDDEN鏍峰紡銆�
         * @param {String} fromTo l,r,t,b|l,r,t,b锛屼袱浣嶅瓧姣嶈〃绀�
         * @param {Boolean} visible 鏄剧ず鎴栭殣钘�
         * @param {Function} [callback]
         * @param {Object} [props] jquery鍔ㄧ敾閰嶇疆
         * @param {Object} [duration]
         * @param {Function} [easing]
         */
        slide : function(fromto, show, fn, props, duration, easing){
            var jq = this.jq(),
                l  = 0, t  = 0,
                w  = jq.width(),
                h  = jq.height(),
                fl = l,ft = t,tl = l,tt = t,
                jqWr = this.clip();
            var from = fromto.charAt(0), 
                to = fromto.charAt(1);
            switch(from){
                case 'l' :
                    fl = l-w;
                break;
                case 'r':
                    fl = l+w;
                break;
                case 't':
                    ft=t-h;
                break;
                case 'b':
                    ft=t+h;
                break;
            }
            
            switch(to){
                case 'l' :
                    tl = l-w;
                break;
                case 'r':
                    tl = l+w;
                break;
                case 't':
                    tt=t-h;
                break;
                case 'b':
                    tt=t+h;
                break;
            }
            jq.css('left',fl)
              .css('top',ft);
            if(!props) props = {};
            if(tl!=fl){
                props.left = props.left === undefined?tl:props.left + tl;
            }
            if(tt!=ft){
                props.top  = props.top===undefined?tt:props.top+tt;
            }
            if(show)
                jq.css('visibility','');
            var self = this;
            jq.animate(props, duration||'fast', easing , function(){
                if(!show){
                    self.display(false);
                    jq.css('visibility', '');
                }
                setTimeout(function(){
                    self.unclip();
                    fn && fn(self);
                }, 0);
            });
        }
};

X.reg('base', Base);


})(Xwb, $);
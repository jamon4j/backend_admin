(function(X, Util, $){
    var undefined;
/**
 * @class Xwb.ax.ActionEvent
 褰撲竴涓姩浣滆Е鍙戝悗锛屽氨鐢熸垚涓€涓猉wb.ax.ActionEvent瀹炰緥浣滃弬鏁颁紶缁欏鐞嗘柟娉曘€�
 鏈被鎼哄甫浜嗕笌璇ュ姩浣滅浉鍏崇殑鏁版嵁鍜屾柟娉曪紝濡傝Е鍙戞簮鍏冪礌锛屽綋鍓嶅厓绱狅紝鑾峰緱褰撳墠璺緞鏁版嵁绛夈€�
 绫绘棤闇€鎵嬪姩瀹炰緥鍖栵紝鐢眥@link Xwb.ax.ActionMgr}鍐呴儴缁存姢銆�
<pre>
鎶€宸э細
鍒╃敤璇ョ被鑾峰緱鍔ㄤ綔鍏宠仈鍏冪礌甯哥敤鏂规硶锛�
1. 缁欑浉鍏冲厓绱犺瀹氬眬閮ㄥ敮涓€ID
2. 鍒╃敤e.jq()鏂规硶鎴�$(e.getEl())杩斿洖涓€瀹氳寖鍥寸埗鍏冪礌鐨刯q瀵硅薄
3. 鏍规嵁璁惧畾鐨刬d鑾峰緱鍏冪礌
渚嬶細
鍦ㄥ鐞嗘柟娉曚腑锛�
<code>
function( e ){
    // 鍋囧浜嬩欢婧愬厓绱� rel="w:123765"
    // 鑾峰緱鍖呭惈w灞炴€х殑html鍏冪礌
    var jqWb = e.jq('w');
    var targetEl = jqWb.find('#xwb_tar');
    // ...
}
</code>
</pre>
 */

/**
 * @property evt
 瑙﹀彂璇ュ姩浣滅殑娴忚鍣ㄤ簨浠跺璞�
 * @type DOMEvent
 */
 
/**
 * @property src
 瑙﹀彂璇ュ姩浣滅殑婧愬厓绱�
 * @type HTMLElement
 */
 
/**
 * @property data
 * 褰撳墠澶勭悊鍏冪礌鐨剅el鏁版嵁
 * @type Object
 */
function ActionEvent(rels){
    this.q = rels;
    // 鍒濆鐘舵€�
    this.idx = -1;
    // end pos
    this.end = this.q.length-1;
};


ActionEvent.prototype = {

    prevented : undefined,
    
    stopPropagationed : undefined,
    
/**
 * 褰撳墠DOM璺緞鍐呭悜涓婃煡鎵緍el灞炴€т腑鍖呭惈name閿殑鏁版嵁
  <pre><code>
  // rel="w:123456"
  var wbId = e.get('w');
 </code></pre>

 * @param {String} name
 * @return {String} value
 */
    get : function(name){
        var r = this.getRel(name);
        return r && r.data && r.data[name];
    },
/**
 * 杩斿洖缁廵scape鍑芥暟杞箟鍚庣殑瀛楃涓叉暟鎹�
 * @param {String} name
 * @return {String}
 */
    escape : function(name){
        var v = this.get(name);
        if(v !== undefined)
            return escape(v);
    },
/**
 * 褰撳墠DOM璺緞鍐呭悜涓婃煡鎵惧惈鏈塺el浠ame涓洪敭鐨勫厓绱�
 * <pre><code>
  // 浠庡綋鍓嶄骇鐢熶簨浠剁殑鍏冪礌寮€濮嬪悜涓婃煡鎵捐幏寰楀寘鍚玾瀛楁鐨勫厓绱�
  var parent = e.getEl('w');
 </code></pre>
 * @param {String} name
 * @return {HTMLElement}
 */
    getEl : function(name){
        var r = this.getRel(name);
        return r && r.src;
    },
/**
 * 娓呯┖鍏冪礌rel鏁版嵁銆�
 */
    clear : function(name){
        var wrap = name?this.getRel(name):this;
        var jq = $(wrap.src);
        jq.data('xwb_rel', null);
        jq.attr('rel', '');
    },
/**
 * 淇濆瓨涓€涓敭鍊煎鍒板惈鏈塶ame閿殑鍏冪礌鐨剅el灞炴€т腑锛屽鏈寚瀹歯ame鍊硷紝鏁版嵁琚繚瀛樺埌褰撳墠瑙﹀彂鍏冪礌锛屽嵆e.src涓€�<br/>
 */
    save : function(k, v, name){
        var wrap = name?this.getRel(name):this;
        var jq = $(wrap.src);
        
        // 鏄惁瀛樺湪缂撳瓨
        var rel = jq.data('xwb_rel');
        if(rel){
            rel = rel[k] = v;
        }else {
            rel = X.ax.ActionMgr.parseRel(jq.attr('rel'));
            rel[k] = v;
        }
        
        wrap.data = rel;
        // 鏇存柊缂撳瓨
        jq.data('xwb_rel', rel);
        
        var serial = [];
        for(var i in rel){
            var val = rel[i] || '';
            serial.push(i+':'+ val.replace(':','\\:').replace(',','\\,'));
        }
        serial = serial.join(',');
        jq.attr('rel', serial);
    },

/**
 * 涓婃函DOM鑾峰緱rel鍚湁name閿厓绱犳垨鍏跺瓙鍏冪礌鐨刯Query瀵硅薄
 *@param {String} name 瀛楁鍚�
 * @param {Selector} [child] 鍙煡鎵惧厓绱犱笅鐨勫瓙鍏冪礌
 * @return {jQuery}
 */
    jq : function(name, child){
        var jq =  $(this.getEl(name));
        if(child)
            jq = jq.find(child);
        return jq;
    },
    
// private
    getRel : function(name){
        var set = this.q;
        for(var i=this.idx,end=this.end;i<=end;i++){
            var d = set[i].data;
            if(d[name] !== undefined)
                return set[i];
        }
    },
    
// private
    _next : function(){
        var nxt = this.q[++this.idx];
        // 鏈€缁堢姸鎬�
        if(nxt === undefined)
            this.idx = 0;
        return nxt;
    },

// private 鎷疯礉鍖呮嫭褰撳墠鐘舵€佺瓑鎵€鏈夋暟鎹€�
    clone : function(){
        var act = new ActionEvent(this.q);
        act.idx = 0;
        return act;
    },

/**
 * 鏄惁缁堟娴忚鍣ㄩ粯璁ゆ搷浣滐紝<b>榛樿涓虹粓姝�</b>銆�<br/>
 甯歌鐨勫鐞嗗畬checkbox鎴杛adio鍏冪礌鐨勪簨浠跺悗锛岃皟鐢ㄨ鏂规硶浣垮緱鍏冪礌鏀瑰彉閫夋嫨鐘舵€併€�<br/>
 * @param {Boolean} prevented
 */
    preventDefault : function(set){
        this.prevented = set;
    },
/**
 * 鏄惁鍋滄娴忚鍣ㄧ殑浜嬩欢鍐掓场锛�<b>榛樿涓哄仠姝�</b>銆�
 * 瀵逛簬{@link Xwb.ui.Base}缁勪欢锛屽湪鏈夎嚜韬殑actionMgr澶勭悊鐨勫悓鏃讹紝涔熸湁鍏ㄥ眬鎴栨洿鐖跺眰action澶勭悊鐨勬儏鍐典笅锛�
 * 鍙互鍦▄@link Xwb.ui.Base#onactiontrig}鏂规硶涓皟鐢ㄦ湰鏂规硶灏嗗姩浣滀簨浠朵笂浼犲埌鐖跺眰锛岃繖鏍风埗灞傜殑鐩戝惉鍣ㄥ氨鑳芥纭鐞嗗叾瀹冧簨浠躲€�
 * 渚嬪锛�
 * <pre>
 * <code>
    new Xwb.ui.Base({
        view:'#panel',
        actionMgr:true,
        // 榛樿鐨刼nactiontrig澶勭悊鍚庢槸鍋滄浜嬩欢涓婁紶鐨勶紝
        // 鎵€浠ヨ鎵嬪姩寮€鍚互灏嗘湭澶勭悊鐨勬彁浜ゅ埌鐖跺眰澶勭悊銆�
        onactiontrig:function(actEvent){
            switch(actEvent.get('e')){
                case 'local' :
                    // do something local stuff here.
                break;
                
                // or else , take other events to parents.
                default:
                    e.stopPropagation(false);
                break;
            }
        }
    });
 * </code>
 * </pre>
 * @param {Boolean} stopPropagation
 * @see #preventDefault
 */    
    stopPropagation : function(set){
        this.stopPropagationed = set;
    },
/**
 * 鏍囪褰撳墠鍔ㄤ綔锛屾垨鑾峰緱褰撳墠鍔ㄤ綔鏍囪銆�
 * 甯哥敤浜庢爣璁颁互闃叉鍔ㄤ綔閲嶅瑙﹀彂鍔ㄤ綔銆�
 * @param {Boolean} locked
 * @param {String} [name] name
 *<pre><code>
 // 閿佸畾鍔ㄤ綔鍏冪礌锛屼娇寰椾笉鑳藉啀瑙﹀彂
 e.lock(1);
 // 寰呰姹傚鐞嗗悗鎭㈠
 Xwb.request.post(data, function(){
    // 瑙ｉ櫎閿佸畾
    e.lock(0);
 });
 </code></pre>

 */
    lock : function(set, name){
        var k = 'xwb_e_' + this.data.e;
        if(name)
            k+= '_' + name;
        if(set === undefined)
            return $(this.src).data(k);
        $(this.src).data(k, set);
    }
};

/**
 * @class Xwb.ax.ActionMgr
 * 鏈被琛屼负绫讳技DOM浜嬩欢妯″瀷涓殑浜嬩欢鍐掓场锛�
 * 鍦ㄦ煇涓€涓厓绱犲唴闆嗕腑鐩戝惉鍚勭浜嬩欢锛屽寘鎷瓙鍏冪礌鐨勪簨浠讹紝
 * 骞跺湪浜嬩欢瑙﹀彂鏃舵柟渚胯幏寰楃浉鍏虫暟鎹紝鑰屾棤闇€瀵瑰瓙鍏冪礌缁戝畾绫讳技浜嬩欢銆�
 * <pre><code>
  灏嗗姩浣滀簨浠剁鐞嗗櫒缁戝畾鍒板厓绱犱笂
  var mgr = new Xwb.ax.ActionMgr({target:'#selector'});
  
  娉ㄥ唽鍔ㄤ綔澶勭悊锛屽綋鎸囧畾鍔ㄤ綔鍙戠敓鏃舵墽琛屽鐞�
  mgr.reg('myaction', function(actEvt){
        // 鏄剧ずa鍏冪礌
        alert(actEvt.src);
        
        // 鏄剧ずmyaction
        alert(actEvt.data.e);
        
        // 鏄剧ず123456
        alert(actEvt.get('u'));
  });
  
  灏嗗姩浣滄坊鍔犲埌HTML妯℃澘涓紝鐢ㄦ埛鐐瑰嚮鍏冪礌鏃跺氨鑳借Е鍙�
  &lt;li rel=&quot;u:123456&quot;&gt;&lt;a href=&quot;#&quot; rel=&quot;e:myaction&quot;&gt;click me&lt;/a&gt;&lt;/li&gt;
  
  鏇村鍏充簬鏈被鐨勪娇鐢ㄦ柟娉曡瑙乤ctions.js鏂囦欢銆�
 </code></pre>
 * @constructor
 * @param {Object} config
 */
/**@cfg {jQuery|HTMLElement} target 鍦ㄨ鍏冪礌鑼冨洿鍐呯洃鍚琣ction浜嬩欢*/
X.ax.ActionMgr = X.reg('ActionMgr', function(cfg){
   cfg && $.extend(this, cfg);
   if(!this.actions)
        this.actions = {};
   if(!this.filters)
        this.filters  = [];
   
   if(this.target){
        this.bind(this.target);
        delete this.target;
   }
});

var globalFilters = [];

/**
 * 瑙ｆ瀽rel瀛楃涓蹭负JSON瀵硅薄鏁版嵁
 * @param {String|HTMLElement} relDataOrRelElement
 * @static
 * @return {Object}
 */
X.ax.ActionMgr.parseRel = function(rel){
    if(typeof rel === 'string')
        return Util.parseKnV(rel);
    return Util.parseKnV($(rel).attr('rel'));
};

// 瑙ｆ瀽HTML鍏冪礌鍙婄埗鍏冪礌鐨剅el灞炴€э紝骞惰繑鍥炶灞炴€ч摼鐨凧SON鏁版嵁锛屼互琚珄@link Xwb.ax.ActionEvent}鍒╃敤銆�
X.ax.ActionMgr.collectRels = function(trigSource, stopEl, cache){
    var 
        rels, 
        rel, 
        self = this;
    
    if(cache===undefined)
        cache = true;
    
    // 寰€涓婃敹闆唕el
    Util.domUp(trigSource, function(el){
        var jq = $(el);
        
        if(cache){
            rel = jq.data('xwb_rel');
            if(!rel){
                rel = jq.attr('rel');
                if(rel){
                    rel = {src:el, data:self.parseRel(rel)};
                    jq.data('xwb_rel', rel);
                }
            }
        }else {
            rel = jq.attr('rel');
            if(rel)
                rel = {src:el, data:self.parseRel(rel)};
        }
    
        if(rel){
            if(!rels)
                rels = [];
            rels[rels.length] = rel;
        }
    
    } , stopEl);
    
    return rels;
};

/**
 * 瑙ｆ瀽DOM鏍戜腑鐨剅el灞炴€у€硷紝杩斿洖璇ュ睘鎬ч摼鎺ョ浉鍏崇殑{@link Xwb.ax.ActionEvent}绫伙紝浠ヤ究鍒╃敤璇ョ被鑾峰緱閾句腑鐨勬暟鎹�
 * @param {HTMLElement} sourceElement 寮€濮嬪厓绱�
 * @param {HTMLElement} [toElement] 缁撴潫鍏冪礌
 * @return {Xwb.ax.ActionEvent}
 * @static
 */
X.ax.ActionMgr.wrapRel = function(el, stopEl, cacheNodeData){
    var rels = this.collectRels(el, stopEl, cacheNodeData);
    var e = new ActionEvent(rels);
    e._next();
    return e;
};

/**
 * 鑾峰緱鍏蜂綋鍗曚釜rel閿€�
 * @param {HTMLElement} sourceElement 寮€濮嬪厓绱�
 * @param {String} name
 * @param {HTMLElement} [toElement] 缁撴潫鍏冪礌
 * @return {String}
 */
X.ax.ActionMgr.getRel = function(el, name, stopEl){
    return this.wrapRel(el, stopEl).get(name);
};

/**@cfg {String} trigEvent 瑙﹀彂鍔ㄤ綔鐨勪簨浠跺悕绉帮紝榛樿涓篶lick */
X.ax.ActionMgr.prototype = {
    
    trigEvent : 'click',

    cacheNodeData : true,
    
/**
 * 娉ㄥ唽鍔ㄤ綔澶勭悊鏂规硶锛屽綋鍙傛暟鍙湁涓€涓椂娉ㄥ唽鍏ㄥ眬鐩戝惉鍣紝鍙洃鍚潵鑷绠＄悊鍣ㄧ殑鎵€鏈夊姩浣�<br/>
 * 鍙湁涓€涓弬鏁版椂鏃剁洃鍚鐞嗗櫒鍐呮墍鏈夊姩浣�
 * @param {String} actionName 鍔ㄤ綔鍚嶇О
 * @param {Function} handler 鍔ㄤ綔澶勭悊鏂规硶
 * @param {Xwb.ax.ActionConfig} config 鍔ㄤ綔淇℃伅
 *@return this
 */
    reg : function(act, handler, cfg){
        var d = { n : act, h : handler };
        if( cfg )
            $.extend( d, cfg );
        
        this.actions[act] = d;
        return this;
    },
    
/**
 * 鑾峰緱涓€涓姩浣滈厤缃俊鎭�
 * @param {String} name
 * @return {Xwb.ax.ActionConfig}
 */
    get : function( name ){
        return this.actions[ name ];
    },
    
/**
 * 澧炲姞鍔ㄤ綔鎷︽埅鍣紝鍙嫤鎴潵鑷綋鍓嶇鐞嗗櫒鎴栨墍鏈夌鐞嗗櫒鐨勬瘡涓姩浣溿€�
 *  @param {Function} filter
 * @param {Boolean} [global] 鏄惁涓烘墍鏈夊姩浣滅殑鎷︽埅鍣紝榛樿鍙嫤鎴綋鍓嶇鐞嗗櫒鐨勫姩浣�
 * @return this
 */
    addFilter : function(filter, global){
        global ? globalFilters.push(filter) : this.filters.push(filter);
        return this;
    },
    
/**
 * 濡傛灉涓嶆兂閫氳繃{@link #target}閰嶇疆缁戝畾闈㈡澘锛屽彲璋冪敤璇ユ柟娉曞皢绠＄悊鍣ㄧ粦瀹氬埌鎸囧畾鍏冪礌
 * @param {Selector} target
 * @return this
 */
    bind : function(selector, evt){
        var scope = this;
        $(selector).bind(evt || this.trigEvent, function(e){
           var rels = X.ax.ActionMgr.collectRels(e.target, this, this.cacheNodeData);
           rels && scope.fireRels(rels, e);
        });
        return this;
    },

/**
 * 鍙互鎵嬪姩瑙﹀彂鍔ㄤ綔锛屼粠鏌愪釜鍏冪礌璧凤紝鑷虫煇涓埗鍏冪礌缁撴潫
 * @param {HTMLElement} sourceElement
 * @param {HTMLElement} stopElement
 */
    doAct : function(el, stopEl, cacheNodeData){
        var rels = X.ax.ActionMgr.collectRels(el, stopEl, cacheNodeData);
        return !(rels && this.fireRels(rels));        
    },

    // @return {Boolean} handled
    fireRels : function(rels, evt){
        var e = new ActionEvent(rels);
        e.evt = evt;
        if(__debug) console.log('act e:', e);
        
        var rel, data,
            hs = globalFilters.length,
            hg = this.filters.length,
            handled,
            prevented, stopPropagationed;
        
        while ( (rel = e._next()) ){
           // 瀛樺湪action
           data = rel.data;
           if(data.e){
               e.src  = rel.src;
               e.data = data;
                // 濡傛灉褰撳墠鎿嶄綔宸查攣瀹氾紝鍙栨秷鎿嶄綔骞惰繑鍥烇紝闃叉閲嶅鍝嶅簲
                if(!e.lock()){
                   var act = this.actions[data.e];
                   if( hs ){
                      handled = true;
                      if( this._fireArray(globalFilters, e, act) === false ){
                        stopPropagationed = e.stopPropagationed;
                        prevented     = e.prevented;
                        break;
                      }
                   }
                   
                   if( hg ){
                        handled = true;
                        if( this._fireArray(this.filters, e, act) === false ){
                            break;
                        }
                   }
                   stopPropagationed = e.stopPropagationed;
                   prevented     = e.prevented;
                   if(act){
                        // clone e
                        var hdE  = e.clone();
                        hdE.src  = e.src;
                        hdE.data = data;
                        hdE.evt  = evt;
                        if(__debug) console.log('act e:',hdE);
                        if(!handled)
                            handled = true;
                        if( act.h.call(this, hdE) === false){
                            if(hdE.stopPropagationed !== undefined)
                                stopPropagationed = hdE.stopPropagationed;
                            if(hdE.prevented !== undefined)
                                prevented = hdE.prevented;
                            break;
                        }
                        if(hdE.stopPropagationed !== undefined)
                            stopPropagationed = hdE.stopPropagationed;
                        if(hdE.prevented !== undefined)
                            prevented = hdE.prevented;
                   }
                }else { // marked
                    if(__debug) console.warn('action e:'+e.data.e+' has been locked for resubmiting');
                    handled = true;
                    stopPropagationed = true;
                    prevented = true;
                    break;
                }
           }
        }
        
        if(evt && handled){
            // we defaultly preventDefault and stopPropagation
            if(prevented === undefined)
                prevented = true;
            if(stopPropagationed === undefined)
                stopPropagationed = true;
            
            if(prevented)
                evt.preventDefault();
    
            if(stopPropagationed)
                evt.stopPropagation();
        }
    },
    
    // 濡傛灉鏈敞鍐宎ction:锛宎ct鏈夊彲鑳戒负绌猴紝鎵€浠ct搴旂敤鏃惰浣滅┖妫€鏌�
    _fireArray : function(gs, e, act){
        for(var i=0,len=gs.length;i<len;i++)
            if( gs[i].call( this, e, act) === false )
                return false;
    }
};

// 椤甸潰action
X.reg('action', X.use('ActionMgr'));


})(Xwb, Xwb.util, $);
(function(X, $){

var Util = X.util;
var undefined;
var T = X.ax.Tpl;
var doc = document;
var Base = X.ui.Base;
var ui = X.ui;
// 璁板綍寮瑰嚭娴眰zIndex
var currentZ = 10001;
var jqWin = $(window);

var PopupKBMonitor = {
	
	layers : [],
	
	hash : {},
	
	keyListeners : 0,
	
	add : function(layer){
		var cid = layer.cacheId;
		if(!this.hash[cid]){
			this.layers.push(layer);
			if(layer.keyEvent){
				if(this.keyListeners === 0){
					if(__debug) console.log('bind key listener');
					$(doc).bind('keydown', this.getEvtHandler());
				}
				this.keyListeners++;
			}
			this.hash[cid] = true;
	  }
	},
	
	remove : function(layer){
		var cid = layer.cacheId;
		if(this.hash[cid]){
			var ly = this.layers;
			if(ly[ly.length - 1] === layer)
				ly.pop();
		  else Util.arrayRemove(ly, layer);
			
			this.keyListeners--;
			if(this.keyListeners===0){
				if(__debug) console.log('remove key listener');
				$(doc).unbind('keydown', this.getEvtHandler());
			}
			delete this.hash[cid];
	  }
	},
	
	getEvtHandler : function(){
		  var kh = this._onDocKeydown;
		  if( !kh )
		  	kh = this._onDocKeydown = Util.bind( this.onDocKeydown, this );
		  return kh;
	},
	
	onDocKeydown : function(e){
			var top = this.layers[this.layers.length-1];
			if(top && top.keyEvent)
				return top.onKeydown(e);
	}
};

/**
 * @class Xwb.ui.Layer
 * 娴眰鍩虹被銆�<br/>
 * 鎵€鏈夋诞灞備笉鐢ㄨ缃畓Index鍊硷紝鍧囩敱搴撹嚜琛岀鐞嗐€�
 * 濡傛湁闇€瑕侊紝鍙€氳繃{@link #setCurrentZ}鍧囧鍖杬Index鍊笺€�
 * @extends Xwb.ui.Base
 */

var Layer = ui.Layer = Util.create(Base, {
    
    /**@cfg {Boolean} autoCenter 褰撴樉绀烘垨鏄剧ず鍚庣獥鍙esize鏃讹紝鏄惁鑷姩灞呬腑*/
    
    /**@cfg {Boolean} hidden 榛樿闅愯棌*/
    hidden : true,
    
    onViewReady : $.noop,
    
    /**
     * @cfg {Boolean} frameMask 鏄剧ず鏃舵槸鍚﹀簲鐢↖FRAME灞備綔閬僵灞傦紝IE6鏃堕粯璁や负true锛屽叾瀹冩祻瑙堝櫒榛樿false
     */
    frameMask : Util.ie6,
    
/**
 * 鎵嬪姩鏇存柊绐楀彛zindex锛岄粯璁ゆ槸鑷姩鏇存柊銆�
 * 褰撳悓鏃舵樉绀哄涓獥鍙ｆ椂,璋冪敤璇ユ柟娉曞彲浣跨獥鍙ｇ疆椤躲€�
 * 閫傜敤浜巔osition:absolute, fixed闈㈡澘銆�
 * @see Xwb.ui.Layer#setCurrentZ
 */
    trackZIndex : function(){
      if(this.z !== currentZ){
         currentZ += 3;

        if(this.mask)
            $(this.mask).css('z-index', currentZ - 1);
        
        if(this.frameMask)
            $(this.getFrameMask()).css('z-index', currentZ - 2);
        
        this.jq().css('z-index', currentZ);
        this.z = currentZ;
      }
    },
    
    /**
     * @cfg {Boolean} keyEvent 鏄惁寮€鍚敭鐩樼洃鍚紝榛樿true锛屽皢澶勭悊ESC閿€�
     */
    keyEvent : true,
    
    /**
     * 濡傛灉鐩戝惉娴眰鎸夐敭浜嬩欢锛屽彲閲嶅啓鏈柟娉曘€傞粯璁ゅ鐞嗘槸鐩戝惉ESC閿紝褰揈SC鎸変笅鏃跺叧闂诞灞傘€�
     * @param {DOMEvent} event
     */
    onKeydown : function(e){
    	// esc
    	if(e.keyCode === 27 && !this.cancelEscKeyEvent){
    		this.close();
    		return false;
    	}
    },
    
    // override
    beforeShow : function(){
        if(this.mask)
            this._applyMask(true);
        var pos = this.jq().css('position');
        if( pos === 'absolute' || pos === 'fixed' )
            this.trackZIndex();
        PopupKBMonitor.add(this);
        
        if(this.autoCenter)
            this.center();
    },
    
    
    // override
    afterHide : function(){
        if(this.mask)
            this._applyMask(false);
        PopupKBMonitor.remove(this);
    },
	
	getFrameMask : function(){
	    if(this.frameMaskEl)
	        return this.frameMaskEl;
	    // 鍥犱负iframe灞傛瘮杈冪壒娈婏紝杈冨皯鍙樺姩锛屾墍浠ョ洿鎺ュ啓鍦ㄨ繖閲岃€屼笉蹇匤S HTML妯℃澘閲屻€�
	    this.frameMaskEl = T.forNode('<iframe class="shade-div shade-iframe" frameborder="0"></iframe>');
	    return this.frameMaskEl;
	},
	
    /**
     * @cfg {Boolean} mask 鏄剧ず鏃舵槸鍚﹀簲鐢ㄩ伄缃╁眰锛岄粯璁alse
     */
    _applyMask : function(b){
      var mask = this.mask;
      if(!mask || mask === true)
        mask = this.mask = T.forNode(T.get('Mask'));
      
      var wh = jqWin.height();
      if(b){
        $(mask)
            .height( wh )
            .appendTo(doc.body);
        if(this.frameMask)
            $(this.getFrameMask()).height(wh).appendTo(doc.body);
        // window resize event handler
        jqWin.bind('resize', Util.getBind(this, 'onMaskWinResize'));
      }else {
        $(mask).remove();
        if(this.frameMask)
            $(this.getFrameMask()).remove();
        jqWin.unbind('resize', Util.getBind(this, 'onMaskWinResize'));
      }
    },
    
    onMaskWinResize : function(){
      var mask = this.mask, wh = jqWin.height();
      if(mask)
        $(mask).height( wh );
        
        if(this.frameMask)
            $(this.getFrameMask()).height(wh);
      
      if(this.autoCenter)
            this.center();
    }
});
/**
 * @param {Number} zIndex
 * @method setCurrentZ
 * @static
 */
Layer.setCurrentZ = function(z){
    currentZ = z;
};

X.reg('Layer', Layer);

/**
 * @class Xwb.ui.Switcher
 * @constructor
 * @param {Object} config
 */

/**
 * @cfg {DOMCollection|jQuery} items tab items
 */

/**
 * @cfg {DOMCollection|jQuery} contents tab contents
 */

/**
 * @cfg {Boolean} autoRecover 褰撻紶鏍囩寮€鏃舵槸鍚﹁嚜鍔ㄥ鍘�
 */

/**
 * @cfg {Boolean} delaySelect 鍦▄@link #trigMode}涓篽over鎯呭喌涓嬮紶鏍囧垝杩囨椂寤惰繜閫夋嫨锛屽崟浣峬s锛岄粯璁や笉寤惰繜
 */

/**
 * @cfg {Boolean} delayHide
 */

/**
 * @cfg {String} trigMode click锛宧over锛岄粯璁lick锛岀偣鍑绘椂鎵嶈Е鍙戦€夋嫨锛屽鏋滆涓篽over鏃讹紝榧犳爣绉讳笂灏变細瑙﹀彂銆�
 */

/**
 * @cfg {String} clickEvent 濡傛灉瑙﹀彂閫夋嫨鐨勬柟寮忎负'click'锛岃缃€夋嫨瑙﹀彂浜嬩欢锛岄粯璁や负mousedown锛屽彲浠ユ敼涓�'click'浜嬩欢銆�
 */

/**
 * @cfg {Function} onselect 閫夋嫨椤规椂瑙﹀彂銆俹nselect(selectedItem)锛屼粛鍙互閫氳繃 switcher.selectedItem璁块棶涓婁竴涓€夋嫨椤广€�
 */
ui.Switcher = function(opt){
	opt && $.extend(this, opt);
	this.initUI();
};

X.reg('Switcher', ui.Switcher);

ui.Switcher.prototype = {
	trigMode : 'click',
	initUI : function(){
		if (this.items)
			this.add(this.items, this.contents);
	},
/**
 * @param {HTMLElement} item
 */
	select : function(item){

        var pre = this.selectedItem;
		
		if (this.autoRecover)
            this.clearTimer(1);
        
        //鍏堟樉绀哄綋鍓嶉」鍐嶉殣钘忓厛鍓嶇殑,浣垮緱杩囨浮骞虫粦,鑰屼笉閫犳垚闂儊
        if(this.selectedCS){
            pre && $(pre).removeClass(this.selectedCS);
			$(item).addClass(this.selectedCS);
		}

		// select callback
		this.onselect && this.onselect(item);
		
        this.selectedItem = item;
		
		if (item.contentEl) {
            var cp = item.contentEl;
            // 鎶婃樉绀哄欢杩熻嚦涓婁竴娆＄晫闈㈡洿鏂颁箣鍚�,
			// 鍙繚鎸佺敤鎴锋搷浣滄祦鐣呭搷搴�
			// 濡傛灉涓嶉渶瑕佽繖鏁堟灉锛屽彲鐩存帴璋冪敤unselect(pre)
            setTimeout( function(){
                if (pre && pre.contentEl)
                    $(pre.contentEl).cssDisplay(false);
                $(cp).cssDisplay(true);
            }, 0 );
        }
	},

/**
 * @param {HTMLElement} item
 */	
	unselect : function(item){
        if(this.selectedCS)
            $(item).removeClass(this.selectedCS);
        
        if(item.contentEl)
            $(item.contentEl).cssDisplay(false);
		
		if(this.mouseoutTimer)
		    this.clearTimer(1);
	
        this.selectedItem = NULL;
	},
	
	// private , type=0, mouseover; 1, mouseout
	clearTimer : function(type){
		if (type !== 0) {
			if (this.mouseoutTimer) {
				clearTimeout(this.mouseoutTimer);
				this.mouseoutTimer = false;
				this.mouseoutTimerFn = false;
			}
		}else {
			if (this.mouseoverTimer) {
				clearTimeout(this.mouseoverTimer);
				this.mouseoverTimer = false;
				this.mouseoverTimerFn = false;
			}
		}
	},
/**
 * @param {HTMLElement|Array} item
 * @param {HTMLElement|Array} panel
 */
    add: function(item, panel){
		if(item.length){
		    if(panel){
			    for(var i=0,len=item.length;i<len;i++)
				    this.add(item[i], panel[i]);
		    }else for(var i=0,len=item.length;i<len;i++)
				    this.add(item[i]);
			return;
		}
		
		var jq = $(item);
        var switcher = this;
		
		// 鍦ㄥ綍鍏ユ椂宸叉爣璁伴€夋嫨
        if(jq.hasClass(this.selectedCS)){
            if(this.selectedItem)
                this.unselect();
            this.selectedItem = item;
        }
        
		if(panel) 
		    item.contentEl = panel;
		
		// install hover event handler
        if (this.trigMode === 'hover' || this.autoRecover) {
			jq.hover(	// mouse over
			function(e){
				if (switcher.autoRecover && switcher.mouseoutTimer) {
					switcher.clearTimeout(1);
				}
				
				// trig mode
				if (switcher.trigMode === 'hover') {
					var pre = switcher.selectedItem;
					if (this !== pre) {
						if (switcher.delaySelect) {
							// 閲嶇疆瀹氭椂
							if (switcher.mouseoverTimer) 
								switcher.clearTimeout(0);
							switcher.mouseoverTimer = setTimeout(function(){
								switcher.select(item);
							}, switcher.delaySelect);
						}
						else 
							switcher.select(this);
					}
				}
				
				return !switcher.enableBubble;
			}, 
			// mouse out
			function(e){
			
				if (switcher.mouseoverTimer) 
					switcher.clearTimeout(0);
				
				if (switcher.trigMode === 'hover') {
					var pre = switcher.selectedItem;
					if (this !== pre && switcher.mouseoutTimerFn) {
						switcher.mouseoutTimerFn();
						switcher.mouseoutTimerFn = NULL;
					}
					
					var el = e.target;
					if (switcher.autoRecover && (el === this || Util.ancestorOf(this, el))) {
						if (!switcher.mouseoutTimer) {
							var nd = this;
							switcher.mouseoutTimerFn = function(){
								switcher.unselect(nd);
							};
							switcher.mouseoutTimer = setTimeout(switcher.mouseoutTimerFn, switcher.delayHide || 500);
						}
					}
				}
				return !switcher.enableBubble;
			});
		}
		
	   // install click event handler
	   if(this.trigMode === 'click'){
	   		jq.bind(this.clickEvent||'mousedown', function(){
				switcher.select(this);
				return false;
			});
	   }
    }
};

/**
 * @class Xwb.ui.Box
 * Box涓嶭ayer鍙槸妯℃澘涓婄殑涓嶅悓銆�<br/>
 * Box榛樿浣跨敤鐨勬ā鏉挎槸"Box"
 * @extends Xwb.ui.Layer
 */
ui.Box = X.reg('Box', Util.create(ui.Layer, {
    view : 'Box'
}));

/**
 * @class Xwb.ui.Tip
 * 鎻愮ず绫伙紝鎻愪緵瓒呮椂澶勭悊銆�
 * @extends Xwb.ui.Box
 */

 
ui.Tip = X.reg('Tip', Util.create(ui.Box, {
    
    cs : 'win-fixed',
/**
 * @cfg {Boolean} autoHide defaults true
 */      
    autoHide : true,
/**
 * @cfg {Number} timeoutHide defaults to 1000 ms
 */    
    timeoutHide : 1000,
/**
 *  璇ュ睘鎬ф槸{@link setShowTimer}寤惰繜鏄剧ず鏃惰缃殑瓒呮椂鏃堕棿锛�
 *  {@link setShowTimer}甯哥敤鍦╩ouseover鏃跺欢杩熸彁绀虹殑鏄剧ず锛宮ouseout鏃舵竻闄ゅ欢杩熶互鎻愰珮鐢ㄦ埛浣撻獙銆�
 */
    timeoutShow : 200,

/**
 * @cfg {Boolean} stayHover stay on mouseover and hide on mouseout, defaults to false
 */
 
    stayHover : false,
/**
 * @cfg {Number} offX 闈㈡澘瀹氫綅鏃跺線鐬勭偣X鏂瑰悜鐨勫亸绉诲閲忥紝榛樿25
 */
    offX : 25,
    
/**
 * @cfg {Number} offX 闈㈡澘瀹氫綅鏃跺線鐬勭偣Y鏂瑰悜鐨勫亸绉诲閲忥紝榛樿-10
 */
    offY : -10,
    
    // override
    innerViewReady : function(v){
        var jq = this.jq();
        if(this.stayHover){
            jq.hover(
                Util.bind(this.onMouseover, this),
                Util.bind(this.onMouseout, this)
            );
        }
    },
    
    onMouseover : function(){
        this.clearHideTimer();
    },
    
    onMouseout : function(){
        this.setHideTimer();
    },
    
/**
 * 娓呴櫎瓒呮椂闅愯棌
 */
    clearHideTimer : function(){
        if(this.hideTimerId){
            clearTimeout(this.hideTimerId);
            this.hideTimerId = false;
        }
    },
    
    beforeShow : function(){
        if( Layer.prototype.beforeShow.apply(this, arguments) === false )
            return false;
        
        if(this.autoHide)
            this.setHideTimer();
    },

/**
 *  璁剧疆鎴栨竻闄ゆ樉绀鸿秴鏃躲€�
 *  璇ユ柟娉曟槸#setHideTimer涓巆learHideTimer瀵瑰簲寤惰繜鏄剧ず鏂规硶锛屽凡闆嗕腑鍦ㄤ竴涓嚱鏁拌皟鐢ㄣ€�
 *  甯哥敤鍦╩ouseover鏃跺欢杩熸彁绀虹殑鏄剧ず锛宮ouseout鏃舵竻闄ゅ欢杩燂紝
 *  鍙湁鏁堥槻姝㈢敤鎴峰彧鏄帬杩囬紶鏍囦絾骞堕潪鏌ョ湅鏃跺彇娑堟樉绀烘彁绀轰互鎻愰珮鐢ㄦ埛浣撻獙銆�
 * @param {Boolean} set
 */
    setShowTimer : function(b){
        if(b){
            this.showTimerId = setTimeout(Util.getBind(this, 'onTimerShow'), this.timeoutShow);
        }else if(this.showTimerId){
            clearTimeout(this.showTimerId);
            this.showTimerId = false;
        }
    },
    
/**
 * 寮€濮嬭秴鏃堕殣钘�,鍦ㄦ寚瀹氭椂闂村唴闅愯棌
 */
    setHideTimer : function(){
        this.clearHideTimer();
        this.hideTimerId = setTimeout(Util.getBind(this, 'onTimerHide'), this.timeoutHide);
    },
    
    onTimerHide : function(){
        this.display(false);
        this.clearHideTimer();
    },
    
    onTimerShow : function(){
        this.display(true);
        this.setShowTimer(false);
    }
}));

})(Xwb, $);
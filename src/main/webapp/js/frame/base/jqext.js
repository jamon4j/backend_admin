/**
 * @class jQuery
 * 鏈被鏄疿wb JavaScript API瀵筳Query鏂规硶鐨勬墿灞曘€�
 */
(function(X, $){
	var FALSE = false,
		TRUE = true,
		NULL = null,
		toInt = parseInt,
		isIE6 = !!($.browser.msie && $.browser.version == '6.0');;
	// 鍥剧墖鎺у埗缁勪欢
	var imgCtrler = function() {
	    this.cid = 'imageCtrler';
	    this.canvas = NULL;
	    this.maxWidth = 440;
	    this.width = 0;
	    this.height = 0;
	    this.curAngle = 0;
	};

	imgCtrler.prototype = {
	    // 鍒濆鍖栵紝鏀寔canvas鐨勫垱寤篶anvas锛孖E浣跨敤鐭╅樀婊ら暅
	    init: function(data) {
	        var _el = data.el;
	        
	        this.width = _el.offsetWidth;
	        this.height = _el.offsetHeight;
	        
	        if($.browser.msie) {
	            var _matrix = 'DXImageTransform.Microsoft.Matrix';
	            
	            _el.style.filter = 'progid:DXImageTransform.Microsoft.Matrix()';
	            _el.filters.item(_matrix).SizingMethod = "auto expand";
	            $(_el).addClass('narrow-move');
	            _matrix = NULL;
	        }else {
	            this.canvas = $('<canvas></canvas>')
	                .attr({
	                    'height': this.height,
	                    'width': this.width
	                })
					.addClass('narrow-move')
					.attr('rel', 'e:zo')
	                .insertBefore(_el)[0];
	            
	            if(this.canvas.getContext) {
	                $(_el).hide();
	                
	                var ctx = this.canvas.getContext('2d');
	                
	                //ctx.drawImage(_el,0,0);
	
	            }
	        }
	        
	        this.element = _el;
	    },
	    
	    //鏃嬭浆鍥剧墖
	    //鏃嬭浆鏂瑰紡锛�'left'鎴栬€�'right'
	    rotate: function(dir) {
	        if(!this.element) {return;}
	        
	        //鐩稿鍘熷鍥剧墖鐨勬棆杞搴�
	        var _angle;
	        if(dir === 'right') {
	            _angle = this.curAngle + 90;
	            this.curAngle = _angle>=360 ? 0 : _angle;
	        }else if(dir === 'left') {
	            _angle = this.curAngle - 90;
	            this.curAngle = _angle<0 ? 360+_angle : _angle;
	        }
	        _angle = NULL;
	        
	        //璋冩暣鍥剧墖鏃嬭浆鍚庣殑澶у皬
	        var drawW,drawH, h=this.width,w=this.height;
	            
	        this.width = w;
	        this.height = h;
	
	        if(w > this.maxWidth) {
	            h = toInt(this.maxWidth * h/w);
	            w = this.maxWidth;
	        }
	        
	        if(this.canvas) {
	            var 
	                ctx = this.canvas.getContext('2d'), el = this.element, 
	                cpx=0, cpy=0;
	            //璁剧疆鐢诲竷澶у皬锛岄噸缃簡鍐呭
	            $(this.canvas).attr({
	                'width': w,
	                'height': h
	            });
	            
	            ctx.clearRect(0,0,w,h);
	            
	            switch(this.curAngle) {
	                case 0:
	                    cpx = 0;
	                    cpy = 0;
	                    drawW = w;
	                    drawH = h;
	                    break;
	                case 90:
	                    cpx = w;
	                    cpy = 0;
	                    drawW = h;
	                    drawH = w;
	                    break;
	                case 180:
	                    cpx = w;
	                    cpy = h;
	                    drawW = w;
	                    drawH = h;
	                    break;
	                case 270:
	                    cpx = 0;
	                    cpy = h;
	                    drawW = h;
	                    drawH = w;
	                    break;
	            }
	            
	            ctx.save();
	            ctx.translate(cpx,cpy);
	            ctx.rotate(this.curAngle * Math.PI/180);
	            ctx.drawImage(el, 0, 0, drawW,drawH);
	            ctx.restore();
	            
	        }else {
	            var 
	                _rad = this.curAngle * Math.PI/180,
	                _cos = Math.cos(_rad),
	                _sin = Math.sin(_rad),
	                _el = this.element,
	                _matrix = 'DXImageTransform.Microsoft.Matrix';
	                
	            _el.filters.item(_matrix).M11 = _cos;
	            _el.filters.item(_matrix).M12 = -_sin;
	            _el.filters.item(_matrix).M21 = _sin;
	            _el.filters.item(_matrix).M22 = _cos;
	            
	            // this.width = _el.offsetWidth;
	            // this.height = _el.offsetHeight;
	            
	            switch(this.curAngle) {
	                case 0:
	                case 180:
	                    drawW = w;
	                    drawH = h;
	                    break;
	                case 90:
	                case 270:
	                    drawW = h;
	                    drawH = w;
	                    break;
	            }
	            
	            _el.width = drawW;
	            _el.height = drawH;
	            //淇IE8涓嬪浘鐗囧崰浣嶇殑闂
	            //18鏄搷浣滆彍鍗曠殑楂樺害
	            if($.browser.version == 8) {
	                _el.parentNode.style.height = _el.offsetHeight+18;
	            }
	        }
	    }
	};

	$.extend($.fn, {
		   /**
			* 鏂囧瓧鏀惧ぇ娓愰殣锛堝井鍗氭暟澧炲姞鏁堟灉锛�
			*@param {Number} num 澧炲姞鏁板€�
			*@param {Number} times 鏀惧ぇ鍊嶆暟
			*@return {jQuery}
			*/
			zoomText: function(num, times) {
				this.each(function() {
					var
						$clone,
						$el = $(this),
						offset = $el.offset(),
						text = $el.text();
						
					times = isNaN(times) ? 2 : times;
					
					if(!isNaN(+text)) {
						text = +text + (num || 1);
					}
					
					$el.text(text);
					
					$clone = $el.clone()
						.attr('id', '')
						.css({
							'position': 'absolute',
							'top': offset.top,
							'left': offset.left,
							'font': $el.css('font'),
							'color': $el.css('color')
						})
						.appendTo($(document.body));
					
					var fontsize = times * parseInt($el.css('font-size'));
					
					$clone.animate({
						'font-size': fontsize,
						'top': offset.top - ($el.height()/4),
						'left': offset.left - ($el.width()/2),
						'opacity': 0.1
					}, {
						'duration': 300,
						'complete': function() {
							$clone.remove();
						}
					});
					
				});
				
				return this;
			},

			/**
			* 鍥剧墖宸﹀彸鏃嬭浆
			* @param {String} dir  鏃嬭浆鏂瑰紡锛�'left'鎴栬€�'right'
			* @return {jQuery} this
			*/
			imgRotate: function(dir) {
			   
				this.each(function() {
					if(this.tagName !== 'IMG') {return FALSE};
					var img = $(this).data('img');

					if (!img)
					{
						var img = new imgCtrler();
						img.init({el: this});
						
						img.maxWidth = $(this).parent().width();

						$(this).data('img', img);
					}
					
					img.rotate(dir);
				});
				
				return this;
			},
			/**
			 * 鏂囨湰杈撳叆妗嗗姞涓婅仛鐒︽竻绌猴紝澶辩劍鍋滅暀鎻愮ず鍔熻兘銆�<br/>
			 濡傛灉鍒╃敤{@link Xwb.ax.ValidationMgr}绫讳綔琛ㄥ崟楠岃瘉锛屽埗浣滅被浼煎姛鑳芥椂涓嶅繀鐩存帴閲囩敤璇ユ柟娉曪紝
			 Validator绫绘彁渚涗竴绯诲垪楠岃瘉鍣ㄦ棤闇€鍐欎唬鐮佸嵆鍙交鏉惧疄鐜帮紝璇﹁璇ョ被鐨勫悇绉嶉獙璇佸櫒銆�<br/>
			 濡傛灉褰撳墠鏂囨湰妗嗗凡缁忚繃{@link Xwb.ax.SelectionHolder}瀹炰緥澶勭悊锛�
			 鍒檉ocusText鏂规硶浼氬埄鐢ㄥ綋鍓峽@link Xwb.ax.SelectionHolder}瀹炰緥杈撳嚭鏂囨湰銆�
			 * @param {String} hoverText 鍋滅暀鎻愮ず鏂囧瓧
			 * @param {String} [focusStyle] 淇グ鏍峰紡绫�
			 * @param {DomSelector} [cssNode]
			 * @param {Boolean} [removeOnFocus] 濡傛灉涓篺alse锛屽綋鑱氱劍鍚庢坊鍔燾ss绫伙紝鍚﹀垯绉婚櫎css绫�
			  <pre><code>
                $('#id').focusText('杩欓噷杈撳叆鐢ㄦ埛鍚�', 'focusStyle');
              </code></pre>
			 */
			focusText : function(text, css, cssNode, removeOnFocus){
			    this.each(function(){
			        $(this).focus(function(){
			            if(this.value === text){
			                var selHolder = $(this).data('xwb_selholder');
			                if(selHolder)
			                    selHolder.setText('');
			                else this.value = '';
			            }
			            if(css){
			                if(removeOnFocus)
			                    $(cssNode||this).removeClass(css);
			                else $(cssNode||this).addClass(css);
			            }
			        })
			        .blur(function(){
			            if($.trim(this.value) === ''){
			                var selHolder = $(this).data('xwb_selholder');
			                if(selHolder)
			                    selHolder.setText(text);
			                else this.value = text;
			            }
			            if(css){
			                if(removeOnFocus)
			                    $(cssNode||this).addClass(css);
			                else $(cssNode||this).removeClass(css);
			            }
			        });
			    });
			},
			
			/**
			 * 鏂规硶浣跨敤'hidden'鏍峰紡鎺у埗鍏冪礌鐨勬樉绀烘垨闅愯棌鐘舵€併€�
			 * 濡傛灉鏃犲弬鏁帮紝杩斿洖褰撳墠鍏冪礌hidden鏍峰紡鐨勭姸鎬侊紝鍚﹀垯鍒╃敤'hidden'CSS绫昏繘琛岄殣钘忔垨鏄剧ず鍏冪礌銆�
			  <pre><code>
			    // 鑾峰緱鏄剧ず鐘舵€�
			    if ($('#id').cssDisplay()) {}
			    // 鏄剧ず
			    $('#id').cssDisplay(true);
             </code></pre>
             *@return {Boolean|jQuery}
			 */
			cssDisplay : function(b){
			    var len = this.length, jq;
			    if(len){
			        if(len === 1){
			            if(b === undefined){
			                var v = !this.hasClass('hidden');
			                return v;
			            }else {
			                if(b) this.removeClass('hidden');
			                else this.addClass('hidden');
			            }
			        }
			        
			        else {
			            this.each(function(){
    			            if(b) $(this).removeClass('hidden');
			                else $(this).addClass('hidden');
			            });
			        }
			    }
			    return this;
			},
			
            /**
             * 妫€鏌ユ槸鍚﹀惈鏈夋煇涓牱寮�,濡傛灉鏈�,娣诲姞鎴栧垹闄よ鏍峰紡.
             * @param {String} css 鏍峰紡鍚嶇О
             * @param {Boolean} addOrRemove true 鏃舵坊鍔犳牱寮忥紝false鏃剁Щ闄よ鏍峰紡
             * @return {jQuery} this
             */
			checkClass : function(cs, b){
			    if(cs){
    			    this.each(function(){
    			        var jq = $(this);
            			var hc = jq.hasClass(cs);
            			if(b){
            				if(!hc)
            				jq.addClass(cs);
            			}else if(hc){
            				jq.removeClass(cs);
            			}			        
    			    });
			    }
        		return this;
			},
			
            /**
            * 鏇挎崲view鍏冪礌鏍峰紡绫�.<br/>
            * <code>comp.switchClass('mouseoverCss', 'mouseoutCss');</code><br/>
            * @param {String} oldSty 宸插瓨鍦ㄧ殑CSS绫诲悕
            * @param {String} newSty 鏂扮殑CSS绫诲悕
            * @return {Object} this
            */
              switchClass: function(oldSty, newSty) {
                    this.each(function(){
                        var jq = $(this);
                        jq.removeClass(oldSty);
                        jq.addClass(newSty);
                    });
                    return this;
              },
              
              /**
               * 鑾峰緱鐩稿浜巚iewport鐨勪綅缃紝鍙€傜敤浜庡崟涓厓绱�
               * @return {left, top}
               */
              absolutePos : function(){
                    var off = this.offset(), doc = $(document);
                    var st  = doc.scrollTop(), sl = doc.scrollLeft();
                    off.left -= sl;
                    off.top -= st;
                    return off;
              },
              
              slideMenu : function(slideLayer, hoverCs){
                this.each(function(){
                    (function(jq){
                        var layer = jq.find(slideLayer);
                        var setTimer, clsTimer;
                        function slidedown(){
                            layer.show().cssDisplay(true);
                            if(hoverCs)
                                jq.addClass(hoverCs);
                        }
                        
                        function slideup(){
                            if(hoverCs)
                                jq.removeClass(hoverCs);
                            layer.cssDisplay(false);
                        }
                        
                        function clear() { 
                            if(setTimer){
                                clearTimeout(setTimer);
                                setTimer = false;
                            }
                            clsTimer = setTimeout(slideup, 80);
                        }
                        
                        function set(){
                            if(clsTimer){
                                clearTimeout(clsTimer);
                                clsTimer = false;
                            }
                            setTimer = setTimeout(slidedown, 100);
                        }
                        
                        jq.hover(set, clear);
                    })($(this));
                });
              },

		/**
	    * 鎴彇寰崥鍐呭
	    *@param {Number} num  浣嶇疆锛岄粯璁�10
        *@param {Boolean} hasFace  鏄惁鏄剧ず琛ㄦ儏鍥剧墖锛屽惁涓烘枃瀛椾唬鏇�
        *@param {String} postfix  鍚庣紑
        *@return jQuery
	    */
        substrText: function(num, hasFace, postfix) {
            var re = new RegExp('(?:<a.*?>.*?<\\/a>)|(?:<img.*?>)|.','gi');
            
	        this.each(function() {
                var 
                    cache = [],
                    postfix = postfix || '...',
                    text = this.innerHTML,
                    match = text.match(re);
                    
                num = num||10;
                    
                if(match && match.length > num) {
                    
                    match = match.slice(0, num).join('');

                    text = hasFace ? match : match.replace(/<img.*?title=\"(.*?)\".*?>/gi, '[$1]');
                    
                    $(this).html(text+postfix);
                }
	        });
            
            return this;
        },

		/**
	    * IE6淇PNG鍥剧墖
	    *@return jQuery
	    */
        fixPng: function() {			

            if(isIE6) {
            	var fixFn = function() {
                    if(this.tagName == 'IMG') {
                        var $img = $('<span></span>').css({
                            width: this.offsetWidth,
                            height: this.offsetHeight,
                            display: 'inline-block'
                        });
                        $img[0].style.filter = 'progid:DXImageTransform.Microsoft.AlphaImageLoader(src="'+this.src+'", sizingMethod="crop")';                        
                        $(this).replaceWith($img);
                    }
                }
                this.each(function(){
                	if(this.Complete){
                		fixFn.call(this);
                	} else {
                		this.onload = fixFn ;
                	}
                });
            }
            
            return this;
        },

        makeScroll : function(opt){
            var scrollor = this[0];
            var tid,cid;
            var dir = 1;
            var nxtBtn = $(opt.nextBtn);
            var prevBtn = $(opt.prevBtn);
            
            function updateStatus(){
                if(opt.ldisabledCs){
                    var show = scrollor.scrollWidth > scrollor.offsetWidth;
                    prevBtn.cssDisplay(show);
                    nxtBtn.cssDisplay(show);
                    prevBtn.checkClass(opt.ldisabledCs, scrollor.scrollLeft==0);
                    nxtBtn.checkClass(opt.rdisabledCs, scrollor.scrollLeft + scrollor.offsetWidth === scrollor.scrollWidth);
                }
            }
            
            opt.pace = opt.pace||25;
            
            function timer(){
                var nxt = scrollor.scrollLeft + opt.pace * dir;
                if(nxt<0)
                    nxt = 0;
                if(nxt>scrollor.scrollWidth)
                    nxt = scrollor.scrollWidth;

                scrollor.scrollLeft = nxt;
                updateStatus();
                tid = setTimeout(arguments.callee, opt.interval||20);
            }
            
            var clicked;
            function startRoll(){
                clicked = false;
                timer();
            }
            
            function leftMousedown(){
                clicked = true;
                dir = -1;
                mid = setTimeout(startRoll, 200);
                return false;
            }
            
            function rightMousedown(){
                clicked = true;
                dir = 1;
                mid = setTimeout(startRoll, 200);
                return false;             
            }
            
            function mouseup(){
                if(clicked){
                    var nxt = scrollor.scrollLeft + opt.pace * dir * 4;
                    if(nxt<0)
                        nxt = 0;
                    if(nxt>scrollor.scrollWidth)
                        nxt = scrollor.scrollWidth;
                    
                    $(scrollor).animate(
                        {scrollLeft : nxt}, 'fast', 
                        function(){ updateStatus();}
                    );
                }
                
                clearTimeout(tid);
                clearTimeout(mid);
            }
            
            if(opt.nextBtn)
                $(opt.nextBtn).mousedown(rightMousedown).mouseup(mouseup).click(function(){return false;});
            
            if(opt.prevBtn)
                $(opt.prevBtn).mousedown(leftMousedown).mouseup(mouseup).click(function(){return false;});
          
            var offw = 0;
            if(opt.items){
                $(scrollor).find(opt.items).each(function(){
                    offw+=this.offsetWidth;
                });
                $(scrollor).find(opt.ct||':first-child').css('width', offw);
            }
            
            updateStatus();
            
            return opt;
        }
	});
	
/**
 * jQuery 楂樹寒鏌ユ壘鎻掍欢锛屽畬鍏ㄥ熀浜庢枃鏈粨鐐圭殑DOM鎿嶄綔锛屽鍘熸湁闈炴枃鏈粨鐐逛笉閫犳垚浠讳綍褰卞搷銆�
 * @param {Array} keywords 楂樹寒鏂囨湰鏁扮粍
 * @param {String} [style] 搴旂敤浜庨珮浜枃鏈殑鏍峰紡绫�
 * 鐢ㄦ硶:
 <pre><code>
   楂樹寒:
   $('#ss').highlight(['keyword', ... ]);
   娓呴櫎:
   $('#ss').clearHighlight();
   
   鎴栬€呬紶閫掕嚜瀹氫箟鐨勬牱寮忕被
   $('#ss').highlight(['keyword', ... ], 'cssClass');
   娓呴櫎:
   $('#ss').clearhighlight('cssClass');   
 </code></pre>
 璇风‘淇濇瘡娆￠珮浜墠娓呴櫎鍏堝墠宸查珮浜唴瀹广€�
 鍙€夐厤缃俊鎭細$.fn.highlight.cls
 * @method highlight
*/
 
(function(){

// private
var IGNORE,S,ESC,LT,GT, inited;

// private
function init(){
    IGNORE = /INPUT|IMG|SCRIPT|STYLE|HEAD|MAP|AREA|TEXTAREA|SELECT|META|OBJECT|IFRAME/i;
    S      = /^\s+$/;
    ESC    = /(\.|\\|\/|\*|\?|\+|\[|\(|\)|\]|\{|\}|\^|\$|\|)/g;
    LT     = /</g;
    GT     = />/g;
    inited = TRUE;
}

// entry
function entry(keys, cls){
    if(!inited)
      init();
  
    if(typeof keys === 'string')
      keys = $.trim(keys).split(S);

    // normalize
    var arr = [];
    for(var i=0,len=keys.length;i<len;i++){
       if(keys[i] && !S.test(keys[i])) {
          arr[arr.length] = keys[i].replace(LT, '&lt;')
                                   .replace(GT, '&gt;')
                                   .replace(ESC, '\\$1');
       }
    }
    var reg     = new RegExp('(' + arr.join('|') + ')', 'gi'),
        placing = '<span class="'+(cls||entry.cls)+'">$1</span>',
        div     = document.createElement('DIV');
    this.each(function(){
      highlightEl(this, reg, placing, div);
    });

	return this;
}

// public
$.fn.highlight = entry;
/**
 * 娓呴櫎楂樹寒
 * @see {@link #highlight}
 * @method clearHighlight
 */
$.fn.clearHighlight = function(cls) {
  if(!inited)
    init();
  var cls = cls||entry.cls;
  this.each(function(){
    clearElhighlight(this, cls);
  });
};


// private
function replaceTextNode(textNode, reg, placing, div) {
  var data = textNode.data;
  if(!S.test(data)){
     data = data.replace(LT, '&lt;').replace(GT, '&gt;');
     if(reg.test(data)){
        if(!div)
          div = document.createElement('DIV');
        // html escape
        div.innerHTML = data.replace(reg, placing);
        // copy nodes
        var chs = div.childNodes,
            arr = [],
            fr = document.createDocumentFragment();
        
        // copy to array
        for(var i=0,len=chs.length;i<len;i++)
          arr[i] = chs[i];
        
        for(i=0;i<len;i++)
          fr.appendChild(arr[i]);
        
        textNode.parentNode.replaceChild(fr, textNode);
     }
  }
}

// private
function highlightEl(el, reg, placing, div){
    var chs = el.childNodes, 
        arr = [], i, len, nd;
      
      // copy to array
      for(i=0,len=chs.length;i<len;i++){
        if(!IGNORE.test( chs[i].tagName ))
          arr.push(chs[i]);
      }
      
      for(i=0,len=arr.length;i<len;i++){
        nd = arr[i];
        // textnode
        if(nd.nodeType === 3){
          try { 
            replaceTextNode(nd, reg, placing, div);
          }catch(e){}
        }else arguments.callee(nd, reg, placing, div);
      }
}


// private
function clearElhighlight(el, cls) {
  var chs = el.childNodes, 
      arr = [], i, len, nd, t;
      
  // copy to array
  for(i=0,len=chs.length;i<len;i++){
    if(!IGNORE.test( chs[i].tagName ))
    arr.push(chs[i]);
  }

  for(i=0,len=arr.length;i<len;i++){
    nd = arr[i];
    t = nd.nodeType;
    // textnode
    if(t === 3)
      continue;
    // span
    if(t === 1 && nd.tagName === 'SPAN' && nd.className === cls){
      // merge text nodes
      var textNode = nd.childNodes[0], 
          p        = nd.parentNode,
          pre      = nd.previousSibling,
          nxt      = nd.nextSibling;
      
      if(pre && pre.nodeType === 3){
        p.removeChild(pre);
        textNode.data = pre.data + textNode.data;
      }
      
      if(nxt && nxt.nodeType === 3){
        p.removeChild(nxt);
        textNode.data = textNode.data + nxt.data;
      }

      p.replaceChild(textNode, nd);
    }else arguments.callee(nd, cls);
  }
}

entry.cls = 'search-txt';

})();

/**
 * 鑾峰緱鎴栬缃甤ookie
 * @param {String} name
 * @param {String} value
 * @param {Object} options
 * @method
 */
$.cookie = function(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === NULL) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        // CAUTION: Needed to parenthesize options.path and options.domain
        // in the following expressions, otherwise they evaluate to undefined
        // in the packed version for some reason...
        var path = options.path ? '; path=' + (options.path) : '';
        var domain = options.domain ? '; domain=' + (options.domain) : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = NULL;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};

})(Xwb, jQuery);
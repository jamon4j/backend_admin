(function(X, $){
/**
 * @class Xwb.mod.Validators
 * 甯哥敤琛ㄥ崟楠岃瘉鍣ㄩ泦鍚�
 */


Xwb.ax.ValidationMgr.prototype

/**
 * @event ft
 * 鑾峰緱鐒︾偣鏃舵竻绌烘彁绀猴紝澶卞幓鐒︾偣鏃跺嚭鐜版彁绀烘晥鏋�
 * _ft|ft缁撳悎浣跨敤锛宊ft鍦ㄨ〃鍗曞垵濮嬪寲鏃剁粦瀹歠ocus/blur浜嬩欢銆�
 * ft鍦ㄩ獙璇佹椂鍋囧鍏冪礌鍊间负榛樿鎻愮ず瀛楃涓叉椂锛岃缃獙璇佸厓绱犲€间负绌恒€�<br/>
 * _ft鏃惰皟鐢╦q.focusText鏂规硶鍒涘缓鏁堟灉
 * 褰撳瓧娈靛繀椤绘椂锛屼娇鐢╪e妫€娴嬶紝
 * 褰撳瓧娈甸潪蹇呴』鏃讹紝浣跨敤ft妫€娴�
 * 鐢ㄦ硶鍙弬瑙佲€滄剰瑙佸弽棣堚€濆璇濇琛ㄥ崟
* <pre><code>
    // 涓嬮潰浣滅敤鏄紝鍏冪礌澶辩劍鏃舵樉绀衡€滈偖绠卞湴鍧€鈥濓紝鑱氱劍鏃舵竻绌哄厓绱犲€笺€�
    // 褰撴樉绀衡€滈偖绠卞湴鍧€鈥濇椂锛屽苟涓嶅奖鍝嶅厓绱犲疄闄呪€滅┖鈥濆€笺€�
    &lt;input type=&quot;text&quot; value=&quot;閭鍦板潃&quot; name=&quot;mail&quot; class=&quot;input-define&quot; warntip=&quot;#feedbackTip&quot; vrel=&quot;_ft|ft|mail&quot;&gt;
 </code></pre>
 */
.reg('ft', function(elem, v, data, next){
    // 鍒濆鍖栨椂
    if(arguments.length == 2){
        var text = elem.value||arguments[1].w;
        $(elem).data('vrel_ft_text', text).focusText(text);
    }else {
        // 楠岃瘉鏃�
        var text = $(elem).data('vrel_ft_text');
        // args:elem, v, data, next
        // 褰撴潯浠舵弧瓒虫椂锛岄噸缃负绌哄€�
        if(v == text)
            this.setValue('');
            
        this.report(true, data);
        next();
    }
})

/**
 * @event ne
 * 绌烘娴嬶紝鏍囪浣滀负鍏冪礌蹇呴』瀛楁銆�
 * <pre>
   vrel="ne=w:鍦ㄨ繖閲岃緭鍏ュ悕绉�,m:璇疯緭鍏ユ樀绉�"
 * </pre>
 * @param {String} [m] 鍑洪敊鎻愮ず鏂囧瓧
 */
.reg('ne', function(elem, v, data, next){
    
    var em = v === '';
    var text = $(elem).data('vrel_ft_text') || data.w;
    if( !em && text ){
        em = v == text;
        // 閲嶇疆涓虹┖锛岄槻鍋滅暀瀛楀共鎵板悗鏉ョ殑楠岃瘉鍣�
        if(em)
            this.setValue('');
    }

    if(em && !data.m)
        data.m = '璇ラ」涓嶈兘涓虹┖';

    if (elem.tagName === 'INPUT' && ( elem.type === 'radio' || elem.type === 'checkbox' )) 
        em = !elem.checked;
        
    this.report(!em, data);

    next();
})

/**
 * @event f
 * 澶卞幓鐒︾偣鏃堕獙璇佸厓绱�
 * <pre>
    // 澶卞幓鐒︾偣鏃堕獙璇佸厓绱犳槸鍚︿负绌�
    vrel="_f|ne"
 * </pre>
 */
.reg('f', function(elem, data){
    var chain = this;
    var tn = elem.tagName.toLowerCase();
    if(chain.context.comForm && (tn == 'input' || tn == 'textarea')){//璁剧疆鐒︾偣鏍峰紡
    	var fc = chain.context.focusCss;
	    $(elem).blur(function(){
	    	$(this).removeClass(fc);
	    	if(!data.ch)//澶卞幓鐒︾偣妫€娴�
	        	chain.validate();
	    }).focus(function(){
	    	$(this).removeClass(chain.context.errCss);
	    	$(this).addClass(fc);
	    });
    }else{
	    $(elem).blur(function(){
	        chain.validate();
	    });
    }
})

/**
 * @event sz
 * 妫€娴嬭緭鍏ュ瓧绗﹂暱搴�
 * 灞炴€э細
    <div class="mdetail-params">
    <ul>
    <li>max=number锛屽厑璁告渶澶у瓧鑺傞暱搴�</li>
    <li>min=number锛屽厑璁告渶灏忓瓧鑺傞暱搴�</li>
    <li>ww, wide code缂╁啓锛屽皢闀垮害鎸夊瀛楃璁＄畻锛屼竴涓眽瀛椾袱涓瓧鑺傞暱搴�</li>
    <li></li>
    </ul>
    </div>
    渚嬶細
    <pre>
        sz=max:6,min:4,m:鏈€灏戜袱涓眽瀛楋紝鏈€澶氫笁涓眽瀛�,ww
    </pre>
 * @param {Number} max 鏈€澶ч暱搴�
 * @param {Number} min 鏈€灏忛暱搴�
 * @param {Boolean} ww 鍙栧€�1鎴�0锛屾寚鏄庨暱搴﹀崟浣嶆槸鍚︽寜瀹藉瓧绗﹂暱搴﹁绠楋紝瀹藉瓧绗﹀崟浣嶄负2锛屼竴涓瓧涓や釜瀛楁瘝
 */
.reg('sz', function(elem, v, data, next){
    // 鍏佽绌�
   if(v){
       var len = data.ww ? Xwb.util.byteLen(v) : v.length,
           err, 
           max = data.max, 
           min = data.min;
       if(max !== undefined && parseInt(max) < len)
            err = true;
       if(min !== undefined && parseInt(min)>len)
            err = true;
       this.report(!err, data);
   }else this.report(true, data);
   
   next();
})
/**
 * @event dt
 * 妫€鏌ユ槸鍚︽湁鏁堢殑鏃ユ湡鏍煎紡
 * <pre><code>
 * vrel="dt"
 * </code></pre>
 */

.reg('dt', function(elem, v, data, next){
    if(v){
        if(!data.m)
            data.m = '涓嶆槸鏈夋晥鐨勬棩鏈熸牸寮�';
        var d = Date.parse(v);
        // 鍙互鍦ㄨ繖鎵╁睍max,min绛�
        this.report(!isNaN(d), data);
    }else this.report(true, data);
    next();
})
/**
 * @event mail
 * 楠岃瘉鏄惁涓洪偖绠辨牸寮�
 * <pre><code>
 * vrel="mail"
 * </code></pre>
 */

.reg('mail', function(elem, v, data, next){
    if(v){
    	var result = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/.test(v);
        if(!data.m && data.m !== 0)
            data.m = '璇疯緭鍏ユ纭殑閭鏍煎紡';
    	this.report(result, data);
    }else this.report(true, data);
	next();
})
/**
 * @event int
 * 楠岃瘉鏄惁涓烘暟瀛楁牸寮�
 * <pre><code>
 * vrel="int|sz=max:5,min:1"
 * </code></pre>
 */

.reg('int', function(elem, v, data, next) {
    if(v !== ''){
    	var result = v && /^\d+$/.test(v);
        if(!data.m && data.m !== 0)
            data.m = '璇ラ」涓烘暟瀛楁牸寮�';
    	this.report(result, data);
    }else this.report(true, data);
	next();
})

/**
 * @event bt
 * 楠岃瘉鏄€兼槸鍚﹀湪鏌愪釜鏁存暟鑼冨洿鍐�
 * <pre><code>
 * vrel="bt=max:1000,min:1"
 * </code></pre>
 * @param {Number} max 
 * @param {Number} min
 */

.reg('bt', function(elem, v, data, next) {
    if(v !== ''){
    	var min = parseInt(data.min),
    		max = parseInt(data.max),
    		v = parseInt(v),
    		err = 0;
    
    	if (v < min)
    		err = 1;
    
    	if (!err && (v > max))
    		err = 2
    
    	this.report(!err, data);
    }else this.report(true, data);
	next();
})
/**
 * @event sinan
 * 妫€鏌ユ樀绉版槸鍚︿负闈炴硶瀛楃銆傞潪娉曞瓧绗︽寚闄や腑鑻辨枃銆佹暟瀛椼€�"_"鎴栧噺鍙风鍙峰鐨勬墍鏈夊瓧绗�
 * <pre><code>
 * vrel="sinan"
 * </code></pre>
 */

.reg('sinan', function(elem, v, data, next){
    if(v){
        if(!data.m)
            data.m = '鏀寔涓嫳鏂囥€佹暟瀛椼€�"_"鎴栧噺鍙�';
        this.report(/^[a-zA-Z0-9\u4e00-\u9fa5_-]+$/.test(v), data);
    }else this.report(true, data);
    next();
});

})(Xwb, $);
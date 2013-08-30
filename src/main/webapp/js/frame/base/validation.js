(function(){

/**
 * @class Xwb.ax.ElementValidationContext
 * 杩欐槸涓帴鍙ｇ被锛屾帴鍙ｆ柟娉曚负{@link Xwb.ax.ElememtValidatorChain}绫绘墍鐢紝
 * 鍙互鎻愪緵婊¤冻楠岃瘉鍗曚釜鍏冪礌鎵€闇€鐨勬墍鏈夋暟鎹€�<br/>
 * Xwb宸叉湁涓€涓鎺ュ彛鐨勫疄鐜扮被{@link Xwb.ax.ValidationMgr}锛岄€氬父鎯呭喌涓嬫棤闇€鍏虫敞姝ゆ帴鍙ｏ紝
 * 寮€鍙戜腑鏇翠笉浼氱洿鎺ュ簲鐢ㄦ湰鎺ュ彛銆�
 * 璇ユ帴鍙ｄ富瑕佺殑浣滅敤鏈夛細
 * <ul>
 * <li>鎻愪緵楠岃瘉鍏冪礌鐨勯獙璇佸櫒</li>
 * <li>楠岃瘉鎴愬姛鎴栧け璐ユ姤鍛婂叆鍙�</li>
 * <li>鎻愪緵楠岃瘉鎴愬姛鎴栧け璐ョ殑澶勭悊鍏ュ彛</li>
 * <li>鎻愪緵褰撳墠浣跨敤鐨勮〃鍗曢獙璇佺鐞嗗櫒</li>
 * </ul>
 * @constructor
 * @param {Xwb.ax.ElementValidationContext}
 * @param {HTMLElement} element
 */

/**
 * 鑾峰緱绗琲涓獙璇佸櫒,鏃犲搴斾笅鏍囧€艰繑鍥炵┖
 * @param {Number} index
 * @param {HtmlElement} element
 * @method nextValidatorData
 */

/**
 * 楠岃瘉鍣ㄦ姤鍛婇獙璇佺粨鏋�
 * @param {Boolean} result
 * @param {Object} data
 * @param {HtmlElement} element
 * @param {Xwb.ax.ElememtValidatorChain} elementChain
 * @method report
 */

/**
 * 鏍规嵁鍚嶇О杩斿洖楠岃瘉鏂规硶
 * @param {String} name
 * @method getValidator
 */

/**
 * callback锛屾瘡涓獙璇佸櫒楠岃瘉澶辫触鍚庨兘瑙﹀彂涓€娆�
 * @param {HtmlElement} element
 * @param {Object} data
 * @param {Xwb.ax.ElememtValidatorChain} chain
 * @method onerror
 */

/**
 * callback锛屽湪楠岃瘉鍏冪礌杩囩▼涓垚鍔熷悗鍙細瑙﹀彂涓€娆�
 * @param {HtmlElement} element
 * @param {Xwb.ax.ElememtValidatorChain} chain
 * @method onpass
 */

/**
 * 杩斿洖琛ㄥ崟楠岃瘉绠＄悊鍣�
 * @param {Xwb.ax.ValidationMgr} mgr
 * @method getMgr
 */

var X = Xwb;
var Util = X.util;

// getElememtExtraValidator ( elemName ) {}
// report(elem ,elem.value)
function ElememtValidatorChain(context, elem){
    this.elem = elem;
    this.context = context;
    this.nextChain = Util.bind(this._doNextChain, this);
}

/**
 * @class Xwb.ax.ElememtValidatorChain
 * 鏈被鍒╃敤鎺ュ彛{@link Xwb.ax.ElementValidationContext}鎻愪緵鐨勬柟娉曚笌鏁版嵁楠岃瘉鍗曚釜鍏冪礌銆�<br/>
 * 鍚屼竴绫诲疄渚嬩笉鍏佽鍦ㄩ獙璇佹湭缁撴潫鏃跺紑濮嬫柊鐨勯獙璇侊紝鍚﹀垯鎶涘嚭"Concurrent validation exception"寮傚父銆�<br/>
 * 涓€鑸儏鍐典笅锛屼笉蹇呯洿鎺ュ疄渚嬪寲璇ョ被瀵瑰崟涓厓绱犺繘琛岄獙璇併€備絾鑻ヨ瀹炰緥鍖栵紝鍙€氳繃浠ヤ笅鏂规硶锛� 
* <pre><code>
 // context
 var mgr = new Xwb.ax.ValidationMgr({
    form : '#myform'
 });

 var chain = new Xwb.ax.ElememtValidatorChain(mgr, '#userName');
 chain.validate();
 
 鎴栬€�
 
 mgr.validateElement('#userName');
 
 </code></pre>

 */
ElememtValidatorChain.prototype = {
    /**
     * @property error
     * 閿欒娆℃暟锛屽彲鍦╟allback璋冪敤鏃舵娴嬪綋鍓嶉獙璇佸悗鐨勯敊璇鏁�
     * @type Number
     */
     
    // error : 0,
    
    /**
     * @property validating
     * 鏄惁楠岃瘉涓�
     * @type Boolean
     */
 
    /**
     * 楠岃瘉鍏冪礌
     * @param {Boolean} callback 楠岃瘉缁撴潫鍚庡洖璋冿紝
     * callback璋冪敤鏃秚his鎸囧悜褰撳墠{@link Xwb.ax.ElememtValidatorChain}瀹炰緥
     */
    validate : function(callback){
        // 绂佹鍚屾椂楠岃瘉
        if(this.validating)
            throw 'Concurrent validation exception.';

        this.setValue(this._getRawValue( this.elem ));

        this._finalChain = callback;
        this.idx = -1;
        this.error = 0;
        this.msgs = [];

        this._doNextChain();
    },
    
    // private
    _getRawValue : function(elem){
        var v = elem.value;
        // 瀵瑰瓧绗︿覆鍊煎仛trim澶勭悊
        if( typeof v === 'string' )
            v = $.trim(v);
        return v;
    },
    

/**
 * 楠岃瘉杩囩▼涓紝鍙互閫氳繃璇ユ柟娉曡幏寰楀厓绱犳渶鏂板€笺€�<br/>
 * 涓嶅缓璁洿鎺ラ€氳繃element.value鑾峰緱鍏冪礌鍊硷紝鍥犱负璇ュ€煎彲鑳藉苟涓嶆槸鐪熸鎰忎箟涓婄殑鍏冪礌鍊笺€�
 * @return {Object}
 */
    getValue : function(){
        return this.value;
    },
    
/**
 * 楠岃瘉杩囩▼涓紝鍙€氳繃璇ユ柟娉曡缃厓绱犲€�
 * @param {Object} value 鍏冪礌鍊�
 */
    setValue : function(v){
        this.value = v;
    },

/**
 * 鑾峰緱楠岃瘉杩囩▼涓嚭鐜扮殑鎵€鏈夐敊璇�
 * @return {Array}
 */
    getErrors : function(){
        return this.msgs;
    },

/**
 * 杩藉姞閿欒鎻愮ず
 * @param {String} message
 */
    addError : function(msg){
        this.msgs.push(msg);
    },

    /**
     * 瀵归獙璇佸厓绱犳墽琛岄澶勭悊
     */
    doPrehandling : function(){
        var ctx = this.context;
        var idx = 0;
        var cmd; // cmd[name, data]
        
        while( (cmd = ctx.nextValidatorData(idx++, this.elem)) && cmd.isPreCmd){
            if(typeof cmd[0] === 'string'){
                var vd = ctx.getValidator(cmd[0]);
                if(!vd)
                    throw 'ValidationException:Undefined validator \''+cmd[0]+'\' in element '+this.elem.name;
                vd.call(this, this.elem, cmd[1]);
            }else cmd[0].call(this, this.elem, cmd[1]);
        }
    },
    
    // private
    _doNextChain : function(){
        this.idx++;
        var vds = this.context.nextValidatorData(this.idx, this.elem);
        if(vds){
            // 璺宠繃棰勫鐞�
            if(vds.isPreCmd) {
                this.nextChain();
            }else{
                 // 鍗曚釜楠岃瘉鍣�
                 if (typeof vds === 'function'){
                     // 瀵勫瓨鏁版嵁
                     vds[1] = {};
                     vds.call(this, this.elem, this.getValue(), vds[1] , this.nextChain);    
                 }else {
                    // 楠岃瘉鍣ㄦ爣璇嗗瓧绗︿覆
                    // 缁撴瀯涓篬sid, data]
                    // 楠岃瘉鍣ㄥ洖璋冨弬鏁颁负 this.validator(elem, value, data, next)
                    if(typeof vds[0] === 'string'){
                        var vd = this.context.getValidator(vds[0]);
                        if(!vd)
                            throw 'ValidationException:Undefined validator ['+vds[0]+'] in element '+this.elem.name;
                        vd.call(this, this.elem, this.getValue(), vds[1], this.nextChain);
                    }else vds[0].call(this, this.elem, this.getValue(), vds[1], this.nextChain);
                 }
            }
        }else {
            if(!this.error){
                if(__debug) console.log('onpass', this.elem, this);
                this.context.onpass(this.elem, this);
            }
            // 缁撴潫
            this._finalChain &&  this._finalChain();
            this._finalChain = null;
        }
    },
    
    /**
     * 楠岃瘉鍣ㄨ皟鐢ㄨ鏂规硶姹囨姤楠岃瘉鎯呭喌
     * @param {Boolean} result 鎴愬姛浼犻€抰rue锛屽惁鍒欎紶閫抐alse
     * @param {Object} 鍏冪礌rel鏁版嵁
     */
    report : function(result, data){
        if(!result)
            this.error++;

        var elem = this.elem;
        
        // 浼犻€掔粰context
        this.context.report(result, data, elem, this);
        
        if(!result){
            if(__debug) console.log('onerror', elem, data);
            this.context.onerror(elem, data, this);
        }
    }
};


var validators;

X.ax.ValidationMgr = function(cfg){
    $.extend(this, cfg);
    this.init();
};

/**
 * @class Xwb.ax.ValidationMgr
 * @extends Xwb.ax.ElementValidationContext
 * 琛ㄥ崟楠岃瘉绫伙紝瀵硅〃鍗曚腑鎵€鏈夐渶瑕侀獙璇佺殑鍏冪礌鎵ц楠岃瘉澶勭悊銆�<br/>
 * 鏈被鎻愪緵涓庡叿浣撳簲鐢ㄦ棤鍏崇殑鏂瑰紡楠岃瘉甯哥敤鐨勮〃鍗曟暟鎹殑鏈夋晥鎬с€�<br/>
 * 鏀寔鍏冪礌鍚屾涓庡紓姝ラ獙璇併€�<br/>
 * <b>寤鸿姣忎釜琛ㄥ崟閮界敤form鍏冪礌鎻愪氦銆�</b><br/>
 * 濡傛灉琛ㄥ崟鏃爄nput鎻愪氦鎸夐挳锛屽彲闅愯棌涓€涓猼ype=submit鐨刬nput鎸夐挳锛屾柟渚跨敤鎴锋寜鍥炶溅鍚庢彁浜よ〃鍗曘€�<br/>
 * 楠岃瘉琛ㄥ崟閫氬父闇€瑕佷袱涓楠わ細
 * <ul>
 * <li>鍦ㄥ厓绱爃tml妯℃澘涓婂垱寤洪獙璇佽鍏冪礌鎵€闇€鐨勫睘鎬с€傚叾涓�<b>vrel</b>鏄繀椤诲睘鎬э紝寮曞叆璇ュ厓绱犵殑楠岃瘉鍣�</li>
 * <li>鍦↗avaScript鏂囦欢涓埄鐢ㄦ湰绫诲垵濮嬪寲琛ㄥ崟</li>
 * </ul>
 * 褰撴墍鏈夐獙璇侀€氳繃鍚庯紝鍥炶皟{@link #onsuccess}鏂规硶锛屽彲浠ュ湪鏂规硶鍐呰繑鍥瀎alse鍊煎彇娑堣〃鍗曠殑鎻愪氦琛屼负銆�<br/>
 * 浠€涔堟槸楠岃瘉鍣紵<br/>
 * 楠岃瘉鍣ㄥ叾瀹炲氨鏄竴涓洖璋冨嚱鏁帮紝闄や簡搴撴彁渚涗竴濂楅€氱敤鐨勯獙璇佸櫒澶栵紝涔熷彲鑷娉ㄥ唽楠岃瘉鍣ㄣ€�<br/>
 * 姣忎釜楠岃瘉鍣ㄩ兘搴旂敤涓€涓爣璇嗭紝閫氳繃{@link #reg}鏂规硶娉ㄥ唽楠岃瘉鍣ㄣ€�<br/>
 * 楠岃瘉鍣ㄦ爣璇嗗瓧绗︿覆琚獺TML妯℃澘鐨勮〃鍗曞厓绱�<b>vrel</b>灞炴€у紩鐢紝鎸囨槑楠岃瘉璇ュ厓绱犳墍闇€鐨勯獙璇佸櫒銆�<br/>
 * 濡傛灉鍦╲rel灞炴€ч噷鐨勯獙璇佸櫒鏍囪瘑鍔犱笅鍒掔嚎锛岄偅涔堟鏃剁殑楠岃瘉鍣ㄥ氨鏄竴涓澶勭悊鍣紝
 * 鍦ㄨ〃鍗曢獙璇佺鐞嗗櫒鍒濆鍖栧悗灏变細鎵ц锛岃€屼笖鍙細鎵ц涓€娆°€�<br/>
 * 楠岃瘉鍣╮el灞炴€ф暟鎹牸寮忔槸浠€涔堬紵<br/>
 * rel灞炴€ф渶缁堣瑙ｆ瀽涓篔avaScript瀵硅薄锛屽畠鏄竴涓敭鍊煎锛屾牸寮忎负<br/>
 * identifier=key:value,key:value,...|identifier=key:value,key:value,...|...<br/>
 * value鏁版嵁闇€鐢╘杞箟':'锛�','瀛楃锛屽\,琛ㄧず鍗曚釜','銆�<br/>
 * 濡傛灉rel鏁版嵁鏈塇TML淇濈暀瀛楃锛岃鍏堢敤&xx;杞箟锛屾瘮濡傚弻寮曞彿銆�
 * 灞炴€у唴瀹圭敱楠岃瘉鍣ㄨ嚜宸卞畾涔夈€�<br/>
 * 渚嬶細
 * <pre><code>
 html:
 &lt;input name=&quot;nick&quot; vrel=&quot;_f|sinan|ne=m:Name can not be empty&quot; warntip=&quot;#nickTip&quot; /&gt;
 涓婇潰鍖呭惈鍏冪礌楠岃瘉鐨勫嚑涓俊鎭細
 vrel -> 鎸囨槑鎵€鐢ㄥ埌鐨勯獙璇佸櫒鏈塮, sina, ne锛屽叾涓璮鏄湪楠岃瘉绠＄悊鍣ㄨ浇鍏ユ椂灏辨墽琛岀殑銆�
 warntip -> 鎸囨槑鍏冪礌楠岃瘉澶辫触鏃舵彁绀轰俊鎭墍鍦ㄧ殑HTML鍏冪礌
 -----
 JavaScript鍒濆渚嬪锛�
 
 Xwb.use('Validator', {
     // 褰撳墠琛ㄥ崟form鍏冪礌
     form:jq.find('#showForm'),
     // 瑙﹀彂鎻愪氦鎸夐挳
     trigger : jq.find('#trig'),
     onsuccess : function(data, next){
         Xwb.request.updateShowProfile(data, function( e ){
             if(e.isOk()){
                 Xwb.ui.MsgBox.success('', '鏄剧ず璁剧疆宸蹭繚瀛樸€�');
             }else Xwb.ui.MsgBox.error('', e.getMsg());
                 
             next();
         });
         // 闈濬ORM鎻愪氦杩斿洖false
         return false;
     },
     // 濡傛灉宸叉湁鐨勯獙璇佸櫒婊¤冻涓嶄簡闇€姹傦紝
     // 涔熷彲浠ラ€氳繃validators灞炴€у畾涔夊綋鍓嶉獙璇佺鐞嗗櫒涓叾瀹冪殑楠岃瘉鍣�
     validators : {
        checkusername : function(elem, v, data, next){
            // ...
        },
        //...
     }
 });
 </code></pre>
 * 楠岃瘉搴旂敤渚嬪瓙鍙傝/mod/mysetting.js,楠岃瘉鍣ㄦ敞鍐屼緥瀛愬弬瑙�/mod/validators.js銆�<br/>
 */


/**
 * @cfg {Boolean} useCache 鏄惁缂撳瓨rel鍊�
 */

/**
 * @cfg {jQuery} form html form鍏冪礌
 */
/**
 * @cfg {jQuery} trigger 濡傛灉瑙﹀彂鎻愪氦鐨勬寜閽笉鏄痠nput鎸夐挳锛屽彲浠ユ寚瀹氬叾瀹冩寜閽厓绱狅紝input鎸夐挳鍒欎笉鐢ㄦ寚瀹�
 */
X.ax.ValidationMgr.prototype = {
    
    // implemention
    nextValidatorData : function(idx, elem){
        var vds = this._getBindingValidators(elem);
        return vds && vds[idx];
    },
    
    // implemention
    getValidator : function( name ){
        // 鍏堣嚜韬煡鎵撅紝鍐嶅叏灞€鏌ユ壘銆�
        var selfVds = this.validators;
        var fnd;
        if(selfVds)
            fnd = selfVds[name];
        
        // 鍏ㄥ眬
        if(!fnd && validators)
            fnd = validators[name];
        return fnd;
    },
    
    // implemention    
    report : function(result, data, elem, chain){
        if(!result) {
            // 鍙湁鍦ㄨ〃鍗曢獙璇佹椂璁＄畻锛屽崟涓厓绱犻獙璇佸拷鐣�
            if(chain.multival){
                // 鍏ㄥ眬绱error
                this.error++;
                if( this.error === 1 && // 棣栨鍑洪敊
                    this.autoFocus){
                    try{elem.focus();}catch(e){}
                }
            }
        }
    },
    
    // implemention
    getMgr : function(){ return this; },
    
    /** @cfg {Boolean} trigOnSubmit 鏄惁鎻愪氦鏃惰Е鍙戦獙璇侊紝榛樿true*/
    trigOnSubmit : true,
    
    onfinal   : $.noop,
    
    /**
     * @cfg {Boolean} autoFocus 楠岃瘉澶辫触鏃舵槸鍚﹁仛鐒﹀埌鍏冪礌涓婏紝榛樿true
     */
    autoFocus : true,
    
    init : function(){
        if(!validators)
            validators = {};
        
        this.nextChain = Util.getBind( this, '_doNextChain' );
        
        this.form = $(this.form)[0];
        
        var self = this;
        
        // 鎵ц棰勫鐞�
        
        $.each(this._getElements(), function(){
            var chain = new ElememtValidatorChain(self, this);
            chain.doPrehandling();
        });
        
        var trigFn = function(){
                self.validate();
                return false;
        };
        
        if( this.trigOnSubmit )
            this.form.onsubmit = trigFn;
        
        if( this.trigger ){
            this.trigger = $(this.trigger)[0];
            $(this.trigger).click(function(){
               return trigFn();
            });
        }
    },
    
    /**
     * 楠岃瘉鍗曚釜鍏冪礌
     * @param {HtmlElement} element
     */
    validateElement : function(elem){
        if(!this.isGlobalVal){
            this._validateElem(elem);
        }
    },
    
    /**
     * 鎵ц琛ㄥ崟楠岃瘉銆傞€氬父涓嶇敤鎵嬪姩璋冪敤鏈柟娉曢獙璇佽〃鍗曪紝鑰屾槸鐢辩敤鎴疯Е鍙戦獙璇併€�
     */
    validate : function(){
        // 闃叉閲嶅鎻愪氦
        if (!this.isGlobalVal) {
            // 瀵勫瓨鎵€鏈夐渶瑕侀獙璇佺殑鍏冪礌
            this.elems = this._getElements();
            // 褰撳墠楠岃瘉鐨勫厓绱犱笅鏍�
            this.currElIdx = -1;
            // 鍏ㄥ眬閿欒娆℃暟
            this.error = 0;
            // 鏄惁楠岃瘉涓�
            this.isGlobalVal = true;
            // 瀛樻斁鍏冪礌鍊�
            this.data = {};
            // 棰勫畾涔夊弬鏁�
            if( this.param )
               $.extend( this.data, this.param );
            
            // 褰撳墠楠岃瘉鍛ㄦ湡ID
            this.uniqueId = Util.uniqueId();
        
            if(this.decorateTrigger && this.trigger)
                Util.disable(this.trigger, true);
            this._doNextChain();
        }
        return false;
    },
    /**
     * 娉ㄥ唽琛ㄥ崟鍏冪礌楠岃瘉鍣ㄣ€�<br/>
     * 楠岃瘉鍣ㄨ皟鐢ㄦ牸寮忥細<br/>
     * validator(element, value, data, next);<br/>
     * element:褰撳墠楠岃瘉鍏冪礌<br/>
     * value:褰撳墠楠岃瘉鍏冪礌鍊�<br/>
     * data:褰撳墠楠岃瘉鍣╮el鏁版嵁<br/>
     * next:楠岃瘉缁撴潫鍚庣殑璋冪敤鍑芥暟 锛屼紶閫掍笅涓€涓獙璇�<br/>
     * 娉ㄦ剰锛氶獙璇佸厓绱犲€煎墠闄や簡绌烘娴嬪鍏跺畠鎯呭喌閮藉簲璇ヨ涓虹┖鍊兼槸鍙互<b>閫氳繃</b>鐨勩€�
     * @param {String} name 楠岃瘉鍣ㄥ悕绉� 
     * @param {Function} validator 楠岃瘉鏂规硶
     * <pre><code>
        渚嬶細
        // 妫€鏌ユ槸鍚︽湁鏁堢殑鏃ユ湡鏍煎紡
        reg('dt', function(elem, v, data, next){
            // 娉ㄦ剰锛氶獙璇佸厓绱犲€煎墠闄や簡绌烘娴嬮獙璇佸櫒澶�
            // 鍏跺畠鎯呭喌閮藉簲璇ヨ涓虹┖鍊兼槸鍙互閫氳繃鐨�
            // 鎵€浠ヨ繖閲屽拷鐣ョ┖妫€鏌�
            if(v){
                // 璁剧疆榛樿鐨勬彁绀�
                if(!data.m)
                    data.m = '涓嶆槸鏈夋晥鐨勬棩鏈熸牸寮�';
                var d = Date.parse(v);
                // 鎶ュ憡楠岃瘉缁撴灉
                this.report(!isNaN(d), data);
            }else this.report(true, data);
            next();
        });
     </code></pre>
     */
    reg : function(cmd, validator) {
        if(!validators)
            validators = {};
        if($.isArray(cmd)){
            for(var i=0,len=cmd.length;i<len;i++){
                this.reg.apply(this, cmd[i]);
            }
        }else validators[cmd] = validator;
        return this;
    },
    
    /**
     * @cfg {Function} onsuccess 楠岃瘉缁撴潫鍚庡洖璋冩柟娉曪紝璋冪敤涓� onsuccess(formData, finalChain)锛�
     * formData涓鸿〃鍗曞厓绱犵粡杩囬獙璇佸鐞嗗悗鐨勬暟鎹紱finalChain涓簅nsuccess蹇呴』鍥炶皟鐨勬柟娉曪紝浠ユ甯哥粨鏉熻〃鍗曠殑楠岃瘉銆�
     * 濡傛灉鏄紓姝ユ彁浜わ紝鍙互鍦ㄨ鏂规硶鍐呮彁浜よ〃鍗曘€�<br/>
     * 鏂规硶杩斿洖false鍙彇娑堟祻瑙堝櫒榛樿鐨勮〃鍗曟彁浜ゃ€�
     */
    onsuccess : function(data, finalChain){
        finalChain();
    },

    _getElements : function(){
        return  this.elements || this.form.elements;
    },

    // @private
    _doNextChain : function(){
        // collect pre data here
        // 閮ㄤ唤鍙兘鏃犻渶楠岃瘉
        var len = this.elems.length;
        
        if(this.currElIdx >= 0 && this.currElIdx < len && !this.error)
            this._collectValue(this.elems[this.currElIdx]);
        
        this.currElIdx++;
        if(this.currElIdx === len){
            if(!this.error){
                if(__debug) console.log('onsuccess', this.data);
                // 杩斿洖闈瀎alse杩涜琛ㄥ崟form鎻愪氦
                // 杩斿洖false琛ㄧず蹇界暐FORM杩涜鑷畾涔夋彁浜わ紙鎴朼jax鎴栧叾瀹冿級...
                // form submit鍚� nextChain涓嶅啀鐢熸晥锛岃繘琛岄〉闈㈡彁浜�
                if( this.onsuccess(this.data, this.nextChain) !== false )
                    this.form.submit();
            // 鎵€鏈夌粨鏉熷苟澶辫触鍚�
            } else this._finalChain();
        }else if(this.currElIdx > len){
            // 鎴愬姛callback璋冪敤鍚庣殑chain
            this._finalChain();
        }else {
            var el = this.elems[this.currElIdx];
            if(el.disabled)
                this._doNextChain();
            else this._validateElem(el, this.nextChain, true);
        }
    },
    
    _validateElem : function(el, callback, multival){
        if($(el).attr('vrel')){
            var chain = new ElememtValidatorChain(this, el);
            // 鏄惁鐙珛楠岃瘉鎴栬€呮潵鑷狥ORM楠岃瘉
            // 濡傛灉鏉ヨ嚜FORM楠岃瘉锛屾坊鍔犲紩鐢╟urrentChain鍒癱hain
            if(multival){
                chain.multival = true;
                this.currentChain = chain;
            }
            chain.validate(callback);
        }else { callback && callback(); }
    },

    _finalChain : function(){
        this.onfinal();
        this.isGlobalVal = false;
        
        if(this.data)
            delete this.data;
        delete this.currentChain;
        
        if(this.decorateTrigger && this.trigger)
            Util.disable(this.trigger, false);
    },
    
    // 楠岃瘉閫氳繃鏃犻敊鎯呭喌涓嬫敹闆嗚〃鍗曞厓绱犳暟鎹�
    // @private
    _collectValue : function(elem){
        // 浼樺厛搴旂敤鍏冪礌鑷韩鐨刢hain.getValue()
        if(this.currentChain && this.currentChain.elem===elem){
            this.data[elem.name] = this.currentChain.getValue();
        }else {
            if (elem.tagName === 'INPUT'){
               if( elem.type === 'radio' || elem.type === 'checkbox' ){
                    if(!elem.checked)
                        return;
               }else if(elem.type === 'submit'){
                    return;
               }
            }
            
            this.data[elem.name] = elem.value;
        }
    },
    
    
    // 浠庡厓绱犱腑璇诲嚭鐩稿叧鐨勯獙璇佷俊鎭€�
    _getBindingValidators : function(elem){
        var cmds,
            jq = $(elem);

        if( this.useCache ){
            if( jq.data('xwb_vd_cmds') ){
                cmds = jq.data('xwb_vd_cmds');
            }else {
                cmds = this.parseCmd(jq.attr('vrel'));
                this._mergeExtraValidators(cmds, elem);
                jq.data('xwb_vd_cmds', cmds);
            }
        }
        else {
            cmds = this.parseCmd(jq.attr('vrel'));
            jq.data('xwb_vd_cmds', cmds);
            this._mergeExtraValidators(cmds, elem);
        }
        return cmds;
    },
    
    // 濡傚凡瀹氫箟鍏冪礌棰濆鐨勯獙璇佸櫒锛屽悎骞堕獙璇佸櫒銆�
    _mergeExtraValidators : function(cmds, elem){
        // 棰濆鏉ヨ嚜Manager閽堝璇ュ厓绱犵殑楠岃瘉鍣�
        // 鍔犲叆鍒板綋鍓嶉泦鍚堜腑
        var extra = this.extraValidators;
        if( extra && extra[elem.name||elem.id] )
            $.merge(cmds, extra[elem.name||elem.id]);
    },
    
    /**
     * 杩斿洖涓€涓寚浠ゆ暟缁勶紝鍙负鎸囦护瀛楃涓叉垨鏁扮粍銆傞澶勭悊淇濆瓨鍦ㄨ繑鍥炴暟缁勭殑preCmds灞炴€�
     * 涓烘暟缁勬椂锛岀涓€涓负鎸囦护瀛楃涓诧紝绗簩涓负鎸囦护鏁版嵁map缁撴瀯銆�
     * 鏍煎紡涓� cmd=k:v,k2:v2|cmd2=k:v,k2:v2 ...
     * 杩斿洖鏁版嵁鏍煎紡涓� [ [name, attr], [name, attr], ...]
     * @private
     */
    parseCmd : function(strRel){
        
        if(!strRel)
            return [];

        var cmds = [], arr = Util.split(strRel, '|'), kd;
        for(var i=0,len=arr.length;i<len;i++){
            // 鏃犲睘鎬ф暟鎹�
            if( arr[i].indexOf('=') === -1 ){
                kd = [arr[i],{}];
            }
            // 鍚睘鎬ф暟鎹�
            else {
                kd = Util.split(arr[i], '=');
                kd[1] = Util.parseKnV(kd[1]);
            }
            // 璇嗗埆涓洪澶勭悊鎸囦护
            if(kd[0].charAt(0) === '_'){
                kd.isPreCmd = true;
                kd[0] = kd[0].substr(1);
            }
            
            cmds[cmds.length] = kd;
        }            
        return cmds;
    },
    
//
//  楠岃瘉澶辫触涓庢垚鍔熷鐞�
//
    errTipName : 'warntip',
    tipTextNode : '#tle',
    // 1 鍗曚釜鎻愰啋, 0 杩炵画鎻愰啋
    warnType : 1,
    // html, text, default html
    tipTextType:'',
    
    
    comForm : false, //鏅€氱殑form,濡傛灉鏄櫘閫氱殑form灏辫鍔犱互涓嬬殑鏍峰紡
    focusCss : 'style-focus', //鑾峰緱鐒︾偣鏃剁殑鏍峰紡
    disableCss : 'style-disabled', //绂佺敤鐨勬牱寮�
    errCss : 'style-wrong', // 鍑洪敊鐨勬牱寮�
    
    
    okTip   : 'oktip',
    
    // implemention
    onerror : function(elem, data, chain){
        // 鏄惁鏇存柊骞舵樉绀洪敊璇彁绀�
        var needed = true;
        chain.addError(data.m);
        var tipId = $(elem).attr(this.errTipName);
        if(tipId){
            var jqTip = this.getWarnTip(elem, tipId);
           if(this.isGlobalVal && chain.error == 1){
                // 鍙兘瀛樺湪澶氫釜楠岃瘉鍣ㄥ叡鐢ㄤ竴涓彁绀哄厓绱犵殑鎯呭喌
                // 蹇呴』鍔犱互鍖哄埆
                // 涓婁竴涓叏灞€楠岃瘉鏈燂紝琛ㄦ槑褰撳墠鐨勭姸鎬佹槸鍚︽湁鏁�
                var previd = jqTip.data('vrel_previd');
                // 褰撳墠tip閿欒娆℃暟,濡傛灉涓猴紣鍒欐彁绀哄€间负褰撳墠鍏冪礌鎻愮ず銆�
                var times =  jqTip.data('vrel_errors') || 0;
                if(this.uniqueId != previd){
                    // 楠岃瘉鍒氬紑濮嬶紝褰撳墠tips瀵勫瓨鏁版嵁涓烘棤鏁堬紝閲嶇疆
                    jqTip.data('vrel_previd', this.uniqueId);
                    jqTip.data('vrel_errors',0);
                    times = 0;
                }else {
                    // 棣栨鍑洪敊鎵嶆洿鏂版彁绀�
                    needed = times == 0;
                }
                // 澧炲姞褰撳墠tip閿欒璁℃暟
                jqTip.data('vrel_errors', ++times);
           }
        }
           // 褰撳墠鏈崰鐢ㄦ彁绀烘垨鎻愮ず涓鸿嚜韬椂锛屽彲浣跨敤鎻愮ず鍏冪礌
        if(needed){
             var msgs = chain.getErrors();
             if(this.warnType === 1){
                     if(msgs.length == 1){
                         msgs = msgs[0];
                     }
             }else {
                 msgs = msgs.join('锛�');
             }
             this.tipWarn(elem, msgs);
        }
    },
    
    // private锛屾煡鎵炬彁绀哄厓绱犵殑鏂规硶
    getWarnTip : function(elem, tipSelector){
        var rs = $(elem).find(tipSelector);
        if(!rs.length)
            rs = $(tipSelector);
        return rs;
    },
    
    // implemention
    onpass : function(elem, chain){
        var tipId = $(elem).attr(this.errTipName);
        var needed = true;
        if(tipId){
            var jqTip = this.getWarnTip(elem, tipId);
            // 鍙兘瀛樺湪澶氫釜楠岃瘉鍣ㄥ叡鐢ㄤ竴涓彁绀哄厓绱犵殑鎯呭喌
            // 蹇呴』鍔犱互鍖哄埆
            if(this.isGlobalVal){
                // 涓婁竴涓叏灞€楠岃瘉鏈燂紝琛ㄦ槑褰撳墠鐨勭姸鎬佹槸鍚︽湁鏁�
                var previd = jqTip.data('vrel_previd');
                // 褰撳墠tip閿欒娆℃暟,濡傛灉涓猴紣鍒欐彁绀哄€间负褰撳墠鍏冪礌鎻愮ず銆�
                var times =  jqTip.data('vrel_errors') || 0;
                if(this.uniqueId != previd){
                    // 楠岃瘉鍒氬紑濮嬶紝褰撳墠tips瀵勫瓨鏁版嵁涓烘棤鏁堬紝閲嶇疆
                    jqTip.data('vrel_previd', this.uniqueId);
                    jqTip.data('vrel_errors',0);
                    times = 0;
                }else {
                    // 鏃犻敊鎵嶉殣钘�
                    needed = times==0;
                }
            }
        }
        if(needed)
            this.tipPass(elem);
    },
    
    /**
     * 鏄剧ず鍏冪礌閿欒鎻愮ず锛屽彲閲嶅啓鑷畾涔夐敊璇彁绀�
     * @param {jQuery} element
     * @param {String} message
     */
    tipWarn : function(elem, msg){
        var tipId = $(elem).attr(this.errTipName);
        if(tipId){
            var jqTip = this.getWarnTip(elem, tipId);
            var jqTxt = jqTip.cssDisplay(true)
                       .find(this.tipTextNode);
            if(!jqTxt.length)
                    jqTxt = jqTip;
            
            this.tipTextType==='text'?jqTxt.text(msg):jqTxt.html(msg);
            return jqTip;
        }
        
        // 鍑洪敊浜嗭紝灏遍殣钘弌ktip
        var okTip = $(elem).attr(this.okTip);
        // 鐢╲isiblity鏄疌SS瑕佹眰鍗犱綅
        if (okTip){
        	$(okTip).css('visibility', 'hidden');
        }
    },
    /**
     * 鏄剧ず鍏冪礌楠岃瘉閫氳繃鍚庢彁绀猴紝鍙噸鍐欒嚜瀹氫箟鎴愬姛鎻愮ず
     * @param {jQuery} element
     */
 
    tipPass : function(elem){
        var tipId = $(elem).attr(this.errTipName);
        if(tipId)
            $(tipId).cssDisplay(false);
    	var okTip = $(elem).attr(this.okTip);
    	if (okTip)
    		$(okTip).css('visibility', '');
    }
};

X.reg('Validator', X.ax.ValidationMgr);

})();
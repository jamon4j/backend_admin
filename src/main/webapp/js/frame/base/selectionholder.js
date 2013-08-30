/**
 *  @class Xwb.ax.SelectionHolder
 *  鏈被涓昏瑙ｅ喅IE涓嬫枃鏈緭鍏ユ鍐呮枃鏈€夋嫨锛屾浛鎹㈠拰鎻掑叆绛夐棶棰樸€�<br/>
 *銆€渚嬪鍙戝竷寰崥妗嗗湪IE锛栦笅锛屽厛鍦ㄦ枃鏈鍐呴€夋嫨涓€娈垫枃瀛楋紝鍐嶄粠琛ㄦ儏閫夋嫨妗嗗唴閫夋嫨涓€涓〃鎯呮彃鍏ュ埌鏂囨湰妗嗭紝
 *  閫氬父鍦ㄨ繖绉嶆儏鍐典笅琛ㄦ儏涓嶈兘寰堝ソ鐨勭洿鎺ユ彃鍦ㄦ枃瀛楅€夋嫨澶勶紝鑰屾槸杩藉姞鍒版枃鏈熬閮ㄣ€�
 *  鍒╃敤鏈被鍙拷鐣ュ叾涓殑鍏煎澶勭悊锛岀洿鎺ヨ皟鐢ㄧ被鏂规硶鍗冲彲瑙ｅ喅闂銆�<br/>
 *  瀵逛簬浣跨敤璇ョ被瀹炰緥灏佽鍚庣殑杈撳叆妗嗗厓绱狅紝浼氬湪鍏冪礌鐨刯Query缂撳瓨xwb_selholder閿繚瀛樻寚鍚戣瀹炰緥鐨勫紩鐢ㄣ€�
 * @constructor
 * @param {Object} config
 */
 /**@cfg {HTMLElement} elem 鍏宠仈鐨勬枃浠舵*/
 
(function(X, Util, $){

    X.ax.SelectionHolder = function(opt){
        $.extend(this, opt);
        if(!Util.hasSelectionSupport())
            this.initEvent(this.elem);
    };
    
    X.ax.SelectionHolder.prototype = {
        
        pos : -1,
        
        length : 0,
        
        initEvent : function(){
            var self = this;
            var fn = function(){
                try{
                    self.saveSpot();
                }catch(e){}
            };
            $(this.elem)
              .mouseup(fn)
              .keyup(fn)
              .data('xwb_selholder', this);
        },
        /**
         *  淇濆瓨杈撳叆妗嗗綋鍓嶉€夋嫨鐘舵€佸拰鍏夋爣浣嶇疆绛変俊鎭紝
         *  杩欎釜涓€鑸儏鍐典笅涓嶄細鐢ㄥ埌锛岄櫎闈炰綘涓嶈兘纭畾鏄惁鐢卞閮ㄥ叾瀹冮€斿緞鏇存柊浜嗚緭鍏ユ鐘舵€併€�
         */
        saveSpot : function(){
            var elem = this.elem;
            this.pos    = Util.getCursorPos(elem);
            this.length = Util.getSelectionText(elem).length;
        },
        /**
         *  寰€杈撳叆妗嗗綋鍓嶅厜鏍囧鎻掑叆涓€娈垫枃鏈�
         *  @param {String} text
         */
        insertText : function(text){
            var elem = this.elem, 
                val  = elem.value;
            if(Util.hasSelectionSupport()){
                Util.replaceSelection(elem, text);
            }else {
                // append
                if(this.pos === -1){
                    elem.value = val + text;
                    Util.focusEnd(elem);
                }else {
                    elem.value = Util.stringReplace(val, text, this.pos, this.pos+this.length);
                    Util.setCursor(elem, this.pos+text.length);  
                }
            }
            this.saveSpot();
        },
        
        /**
         *璁剧疆閫夋嫨妗嗘枃鏈�
         * @param {String} text
         */
        setText : function(text){
            this.elem.value = text;
            try{
                this.focusEnd();
            }catch(e){}
        },
        /**娓呴櫎閫夋嫨妗嗙姸鎬佷俊鎭�*/
        clearSpot : function(){
            this.length = 0;
            this.pos = -1;
        },
        /**灏嗗厜鏍囧畾浣嶈嚦鏂囨湰妗嗘湯灏�*/
        focusEnd : function(){
            Util.focusEnd(this.elem);
            this.saveSpot();
        },
        /**灏嗗厜鏍囧畾浣嶈嚦鏂囨湰妗嗗紑濮嬩綅缃�*/
        focusStart : function(){
            Util.setCursor(this.elem, 0);
            this.saveSpot();
        },
        replaceString : function(text,from,to){
    		this.elem.value = Util.stringReplace(this.elem.value,text,from,to);
			Util.setCursor(this.elem , from + text.length );
			this.saveSpot();
        }
    };

    X.reg('SelectionHolder', X.ax.SelectionHolder);

})(Xwb, Xwb.util, $);
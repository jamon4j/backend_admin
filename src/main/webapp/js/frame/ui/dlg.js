(function(X, $){

var Util = X.util;
var undefined;
var doc = document;
var T = X.ax.Tpl;
var ui = X.ui;

/**
 * @class Xwb.ui.Dialog
 * @extends Xwb.ui.Box
 * 瀵硅瘽妗�
 */

/**
 * @cfg {String} buttonTpl 鎸囧畾鍗曚釜鎸夐挳鐨凥TML妯℃澘
 */

/**
 * @cfg {String} buttonHtml 鎸囧畾鎵€鏈夋寜閽殑HTML妯℃澘
 */
/**
 * @cfg {String} defBtn default focused button id
 */


/**@cfg {String} dlgContentHtml 瀹氬埗瀵硅瘽妗嗗唴瀹归儴浠� */

ui.Dialog = X.reg('Dlg', Util.create(ui.Box, function(father){
    return {
        
        cs : 'win-tips win-fixed',
        
        contentHtml : 'DialogContent',
        
        focusBtnCs : 'highlight',
        
        mask : true,
        
        closeable : true,
        
        // 鍒涘缓鎸夐挳html
        initUI : function(){
            if(this.buttons && !this.buttonHtml){
                var htmls = [];
                for(var i=0,btns=this.buttons,len=btns.length;i<len;i++){
                    htmls.push(T.parse(this.buttonTpl || 'Button', btns[i]));
                }
                this.buttonHtml = htmls.join('');
            }
            father.initUI.call(this);
        },
        
        // override super
        onClsBtnClick : function(){
            if( this.onbuttonclick('cancel') !== false )
                this.close();
            return false;
        },
        
        /**
         * 鑱氱劍鍒版寚瀹氭寜閽�
         * @param {String} buttonId
         */
        setFocus : function(btn){
            if(btn || this.defBtn)
                this.jq('#xwb_btn_' + (btn||this.defBtn)).focus().addClass(this.focusBtnCs);
        },
        
        afterShow : function(){
            father.afterShow.call(this);
            if(this.defBtn)
                this.setFocus();
        },
        /**
         * @cfg {Function} onbuttonclick 褰撴寜閽偣鍑绘椂鍥炶皟锛屽弬鏁�:buttonId
         */
        onbuttonclick : function(eid){
            if(__debug) console.log(eid+' clicked');
        },
    /**
     * 閲嶅畾涔墈@link #onbuttonclick}澶勭悊
     * @param {Function} handler
     */
        setHandler : function(handler){
            this.onbuttonclick = handler;
            return this;
        },
    /**
     * 鑾峰緱鎸囧畾鎸夐挳
     * @return {jQuery}
     */
        getButton : function(bid){
            return this.jq('#xwb_btn_' +bid);
        },
         
        innerViewReady : function(v){
            father.innerViewReady.call(this, v);
            var w = this;
            $(v).find('#xwb_dlg_btns').click(function(e){
                var btn = Util.domUp(e.target, function(el){
                        return el.id && ( el.id.indexOf('xwb_btn_') ===0 );
                    }, this);
                if(btn){
                    var eid = btn.id.substr('xwb_btn_'.length);
                    if( w.buttons ){
                        $.each( w.buttons, function(){
                            if(this.id === eid){
                                var ret;
                                if(this.onclick)
                                    ret = this.onclick(w);
                                
                                if(ret !== false && w['on'+eid])
                                    ret = w['on'+eid]();
                                    
                                if(ret !== false)
                                   if( w.onbuttonclick(eid) !== false )
                                    w.close();
                            }
                        });
                    }
                    return false;
                }
            });
        }
    };
}));

/**
 * @class Xwb.ui.MsgBox
 * 甯哥敤瀵硅瘽妗嗛泦鍚�
 * @static
 */

ui.MsgBox = X.reg('msgbox', {
    /**
     * 鑾峰緱搴撲腑鍏敤瀵硅瘽妗嗗疄渚�
     * @return {Xwb.ui.Dialog}
     */
    getSysBox : function(){
        var w = this.sysBox;
        if(!w){
            w = this.sysBox =  X.use('Dlg', {
                appendTo:doc.body,
                title:'鎻愮ず',
                dlgContentHtml : 'MsgDlgContent',
                mask : true,
                //瀵硅瘽妗嗛粯璁ゆ寜閽�
                buttons : [
                  {title: '纭�&nbsp;瀹�',     id :'ok'},
                  {title: '鍙�&nbsp;娑�',     id :'cancel'},
                  {title: '&nbsp;鏄�&nbsp;', id :'yes'},
                  {title: '&nbsp;鍚�&nbsp;', id :'no'},
                  {title: '鍏�&nbsp;闂�',     id :'close'}
                ],
                
                /***/
                setContent : function(html){
                    this.jq('#xwb_msgdlg_ct').html(html);
                },
                
                setIcon : function(icon){
                    var jq = w.jq('#xwb_msgdlg_icon');
                    jq.attr('className', jq.attr('className').replace(/icon\-\S+/i, 'icon-'+icon));
                },
                
                afterHide : function(){
                    ui.Dialog.prototype.afterHide.call(this);
                    // 澶嶅師callback
                    this.onbuttonclick = ui.Dialog.prototype.onbuttonclick;
                }
            });
        }
        return w;
    },
    /**
     * 鑾峰緱搴撲腑鍏敤鎻愮ず妗嗗疄渚�
     * @return {Xwb.ui.Tip}
     */
    getTipBox : function(){
        var w = this.tipBox;
        if(!w){
            w = this.tipBox = X.use('Tip', {
                cs : 'win-tips win-fixed',
                contentHtml : 'DialogContent',
                appendTo:doc.body,
                title:'鎻愮ず',
                timeoutHide:1200,
                dlgContentHtml : 'MsgDlgContent',

                setContent : function(html){
                    this.jq('#xwb_msgdlg_ct').html(html);
                },
                
                setIcon : function(icon){
                    var jq = w.jq('#xwb_msgdlg_icon');
                    jq.attr('className', jq.attr('className').replace(/icon\-\S+/i, 'icon-'+icon));
                },
                
                afterHide : function(){
                    ui.Tip.prototype.afterHide.call(this);
                    // 澶嶅師callback
                    if(this.onhide){
                        this.onhide();
                        this.onhide = false;
                    }
                }
            });
        }
        return w;
    },
    /**
     * 鑾峰緱搴撲腑鍏敤瀹氬悜寮瑰嚭妗嗗疄渚�
     * @return {Xwb.ui.Dialog}
     */
    getAnchorDlg : function(){
        var w = this._anchorDlg;
        if(!w){
            w = this._anchorDlg = X.use('Dlg', {
                cs:'win-tips-ask',
                mask : false,
                dlgContentHtml:'AnchorDlgContent', 
                appendTo:doc.body,
                //瀵硅瘽妗嗛粯璁ゆ寜閽�
                defBtn:'ok',
                buttons : [
                  {title: '纭�&nbsp;瀹�',     id :'ok'},
                  {title: '鍙�&nbsp;娑�',     id :'cancel'}
                ],
                setAnchor : function(anchorElem){
                    this.anchorEl = anchorElem;
                    return this;
                },
                
                beforeShow : function(){
                    ui.Dialog.prototype.beforeShow.call(this);
                    if(this.anchorEl){
                        this.anchor(this.anchorEl, 'tc', function(ret, sw, sh){
                            ret[1]-=2;
                        });
                        var self = this;
                        this.slide('bc',true, function(){
                            ui.Dialog.prototype.afterShow.call(self);
                        });
                    }
                },
                
                // 缃负绌猴紝鍦ㄦ晥鏋滃畬鎴愬悗鍐嶈皟鐢ㄧ埗绫籥fterShow
                afterShow : $.noop,
                
                beforeHide : function(){
                    if(this.anchorEl){
                        this.slide('cb',false);
                        delete this.anchorEl;
                        return false;
                    }else ui.Dialog.prototype.beforeHide.call(this);
                },
                
                afterHide : function(){
                    ui.Dialog.prototype.afterHide.call(this);
                    // 澶嶅師callback
                    this.onbuttonclick = ui.Dialog.prototype.onbuttonclick;
                }
            });
        }
        return w;
    },
    /**
     * 鑾峰緱搴撲腑鍏敤瀹氬悜鎻愮ず妗嗗疄渚�
     * @return {Xwb.ui.Dialog}
     */
    getAnchorTip : function(){
        var w = this._anchorTip;
        if(!w){
            w = this._anchorTip = X.use('Tip', {
                view : 'Box',
                cs:'operate-success',
                contentHtml:'AnchorTipContent', 
                appendTo:doc.body,
                timeoutHide:1800,
                setAnchor : function(anchorElem){
                    this.anchorEl = anchorElem;
                    return this;
                },
                
                beforeShow : function(){
                    if(this.anchorEl){
                        this.anchor(this.anchorEl, 'tc', function(ret, sw, sh){
                            ret[1]-=8;
                        });
                        this.slide('bc',true);
                    }
                    ui.Tip.prototype.beforeShow.call(this);
                },
                
                beforeHide : function(){
                    if(this.anchorEl){
                        this.slide('cb',false);
                        delete this.anchorEl;
                        return false;
                    }else ui.Tip.prototype.beforeHide.call(this);
                },
                
                afterHide : function(){
                    ui.Tip.prototype.afterHide.call(this);
                    // 澶嶅師callback
                    if(this.onhide){
                        this.onhide();
                        this.onhide = false;
                    }
                }
            });
        }
        
        return w;
    },
    /**
     * @param {String} message
     * @param {Function} callback
     */
    tipError : function(msg, fn){ this.tip(msg, 'error', fn); },
    /**
     * @param {String} message
     * @param {Function} callback
     */
    tipOk : function(msg, fn){ this.tip(msg, 'success', fn); },
    /**
     * @param {String} message
     * @param {Function} callback
     */
    tipWarn : function(msg, fn){ this.tip(msg, 'alert', fn); },
    /**
     * @param {HTMLElement} anchorElement
     * @param {String} message
     * @param {Function} callback
     */
    anchorTipOk : function(elem, msg, fn){ this.anchorTip(elem, msg,'',fn); },
    /**
     * @param {HTMLElement} anchorElement
     * @param {String} message
     * @param {Function} callback
     */
    anchorConfirm : function(elem, msg, fn){
        var dlg = this.getAnchorDlg();
        dlg.setTitle(msg)
           .setHandler(fn||$.noop)
           .setAnchor(elem)
           .display(true);
    },
    /**
     * @param {String} message
     * @param {String} icon
     * @param {Function} callback
     */
    tip : function(msg, icon, fn){
        var tip = this.getTipBox();
        tip.setIcon(icon||'alert');
        tip.setContent(msg||'');
        tip.display(true);
        fn && (tip.onhide = fn);
    },
    /**
     * @param {HTMLElement} anchorElement
     * @param {String} message
     * @param {String} icon
     * @param {Function} callback
     */
    anchorTip : function(anchorElem, msg, icon, fn){
        var tip = this.getAnchorTip();
        tip.setTitle(msg)
           .setAnchor(anchorElem)
           .display(true);
        fn && (tip.onhide = fn);
    },
    /**
     * @param {String} title
     * @param {String} message
     * @param {Function} [callback]
     * @param {String} [buttons]
     * @param {String} [icon]
     * @param {String} [defaultButton]
     */
    alert : function(title, msg, callback, buttons, icon, def){
        var w = this.getSysBox(), btns = w.buttons;
        if(!buttons)
            def = buttons = 'ok';
        if(!icon)
            icon = 'alert';
        for(var i=0,len=btns.length;i<len;i++){
            w.jq('#xwb_btn_'+btns[i].id).cssDisplay(buttons.indexOf( btns[i].id ) >= 0);
        }
        w.defBtn = def;
        title && w.setTitle(title);
        msg   && w.setContent(msg);
        icon  && w.setIcon(icon);
        callback && (w.onbuttonclick = callback);
        w.display(true);
        return w;
    },
    /**
     * @param {String} title
     * @param {String} message
     * @param {Function} [callback]
     * @param {String} defaultButton
     */
    confirm : function(title, msg, callback, def){
        this.alert(title || '鎻愮ず', msg, callback, 'ok|cancel', 'ask', def||'ok');
    },
    /**
     * @param {String} title
     * @param {String} message
     * @param {Function} [callback]
     * @param {String} defaultButton
     */
    success : function(title, msg, callback, buttons, def){
        this.alert(title, msg, callback, buttons || 'ok', 'success', def||'ok');
    },
    /**
     * @param {String} title
     * @param {String} message
     * @param {Function} [callback]
     * @param {String} defaultButton
     */
    error  : function(title, msg, callback, buttons, def){
        this.alert(title, msg, callback, buttons || 'ok', 'error', def||'ok');
    }
});

})(Xwb, $);
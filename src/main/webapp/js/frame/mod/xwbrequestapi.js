/*!
 * X weibo JavaScript Library v1.1
 * http://x.weibo.com/
 * 
 * Copyright 2010 SINA Inc.
 * Date: 2010/10/28 21:22:06
 */

(function(X){

if(!window.__debug)
    __debug = false;

var 
    localDomain  = location.hostname,
    domainReg = /:\/\/(.[^\/]+)/;

var XwbRequest = {
    
/**
 * @class Xwb.ax.RequestConfig
 * @extends Xwb.ax.AjaxConfig
 */
 
 /**
  * @cfg {Function} success
  * @param {Xwb.ax.ResponseDefinition} data
  */

/**
 * @class Xwb.request
 * Xwb搴撴暟鎹眰API銆�<br/>
 * 鍙戣捣浠讳綍璇锋眰鍓嶈鍏堟墽琛屽垵濮嬪寲{@link #init}銆�
 * @singleton
 */

/**
 *  鍒濆鍖栬姹傘€傚彂璧蜂换浣曡姹傚墠璇峰厛鍒濆鍖栥€�
 * @param {String} serverBaseUrl 鏈嶅姟鍣║RL.
 * @return this
 */
    init : function(server){
        this.basePath = server;
        return this;
    },
    
/**
 * 鍙戣捣涓€涓姹傘€�<br>璇锋眰涓嶅繀鐞嗕細鏄惁璺ㄥ煙锛岀郴缁熶細鍒ゆ柇鏄惁鍚屽煙璋冪敤ajax鎴朖SONP璇锋眰銆�
 * @param {Xwb.ax.RequestConfig} config
 * @return {Connector} connector XMLHttpRequest|SCRIPT
 */
    direct : function(cfg){
        if(!cfg)
            cfg = {};
            
        // make a success handler wrapper
        var handler = cfg.success, connector;
        cfg.success = function(data, connector){
            var e = new ( cfg.responseDefinition || XwbRequest.DefaultResponseDefinition ) (data, cfg, connector);
            
            if(__debug) console.log('req e:', e);
            
            if(cfg.scope)
                handler.call(cfg.scope, e);
            else handler(e);
            
            data = null;
            e = null;
            connector = null;
        };
        // check domain the same
        var domain = cfg.url.match(domainReg);
        connector = !domain || domain[1] == localDomain ? Util.ajax(cfg) : Util.jsonp(cfg);
        return connector;
    },
    
/**
 * 鍒╃敤缁欏畾鍙傛暟鍙戣捣涓€涓狿OST璇锋眰
 * <code><pre>
    // POST
    Xwb.request.post(
        'http://demo.rayli.com.cn/?m=action.getCounts',
        {ids:'3042338323,3042296891'},
        function(e){
            if(e.isOk()){
                console.log(e.getRaw());
            }
        }
    );
   </pre></code>
 * @param {String} url
 * @param {Object} data
 * @param {Function} successCallback
 * @param {Xwb.ax.RequestConfig} config
 * @return {Connector} connector XMLHttpRequest|SCRIPT
 */
    postReq : function(url, data, success, cfg){
        !cfg && (cfg = {});
        cfg.method = 'POST';
        return this.q(url, data, success, cfg);
    },

/**
 * 鍒╃敤缁欏畾鍙傛暟鍙戣捣涓€涓姹傘€�
 * q鏄痲uery鐨勭缉鍐欍€� 
 * <code><pre>
    // JSONP
    Xwb.request.q(
        'http://bbs.rayli.com.cn/api/sinax.php',
        {
            action : 'sinalogin',
            name   : 'yourname',
            pwd    : 'youpassword'
        },
        function(e){
            if(e.isOk()){
                console.log(e.getRaw());
            }
        },
        
        // 榛樿 'jsonp'锛屽彲鏍规嵁鍏蜂綋鐩爣鑰岃缃�
        {jsonp:'jscallback'}
    );
   </pre></code>
 * @param {String} url
 * @param {Object} data
 * @param {Function} successCallback
 * @param {Xwb.ax.RequestConfig} config
 * @return {Connector} connector XMLHttpRequest|SCRIPT
 */
    q : function(url, data, success, cfg){
        !cfg && (cfg = {});
        cfg.url = url;
        // merge data
        if(cfg.data)
            Util.extend(cfg.data, data);
        else cfg.data = data;
        cfg.success = success;
        return this.direct(cfg);
    },
    
    basePath : '/',
    
/**
 * 鍙戣捣XWB鐨刟ction璇锋眰
 * @param {String} actionName
 * @param {Xwb.ax.RequestConfig} config
 * @return {Connector} connector XMLHttpRequest|SCRIPT
 */
    act : function(name, data, success, cfg){
        var url = this.apiUrl('action', name);
        return this.postReq(url, data, function(){
            success && success.apply(this, arguments);
            // 鏁版嵁灞傚彂閫� act.寮€澶寸殑鍚勭action浜嬩欢
            var arg = ['api.'+name];
            for(var i=0,len=arguments.length;i<len;i++)
                arg.push(arguments[i]);
            X.fire.apply(X, arg);
        }, cfg);
    },
    
/**
 * 鍒涘缓Xwb椤甸潰閾炬帴
 * @param {String} moduleName
 * @param {String} actionName
 * @param {String} [queryString]
 * @param {String} [entry]
 * @return {String}
 */
    mkUrl : function(module, action, queryStr, entry){
        var params = (entry||'')+'?m=' + module;
        if (action)
            params += '.' + action;

        if (queryStr){
          params += '&';
          typeof queryStr === 'string' ?  params += queryStr : params+=Util.queryString(queryStr);
        }
        return this.basePath + params;
    },
    
/**
 * 鑾峰緱api/weibo/璇锋眰URL
 * @param {String} moduleName
 * @param {String} actionName
 * @param {String} [queryString]
 */
    apiUrl : function(module, action, queryStr){
        return this.mkUrl('api/weibo/'+module, action, queryStr);
    },

/**
 * 瑙ｆ瀽鍘熷杩斿洖鐨勬暟鎹紝寰堝皯浼氱敤鍒版湰鏂规硶锛岄櫎闈炶鎵嬪姩瑙ｉ噴杩斿洖鐨凧SON鏁版嵁銆�
 * @param {Object} rawData
 * @return {Xwb.request.DefaultResponseDefinition}
 */
    parseProtocol : function(ret){
        return new XwbRequest.DefaultResponseDefinition( ret );
    },
    
    event : function(name, data, success, cfg) {
        var url = this.mkUrl('event', name);
        this.postReq(url, data, success, cfg);
    },
    
// ------------------------------------
// XWB鍏蜂綋鏁版嵁璇锋眰API
// ------------------------------------

/**
 * 鍙戝竷寰崥
 * @param {String} text 寰崥鍐呭
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {String} pic
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    post : function(text, fn, pic, cfg){
        var data = {text:text};
        if(pic)
            data.pic = pic;
        XwbRequest.act('update', data, fn, cfg);
    },
    
/**
 * 鍒嗕韩鍥剧墖寰崥
 * @param {String} text 寰崥鍐呭
 * @param {String} picId 鍥剧墖pid
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    postImgText : function(text, picId, fn, cfg){
        XwbRequest.act('upload', {text:text, pic:picId}, fn, cfg);
    },
    
/**
 * 杞彂寰崥
 * @param {String} postId  寰崥id
 * @param {String} text    寰崥鍐呭
 * @param {String} userList   鍚屾椂浣滀负userList鐨勮瘎璁哄彂甯冿紝鐢ㄦ埛ID鐢ㄩ€楀彿鍒嗛殧
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    repost : function(id, text, uids, fn, cfg){
        XwbRequest.act('repost', {
            id:id, 
            text:text, 
            rtids : uids
          }, fn, cfg
        );
    },
/**
 * 鑾峰彇琛ㄦ儏鏁版嵁
 * 
 */
	getEmotion: function(fn) {
		XwbRequest.act('emotions', null,fn);
	},

/**
 * 鍒犻櫎寰崥
 * @param {String} postId 寰崥id
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    del : function(id, fn, cfg){
        XwbRequest.act('destroy', {id:id}, fn, cfg);
    },

/**
 * 璇勮寰崥
 * @param {String} postId 寰崥id
 * @param {String} text 寰崥鍐呭
 * @param {Number} forward 鏄惁浣滀负涓€鏉℃柊寰崥鍙戝竷锛�1鏄紝0鍚�
 * @param {Number} headPictureType 璇勮鏄剧幇澶村儚绫诲瀷, 榛樿鏄�1锛�30澶у皬鐨勫ご鍍忥紝2锛�50澶у皬鐨勫ご鍍�
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    comment : function(id, text, forward, hpt, fn, cfg){
        XwbRequest.act('comment', {
            id:id,
            text:text,
            forward : forward,
            type:hpt
           }, fn, cfg
        );
    },

/**
 * @param {String} commentId 璇勮寰崥id
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    delComment : function(id, fn, cfg){
        XwbRequest.act('comment_destroy', {id:id}, fn, cfg);
    },
    
/**
 * 鍥炲寰崥璇勮
 * @param {String} postId 寰崥id
 * @param {String} commentPostId 瑕佸洖澶嶇殑璇勮ID
 * @param {String} text 寰崥鍐呭
 * @param {Number} forward 鏄惁浣滀负涓€鏉℃柊寰崥鍙戝竷锛�1鏄紝0鍚�
 * @param {Number} headPictureType 璇勮鏄剧幇澶村儚绫诲瀷, 榛樿鏄�1锛�30澶у皬鐨勫ご鍍忥紝2锛�50澶у皬鐨勫ご鍍�
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    reply : function(id, cid, text, forward, hpt, fn, cfg){
        XwbRequest.act('reply', {
            id:id,
            cid:cid,
            text:text,
            forward : forward,
            type:hpt
           }, fn, cfg
        );
    },
/**
 * 鍏虫敞鏌愪汉锛屾垨鎵归噺鍏虫敞鐢ㄦ埛
 * @param {String} user 鍏虫敞鐢ㄦ埛锛孶ID鎴栧井鍗氬悕绉�
 * @param {Number} userDataType user鍙傛暟绫诲瀷锛�0涓簎ser id锛�1涓哄井鍗氬悕绉�
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    follow : function(user, dt, fn, cfg){
        XwbRequest.act('createFriendship', {uid:user, type:dt}, fn, cfg);
    },

/**
 * @param {String} user 鍏虫敞鐢ㄦ埛锛孶ID鎴栧井鍗氬悕绉�
 * @param {Number} userDataType user鍙傛暟绫诲瀷锛�0涓簎ser id锛�1涓哄井鍗氬悕绉�
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    unfollow : function(user, name, fn, cfg){
        XwbRequest.act('deleteFriendship', {id:user, name:name, is_follower:0}, fn, cfg);
    },
	
/**
 * 绉婚櫎绮変笣
 * @param {String} user 鍏虫敞鐢ㄦ埛锛孶ID鎴栧井鍗氬悕绉�
 * @param {Number} userDataType user鍙傛暟绫诲瀷锛�0涓簎ser id锛�1涓哄井鍗氬悕绉�
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
	removeFans : function(user, name, fn, cfg) {
		XwbRequest.act('deleteFriendship', {id:user, name:name, is_follower:1}, fn, cfg);
	},

/**
 * 鏀惰棌寰崥
 * @param {String} blogId 瑕佹敹钘忕殑寰崥ID
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    fav : function(id, fn, cfg){
        XwbRequest.act('createFavorite', {id:id}, fn, cfg);
    },
    
    delFav : function(id, fn, cfg){
        XwbRequest.act('deleteFavorite', {id:id}, fn, cfg);
    },

/**
 * 鏇存敼澶村儚銆�
 * WARNING : 鏇存敼澶村儚闇€瑕佺敱form鎻愪氦
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    updateHeadPic : function(image, fn, cfg){
        XwbRequest.act('updateProfileImage', {image:image}, fn, cfg);
    },
    
/**
 * 鏇存柊鐢ㄦ埛璧勬枡
 * @param {Object} data 鐢ㄦ埛璧勬枡锛堥敭鍊煎锛�
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    setProfile : function(data, fn, cfg){
        XwbRequest.act('saveProfile', data, fn, cfg);
    },

/**
 * 鑾峰彇鏈鏁� 鍖呮嫭鏂板井鍗氭暟锛孈鎴戠殑寰崥鏁帮紝璇勮鏁帮紝绮変笣鏁帮紝绉佷俊
 * @param {String} lastReadId 鏈€鏂板凡璇诲井鍗欼D
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    unread : function(id, fn, cfg){
        XwbRequest.act('unread', {id:id}, fn, cfg);
    },
    
/**
 * 娓呴浂鏈娑堟伅鏁扮洰
 * @param {Number} messageType  1涓烘竻闆惰瘎璁猴紝2涓烘竻闆禓me锛�3涓烘竻闆剁淇★紝4涓烘竻闆剁矇涓濓紝榛樿娓呴浂鍏ㄩ儴
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    clearUnread : function(type, fn, cfg){
        XwbRequest.act('clearTip', {type:type}, fn, cfg);
    },
    
/**
 * 鑾峰彇鎸囧畾鐨勫井鍗氳瘎璁哄垪琛�
 * @param {String} id 寰崥ID
 * @param {Number} page 璇勮鐨勯〉鐮�
 * @param {Number} type 鍒楄〃绫诲瀷, 榛樿鏄�1锛屽井鍗氬垪琛ㄧ殑鏌愭潯寰崥璇勮鍒楄〃锛�2鍗曟潯寰崥鐨勮缁嗚瘎璁哄垪琛�
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    getComments : function(id, page, type, fn, cfg){
        XwbRequest.act('getComments', {id:id, page:page, type:type||1}, fn, cfg);
    },

/**
 * 鍙戠淇�
 * @param {String} targetUserId 鐢ㄦ埛甯愬彿ID锛屼笌鐢ㄦ埛寰崥鍚嶇О涓よ€呯粰鍑哄叾涓€鍗冲彲
 * @param {Number} userType 鎸囨槑绗竴涓弬鏁扮殑绫诲瀷锛岀敤鎴稩D鏃跺€间负0, 鐢ㄦ埛寰崥鍚嶇О鏃朵负1锛岄粯璁や负0
 * @param {String} targetWeiBoName 鐢ㄦ埛寰崥鍚嶇О涓庡笎鍙稩D涓よ€呯粰鍑哄叾涓€鍗冲彲
 * @param {String} text 绉佷俊鍐呭
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    msg : function(uid, userType, text, fn, cfg){
        XwbRequest.act('sendDirectMessage', {id : userType?'':uid, name: userType?uid:'', text:text}, fn, cfg);
    },

/**
 * 鍒犻櫎绉佷俊
 * @param {String} msgId 绉佷俊ID
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    delMsg : function(id, fn, cfg){
        XwbRequest.act('deleteDirectMessage', {id:id}, fn, cfg);
    },
/**
 * 鏌ョ湅鏌愪汉鏄惁鏄洰鏍囩敤鎴风殑绮変笣
 * @param {String} user 鐩爣鐢ㄦ埛
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Number} targetAccountType 鐩爣甯愬彿绫诲瀷锛屽鏋滃弬鏁颁紶鍏ョ殑鏄敤鎴稩D锛屼负0,濡傛灉鍙傛暟涓虹敤鎴峰井鍗氬悕绉板垯涓�1
 * @param {String} src 婧愮敤鎴�(涓嶆寚瀹氾紝灏变娇鐢ㄥ綋鍓嶇櫥褰曠敤鎴�)
 * @param {Number} sourceAccountType 婧愬笎鍙风被鍨嬶紝濡傛灉鍙傛暟浼犲叆鐨勬槸鐢ㄦ埛ID锛屼负0,濡傛灉鍙傛暟涓虹敤鎴峰井鍗氬悕绉板垯涓�1
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    followed : function(user, fn, userType, src, srcType, cfg){
        var data = {};
        if( userType )
            data.t_name = user;
        else data.t_id  = user;
        if(src){
            if(srcType)
                data.s_name = src;
            else data.s_id  = src;
        }
        XwbRequest.act('friendShip', data, fn, cfg);
    },
    
/**
 *  涓汉璁剧疆
 * @param {String} type 璁剧疆绫诲瀷锛岄粯璁ゆ槸鈥檃utoshow鈥欐柊寰崥鏄剧ず鏂瑰紡锛屸€檛ipshow鈥欐湭璇绘暟鏄剧ず鏂瑰紡
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    setting : function(type, fn, cfg){
        XwbRequest.act('setting',{type:type}, fn, cfg);
    },

/**
 *  灞忚斀鍗曟潯寰崥
 * @param {String} weiboId 寰崥ID
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    shieldBlog : function(wbId, fn, cfg){
        XwbRequest.postReq(XwbRequest.mkUrl('show', 'disabled'), {id:wbId}, fn, cfg);
    },
/**
 *  涓炬姤鍗曟潯寰崥
 * @param {String} weiboId 寰崥ID
 * @param {String} content
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    reportSpam : function(wbId, content, fn, cfg){
        XwbRequest.postReq(XwbRequest.mkUrl('show', 'reportSpam'), {cid:wbId, content:content}, fn, cfg);
    },

/**
 *  澧炲姞鏍囩
 * @param {String} tagList 閫楀彿鍒嗛殧
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    createTags : function(tagList, fn, cfg){
        XwbRequest.act('createTags', {tagName:tagList}, fn, cfg);
    },

/**
 * 鍒犻櫎鏍囩
 */
    delTag : function(tagId, fn, cfg){
        XwbRequest.act('deleteTags', {tag_id:tagId}, fn, cfg);
    },
    
    updateShowProfile : function(data, fn, cfg){
        XwbRequest.act('saveShow', data, fn, cfg);
    },
    
    updateNoticeProfile : function(data, fn, cfg){
        XwbRequest.act('saveNotice', data, fn, cfg);
    },
/**
 * 鍙戦€佹彁閱掋€�
 * @param {String} userId, (sina_id), 0琛ㄧず鏈珯鎵€鏈夌敤鎴�
 * @param {String} title
 * @param {String} content
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    notice : function(uid, title, content, time, fn, cfg){
        XwbRequest.postReq(XwbRequest.apiUrl('action', 'sendNotice'), {available_time:time,uid:uid,title:title,content:content}, fn, cfg);
    },
    
/**
 * 璁剧疆鐨偆
 * @param {Object} 鐨偆鍙傛暟锛堝鏋滃彧鏈塻kin_id锛岃〃绀轰娇鐢ㄧ毊鑲ゅ垪琛紝鍚﹀垯灏辨槸鑷畾涔夌毊鑲わ級
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    saveSkin : function(data, fn, cfg){
        // url, data, success, cfg
        XwbRequest.postReq(XwbRequest.mkUrl('setting', 'setSkin'), data, fn, cfg);
    },
    
/**
 * 璁剧疆鐨偆(鍚庡彴璁剧疆)
 * @param {Object} 鐨偆鍙傛暟锛堝鏋滃彧鏈塻kin_id锛岃〃绀轰娇鐢ㄧ毊鑲ゅ垪琛紝鍚﹀垯灏辨槸鑷畾涔夌毊鑲わ級
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    saveMgrSkin : function(data, fn, cfg){
        // url, data, success, cfg
        XwbRequest.postReq(XwbRequest.mkUrl('mgr/setting', 'setSkin'), data, fn, cfg);
    },
    
/**
 * 鑾峰彇澶氭潯寰崥杞彂鏁帮紝璇勮鏁扮瓑淇℃伅
 * @param {String} weiboIds 寰崥ID鍒楄〃锛岀敱閫楀彿鍒嗛殧
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    counts : function(ids, fn, cfg){
        XwbRequest.act('getCounts', {ids:ids}, fn, cfg);
    },

/**
 * 娣诲姞鐢ㄦ埛榛戝悕鍗�
 * @param {String} id 鐢ㄦ埛ID
 * @param {String} name 鐢ㄦ埛鏄电О 
 * 鍙傛暟浜岄€変竴鍗筹紝濡傚彧鐭ラ亾鏄电О (null, 'billgate');
 */
	blacklistAdd : function(id, name, fn, cfg) {
		XwbRequest.act('createBlocks', {id:id,name:name}, fn, cfg);
	},

	blacklistDel : function(id, name, fn, cfg) {
		XwbRequest.act('deleteBlocks', {id:id,name:name}, fn, cfg);
	},
	

/**
 * 鑾峰彇鐪佷唤鍩庡競鍒楄〃
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 */
	getProvinces : function(fn) {
		XwbRequest.act('getProvinces', null, fn);
	},

/**
 * 鐢ㄦ埛鍙嶉
 * @param {Object} data 寰崥ID鍒楄〃锛岀敱閫楀彿鍒嗛殧
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
	feedback : function(data, fn, cfg){
	    this.postReq( this.mkUrl('feedback','save'), data, fn, cfg );
	},

/**
 * @class Xwb.request
 */
/**
 * 瑙ｆ瀽鐭摼鎺�,ID鍙负澶氫釜锛岄€楀彿鍒嗛殧銆�<br/>
 * 涓€鑸儏鍐典笅锛屼笉蹇呯洿鎺ヨ皟鐢ㄦ湰鏂规硶锛屽埄鐢▄@link Xwb.ax.Shortlink}绫绘彁渚涘悇绉嶈В鏋愮煭閾剧殑鏂规硶锛屽寘鎷枃鏈紝DOM涓婄殑鐭摼鎺ワ紝鍙В鍐冲ぇ閮ㄤ唤闂銆�
 * @param {String} shortLinkId
 * @param {Function} callback 鎴愬姛鍚庡洖璋冩柟娉曪紝鍙傛暟涓� callback(Xwb.ax.ResponseDefinition definition)
 * @param {Xwb.ax.RequestConfig} [config] 鍙€夛紝璇锋眰閰嶇疆淇℃伅
 */
    sinaurl : function(id, fn){
        XwbRequest.act('sinaurl', {id:id}, fn);
    },
    

    eventSave : function(data, fn) {
        XwbRequest.event('saveEvent', data, fn);
    },
    
    eventJoin : function(data, fn) {
        data = $.extend({}, data, {action: 'join'});
        XwbRequest.event('joinEvent', data, fn);
    },
    
    eventClose : function(id, fn) {
        XwbRequest.event('closeEvent', {eid: id}, fn);
    },
    
    //鍒犻櫎娲诲姩
    eventDelete : function(id, fn) {
        XwbRequest.event('deleteEvent', {eid: id}, fn);
    },

    
    // 閫€鍑烘椿鍔�
    eventExit : function (eid, fn, cfg) {
        XwbRequest.event('doAction', {action:'exit',eid:eid}, fn, cfg);
    }
};


/**
 * @class Xwb.ax.ResponseDefinition
 * 璇ョ被瀹氫箟鑾峰緱杩斿洖鍐呭鏁版嵁鐨勬柟寮忥紝鍗冲皝瑁呬簡搴曞眰鏁版嵁杩斿洖鐨勫叿浣撶粨鏋勶紝澶栭儴搴旂敤鍙互浠ヤ竴鑷寸殑<b>鏂规硶</b>璇诲彇杩斿洖鐨勬暟鎹€�<br/>
 * 寮傛杩斿洖鐨凧SONP鏁版嵁鏍煎紡鏄墠绔笌鍚庡彴鏃㈠畾鐨勪竴涓牸寮忥紝<strong>浠讳綍寮傛璇锋眰閮借閬靛惊璇ユ牸寮�</strong>銆�<br/>
 * 涓€鑸儏鍐典笅涓嶅繀鐩存帴瀹炵幇鍖栨湰绫伙紝褰搟@link Xwb.request}鍙戣捣鐨勫紓姝ヨ姹傝繑鍥炴椂锛屽洖璋冧紶閫掔殑鍙傛暟灏辨槸鏈被鐨勫疄渚嬪寲瀵硅薄銆�
 * <pre><code>
    // response鍙傛暟鍗充负Xwb.ax.ResponseDefinition绫诲疄渚�
    Xwb.request.q('http://server.com/', {}, function(response){
        if(response.isOk()){
            alert(response.getData());
        }
   });
 </code></pre>
 * @constructor
 * @param {Object} rawData row json data responsed by server
 * @param {Object} requestConfig 杩炴帴閰嶇疆淇℃伅
 * @param {XMLHttpRequest|JSONP} connector 鍙戣捣璇锋眰鐨勮繛鎺ュ櫒(ajax:XMLHttpRequest鎴朖SONP:script缁撶偣)
 */
XwbRequest.DefaultResponseDefinition = function(rawData, reqCfg, connector){
    this.raw = rawData;
    this.reqCfg = reqCfg;
    if(connector)
        this.connector = connector;
};

XwbRequest.DefaultResponseDefinition.prototype = {
/**
 * 鑾峰緱璇ヨ姹傚彂璧锋椂鐨勯厤缃俊鎭�
 * @return {Object}
 */
    getRequestCfg : function(){
        return this.reqCfg;
    },
/**
 * 鑾峰緱璇ヨ姹傛墍浣挎墍鏈夎繛鎺ュ櫒(ajax:XMLHttpRequest瀵硅薄鎴朖SONP:script缁撶偣)
 * @return {Object}
 */
    getConnector : function(){
        return this.connector;
    },
    
/**
 * 鑾峰緱璇锋眰鍘熷杩斿洖鏁版嵁锛屾牴鎹姹傛暟鎹被鍨嬬殑涓嶅悓杩斿洖text鏂囨湰鎴杍son瀵硅薄
 * @return {Object} jsonData
 */
    getRaw : function(){
        return this.raw;
    },

/**
 * 鑾峰緱璇ヨ姹傜殑搴旂敤鏁版嵁
 * @return {Object}
 */
    getData : function(){
        return this.getRaw().rst;
    },

/**
 * 妫€娴嬫湇鍔″櫒鏁版嵁璋冪敤鏄惁鎴愬姛銆�
 * 璇ユ娴嬪浜庢湇鍔″櫒鎴愬姛杩斿洖涔嬪悗锛�
 * 瀵瑰鎴风鎻愪氦鐨勮姹傛暟鎹湁鏁堟€х殑涓€绉嶅弽搴斻€�
 * @return {Boolean}
 */
    isOk : function(){
        return !this.getCode();
    },

/**
 * 鑾峰緱杩斿洖鐮�
 * @return {Number}
 */
    getCode : function(){
        return this.getRaw().errno;
    },

/**
 * 鑾峰緱閿欒鐨勫叿浣撲俊鎭€傝繖涓敊璇俊鎭槸API榛樿杩斿洖鐨勯敊璇俊鎭紝涓昏缁欏紑鍙戜汉鍛樺弬鑰冦€�
 * @return {Object} errorInfo
 */
    getError : function(){
        return this.getRaw().err;
    },
    
/**
 * 浠嶦RRORMAP鑾峰緱閿欒鐮佸搴斾俊鎭紝杩斿洖鐨勪俊鎭槸闈㈠悜鐢ㄦ埛鐨勪俊鎭紝濡傛灉瑕佽幏寰楀紑鍙戜汉鍛樺弬鑰冪殑閿欒淇℃伅锛岃鐢▄@link #getError}銆�
 * @param {String} defaultString 濡傛灉涓嶅瓨鍦紝杩斿洖璇ュ瓧绗︿覆銆�
 * @return {String}
 */
    getMsg : function(def){
        if(__debug) if( !ERRORMAP[ this.getCode() ] ) console.warn('鏈畾涔夐敊璇爜娑堟伅锛�' + this.getCode(), '@', this.getRaw());
        // '绯荤粺绻佸繖锛岃绋嶅悗閲嶈瘯锛�'
        return ERRORMAP[ this.getCode() ] || def || ('绯荤粺涓嶇粰鍔涳紝璇风◢鍚庡啀璇曡瘯鍚с€�');
    },

/**
 * 鏋氫妇杩斿洖鐨刣ata鏁版嵁锛屽彧鏋氫妇涓嬫爣涓烘暟瀛楃殑鏉￠」銆�
 * @param {Function} callback
 * @param {Object} scope
 */
    each : function(func, scope){
        var i = 0, data = this.getData();
        for( var item in data ){
            if( isNaN (item) )
                continue;
            if( scope ){
                if( func.call(scope, data[item], i) === false)
                    break;
            } else if( func(data[item], i) === false)
                 break;
            i++;
        }
    }
};


//
//  杩欓噷鍙檺瀹氬悗鍙拌繑鍥炵殑閿欒鐮侊紝璇蜂笉瑕佸畾涔夊叾瀹冨浣欑殑閿欒鐮併€�
//
var ERRORMAP = XwbRequest.ERRORMAP = {
        '0': '鍙戝竷澶辫触銆�',
		'5': '瓒呰繃瀛楁暟浜嗭紒',
		'1': '鍥剧墖姝ｅ湪涓婁紶锛岃绋嶅€欍€�',
		'2': '姝ｅ湪鍙戝竷,璇风◢鍊欍€傘€�',
		'3': '璇峰厛杈撳叆鍐呭銆�',
		'4': '璇峰啓涓婁綘瑕佽鐨勮瘽棰樸€�',
		'1010005':'瓒呭嚭瀛楁暟浜嗗摝锛�',
		'1020002': '璇蜂笉瑕侀噸澶嶅彂甯冪浉鍚岀殑鍐呭銆�',
		'1010006': '涓嶈兘閲囩敤sina鍩熶笅鐨勯偖绠便€�',
		'1010007': '宸茬粡鎻愪氦锛岃鑰愬績绛夊緟绠＄悊鍛樺鏍革紝璋㈣阿锛�',
        '20011': '璇勮瀛楁暟瓒呰繃闄愬埗',
		'20016': '浠栬繕娌℃湁鍏虫敞浣�,涓嶈兘鍙戠淇�',
		'30001': '鐨偆淇濆瓨澶辫触锛岃閲嶈瘯銆�',
		//鍥剧墖鐩稿叧
		'20020':'涓婁紶鍥剧墖涓虹┖',
		'20021':'涓婁紶鍥剧墖澶у皬瓒呰繃闄愬埗',
		'20022':'涓婁紶鍥剧墖绫诲瀷涓嶇鍚堣姹�',
		'20023':'涓婁紶鍥剧墖澶辫触锛岄噸鏂拌瘯璇曠湅锛�',
		'20024':'闈炴硶鐨勪笂浼犲浘鐗�',
	    '1021200':'姝ゆ樀绉颁笉瀛樺湪',
	    '1020500':'姝ゅ井鍗氬凡琚綔鑰呯Щ闄ゃ€�',
	    '1020301':'姝ゅ井鍗氬凡琚綔鑰呯Щ闄ゃ€�',
	    '1020700':'姝ゅ井鍗氬凡琚綔鑰呯Щ闄ゃ€�',
	    '1020402':'姝ゅ井鍗氬凡琚綔鑰呯Щ闄ゃ€�',
	    '1020504':'姝ゅ井鍗氬凡琚綔鑰呯Щ闄ゃ€�',
	    '1020501':'姝よ瘎璁哄凡琚綔鑰呯Щ闄ゃ€�',
	    '1020600':'姝よ瘎璁哄凡琚綔鑰呯Щ闄ゃ€�',
		'1040003':'鎮ㄥ皻鏈櫥褰曪紝璇峰厛鐧诲綍鍐嶆搷浣�',
		'1040000':'鎮ㄥ皻鏈櫥褰曪紝璇峰厛鐧诲綍鍐嶆搷浣�',
		'1050000':'绯荤粺绻佸繖锛岃绋嶅€欏啀璇曘€�',
		'1040007':'鍙戣瘎璁哄お澶氬暒锛屼紤鎭竴浼氬効鍚с€�',
		'1040006':'鍙戝井鍗氬お澶氬暒锛屼紤鎭竴浼氬効鍚с€�',
		'1040005':'浣犲凡缁忚绂佹鍙戣█',
		'1040004': '璇蜂笉瑕佸彂琛ㄨ繚娉曞拰涓嶈壇淇℃伅銆�',
		'1021301': '璇ユ樀绉板凡瀛樺湪锛岃鎹竴涓樀绉般€�',
		'1020104': '涓嶈澶椽蹇冨摝锛屽彂涓€娆″氨澶熷暒銆�',
		'1020808': '浣犱笉鑳藉叧娉ㄨ嚜宸便€�',
		'1020801': '鍏虫敞鐨勭敤鎴蜂笉瀛樺湪銆�',
		'1020800': '鍏虫敞澶辫触',
		'1020805': '宸插叧娉ㄨ鐢ㄦ埛',
		'1050000': '鎿嶄綔澶辫触锛岄噸鏂拌瘯璇曠湅锛�',
		'1020404': '鐢变簬鐢ㄦ埛璁剧疆锛屼綘鏆備笉鑳藉彂琛ㄨ瘎璁恒€�',
		'1020405': '鏍规嵁瀵规柟鐨勮缃紝浣犱笉鑳借繘琛屾鎿嶄綔銆�',
		'1020806': '浣犱娇鐢ㄧ殑甯愬彿鎴朓P鍏虫敞杩囦簬棰戠箒锛屼粖鏃ュ皢鏃犳硶鍐嶈繘琛屽悓绫绘搷浣滐紝璇疯皡瑙ｏ紒',
		// 涓婁紶鏂囦欢鐢变簬鍏跺畠鍘熷洜鍑洪敊
		// 杩欓噷鍚庡彴鏈€濂藉拰涓婇潰鐨勪竴鑷�
		'2010009' : '涓婁紶鍥剧墖澶у皬瓒呰繃闄愬埗銆�',
		'2010010' : '涓婁紶鍥剧墖澶у皬瓒呰繃闄愬埗銆�',
		'2010011' : '涓婁紶鍥剧墖鐨勬暟鎹笉瀹屾暣锛岄噸鏂拌瘯璇曠湅锛�',
		'2010012' : '鍥剧墖涓婁紶澶辫触锛岄噸鏂拌瘯璇曠湅锛�',
		'2010013' : '涓婁紶鍥剧墖澶辫触锛岄噸鏂拌瘯璇曠湅锛�',
		'2010014' : '涓婁紶鍥剧墖澶辫触锛岄噸鏂拌瘯璇曠湅锛�',
        '1040016' : '鍑洪敊鍟︼紝璇ョ綉绔欒皟鐢ˋPI娆℃暟宸茶秴杩囬檺鍒讹紝璇疯仈绯荤珯闀胯В鍐筹紒'		
};

var Util = X.util;

//
//  涓烘柟渚垮鐞嗭紝鎵€鏈変簨浠剁粺涓€鏈塜灞傚彂閫�
//
if(!X.fire)
    X.fire = function(){};

X.request = XwbRequest;

})(window.Xwb);
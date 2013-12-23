(function(X, Util, $){
/**
 * @class Xwb.ax.Shortlink
 * 鐭摼鎺ヨВ鏋愮浉鍏冲嚱鏁帮紝鍖呭惈鐭摼鎺ョ殑璇嗗埆锛岀紦瀛橈紝瑙ｆ瀽锛岀粨鍚圖OM娓叉煋绛夋搷浣溿€�<br/>
 * 鐩墠鐭摼鏈変笁涓被鍨嬶細<br/>
 * 1. 鏂版氮http://t.cn鐭摼<br/>
 * 2. 鏂版氮http://sinaurl.cn鐭摼锛屽吋瀹瑰鐞�<br/>
 * 3. Xweibo绔欑偣鑷畾涔夌煭閾�<br/>
 鐢ㄦ硶锛�
* <pre><code>
    // 鎸囨槑瑕佽В鏋愮殑鐭摼鍓嶇紑
    var shortlink = new Xwb.ax.Shortlink({ local:'http://xweibourl.cn' });
    
    var testUrl = 'http://xweibourl.cn/aBcDeF';
    
    // true
    shortlink.is(testUrl);
    
    testUrl = 'a text including http://xweibourl.cn/aBcDeF,http://xweibourl.cn/DefGHE';
    
    // 杩斿洖['aBcDeF','DefGHE']
    shortlink.search(testUrl);
    
    // 澶勭悊HTML鍏冪礌涓寘鍚煭閾剧殑a鍏冪礌
    shortlink.render('#divId', function(urlInfo, aHrefElement){
            aHrefElement.title = urlInfo.url;
    });
 </code></pre>
  urlInfo缁撴瀯浠ヨ棰戜负渚嬪涓嬶細
* <pre><code>
    {
        // 瀹為檯鍦板潃
        url :	"http://x.xweibo.com/pr...Y6XTWIwA/?rpid=58114392",
        // url绫诲瀷锛屾湁url,music,video绛�
        type : "video",
        // 鏍囬
        title : "杩樼彔浼ゆ劅",
        // 缂╃暐鍥�
        screen : "http://i01.img.tudou.co...mgs/i/072/911/086/p.jpg",
        // 瑙嗛鍦板潃
        flash : "http://www.tudou.com/v/wzLY6XTWIwA/v.swf",
        mp4:"",
        // 鍥炬爣
        icon : "http://img.t.sinajs.cn/...ommon/feedvideoplay.gif"
    }
 </code></pre>

 鍏跺畠鐢ㄦ硶鍙傝鏂规硶璇存槑銆�
 */
X.ax.Shortlink = function(cfg){
    $.extend(this, cfg);
    
    // URL缂撳瓨
    this._c = {};
};

X.ax.Shortlink.prototype = {
    
    EXTERNAL_URL : 'http://t\\.cn|http://sinaurl\\.cn',
    /**
     * @cfg {String} local 鎸囧畾鏈湴鐭摼鎺ュ墠缂€
     */
    local : '',
    
    _getReg : function(index){
        var slReg = this.slReg;
        if(!slReg){
            var local = this.local;
            slReg = this.slReg = [local ?
                        new RegExp('(?:' + local.replace('.','\\.') + '|'+this.EXTERNAL_URL+')/([0-9a-z]+)','gi'):
                        new RegExp('(?:' + this.EXTERNAL_URL+')/([0-9a-z]+)','gi'), local?
                        new RegExp('(?:' + local.replace('.','\\.') + '|'+this.EXTERNAL_URL+')/([0-9a-z]+)','i'):
                        new RegExp('(?:'+this.EXTERNAL_URL+')/([0-9a-z]+)','i')
            ];
        }
        return slReg[index];
    },
    
    /**
     * 鑾峰緱缂撳瓨涓殑URL淇℃伅锛屾暟鎹牸寮忎负{urlShortId:urlInfo}
     * @return {Object}
     */
    getUrls : function(){
        return this._c;
    },
    
    /**
     *  浠庣粰瀹氭枃鏈唴鏌ユ壘鎵€鏈夌煭閾炬帴锛岃繑鍥炲寘鍚煭閾炬帴ID瀛楃涓茬殑鏁扮粍銆�
     * 
    * <pre><code>
        var text = 'Xweibo,2011 go go go!http://testurl.cn/xweibo_trunk/hBACWI';
        var urls = search(text);
        缁撴灉锛歔'hBACWI']
     </code></pre>

     * @param {String} text
     * @return {Array}
     */
    search : function(text){
        var urls = [];
        if(text){
            var slReg = this._getReg(0);
            // reuse slReg
            while((slReg.exec(text)) != null){
                urls.push( RegExp.$1 );
            }
        }
        return urls;
    },
    /**
     * 鍒ゆ柇缁欏畾鐨刄RL鏄惁涓虹煭閾炬帴鏍煎紡锛屽鏋滀负鐭摼鎺ワ紝杩斿洖璇ラ摼鎺ユ帴瀛楃涓睮D銆�
    * <pre><code>
            var text = 'http://testurl.cn/xweibo_trunk/hBACWI';
            var linkId;
            if((linkId=is(text))){
                // hBACWI
                alert(linkId);
            }
     </code></pre>
     * @param {String} url
     * @return {Boolean|String}
     */
    is : function(url){
        var is = this._getReg(1).test(url);
        return is && RegExp.$1;
    },
    /**
     * 瑙ｆ瀽鏉ヨ嚜寰崥鍒楄〃鏁版嵁鐨勭煭閾炬帴锛岃В鏋愬畬姣曞悗鍙洖璋冧竴娆allback銆�<br/>
     * 鐩墠寰崥鏁版嵁涓殑鐭摼鎺ヤ富瑕佹潵鑷唴瀹瑰尯锛屽寘鎷嚜韬唴瀹逛笌杞彂鍐呭(wbData.tx, wbData.rt.tx)鍜屽井鍗氬鎴风缁堢绫诲瀷鍖�(wbData.s)銆�<br/>
     * 鏂规硶鎵ц鏉ュ皢宸茶В鏋愮殑鐭摼鏁版嵁缂撳瓨鍦ㄥ弬鏁發ist鐨剆hortlinks灞炴€т腑锛屽畠鏄竴涓暟缁勶紝
     * 璇ユ暟缁勫唴鐨勭煭閾惧彲鑳介噸澶嶏紝鍥犱负涓€寰崥鍐呭彲瀛樺湪澶氫釜閲嶅鐨勭煭閾炬帴銆�<br/>
     * <b>濡傛灉閾炬帴鏃犳晥锛宻hortlinkInfo灞炴€т负null銆�</b><br/>
     * shortlinkInfo缁撴瀯涓烘暟缁�+HASH褰㈠紡锛屾棦鍙亶鍘嗭紝鍙堝彲鏍规嵁閾炬帴URL蹇€熷畾浣嶉摼鎺ヤ俊鎭€�<br/>
     * 鏁扮粍鍏冪礌鏁版嵁鏍煎紡涓�
    * <pre><code>
        {
          shortlinks : [
            // 鐭摼淇℃伅
            shortlinkInfo,
            // 璇ョ煭閾炬槸鍚︽潵鑷浆鍙戝尯
            true
          ],
          //...
        }
     </code></pre>
     * callback 鍥炶皟鐨勫弬鏁颁负(wbData, shortlinks)<br/>
     * shortlinks鏍煎紡涓篬 [shorlinkInfo, isForward], ... ],
     * shortlinkInfo涓轰笂闈hortlinks涓殑鍏冪礌<br/>
     * isForward 鎸囨槑璇ョ煭閾炬槸鍚︽潵鑷浆鍙戝唴瀹广€�<br/>
     * @param {Object} weiboListDataMap
     * @param {Function} callback
     */
    from : function(list, callback){
        var 
            // 涓存椂hash妫€娴嬫槸鍚﹂噸澶�
            tmp = {},
            // 鏈煡璇㈢殑鐭摼
            reqUrls = [],
            arr, i, len, u, wbu;
        
        for(var id in list){
            wbu = list[id].shortlinks = [];
            // 鍐呭鍖�
            arr = this.search(list[id].tx);
            for(i=0,len=arr.length;i<len;i++){
                u = arr[i];
                if(!tmp[u]){
                    tmp[u] = true;
                    reqUrls.push(u);
                }
                wbu.push([u]);
            }
            
            // 杞彂鍖�
            if(list[id].rt){
                arr = this.search(list[id].rt.tx);
                for(i=0,len=arr.length;i<len;i++){
                    u = arr[i];
                    if(!tmp[u]){
                        tmp[u] = true;
                        reqUrls.push(u);
                    }
                    wbu.push([u, true]);
                }
            }
            // 缁堢绫诲瀷鍖�
            if(list[id].s){
                arr = this.search(list[id].s);
                for(i=0,len=arr.length;i<len;i++){
                    u = arr[i];
                    if(!tmp[u]){
                        tmp[u] = true;
                        reqUrls.push(u);
                    }
                    wbu.push([u]);
                }
            }
        }
        
        this.info(reqUrls, function(cache){
            // 灏嗚幏寰楃殑鐭摼璇︾粏鏁版嵁濉叆shortlinks鍐�
            for(var id in list){
                var sl = list[id].shortlinks;
                var u;
                for(var i=0,len=sl.length;i<len;i++){
                    u = sl[i][0];
                    // 鍙兘瀛樺湪鏃犳晥鐭摼
                    if(cache[u]){
                        sl[i][0] = cache[u];
                        cache[u].id = u;
                    }else sl[i][0] = null;
                    sl[u] = sl[i];
                }
            }
            callback(list);
        });
    },
    /**
     * 鏌ヨ缁欏畾鐭摼鎺D鏁扮粍涓墍鏈夌煭閾炬帴鐨勪俊鎭€�<br/>
     * 濡傛灉鐭摼鎺ヤ俊鎭凡缂撳瓨锛屼紭鍏堜娇鐢ㄧ紦瀛樻暟鎹€�
     * @param {Array} urls 鐭摼鎺ユ暟缁勶紝濡俒'hBACWI','hBAWEF',...]
     * @param {Function} callback 鏌ヨ缁撴潫鍚庡洖璋冿紝鍙傛暟涓哄簱宸茬紦瀛樼殑鎵€鏈夌煭閾炬帴map鏁版嵁銆�
     */
    info : function(urls, callback){
        // 缂撳瓨鐐�
        var gbl = this._c;

        for(var len=urls.length-1;len>=0;len--){
            if(gbl[urls[len]]){
                Util.arrayRemove(urls, len);
            }
        }
        
        if(urls.length){
            X.request.sinaurl(urls.join(','), function(e) {
                if(e.isOk()){
                    // 杩欓噷鍚庡彴API杩斿洖鏍煎紡涓嶄竴鑷达紒?????
                    $.each(e.raw.data, function(sid, dat){
                        if(!gbl[sid])
                            gbl[sid] = dat;
                    });
                    callback(gbl);
                }
            });
        }else callback(gbl);
    },
    
    /**
     * 鏌ユ壘缁欏畾DOM鍏冪礌鍐呮墍鏈夌殑鐭摼鎺鍏冪礌锛屽埄鐢╮enderer鍙傛暟鍥炶皟澶勭悊杩欎簺鍏冪礌銆�<br/>
     * 鏂规硶浼氭煡鎵惧厓绱犱腑涓寘鍚煭閾炬帴鐨刟鏍囩鍏冪礌锛岃幏寰楁墍鏈夌煭閾炬帴锛屾煡璇㈢煭閾炬帴缂撳瓨锛屽鏈紦瀛橈紝
     * 鍙戣捣API璇锋眰瑙ｆ瀽鏈紦瀛樼殑鐭摼鎺ャ€�<br/>
     * 鏂规硶鍙嚜琛屽鐞嗙粰瀹氬厓绱犵粨鐐瑰唴鎵€鏈夊寘鍚煭閾剧殑A鍏冪礌銆�<br/>
     * 鏌愪釜鍏冪礌閲屾墍鏈夌殑鐭摼鎺ュ姞涓婃彁绀虹湡瀹炲湴鍧€锛�
     * <pre><code>
     * render('#divId', function(urlInfo, aHrefElement){
            aHrefElement.title = urlInfo.url;
       });
     *</code></pre>
     * @param {jQuery|HTMLElement} scopeElement
     * @param {Function} renderer 锛岃皟鐢ㄥ弬鏁颁负 renderer( shortlinkInfo, aHrefElement )
     */
    render : function(jqItem, renderer){
        var links = $(jqItem).find('a'),
            self = this, 
            urls = [], tmp=[];
        
        links.each(function(){
            if((sl = self.is(this.href))){
                urls.push([sl, this]);
                tmp.push(sl);
            }
        });
        
        this.info(tmp, function(infos){
            // got all shortlink
            for(var i=0,len=urls.length;i<len;i++){
                var info =  infos[urls[i][0]];
                renderer(info, urls[i][1]);
            }
        });
    }
};


})(Xwb, Xwb.util, $);
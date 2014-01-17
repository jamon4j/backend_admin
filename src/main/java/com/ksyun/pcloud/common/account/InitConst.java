package com.ksyun.pcloud.common.account;

import java.util.HashMap;

/**
 * User: Norton Chen(cjx)
 * Date: 2013-6-14
 */
public class InitConst{
    public static long SYSTEM_START_AT = System.currentTimeMillis();

    //added by Faustin for watchdog
    public static int API_MODULE_IDX_USER   = 0;

    public static int API_MODULES_MAX       = 8;
    public static long[] API_CALL_MODULE = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    public static long AddApiCall(int i) {
        if(i < 0 || i >= API_MODULES_MAX) return -1;
        return ++API_CALL_MODULE[i];
    }
    
    
    //to delete after new program finished
    public static final String Access_Key_ID = "91dzone";
    public static final String Access_Key_Secret = "UUUS8m+5GNbte7rTot7r2rBVRo3iPt3gXpOSjtA4";

    public static final String SECRETKEY_INNER = "LBSEFJMN6HQ5LG7XBK6A";
    
    public static final String MEMCACHE_91_KEY_PREFIX = "memcache_91_key_prefix";
    public static final String MEMCACHE_KEY_PREFIX = "memcache_key_prefix";
    
    public static final String COOKIE_KEY = "ksyun[token]";
    public static final String KS3_COOKIE_KEY = "kscdigest";
    
    public static final String TYPE_COMPANY = "company";
    public static final String TYPE_INDIVIDUAL = "individual";
    
    //开通服务类型
    public static final String PORTAL_WEBSITE = "门户网站";
    public static final String EBUSSINESS_WEBSITE = "电子商务网站";
    public static final String BBS_WEBSITE = "社区论坛网站";
    public static final String SHARE_WEBSITE = "分享类网站";
    public static final String SNS = "SNS";
    public static final String PCGAME_DEV = "PC游戏开发";
    public static final String PCSOFT_DEV = "PC软件开发";
    public static final String INTELLIGENCE_3C = "智能3C";
    public static final String MOBILEGAME_DEV = "手机游戏开发";
    public static final String MOBILEAPP_DEV = "移动APP开发";
    public static final String OTHER = "其他";
    
    //3rd Banner
    public static final String FROM3RD = "from";
    
    //k-v存储secret，可以保证多平台拓展性
    public static final HashMap<String, String> keyDict = getKeyDict();
    private static HashMap<String, String> getKeyDict(){
    	HashMap<String, String> keyDict = new HashMap<String, String>();
    	keyDict.put("91dzone", "UUUS8m+5GNbte7rTot7r2rBVRo3iPt3gXpOSjtA4");
    	return keyDict;    	
    }
    
    //B64加密使用到私钥
    public static final String MSGKEY = "AUIJP4LZQQPVHV2JVJ2A";

    public static final HashMap<String, String> BizTypeMap = getBizType();
    private static HashMap<String, String> getBizType(){
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("PORTAL_WEBSITE","门户网站");
        hm.put("EBUSSINESS_WEBSITE","电子商务网站");
        hm.put("BBS_WEBSITE","社区论坛网站");
        hm.put("SHARE_WEBSITE","分享类网站");
        hm.put("SNS","SNS");
        hm.put("PCGAME_DEV","PC游戏开发");
        hm.put("PCSOFT_DEV","PC软件开发");
        hm.put("INTELLIGENCE_3C","智能3C");
        hm.put("MOBILEGAME_DEV","手机游戏开发");
        hm.put("MOBILEAPP_DEV","移动APP开发");
        hm.put("OTHER","其他");
        return hm;
    };


    //ERROR
    public static final HashMap<String, String> ErrorMap = getErrorType();
    private static HashMap<String, String> getErrorType(){
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("101","操作错误！请重新登录！");
        hm.put("102","请登录进行后续操作！");
        hm.put("103","参数不完整！请重新登录！");
        hm.put("104","验证失败！请重新登录！");
        hm.put("105","登录信息失效，请重新登录！");
        hm.put("106","用户名和密码不匹配，请重新登录！");
        hm.put("106","用户名和密码不匹配，请重新登录！");

        hm.put("201","操作错误！");
        hm.put("202","绑定失败！");
        hm.put("203","此账号已经绑定过了！");
        hm.put("204","邮箱验证失败！");
        hm.put("205","参数不完整！");
        hm.put("206","操作错误！");
        hm.put("207","手机验证错误！");
        hm.put("208","密码与邮箱不匹配！");
        hm.put("209","参数信息失效！");
        hm.put("210","请求太频繁！");
        hm.put("211","请输入密码！");

        hm.put("301","操作错误！请重新注册！");
        hm.put("302","参数不完整！请重新注册！");
        hm.put("303","这个账号已经注册！请登录！");
        hm.put("304","手机验证失败！请重新注册");
        return hm;
    };
    
    
    public static final String CREATE_PRDER_INTERFACE = "http://test.i.ksyun.com/order/open/saveKs3Order";
    public static final String DEFAULT_TRAIL_DAYS = "30";

}
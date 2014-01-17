package com.ksyun.pcloud.common.account; /**
 * @author: yangji
 * @data:   Jul 10, 2013
 */
//package com.ksyun.ks3.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.dom4j.Document;

public class UserHelper {

    private static final String DEVICE_ID = "KSYUN_WEBSITE";
    private static final String DEFAULT_USER_AGENT = "xlive-2.10.30";
    private static final String DEFAULT_HOST = "http://devuser.ksyun.com/xsvr/";
    private static final int DEFAULT_VERSION = 2;

	//登陆
    public static Map<String, String> login(String user, String password) {
        Map<String, String> loginMap = new HashMap<String, String>();
        String device = DEVICE_ID + System.currentTimeMillis();
        loginMap.put("deviceId", device);
        loginMap.put("user", user);
        loginMap.put("password", password);

        Document loginXml = RestfulRequestor.map2Xml(loginMap);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, loginXml, DEFAULT_HOST + "kscLogin");
        System.out.println(resultXml.asXML());
        loginMap = RestfulRequestor.xml2Map(resultXml);
        return loginMap;
    }
    public static void main(String[] args) {
    	
    }

    //使用手机验证码进行注册
    public static Map<String, String> registerWithMobileVerifyCode(String email ,String password ,String mobile ,String verifyCode ){
        Map<String, String> codeMap = new HashMap<String, String>();
        codeMap.put("email", email);
        codeMap.put("password", password);
        codeMap.put("mobile", mobile);
        codeMap.put("verifyCode", verifyCode);

        Document loginXml = RestfulRequestor.map2Xml(codeMap);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, loginXml, DEFAULT_HOST + "kscRegisterWithMobileVerifyCode");
        codeMap = RestfulRequestor.xml2Map(resultXml);
        return codeMap;
    }

    //请求绑定邮箱
    public static Map<String, String> requestBindEmail(String token,String email){
        Map<String, String> bindMap = new HashMap<String, String>();
        bindMap.put("email", email);
        bindMap.put("token", token);
        Document loginXml = RestfulRequestor.map2Xml(bindMap);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, loginXml, DEFAULT_HOST + "kscRequestBindEmail");
        bindMap = RestfulRequestor.xml2Map(resultXml);
        System.out.println(resultXml.toString()+"--"+bindMap.get("result"));
        return bindMap;
    }

    //验证激活邮件中的验证码
    public static Map<String, String> verifyEmailCode(String email, String verifyCode){
        Map<String, String> codeMap = new HashMap<String, String>();
        codeMap.put("verifyCode", verifyCode);
        codeMap.put("email", email);

        Document loginXml = RestfulRequestor.map2Xml(codeMap);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, loginXml, DEFAULT_HOST + "kscVerifyEmailCode");
        codeMap = RestfulRequestor.xml2Map(resultXml);
        return codeMap;
    }

    //请求绑定手机,给手机发验证码
    public static Map<String, String> requestMobileCode(String mobile){
        Map<String, String> codeMap = new HashMap<String, String>();
        codeMap.put("mobile", mobile);

        Document loginXml = RestfulRequestor.map2Xml(codeMap);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, loginXml, DEFAULT_HOST + "kscRequestMobileCode");
        codeMap = RestfulRequestor.xml2Map(resultXml);
        return codeMap;
    }

    //验证手机验证码
    public static Map<String, String> verifyMobileCode(String token , String mobile, String verifyCode){
        Map<String, String> codeMap = new HashMap<String, String>();
        codeMap.put("verifyCode", verifyCode);
        codeMap.put("mobile", mobile);
        codeMap.put("token", token);

        Document loginXml = RestfulRequestor.map2Xml(codeMap);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, loginXml, DEFAULT_HOST + "kscVerifyMobileCode");
        codeMap = RestfulRequestor.xml2Map(resultXml);
        return codeMap;
    }


    //查看用户登陆信息（token，userId，email，mobile等等）
    public static Map<String, String> getLoginInfo(String token){
        Map<String, String> loginMap = new HashMap<String, String>();
        loginMap.put("token", token);
        Document loginXml = RestfulRequestor.map2Xml(loginMap);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, loginXml, DEFAULT_HOST + "kscGetLoginInfo");
        if(resultXml==null)
            return null;
        loginMap = RestfulRequestor.xml2Map(resultXml);
        return loginMap;
    }

    //用户是否激活（激活==邮箱验证+手机绑定）
    public static Boolean isActivated(String token){

        //如果token你都不给我，你玩毛线啊！
        if(token==null)
            return null;

        boolean isActivated = true;
        Map<String,String> map = UserHelper.getLoginInfo(token);

        //map为null说明请求了三次都没请求到数据
        if(map==null)
            return null;

        String result = map.get("result");
        if(result.trim().toLowerCase().endsWith("ok")){
            String mobile = map.get("mobile");
            String email = map.get("emailstatus");
            if(mobile==null||mobile.trim().equals(""))
                isActivated = false;
            if(email.equals("False"))
                isActivated = false;

            return isActivated;
        }else{
            //返回null说明 这个token已经失效了,或者请求三次没登陆成功
            return null;
        }
    }

   
    //检验email是否被注册
    public static int checkEmailAvailable(String email){
        String result = "";
        int ret = 1;
        if(!isEmailFormat(email)){
            ret = Define.WEB_ERR_EAMILFORMATERROR;
            return ret;
        }
        Map<String, String> emap = new HashMap<String, String>();
        emap.put("email ", email);
        Document eXml = RestfulRequestor.map2Xml(emap);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, eXml, DEFAULT_HOST + "kscCheckEmailAvailable");
        if(resultXml==null){
            ret = Define.WEB_ERR_SERVER_EOOR;
            return ret;
        }
        emap = RestfulRequestor.xml2Map(resultXml);
        result = emap.get("result");
        if(result.equals("kscEmailNotAvailable")){
            ret = Define.WEB_ERR_EMAILNOTAVAILABLE;
            return ret;
        }
        return ret;
    }

    //判断是否是邮箱格式
    public static boolean isEmailFormat(String email){
        boolean isExist = false;
        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
        Matcher m = p.matcher(email);
        boolean b = m.matches();
        if(b) {
            isExist=true;
        }
        return isExist;
    }

    //检查token有效性
    public static Boolean checkToken(String token){
        Map<String, String> map = new HashMap<String, String>();
        map.put("token ", token);
        Document paramsXml = RestfulRequestor.map2Xml(map);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, paramsXml, DEFAULT_HOST + "kscConfirm");
        if(resultXml==null)
            return null;
        System.out.println(resultXml.asXML());
        map = RestfulRequestor.xml2Map(resultXml);
        return map.get("result").equals("ok");
    }
    
    //查看已经开通的服务列表
    public static List<String> getOpenedServices(String token){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("token", token);
        Document document = RestfulRequestor.map2Xml(map);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, document, DEFAULT_HOST + "kscGetOpenedServices");
        if(resultXml==null)
            return null;
        List<String> list = RestfulRequestor.xml2List(resultXml);
        return list;
    }
    
    //申请开通某项服务(不填写个人信息)
    public static Map<String, String> requestOpenService(String token, KSCService service){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("token", token);
    	map.put("service_name", service.toString());
        Document document = RestfulRequestor.map2Xml(map);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, document, DEFAULT_HOST + "kscOpenService");
        if(resultXml==null)
            return null;
        map = RestfulRequestor.xml2Map(resultXml);
        return map;
    }
    
    //申请关闭某项服务
    public static Map<String, String> requestCloseService(String token, KSCService service){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("token", token);
    	map.put("service_name", service.toString());
        Document document = RestfulRequestor.map2Xml(map);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, document, DEFAULT_HOST + "kscDeleteService");
        if(resultXml==null)
            return null;
        map = RestfulRequestor.xml2Map(resultXml);        
    	return map;
    }
    
    //检查是否开通了某项服务
    public static Map<String, String> checkServicePermission(String token, KSCService service){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("token", token);
    	map.put("service_name", service.toString());
        Document document = RestfulRequestor.map2Xml(map);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, document, DEFAULT_HOST + "kscCheckServicePermission");
        if(resultXml==null)
            return null;
        map = RestfulRequestor.xml2Map(resultXml);        
    	return map;
    }
    
    //取得用户信息
    public static Map<String, String> getUserInfo(String token){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("token", token);
        Document document = RestfulRequestor.map2Xml(map);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, document, DEFAULT_HOST + "kscGetUserInfo");
        if(resultXml==null)
            return null;
        map = RestfulRequestor.xml2Map(resultXml);        
    	return map;
    }
    
    //设置用户信息（姓名，公司名称，公司地址，业务类型等信息）
    public static Map<String, String> setUserInfo(String token,String name, String type,String detail){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("token", token);
    	map.put("name", name);
    	map.put("acctype", type);
    	map.put("detail", detail);
        Document document = RestfulRequestor.map2Xml(map);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, document, DEFAULT_HOST + "kscSetUserInfo");
        if(resultXml==null)
            return null;
        map = RestfulRequestor.xml2Map(resultXml);
    	return map;    	
    }
    
    //创建KS3订单
    public static Integer requestCreateOrder(String userId){
    	
    	JSONObject document = new JSONObject();
    	document.put("user_id", Long.valueOf(userId));
    	document.put("trial_day", InitConst.DEFAULT_TRAIL_DAYS);
    	
    	JSONObject result = RestfulRequestor.request(document, InitConst.CREATE_PRDER_INTERFACE);
    	
    	if(result==null)
    		return null;
    	
    	return  result.getInt("ret");    	    	
    }   
    
    public static Map<String, String> adminGetUserInfoByEmail(String sutoken,String email){
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", sutoken);
        map.put("email", email);
        Document loginXml = RestfulRequestor.map2Xml(map);
        Document resultXml = RestfulRequestor.request(DEFAULT_USER_AGENT,
                DEFAULT_VERSION, loginXml, DEFAULT_HOST + "kscAdminGetUserInfoByEmail");
        if(resultXml==null)
            return null;
        map = RestfulRequestor.xml2Map(resultXml);
        return map;
    }

}

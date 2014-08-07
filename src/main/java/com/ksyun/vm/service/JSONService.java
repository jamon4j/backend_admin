package com.ksyun.vm.service;

import java.util.*;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ksyun.vm.exception.ErrorCodeException;
import com.ksyun.vm.exception.NoTokenException;
import com.ksyun.vm.pojo.BasePo;
import com.ksyun.vm.pojo.OpenStackResult;
import com.ksyun.vm.pojo.UserPo;
import com.ksyun.vm.utils.Constants;
import com.ksyun.vm.utils.HttpUtils;
import com.ksyun.vm.utils.InitConst;
import com.ksyun.vm.utils.enumeration.HttpMethod;

/**
 * User: liuchuandong Date: 13-9-5 Time: 下午4:14 Func: openstack api 获取方法
 */
@Service
public class JSONService {
    Logger logger = LoggerFactory.getLogger(getClass());

    private String id;
    private String tenantId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * GET 获取方式
     *
     * @param proKey   连接的key
     * @param username 用户名
     * @param password 密码
     * @param clazz    转换的类的class
     * @param param    连接中的参数
     * @param <T>      类
     * @return 返回基本类 T
     * @throws NoTokenException   没有授权
     * @throws ErrorCodeException 执行错误异常
     */
    public <T extends BasePo> T poGet(String proKey, String username,
                                      String password, Class<T> clazz, Object... param)
            throws NoTokenException, ErrorCodeException {
        return getPo(proKey, username, password, clazz, HttpMethod.GET, null,
                param);
    }

    /**
     * POST 获取方式
     *
     * @param proKey      连接的key
     * @param username    用户名
     * @param password    密码
     * @param clazz       转换的类的class
     * @param requestBody 请求体
     * @param param       连接中的参数
     * @param <T>         类
     * @return 返回基本类 T
     * @throws NoTokenException   没有授权
     * @throws ErrorCodeException 执行错误异常
     */
    public <T extends BasePo> T poPost(String proKey, String username,
                                       String password, Class<T> clazz, String requestBody,
                                       Object... param) throws NoTokenException, ErrorCodeException {
        return getPo(proKey, username, password, clazz, HttpMethod.POST,
                requestBody, param);
    }

    /**
     * DELETE 获取方式
     *
     * @param proKey   连接的key
     * @param username 用户名
     * @param password 密码
     * @param param    连接中的参数
     * @param <T>      类
     * @return 返回基本类 T
     * @throws NoTokenException   没有授权
     * @throws ErrorCodeException 执行错误异常
     */
    public <T extends BasePo> T poDelete(String proKey, String username,
                                         String password, Object... param) throws NoTokenException,
            ErrorCodeException {
        return getPo(proKey, username, password, null, HttpMethod.DELETE, null,
                param);
    }

    /**
     * PUT 获取方式
     *
     * @param proKey      连接的key
     * @param username    用户名
     * @param password    密码
     * @param clazz       转换的类的class
     * @param requestBody 请求体
     * @param param       连接中的参数
     * @param <T>         类
     * @return 返回基本类 T
     * @throws NoTokenException   没有授权
     * @throws ErrorCodeException 执行错误异常
     */
    public <T extends BasePo> T poPut(String proKey, String username,
                                      String password, Class<T> clazz, String requestBody,
                                      Object... param) throws NoTokenException, ErrorCodeException {
        return getPo(proKey, username, password, clazz, HttpMethod.PUT,
                requestBody, param);
    }

    /**
     * 基本请求方法
     *
     * @param proKey      连接的key
     * @param username    用户名
     * @param password    密码
     * @param clazz       转换的类的class
     * @param requestBody 请求体
     * @param param       连接中的参数
     * @param <T>         类
     * @return 返回基本类 T
     * @throws NoTokenException   没有授权
     * @throws ErrorCodeException 执行错误异常
     */
    private <T extends BasePo> T getPo(String proKey, String username,
                                       String password, Class<T> clazz, HttpMethod method,
                                       String requestBody, Object... param) throws NoTokenException,
            ErrorCodeException {
        OpenStackResult result;
        switch (method) {
            case GET:
                result = HttpUtils.get(getUrl(proKey, param),
                        setHeader(username, password));
                logger.info("username=[{}] get [{}],result=[{}] !", new Object[]{
                        username, proKey, result});
                if (result != null && (result.getStatus() >= org.apache.http.HttpStatus.SC_OK && result.getStatus() <= org.apache.http.HttpStatus.SC_MULTI_STATUS)) {
                    if (clazz == null) {
                        return null;
                    }
                    return JSONObject.parseObject(result.getMessage(), clazz);
                } else {
                    // 错误日志入库
                    logger.info("username=[{}] get [{}],result=[{}] !", new Object[]{
                            username, proKey, result});
                    throw new ErrorCodeException(result);
                }
            case POST:
                result = HttpUtils.post(getUrl(proKey, param),
                        setHeader(username, password), requestBody);
                logger.info("username=[{}] post [{}],result=[{}] !", new Object[]{
                        username, proKey, result});
                if (result != null && (result.getStatus() >= org.apache.http.HttpStatus.SC_OK && result.getStatus() <= org.apache.http.HttpStatus.SC_MULTI_STATUS)) {
                    if (clazz == null) {
                        return null;
                    }
                    return JSONObject.parseObject(result.getMessage(), clazz);
                } else {
                    // 错误日志入库
                    throw new ErrorCodeException(result);
                }
            case DELETE:
                result = HttpUtils.delete(getUrl(proKey, param),
                        setHeader(username, password));
                logger.info("username=[{}] delete [{}],result=[{}] !",
                        new Object[]{username, proKey, result});
                if (result != null && (result.getStatus() >= org.apache.http.HttpStatus.SC_OK && result.getStatus() <= org.apache.http.HttpStatus.SC_MULTI_STATUS)) {
                    if (clazz == null) {
                        return null;
                    }
                    JSONObject.parseObject(result.getMessage(), clazz);
                } else {
                    // 错误日志入库

                    throw new ErrorCodeException(result);
                }
            case PUT:
                result = HttpUtils.put(getUrl(proKey, param),
                        setHeader(username, password), requestBody);
                logger.info("username=[{}] put [{}],result=[{}] !", new Object[]{
                        username, proKey, result});
                if (result != null && (result.getStatus() >= org.apache.http.HttpStatus.SC_OK && result.getStatus()
                        <= org.apache.http.HttpStatus.SC_MULTI_STATUS)) {
                    if (clazz == null) {
                        return null;
                    }
                    return JSONObject.parseObject(result.getMessage(), clazz);
                } else {
                    // 错误日志入库

                    throw new ErrorCodeException(result);
                }
        }
        return null;
    }

    /**
     * GET 方式获取List
     *
     * @param proKey   连接的key
     * @param username 用户名
     * @param password 密码
     * @param clazz    返回的类的class
     * @param <T>      类
     * @param param    url 参数
     * @return 返回基本类 T
     * @throws NoTokenException   没有授权
     * @throws ErrorCodeException 执行错误异常
     */
    public <T extends BasePo> List<T> getPoList(String proKey, String username,
                                                String password, Class<T> clazz, Object... param)
            throws NoTokenException, ErrorCodeException {
        OpenStackResult result = HttpUtils.get(getUrl(proKey, param),
                setHeader(username, password));
        logger.info("username=[{}] getPoList [{}],result=[{}] !", new Object[]{
                username, proKey, result});

        if (result == null) {
            return null;
        }
        if (result.getStatus() == HttpStatus.SC_OK) {
            List<T> list = JSONArray.parseArray(result.getMessage(), clazz);
            return list;
        } else {
            // 错误日志入库
            throw new ErrorCodeException(result);
        }
    }


    public <T extends BasePo> List<T> getPoList(String proKey, String username,
                                                String password,String Region, Class<T> clazz, Object... param)
            throws NoTokenException, ErrorCodeException {
        OpenStackResult result = HttpUtils.get(getUrl(proKey, param),
                setHeader(username, password,Region));
        logger.info("useername=[{}] password=[{}] Region=[{}]",username,password,Region);

        logger.info("username=[{}] getPoList [{}],result=[{}] !", new Object[]{
                username, proKey, result});

        if (result == null) {
            return null;
        }
        if (result.getStatus() == HttpStatus.SC_OK) {
            List<T> list = JSONArray.parseArray(result.getMessage(), clazz);
            return list;
        } else {
            // 错误日志入库
            throw new ErrorCodeException(result);
        }
    }

    /**
     * GET 方式获取List
     *
     * @param proKey 连接的key
     * @param token  token
     * @param clazz  返回的类的class
     * @param <T>    类
     * @param param  url 参数
     * @return 返回基本类 T
     * @throws NoTokenException   没有授权
     * @throws ErrorCodeException 执行错误异常
     */
    public <T extends BasePo> List<T> getPoListToken(String proKey, String token, Class<T> clazz, Object... param) throws NoTokenException, ErrorCodeException {
        long start = Calendar.getInstance().getTimeInMillis();
        OpenStackResult result = HttpUtils.get(getUrl(proKey, param), setHeaderWithToken(token));
        long end = Calendar.getInstance().getTimeInMillis();
        logger.info("调用 " + getUrl(proKey, param) + " 耗时:" + (end - start) + " ms");
        if (result == null) {
            return new ArrayList<>();
        }
        if ((result.getStatus() >= org.apache.http.HttpStatus.SC_OK && result.getStatus() <= org.apache.http.HttpStatus.SC_MULTI_STATUS)) {
            List<T> list = JSONArray.parseArray(result.getMessage(), clazz);
            return list;
        } else {
            throw new ErrorCodeException(result);
        }
    }


    /**
     * 获取要设置的header的map
     *
     * @param token token
     * @return map
     * @throws NoTokenException 没有授权
     */
    private Map<String, String> setHeaderWithToken(String token) {
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        header.put("X-Auth-Token", token);
        return header;
    }


    /**
     * 获取URL连接
     *
     * @param key   连接的key
     * @param param 连接的参数
     * @return URL
     */
    private String getUrl(String key, Object... param) {
        String host = Constants.getPropertyValue(InitConst.HTTP_HOST);
        String port = Constants.getPropertyValue(InitConst.HTTP_PORT);
        String uri = Constants.getPropertyValue(key, param);
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(host);
        sb.append(":");
        sb.append(port);
        sb.append(uri);
//        String url = "http://" + host + ":" + port + uri;
        String url = sb.toString();
        logger.info("url:{}", url);
        return url;
    }

    /**
     * 获取要设置的header的map
     *
     * @param username 用户名
     * @param password 密码
     * @return map
     * @throws NoTokenException 没有授权
     */
    private Map<String, String> setHeader(String username, String password)
            throws NoTokenException, ErrorCodeException {
        UserPo po = getToken(username, password);
        if (po == null) {
            throw new NoTokenException(username, password);
        }
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("X-Auth-Token", po.getToken());
        logger.info("X-Auth-Token:{}", po.getToken());
        return header;
    }


    private Map<String, String> setHeader(String username, String password, String Region)
            throws NoTokenException, ErrorCodeException {
        UserPo po = getToken(username, password);
        if (po == null) {
            throw new NoTokenException(username, password);
        }
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("X-Auth-Token", po.getToken());
        header.put("X-Auth-Region", Region);
        logger.info("X-Auth-Token:{}", po.getToken());
        logger.info("X-Auth-Region", Region);
        return header;
    }

    /**
     * 获取授权的token
     *
     * @param username 用户名
     * @param password 密码
     * @return openstack用户类
     */
    public UserPo getToken(String username, String password)
            throws ErrorCodeException {
        if (username == null && password == null) {
            UserPo userPo = new UserPo();
            userPo.setToken(getId() + ":" + getTenantId());
            return userPo;
        }
        String url = getUrl(InitConst.KVM_USER_TOKEN);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        Map<String, String> request = new HashMap<String, String>();
        request.put("name", username);
        if (password != null && !password.equals(""))
            request.put("password", password);
        String requestBody = JSONObject.toJSONString(request);
        OpenStackResult result = HttpUtils.post(url, header, requestBody);
        logger.info("OpenStackResult:{}", ToStringBuilder.reflectionToString(result));
        if (result == null) {
            return null;
        }
        UserPo userPo = null;
        if (result.getStatus() == HttpStatus.SC_OK) {
            userPo = JSONObject.parseObject(result.getMessage(), UserPo.class);
        } else {
            throw new ErrorCodeException(result);
        }
        return userPo;
    }

    /**
     * 获取要设置的header的map json模式
     *
     * @return map
     */
    private Map<String, String> setHeaderWithNoAuth() {
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        return header;
    }

    /**
     * 无授权请求
     *
     * @param prokey      url的key
     * @param clazz       获取类
     * @param requestBody 请求体
     * @param params      url参数
     * @param <T>         获取的类
     * @return 类T
     */
    public <T extends BasePo> T poWithNoAuth(String prokey, Class<T> clazz,
                                             String requestBody, Object... params) throws ErrorCodeException {
        String url = getUrl(prokey, params);
        Map<String, String> header = setHeaderWithNoAuth();
        OpenStackResult result = HttpUtils.post(url, header, requestBody);
        if (result == null) {
            return null;
        }
        T t = null;
        if (result.getStatus() == HttpStatus.SC_OK) {
            if (clazz == null) {
                return t;
            }
            t = JSONObject.parseObject(result.getMessage(), clazz);
        } else {
            throw new ErrorCodeException(result);
        }
        return t;
    }

    public static void main(String[] args) {
        System.out.println(JSONObject.parseObject(null, Object.class));
    }
}

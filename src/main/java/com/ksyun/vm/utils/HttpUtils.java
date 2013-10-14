package com.ksyun.vm.utils;

import com.ksyun.vm.pojo.OpenStackResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * User: liuchuandong
 * Date: 13-9-5
 * Time: 下午3:13
 * Func: httpclient处理类
 */
public class HttpUtils {
	private Logger logger = LoggerFactory.getLogger(getClass());

    static PoolingClientConnectionManager cm;
    static HttpParams params;
    static final int TIMEOUT = 20000;//连接超时时间
    static final int SO_TIMEOUT = 60000;//数据传输超时
    static HttpClient client;

    /**
     * 静态初始化
     */
    static{
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
        cm = new PoolingClientConnectionManager(schemeRegistry);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
        params = new BasicHttpParams();
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIMEOUT);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
    }

    /**
     * 获取client
     * @return
     */
    public static DefaultHttpClient getHttpClient(){
        DefaultHttpClient client = new DefaultHttpClient(cm,params);
        return client;
    }

    /**
     * post请求
     * @param url
     * @param header
     * @param requestBody
     * @return
     */
    public static OpenStackResult post(String url,Map<String,String> header,String requestBody) {
        client = getHttpClient();
        HttpPost post = new HttpPost(url);
        try {
            setHeader(post,header);
            StringEntity stringEntity = new StringEntity(requestBody,"UTF-8");
            post.setEntity(stringEntity);
            HttpResponse response = client.execute(post);
            OpenStackResult result = getResult(response);
            close();
            return result;
        }catch (IOException e){
            post.abort();
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get请求
     * @param url
     * @param header
     * @return
     */
    public static OpenStackResult get(String url,Map<String,String> header){
        client = getHttpClient();
        HttpGet get = new HttpGet(url);
        try{
            setHeader(get,header);
            HttpResponse response = client.execute(get);
            OpenStackResult result = getResult(response);
            close();
            return result;
        } catch (IOException e) {
            get.abort();
            e.printStackTrace();
            return null;
        }
    }

    /**
     * delete 请求
     * @param url
     * @param header
     * @return
     */
    public static OpenStackResult delete(String url,Map<String,String> header){
        client = getHttpClient();
        HttpDelete delete = new HttpDelete(url);
        try{
            setHeader(delete,header);
            HttpResponse response = client.execute(delete);
            OpenStackResult result = getResult(response);
            close();
            return result;
        } catch (IOException e) {
            delete.abort();
            e.printStackTrace();
            return null;
        }
    }

    /**
     * put请求
     * @param url
     * @param header
     * @param requestBody
     * @return
     */
    public static OpenStackResult put(String url,Map<String,String> header,String requestBody){
        client = getHttpClient();
        HttpPut put = new HttpPut(url);
        try {
            setHeader(put,header);
            StringEntity stringEntity = new StringEntity(requestBody);
            put.setEntity(stringEntity);
            HttpResponse response = client.execute(put);
            OpenStackResult result = getResult(response);
            close();
            return result;
        }catch (IOException e){
            put.abort();
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放连接
     */
    public static void close(){
        client.getConnectionManager().closeIdleConnections(0L, TimeUnit.SECONDS);
    }

    /**
     * 通用请求
     * @param response
     * @return
     * @throws java.io.IOException
     */
    private static OpenStackResult getResult(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        String content = null;
        int status = response.getStatusLine().getStatusCode();
        if(entity!=null){
            content = inputStreamToString(entity.getContent());
        }
        close();
        OpenStackResult result = new OpenStackResult();
        result.setStatus(status);
        result.setMessage(content);
        return result;
    }

    /**
     * 设置请求头
     * @param base
     * @param header
     */
    private static void setHeader(HttpRequestBase base,Map<String,String> header){
        Iterator<Map.Entry<String,String>> it = header.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,String> entry = it.next();
            base.setHeader(entry.getKey(),entry.getValue());
        }
    }

    /**
     * 获取内容
     * @param input
     * @return
     * @throws java.io.IOException
     */
    private static String inputStreamToString(InputStream input) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int count = -1;
        while ((count = input.read(data, 0, 1024)) != -1)
            outStream.write(data, 0, count);
        data = null;
        return new String(outStream.toByteArray(), "UTF-8");
    }


}

package com.utils.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName HttpUtils
 * @Description: http工具类
 * @Author 王瑞清
 * @Date 2020/2/7
 * @Version V1.0
 **/
public class HttpUtils {

    protected static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static CloseableHttpClient httpClient = null;
    public static HttpClientContext context = null;
    public static CookieStore cookieStore = null;
    public static RequestConfig requestConfig = null;

    static {
        init();
    }

    /**
     * 初始化
     */
    private static void init() {
        context = HttpClientContext.create();
        cookieStore = new BasicCookieStore();
        requestConfig = RequestConfig.custom().setConnectTimeout(120000).setSocketTimeout(60000).setConnectionRequestTimeout(60000).build();
        httpClient = HttpClientBuilder.create().setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setRedirectStrategy(new DefaultRedirectStrategy()).setDefaultRequestConfig(requestConfig).setDefaultCookieStore(cookieStore).build();
    }

    /**
     * GET请求
     */
    public static String DO_GET(String url, Map<String, String> headers, String encode) {
        if (encode == null) {
            encode = "utf-8";
        }
        String content = null;
        HttpGet httpGet = new HttpGet(url);

        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            httpResponse.setHeaders(httpResponse.getAllHeaders());
            httpResponse.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            httpResponse.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return content;
    }

    /**
     * POST请求 参数以form表单键值对的形式提交
     */
    public static String DO_POST(String url, Map<String, Object> params, Map<String, String> headers, String encode) {
        if (encode == null) {
            encode = "utf-8";
        }
        HttpPost httpost = new HttpPost(url);
        // 设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 组织请求参数
        List<NameValuePair> paramList = new ArrayList<>();
        if (params != null && params.size() > 0) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                paramList.add(new BasicNameValuePair(key, params.get(key).toString()));
            }
        }
        try {
            httpost.setEntity(new UrlEncodedFormEntity(paramList, encode));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            httpResponse.setHeaders(httpResponse.getAllHeaders());
            httpResponse.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            httpResponse.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return content;
    }

    /**
     * POST请求 参数以原生字符串进行提交
     */
    public static String DO_POST(String url, String stringJson, Map<String, String> headers, String encode) {
        CloseableHttpResponse httpResponse = null;
        try {
            if (encode == null) {
                encode = "utf-8";
            }
            HttpPost httpost = new HttpPost(url);
            // 设置header
            httpost.addHeader("Content-Type", "application/json; charset=utf-8");
            httpost.setHeader("Accept", "application/json");
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            // 组织请求参数
            StringEntity stringEntity = new StringEntity(stringJson);
            httpost.setEntity(stringEntity);
            String content = null;
            // 响应信息
            httpResponse = httpClient.execute(httpost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                logger.info("DO_POST ok++++++++++++++");
                HttpEntity entity = httpResponse.getEntity();
                content = EntityUtils.toString(entity, encode);
            }

            return content;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * PUT请求 参数以原生字符串进行提交
     */
    public static String DO_PUT(String url, String stringJson, Map<String, String> headers, String encode) {
        if (encode == null) {
            encode = "utf-8";
        }
        HttpPut httpput = new HttpPut(url);
        // 设置header
        httpput.setHeader("Content-type", "application/json");
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpput.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 组织请求参数
        StringEntity stringEntity = new StringEntity(stringJson, encode);
        httpput.setEntity(stringEntity);
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            // 响应信息
            httpResponse = httpClient.execute(httpput);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                logger.info("DO_PUT ok++++++++++++++");
                HttpEntity entity = httpResponse.getEntity();
                content = EntityUtils.toString(entity, encode);
            }

            return content;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * DELETE请求
     */
    public static String DO_DELETE(String url, Map<String, String> headers, String encode) {
        if (encode == null) {
            encode = "utf-8";
        }
        String content = null;
        HttpDelete httpdelete = new HttpDelete(url);
        // 设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpdelete.setHeader(entry.getKey(), entry.getValue());
            }
        }
        CloseableHttpResponse httpResponse = null;
        try {
            // 响应信息
            httpResponse = httpClient.execute(httpdelete);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                logger.info("DO_DELETE ok++++++++++++++");
                HttpEntity entity = httpResponse.getEntity();
                content = EntityUtils.toString(entity, encode);
            }

            return content;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}

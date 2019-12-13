package com.google.util.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Http请求工具类
 *
 * @author wk
 * update time 20190815 14:00
 */
public class HttpRequestUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpRequestUtils.class);

    private static final Charset CHARSET_UTF8 = Charset.forName(CharSetConstants.UTF_8);
    private static final Charset CHARSET_GBK = Charset.forName(CharSetConstants.GBK);

    private static PoolingHttpClientConnectionManager cm = null;

    static {
        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(createIgnoreVerifySSL());
        } catch (NoSuchAlgorithmException e) {
            log.error("创建SSL连接失败");
            e.printStackTrace();
        } catch (KeyManagementException e) {
            log.error("创建SSL连接失败");
            e.printStackTrace();
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(100);
        //cm.setMaxPerRoute(new HttpRoute(new HttpHost("somehost",80)),150);
    }

    private static CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     * 绕过证书方式实现ssl请求
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    /**
     * 判断字符集
     *
     * @param charsetFlag
     * @return
     */
    private static Charset judgeCharSet(String charsetFlag) {
        if (CharSetConstants.UTF_8.equals(charsetFlag)) {
            return CHARSET_UTF8;
        } else if (CharSetConstants.GBK.equals(charsetFlag)) {
            return CHARSET_GBK;
        }
        return null;
    }

    /**
     * @param timeout        设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
     * @param connectTimeout 设置连接超时时间，单位毫秒。
     * @param socketTimeout  请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
     * @return
     */
    private static RequestConfig getRequestConfig(int timeout, int connectTimeout, int socketTimeout) {
        return RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(socketTimeout).build();
    }

    /**
     * 关闭流
     *
     * @param res
     */
    private static void closeStream(CloseableHttpResponse res) {
        if (res != null) {
            try {
                EntityUtils.consume(res.getEntity());
                res.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * return HttpResult
     *
     * @param httpResponse
     * @param charset
     * @return
     * @throws IOException
     */
    private static HttpResult getHttpResult(CloseableHttpResponse httpResponse, Charset charset) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK && httpResponse.getEntity() != null) {
            return new HttpResult(statusCode,
                    EntityUtils.toString(httpResponse.getEntity(), charset));
        } else {
            return new HttpResult(statusCode, "");
        }
    }

    /**
     * 无参get请求
     *
     * @param url
     * @return
     */
    public static HttpResult doGet(String url, JSONObject jsonData) {
        CloseableHttpClient closeableHttpClient = HttpRequestUtils.getHttpClient();
        CloseableHttpResponse httpResponse = null;
        try {

            String params = "";
            if (jsonData != null && !jsonData.isEmpty()) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                Set<Map.Entry<String, Object>> set = jsonData.entrySet();
                for (Map.Entry<String, Object> entry : set) {
                    String key = entry.getKey();
                    String value = entry.getValue().toString();
                    nameValuePairs.add(new BasicNameValuePair(key, value));
                }
                params = EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, CHARSET_GBK));
            }

            HttpGet get = new HttpGet(url + params);
            get.setConfig(getRequestConfig(5000, 5000, 20000));
            httpResponse = closeableHttpClient.execute(get);
            return getHttpResult(httpResponse, CHARSET_UTF8);
        } catch (Exception e) {
            log.error("httpclient请求失败", e);
            e.printStackTrace();
            return null;
        } finally {
            closeStream(httpResponse);
        }
    }

    /**
     * doGetSSL请求
     *
     * @param url
     * @param jsonData
     * @return
     */
    public static HttpResult doGetSSL(String url, JSONObject jsonData) {

        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = getHttpClient();
        try {
            String params = "";
            if (jsonData != null && !jsonData.isEmpty()) {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                Set<Map.Entry<String, Object>> set = jsonData.entrySet();
                for (Map.Entry<String, Object> entry : set) {
                    String key = entry.getKey();
                    String value = entry.getValue().toString();
                    nameValuePairs.add(new BasicNameValuePair(key, value));
                }
                params = EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, CharSetConstants.UTF_8));
            }
            StringBuffer stringBuffer = new StringBuffer(url);
            stringBuffer.append("?").append(params);
            HttpGet httpGet = new HttpGet(stringBuffer.toString());
            httpGet.setConfig(getRequestConfig(5000, 5000, 20000));
            httpResponse = httpClient.execute(httpGet);
            return getHttpResult(httpResponse, CHARSET_UTF8);
        } catch (Exception e) {
            log.error("httpclient请求失败", e);
            e.printStackTrace();
            return null;
        } finally {
            closeStream(httpResponse);
        }
    }

    /**
     * 参数为字符串比如json串
     *
     * @param url
     * @param str
     * @param charsetFlag
     * @return
     */
    public static HttpResult doPost(String url, String str, String charsetFlag) {
        CloseableHttpClient httpClient = HttpRequestUtils.getHttpClient();
        CloseableHttpResponse httpResponse = null;
        Charset charset = judgeCharSet(charsetFlag);
        try {
            HttpPost post = new HttpPost(url);
            StringEntity se = new StringEntity(str, charset);
            post.setEntity(se);
            httpResponse = httpClient.execute(post);
            return getHttpResult(httpResponse, charset);
        } catch (Exception e) {
            log.error("httpclient请求失败", e);
            e.printStackTrace();
            return null;
        } finally {
            closeStream(httpResponse);
        }
    }

    /**
     * doPost请求，参数UrlEncodedFormEntity
     *
     * @param url
     * @param map
     * @return
     */
    public static HttpResult doPost(String url, Map<String, Object> map) {
        CloseableHttpResponse httpResponse = null;
        try {
            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            if (map != null) {
                List<NameValuePair> params = new ArrayList<>();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, CHARSET_UTF8);
                httpPost.setConfig(getRequestConfig(5000, 5000, 20000));
                httpPost.setEntity(formEntity);
            }
            httpResponse = closeableHttpClient.execute(httpPost);
            return getHttpResult(httpResponse, CHARSET_UTF8);
        } catch (Exception e) {
            log.error("httpclient请求失败", e);
            e.printStackTrace();
            return null;
        } finally {
            closeStream(httpResponse);
        }
    }

    /**
     * @param url
     * @param str
     * @return
     */
    public static HttpResult doPost(String url, String str) {
        CloseableHttpClient httpClient = HttpRequestUtils.getHttpClient();
        CloseableHttpResponse httpResponse = null;
        try {
            HttpPost post = new HttpPost(url);
            StringEntity stringEntity = new StringEntity(str, ContentType.create("application/xml", CharSetConstants.UTF_8));
            post.setEntity(stringEntity);
            post.setConfig(getRequestConfig(5000, 5000, 20000));
            httpResponse = httpClient.execute(post);
            return getHttpResult(httpResponse, CHARSET_UTF8);
        } catch (Exception e) {
            log.error("httpclient请求失败", e);
        } finally {
            closeStream(httpResponse);
        }
        return null;
    }

    /**
     * doPost 编码utf-8
     *
     * @param url
     * @param json
     * @param mimeType
     * @return
     */
    public static HttpResult doPost(String url, JSONObject json, String mimeType) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        HttpPost post = new HttpPost(url);
        try {
            String data = json.toString();
            StringEntity stringEntity = new StringEntity(data, ContentType.create(mimeType, CharSetConstants.UTF_8));
            post.setEntity(stringEntity);
            post.setConfig(getRequestConfig(5000, 5000, 20000));
            httpResponse = closeableHttpClient.execute(post);
            return getHttpResult(httpResponse, CHARSET_UTF8);
        } catch (Exception e) {
            log.error("httpclient请求失败", e);
            e.printStackTrace();
            return null;
        } finally {
            closeStream(httpResponse);
        }
    }

    public static HttpResult soapPost(String url, String str) {
        CloseableHttpClient httpClient = HttpRequestUtils.getHttpClient();
        CloseableHttpResponse httpResponse = null;
        try {
            HttpPost post = new HttpPost(url);
            StringEntity stringEntity = new StringEntity(str, ContentType.create("text/xml", CharSetConstants.UTF_8));
            post.setEntity(stringEntity);
            post.setConfig(getRequestConfig(5000, 5000, 20000));
            httpResponse = httpClient.execute(post);
            return getHttpResult(httpResponse, CHARSET_UTF8);
        } catch (Exception e) {
            log.error("httpclient请求失败", e);
            e.printStackTrace();
            return null;
        } finally {
            closeStream(httpResponse);
        }
    }


}

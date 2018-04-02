package com.wisdom.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.util.List;
import java.util.Map;

/**
 * Created by baoweiw on 2015/7/27.
 */
public class HttpRequestUtil {

    /**
     * 服务端发起get请求
     * @param url
     * @return
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        HttpClient client = new DefaultHttpClient();
        HttpResponse resp = client.execute(httpGet);
        HttpEntity entity = resp.getEntity();
        String respContent = EntityUtils.toString(entity, "utf-8").trim();
        httpGet.abort();
        client.getConnectionManager().shutdown();

        return respContent;
    }

    /**
     * 服务端发起post请求
     * @param url
     * @param content
     * @return
     * @throws IOException
     */
    public static int post(String url, String content) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        try {
            httppost.setEntity(new StringEntity(content, "utf-8"));
            HttpResponse response = httpclient.execute(httppost);
            return response.getStatusLine().getStatusCode();
        } catch (IOException e) {
            e.printStackTrace();
            return 500;
        }
    }


    public static JSONObject postByJson(String url, String json){
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.OK.value()){
                HttpEntity entity = res.getEntity();
                InputStream is = entity.getContent();
//                String charset = EntityUtils.getContentCharSet(entity);
                if (entity != null) {
                    // 将InputStream转换为Reader，并使用缓冲读取，提高效率，同时可以按行读取内容
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            is, "UTF-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    is.close();
                    response = new JSONObject(line);
                }

//                response = new JSONObject(new JSONTokener(new InputStreamReader(is));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }


    public static String httpPost(String postData, String postUrl) {
        try {
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(
                    conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }


    /**
     * 发起https请求并获取结果
     *
     * @param urlPath 请求地址
     * @param method 请求方式（GET、POST）
     * @param content 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static String getConnectionResult(String urlPath,String method,String content){
        try {
            URL url;
            url = new URL(urlPath);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod(method.toUpperCase());
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setReadTimeout(10000);  //超时时间
            if ("GET".equalsIgnoreCase(method))connection.connect();
            if(!content.isEmpty()){
                OutputStream os=connection.getOutputStream();
                BufferedOutputStream bos=new BufferedOutputStream(os);
                bos.write(content.getBytes("utf-8"));
                bos.close();
            }
            InputStream is=connection.getInputStream();
            String str="";
            StringBuffer outputValue=new StringBuffer();
            BufferedReader br=new BufferedReader(new InputStreamReader(is, "utf-8"));
            while((str=br.readLine())!=null)
            {
                outputValue.append(str);
                outputValue.append("\n");
            }
            br.close();
            String result=outputValue.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送https请求
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return 返回微信服务器响应的信息
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr,String... token) {
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            if(token.length>0) {
                conn.setRequestProperty("Authorization", "Bearer " + token[0]);
            }

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            String responseCode=conn.getResponseCode()+">>>>";
            if(responseCode.equals("403>>>>")){
                conn.disconnect();
                return responseCode;
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            if(buffer!=null){
                responseCode=responseCode+buffer.toString();
            }else{
                responseCode=responseCode;
            }
            return responseCode;
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url,String... token) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url ;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if(token.length>0) {
                connection.setRequestProperty("Authorization", "Bearer "+token[0]);
            }
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

}

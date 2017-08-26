package com.taotao.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/8/25
 */
public class HttpClientTest {

    @Test
    public void goGet() throws Exception{
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建GET对象
        HttpGet get = new HttpGet("http://www.sogou.com");
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //获取结果
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity httpEntity = response.getEntity();
        String string = EntityUtils.toString(httpEntity, "utf-8");
        System.out.println(string);
        //关闭请求
        response.close();
        httpClient.close();
    }

    @Test
    public void doGetWithParams() throws Exception{
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();



        //创建一个uri对象
        URIBuilder uriBuilder = new URIBuilder("http://www.sogou.com/web");
        uriBuilder.addParameter("query", "捷库");
        HttpGet get = new HttpGet(uriBuilder.build());


        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //获取结果
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity httpEntity = response.getEntity();
        String string = EntityUtils.toString(httpEntity, "utf-8");
        System.out.println(string);
        //关闭请求
        response.close();
        httpClient.close();
    }

    @Test
    public void doPost() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建一个post对象
        HttpPost post = new HttpPost("http://localhost:8082/httpclient/post.action");
        //执行post请求
        CloseableHttpResponse response = httpClient.execute(post);
        String string = EntityUtils.toString(response.getEntity());
        System.out.println(string);
        response.close();
        httpClient.close();

    }

    @Test
    public void doPostWithParam() throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建一个post对象
        HttpPost post = new HttpPost("http://localhost:8082/httpclient/post.action");
        //创建一个Entity。模拟一个表单
        List<NameValuePair> kvList = new ArrayList<>();
        kvList.add(new BasicNameValuePair("username", "张三"));
        kvList.add(new BasicNameValuePair("password", "123"));

        //包装成一个Entity对象
        StringEntity entity = new UrlEncodedFormEntity(kvList, "utf-8");
        //设置请求的内容
        post.setEntity(entity);

        //执行post请求
        CloseableHttpResponse response = httpClient.execute(post);
        String string = EntityUtils.toString(response.getEntity());
        System.out.println(string);
        response.close();
        httpClient.close();
    }
}

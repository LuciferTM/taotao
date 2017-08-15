package com.taotao.common.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;


public class QiniuUtils {
    public static String QINIU_URL = "http://iap-bucket.qiniudn.com/";
    private static String accessKey = "FwJ1DJn89lVJk1jLbX6502GmXzfiUYu11od_FINK";
    private static String secretKey = "z85uby4g8re9hIg9HHOyYo1TsAdqz4STMpY6lSnD";
    private static String bucket = "iap-bucket";
    //构造一个带指定Zone对象的配置类
    private static Configuration cfg = new Configuration(Zone.zone0());
    //...其他参数参考类注释
    private static UploadManager uploadManager = new UploadManager(cfg);

    public static String uploadFile(String fileName, InputStream input) {
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(input, fileName, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            key = putRet.key;
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            return key;
        }
        return key;
    }
}
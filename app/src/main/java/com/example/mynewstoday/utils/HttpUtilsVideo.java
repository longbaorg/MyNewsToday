package com.example.mynewstoday.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;



public class HttpUtilsVideo {
    //视频碎片的联网工具类


    public static String getStringContent(String path){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get().url(path).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                return body.string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

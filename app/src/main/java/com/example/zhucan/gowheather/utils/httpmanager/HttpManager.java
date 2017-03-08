package com.example.zhucan.gowheather.utils.httpmanager;

import android.support.annotation.VisibleForTesting;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lubin on 2016/11/27.
 */

public class HttpManager {

    public static final String NO_CALLBACK="没有响应";
    private static final String TAG = HttpManager.class.getSimpleName();

    //httpclient 的post方法
    public static String postRequest(String url, Map<String, String> paramMap) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        //用传进来的参数构建Builder
        FormBody.Builder builder =new FormBody.Builder();

            Set<String> keySet = paramMap.keySet();
            for(String key:keySet) {
                builder.add(key, paramMap.get(key));
            }

        RequestBody requestBody = builder.build();
        //创建request
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        String result = "";
        try {
            //获取服务器的响应数据
            Response response = okHttpClient.newCall(request).execute();
            if(response.isSuccessful()) {
                result = response.body().string();
            }
        } catch (IOException e) {
            throw e;
        }
        //返回响应数据已供进一步操作
        return result;
    }

    //httpclient的get方法
    public static String getRequest(String url, Map<String, String> paramMap) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();

        //使用buildUrl方法来把url和数据拼接
        url = buildUrlWithParam(url, paramMap);
        //创建request
        final Request request = new Request.Builder().url(url).build();
        String result = "";
        try {
            //获取服务器响应
            Response response = okHttpClient.newCall(request).execute();
            if(response.isSuccessful()) {
                result = response.body().string();
            }
        } catch(IOException e) {
            throw e;
        }
        return result;
    }
    public static String getResponse(String url) throws IOException{
        OkHttpClient client=new OkHttpClient();

        final Request request=new Request.Builder().url(url).build();

        String result="";
        try{
            Response response=client.newCall(request).execute();
            if (response.isSuccessful()){
                result=response.body().string();
            }
        }catch (IOException e){
            throw e;
        }
        return result;

    }

    /**
     * 将参数和url拼装成完整的可用的url
     * @param originUrl 不带参数的url
     * @param paramMap 参数集合
     * @return 拼装后的url
     */
    @VisibleForTesting
    public static String buildUrlWithParam(String originUrl, Map<String, String> paramMap) {
        //使用StringBuilder来拼接url和参数
        StringBuilder url = new StringBuilder(originUrl);
            url.append("?");
            Set<String> keySet = paramMap.keySet();
            for(String key:keySet) {
                url.append(key + "=" + paramMap.get(key) + "&");

            url.deleteCharAt(url.length()-1);
        }
        return url.toString();
    }
}

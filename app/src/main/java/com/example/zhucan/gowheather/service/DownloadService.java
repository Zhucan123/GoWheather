package com.example.zhucan.gowheather.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.example.zhucan.gowheather.config.Configsetting;
import com.example.zhucan.gowheather.config.StringConfig;
import com.example.zhucan.gowheather.utils.httpmanager.HttpManager;
import com.example.zhucan.gowheather.utils.httpmanager.SharedPreference.PreferenceUtil;

import java.io.IOException;

/**
 * Created by zhucan on 2017/3/3.
 */

public class DownloadService extends Service {
    private String TAG="Service";
    private String data;

    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        DataTask task=new DataTask();
        task.execute(Configsetting.getCityWeatherUrl(PreferenceUtil.getCityId()));
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        NowDataTask task=new NowDataTask();
        task.execute(Configsetting.getNowWeatherUrl(PreferenceUtil.getCityId()));
        DataTask task1=new DataTask();
        task1.execute(Configsetting.getCityWeatherUrl(PreferenceUtil.getCityId()));
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }


    public class DataTask extends AsyncTask<String,Void,String>{
        String result;
        @Override
        protected String doInBackground(String...params){
           try {
               result = HttpManager.getResponse(params[0]);
           }catch (IOException e){
               e.printStackTrace();
           }
            PreferenceUtil.inCreaseWeather(result);
            return result;
        }

        @Override
        protected void onPostExecute(String result){
            Intent intent=new Intent();
            intent.setAction("com.service.MY_RECEIVER");
            intent.putExtra(StringConfig.WEATHER_DATA,result);
            sendBroadcast(intent);

        }
    }

    public class NowDataTask extends AsyncTask<String,Void,String>{
        String result;
        @Override
        protected String doInBackground(String...params){
            try {

                result = HttpManager.getResponse(params[0]);

            }catch (IOException e){
                e.printStackTrace();
            }
            PreferenceUtil.setNowData(result);

            return result;
        }

        @Override
        protected void onPostExecute(String result){
            Intent intent=new Intent();
            intent.setAction("com.service.MY_RECEIVER");
            intent.putExtra(StringConfig.NOW_WEATHER_DATA,result);
            sendBroadcast(intent);

        }
    }
}

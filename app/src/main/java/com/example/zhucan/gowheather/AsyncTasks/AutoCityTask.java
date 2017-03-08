package com.example.zhucan.gowheather.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.zhucan.gowheather.citydata.model.AutoCity;
import com.example.zhucan.gowheather.config.Configsetting;
import com.example.zhucan.gowheather.utils.httpmanager.HttpManager;
import com.example.zhucan.gowheather.utils.httpmanager.SharedPreference.PreferenceUtil;
import com.example.zhucan.gowheather.utils.httpmanager.ToastUtil.ToastUtil;
import com.google.gson.Gson;
import java.io.IOException;


/**
 * Created by zhucan on 2017/3/5.
 */

public class AutoCityTask extends AsyncTask<Void,Void,String>{
    private String result;
    private String cityName;
    private StringBuilder sb=new StringBuilder("");


    protected String doInBackground(Void...params){
        try{
            result= HttpManager.getResponse(Configsetting.AUTO_CITY_URL);
           String json=result.substring(result.indexOf("=")+2,result.length()-1);

            Gson g=new Gson();
            AutoCity city=g.fromJson(json,AutoCity.class);
            cityName=city.getCity();
            PreferenceUtil.setCityName(cityName);

        }catch (IOException e){
            e.printStackTrace();
    }

        return cityName;
    }
    protected void onPostExecute(String cityName){
        Log.i("奶奶的个球啊 ","onPostExecute: "+PreferenceUtil.getCityName());
        ToastUtil.showToast(PreferenceUtil.getCityName());
    }
}

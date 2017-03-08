package com.example.zhucan.gowheather.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.zhucan.gowheather.R;
import com.example.zhucan.gowheather.citydata.model.CitySearchId;
import com.example.zhucan.gowheather.citydata.model.CityWeather;
import com.example.zhucan.gowheather.config.Configsetting;
import com.example.zhucan.gowheather.service.DownloadService;
import com.example.zhucan.gowheather.utils.httpmanager.HttpManager;
import com.example.zhucan.gowheather.utils.httpmanager.SharedPreference.PreferenceUtil;
import com.example.zhucan.gowheather.utils.httpmanager.ToastUtil.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zhucan on 2017/2/26.
 */

public class CityIdTask extends AsyncTask<String,Void,String> {
    Context context;
    private String cityName;
    private String result;
    private String TAG="我去你妹的";

    public CityIdTask(Context context,String cityName){
        this.cityName=cityName;
        this.context=context;

    }

    protected String doInBackground(String...params){
        try {
            result = HttpManager.getResponse(Configsetting.getCityidUrl(cityName));

        }catch (IOException e){
            e.printStackTrace();
        }
        int index = result.indexOf("\"status\":");
        String status = result.substring(index + 10, index + 12);

        if (status.equals("ok")) {
            Gson gson = new Gson();
            CitySearchId bean = gson.fromJson(result, CitySearchId.class
            );
            CitySearchId.HeWeather5Bean bean1 = bean.getHeWeather5().get(0);
            String cityId = bean1.getBasic().getId();

            PreferenceUtil.inCreaseCityId(cityId);



        }
        return status;
    }

    protected void onPostExecute(String result){
       if (result.equals("ok"))
      {

                ToastUtil.showToast("已确认地址...");
            }else {
           ToastUtil.showToast("你搜索的地址未找到...");
       }
        Intent intent=new Intent(context,DownloadService.class);
        context.startService(intent);

    }
}

package com.example.zhucan.gowheather.utils.httpmanager.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.zhucan.gowheather.citydata.model.CityWeather;
import com.example.zhucan.gowheather.utils.httpmanager.ApplicationContext;

/**
 * Created by zhucan on 2017/2/26.
 */

public class PreferenceUtil {

    private static String CITY_ID="cityId";
    private static String FILE_NAME="myData";
    private static String WEATHER_DATA="weatherData";
    private static String CITY_NAME="myCityName";
    private static String NOW_DATA="myNowData";

    private static SharedPreferences preferences= ApplicationContext.getContext().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
    private static SharedPreferences.Editor editor = preferences.edit();

    public static void inCreaseCityId(String cityId){
        editor.putString(CITY_ID,cityId);
        editor.commit();
    }

    public static String getCityId(){
        return preferences.getString(CITY_ID,null);
    }

    public static void inCreaseWeather(String data){
        editor.putString(WEATHER_DATA,data);
        editor.commit();
    }

    public static void setCityName(String name){
        editor.putString(CITY_NAME,name);
        editor.commit();
    }

    public static String getCityName(){
        return preferences.getString(CITY_NAME,null);
    }

    public static String getWeatherData(){
        return preferences.getString(WEATHER_DATA,null);
    }

    public static void setNowData(String data){
        editor.putString(NOW_DATA,data);
        editor.commit();
    }

    public static String getNowData(){
        return preferences.getString(NOW_DATA,null);
    }



}



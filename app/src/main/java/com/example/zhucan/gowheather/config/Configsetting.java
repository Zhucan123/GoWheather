package com.example.zhucan.gowheather.config;

/**
 * Created by zhucan on 2017/2/24.
 */

public final class Configsetting {
    private Configsetting(){

    }

    //天气api地址
    public final static String CITY_WEATHER_URL="https://free-api.heweather.com/v5";

    //天气api验证key码
    public static String weatherKey="4526518f2aa44138b50a075cd9e78406";

    //城市列表获取url
    public final static String CITY_LIST_URL="http://files.heweather.com/china-city-list.json";

    //通过网络定位的url
    public final static String AUTO_CITY_URL="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js";
    //获取拼接后天气url
    public static String getCityWeatherUrl(String cityname){
        return CITY_WEATHER_URL+"/weather?city="+cityname+"&key="+weatherKey;
    }

    //获取实时天气url
    public static String getNowWeatherUrl(String cityname){
        return CITY_WEATHER_URL+"/now?city="+cityname+"&key="+weatherKey;
    }

    //把城市名字通过网站转换为cityid的url
    public  static String getCityidUrl(String name){
        return CITY_WEATHER_URL+"/search?city="+name+"&key="+weatherKey;
    }

}

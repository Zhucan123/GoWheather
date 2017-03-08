package com.example.zhucan.gowheather.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.zhucan.gowheather.AsyncTasks.AutoCityTask;
import com.example.zhucan.gowheather.AsyncTasks.CityIdTask;
import com.example.zhucan.gowheather.R;
import com.example.zhucan.gowheather.citydata.model.AutoCity;
import com.example.zhucan.gowheather.citydata.model.CityNow;
import com.example.zhucan.gowheather.citydata.model.CityWeather;
import com.example.zhucan.gowheather.config.Configsetting;
import com.example.zhucan.gowheather.config.StringConfig;
import com.example.zhucan.gowheather.service.DownloadService;
import com.example.zhucan.gowheather.utils.httpmanager.HttpManager;
import com.example.zhucan.gowheather.utils.httpmanager.SharedPreference.PreferenceUtil;
import com.example.zhucan.gowheather.utils.httpmanager.ToastUtil.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private String TAG = "我去你妹的啊";
    private String name;
    private int[] icons={R.drawable.icon1,R.drawable.icon2,R.drawable.icon3,
            R.drawable.icon4,R.drawable.icon5,R.drawable.icon7,R.drawable.icon6,
    R.drawable.icon8};

    private int[] background={R.drawable.homebg1,R.drawable.homebg2,
            R.drawable.homebg3,R.drawable.homebg4,};

    private String [] indexstr={"空气指数:","舒适度指数:","洗车指数:","穿衣指数:",
    "感冒指数:","运动指数:","旅游指数:","紫外线指数:"};

    private TextView tem;
    private TextView site;
    private TextView weather;
    private TextView wind;
    private TextView humidity;
    private TextView windLv;
    private TextView bodyTem;

    private TextView todayweather;
    private TextView todaytem;
    private TextView tomorrowWeather;
    private TextView tomorrowtem;
    private TextView threeweather;
    private TextView threetem;

    private ListView listView;
    private ImageButton button;



    class myRun implements Runnable{
        @Override
        public void run(){
           ToastUtil.showToast("地址不正确?  可自行搜索");
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        RelativeLayout layout=(RelativeLayout) findViewById(R.id.temBack) ;
        layout.setBackgroundResource(background[(int)(Math.random()*4)]);

        tem=(TextView)findViewById(R.id.temperature);
        site=(TextView)findViewById(R.id.site);
        weather=(TextView)findViewById(R.id.weather1);
        wind=(TextView)findViewById(R.id.wind);
        windLv=(TextView)findViewById(R.id.wind1);
        humidity=(TextView)findViewById(R.id.humidity1);
        bodyTem=(TextView)findViewById(R.id.bodyTem);

        todayweather=(TextView)findViewById(R.id.todayWeather);
        todaytem=(TextView)findViewById(R.id.todayTem);
        tomorrowWeather=(TextView)findViewById(R.id.tomorrowWeather);
        tomorrowtem=(TextView)findViewById(R.id.tomorrowTem);
        threeweather=(TextView)findViewById(R.id.threeWeather);
        threetem=(TextView)findViewById(R.id.threeTem);

        listView=(ListView)findViewById(R.id.weatherList);
        button=(ImageButton)findViewById(R.id.imageButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CityChoose.class);
                startActivity(intent);
            }
        });


       final Intent intent=new Intent(MainActivity.this,DownloadService.class);
        name= PreferenceUtil.getCityName();
              if (name==null){
                  ToastUtil.showToast("自动定位中...");
                  AutoCityTask autoTask=new AutoCityTask();
                  autoTask.execute();

                  Handler hand=new Handler();
                  hand.post(new myRun());
        }else{
                  startService(intent);
              }

        Maneuver receiver=new Maneuver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.service.MY_RECEIVER");
        registerReceiver(receiver,filter);


    }
    public class Maneuver extends BroadcastReceiver {

        public Maneuver() {
        }
        @Override
        public void onReceive(Context context, Intent intent) {

            String weatherData = intent.getStringExtra(StringConfig.WEATHER_DATA);

            if (weatherData==null){
                weatherData=PreferenceUtil.getWeatherData();
            }
            else {
                inCreaseListView(weatherData);

            }
            String nowData=intent.getStringExtra(StringConfig.NOW_WEATHER_DATA);

            if(nowData==null){
                nowData=PreferenceUtil.getNowData();
            }else {
                inCreaseView(nowData);
            }

        }
    }
    public void inCreaseView(String nowData){
        Gson gson = new Gson();
        CityNow data = gson.fromJson(nowData, CityNow.class);
        CityNow.HeWeather5Bean bean = data.getHeWeather5().get(0);
        tem.setText(bean.getNow().getTmp() + "°" + "");
        site.setText(bean.getBasic().getCity());
        weather.setText(bean.getNow().getCond().getTxt());
        wind.setText(bean.getNow().getWind().getDir());
        windLv.setText(bean.getNow().getWind().getSc() + "级" + "");
        humidity.setText(bean.getNow().getHum() + "%" + "");
        bodyTem.setText(bean.getNow().getFl() + "°" + "");

    }

    public void inCreaseListView(String weatherData){
        String[] indexname=new String[8];
        String[] index=new String[8];
        Gson gson=new Gson();
        CityWeather data=gson.fromJson(weatherData,CityWeather.class);
        CityWeather.HeWeather5Bean.SuggestionBean bean=data.getHeWeather5().get(0).getSuggestion();
        indexname[0]=bean.getAir().getBrf();
        index[0]=bean.getAir().getTxt();
        indexname[1]=bean.getComf().getBrf();
        index[1]=bean.getComf().getTxt();
        indexname[2]=bean.getCw().getBrf();
        index[2]=bean.getCw().getTxt();
        indexname[3]=bean.getDrsg().getBrf();
        index[3]=bean.getDrsg().getTxt();
        indexname[4]=bean.getFlu().getBrf();
        index[4]=bean.getFlu().getTxt();
        indexname[5]=bean.getSport().getBrf();
        index[5]=bean.getSport().getTxt();
        indexname[6]=bean.getTrav().getBrf();
        index[6]=bean.getTrav().getTxt();
        indexname[7]=bean.getUv().getBrf();
        index[7]=bean.getUv().getTxt();
        for (int i=0;i<8;i++){
            Log.i(TAG, "inCreaseListView: "+index[i]+indexname[i]);
        }

        List<Map<String,Object>> list=new ArrayList<>();
        for (int i=0;i<indexstr.length;i++){
            Map<String,Object> item=new HashMap<>();
            item.put("icon",icons[i]);
            item.put("indexstr",indexstr[i]);
            item.put("indexname",indexname[i]);
            item.put("index",index[i]);
            list.add(item);
        }

        SimpleAdapter adapter=new SimpleAdapter(MainActivity.this,list,R.layout.showcity,
                new String[]{"icon","indexstr","indexname","index"},new int[]{R.id.showIcon,
                R.id.showIndexName,R.id.showIndex,R.id.showMessage});

        listView.setAdapter(adapter);

        CityWeather.HeWeather5Bean.DailyForecastBean day1=data.getHeWeather5().get(0).getDaily_forecast().get(0);
        CityWeather.HeWeather5Bean.DailyForecastBean day2=data.getHeWeather5().get(0).getDaily_forecast().get(1);
        CityWeather.HeWeather5Bean.DailyForecastBean day3=data.getHeWeather5().get(0).getDaily_forecast().get(2);
        todayweather.setText("白天:"+day1.getCond().getTxt_d()+"/"+"夜间:"+day1.getCond().getTxt_n());
        todaytem.setText(day1.getTmp().getMax()+ "°"+"/"+day1.getTmp().getMin()+ "°");
        Log.i(TAG, "inCreaseListView: "+day1.toString());

        tomorrowWeather.setText("白天:"+day2.getCond().getTxt_d()+"/"+"夜间:"+day2.getCond().getTxt_n());
        tomorrowtem.setText(day2.getTmp().getMax()+ "°"+"/"+day2.getTmp().getMin()+ "°");
        threeweather.setText("白天:"+day3.getCond().getTxt_d()+"/"+"夜间:"+day3.getCond().getTxt_n());
        threetem.setText(day3.getTmp().getMax()+ "°"+"/"+day3.getTmp().getMin()+ "°");



    }

    public class AutoCityTask extends AsyncTask<Void,Void,String>{
        private String result;
        private String cityName;

        @Override
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
        @Override
        protected void onPostExecute(String cityName){
            ToastUtil.showToast(PreferenceUtil.getCityName());
            name=cityName;
            CityIdTask idTask=new CityIdTask(MainActivity.this,name);
            idTask.execute();
            //Intent intent=new Intent(MainActivity.this,DownloadService.class);
           // startService(intent);
        }
    }


}







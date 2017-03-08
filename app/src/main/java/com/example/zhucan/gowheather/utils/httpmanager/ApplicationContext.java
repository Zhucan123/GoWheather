package com.example.zhucan.gowheather.utils.httpmanager;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhucan on 2017/2/27.
 */

public class ApplicationContext extends Application{
    private static Context mcontext;
@Override
    public void onCreate(){
        super.onCreate();
        ApplicationContext.mcontext=getApplicationContext();

    }
    public static Context getContext(){
        return ApplicationContext.mcontext;
    }
}

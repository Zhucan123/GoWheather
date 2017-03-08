package com.example.zhucan.gowheather.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.zhucan.gowheather.R;

/**
 * Created by zhucan on 2017/3/7.
 */

public class Welcome extends Activity {

    public class myrun implements Runnable{
        @Override
        public void run(){
            Intent intent=new Intent(Welcome.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        Handler handler=new Handler();
        handler.postDelayed(new myrun(),2000);
}
}

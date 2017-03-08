package com.example.zhucan.gowheather.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.zhucan.gowheather.AsyncTasks.CityIdTask;
import com.example.zhucan.gowheather.R;
import com.example.zhucan.gowheather.config.Configsetting;
import com.example.zhucan.gowheather.utils.httpmanager.SharedPreference.PreferenceUtil;

/**
 * Created by zhucan on 2017/2/26.
 */

public class CityChoose extends Activity {
    EditText editText;
    ImageButton button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosecity);

        editText=(EditText)findViewById(R.id.searchtext);
        button=(ImageButton)findViewById(R.id.go);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cityName=editText.getText().toString();
                CityIdTask cityIdTask=new CityIdTask(CityChoose.this,cityName);
                cityIdTask.execute();
            }
        });



    }
}
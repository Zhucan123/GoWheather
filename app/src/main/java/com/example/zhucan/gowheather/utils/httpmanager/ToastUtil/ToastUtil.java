package com.example.zhucan.gowheather.utils.httpmanager.ToastUtil;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.zhucan.gowheather.utils.httpmanager.ApplicationContext;

/**
 * Created by zhucan on 2017/2/27.
 */

public final class ToastUtil {
   private ToastUtil(){

    }

    public static void showToast(String show){
        Toast.makeText(ApplicationContext.getContext(),show,Toast.LENGTH_LONG).show();
    }
}

package com.example.zhucan.gowheather.utils.httpmanager.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhucan on 2017/2/14.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    final String CREATE_TABLE_SQL="create table dict(_id integer primary " +
            " key autoincrement,id,cityZh,provinceZh,leaderZh)";


    public MyDatabaseHelper(Context context, String name, int version){
        super(context,name ,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        System.out.println("---onUpdate Called--"+oldVersion+"--->"+newVersion);
    }

}

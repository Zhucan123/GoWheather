package com.example.zhucan.gowheather.utils.httpmanager.sqlite;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhucan on 2017/2/24.
 */

public class SQLiteUtil {

    public static void insert(SQLiteDatabase db,List<String> keys, List<String> values){

        ContentValues contentValues=new ContentValues();
        for (int i=0;i<keys.size();i++){
            contentValues.put(keys.get(i),values.get(i));
        }
            db.insert("dict",null,contentValues);
    }

    public static List<String> getdata(SQLiteDatabase db, List<String> key, String columns) {
        String parameter = key.get(0);
        String condition = key.get(1);
        Cursor cursor = db.query("dict", new String[]{columns}, condition + " like ?", new String[]{parameter}, null, null, null);
        List<String> mylist = new ArrayList<>();
        while (cursor.moveToNext()) {
            mylist.add(cursor.getString(cursor.getColumnIndex(columns)));
        }
        return mylist;
    }

    public static List<String> query(SQLiteDatabase db,String columns){
        Cursor cursor=db.query("dict",new String[]{columns},null,null,null,null,null);
        List<String> list=new ArrayList<>();
        while(cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("provinceZh")));
        }
        return list;
    }
}

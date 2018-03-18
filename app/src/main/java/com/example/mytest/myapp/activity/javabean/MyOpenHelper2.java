package com.example.mytest.myapp.activity.javabean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mytest.myapp.R;

/**
 * @author Zhou
 * @Date 2018/3/17
 */

public class MyOpenHelper2 extends SQLiteOpenHelper {

    private String createsql = "create table news_tb (_id integer primary key autoincrement,image,title,info) ";


    public MyOpenHelper2(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i("数据库", "建表成功");
        db.execSQL(createsql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("版本变化：" + oldVersion + "------>" + newVersion);
    }
}

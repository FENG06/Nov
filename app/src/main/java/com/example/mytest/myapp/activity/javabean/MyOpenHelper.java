package com.example.mytest.myapp.activity.javabean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Zhou
 * @Date 2018/3/14
 */

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table if not exists news_tb" + "(_id integer primary key autoincrement,image,title,info)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        System.out.println("版本变化:" + i + "---->" + i1);
    }
}

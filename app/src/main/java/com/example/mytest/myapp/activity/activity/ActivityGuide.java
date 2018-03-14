package com.example.mytest.myapp.activity.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.mytest.myapp.R;
import com.example.mytest.myapp.activity.javabean.MyOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

/**
 * Created by Zhou on 2017/11/28.
 */

public class ActivityGuide extends Activity {

    private ListView news;//显示新闻的列表
    private SimpleAdapter simpleAdapter;//关联数据源的Adapter

//    保存已加载的新闻数据的集合

    private List<Map<String, Object>> newsList = new ArrayList<>();

    private MyOpenHelper mHelper; //数据库辅助类
    private SQLiteDatabase mDB;//数据库封装类

    private int totalCount;//新闻总条数
    private int loadedCount;//已经加载新闻的条数
    private int lastItem;//显示可见的最后一项的序号
    private View footView;//显示底部加载信息的控件
    private int loadItemNum = 4;//每次加载的项数

    private android.os.Handler handler = new android.os.Handler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        mHelper = new MyOpenHelper(this, "news.db", null, 1);
        mDB = mHelper.getReadableDatabase();//获取数据库
        news = findViewById(R.id.news);
//        将布局文件转换成View控件，并添加到列表中
        footView = getLayoutInflater().inflate(R.layout.guide_load, null);

        news.addFooterView(footView);

        newsList = getData("select * from news_tb order by _id limit 0,6", null);

        simpleAdapter = new SimpleAdapter(this, newsList, R.layout.guide_item, new String[]{"image", "title", "info"}, new int[]{R.id.newsimage, R.id.newsname, R.id.newsinfo});

        news.setAdapter(simpleAdapter);

        totalCount = getCount();

        MyOnScrollListener myListener = new MyOnScrollListener();
//        创建滚动时间监听器
        news.setOnScrollListener(myListener);//为列表添加滚动事件监听器

    }


//    根据查询语句获取新闻数据

    private List<Map<String, Object>> getData(String sql, String[] args) {
        List<Map<String, Object>> list = new ArrayList<>();
        Cursor cursor = mDB.rawQuery(sql, args);//查询符合条件的记录
        while (cursor.moveToNext()) {
//            循环遍历每条记录，获取相应数据，并将数据保存到集合

            Map<String, Object> item = new HashMap<>();
            item.put("image", cursor.getString(cursor.getColumnIndex("image")));
            item.put("title", cursor.getString(cursor.getColumnIndex("title")));
            item.put("info", cursor.getString(cursor.getColumnIndex("info")));

            list.add(item);
        }
        return list;
    }

    //    查询数据库中一共有多少条记录

    public int getCount() {

        Cursor cursor = mDB.rawQuery("select count(*) from news_tb", null);
        if (cursor.moveToNext()) {
            return cursor.getInt(0);
        }
        return 0;
    }

    @Override
    protected void onDestroy() {

        if (mDB != null) {
            mDB.close();
        }

        super.onDestroy();
    }

    private class MyOnScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {


//            注意，footView也算一项，当显示footView时，lastItem的 值与loadCount的值相等

            if (lastItem == loadedCount && i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

                if (loadedCount <= totalCount) {

//                    如果没有完全加载完，则记载新项
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (loadedCount + loadItemNum > totalCount) {
                                loadMore(totalCount - loadedCount);
//                                加载最后几项数据
                            }
                        }
                    }, 3000);
                }
            }
        }

        @Override
        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            lastItem = firstVisibleItem + visibleItemCount - 1;

            loadedCount = simpleAdapter.getCount();//记录已加载的项

            if (loadedCount >= totalCount) {

//                如果所有项都加载完，不再显示加载信息

                news.removeFooterView(footView);

            }

        }
    }

    private void loadMore(int num) {
        List<Map<String, Object>> mulsit = getData("select * from news_tb order by _id limit ?,?", new String[]{loadedCount + "", num + ""});
        newsList.addAll(mulsit);
        simpleAdapter.notifyDataSetChanged();

    }
}

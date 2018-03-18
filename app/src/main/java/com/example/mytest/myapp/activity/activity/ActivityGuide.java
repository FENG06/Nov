package com.example.mytest.myapp.activity.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.mytest.myapp.R;
import com.example.mytest.myapp.activity.javabean.MyOpenHelper2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zhou
 * @date 2017/11/28
 */

public class ActivityGuide extends Activity {


    private ListView news;
    // 显示新闻的列表

    private SimpleAdapter simpleAdapter1;
    // 关联数据源的Adapter

    // 保存已加载的新闻数据的集合

    private List<Map<String, Object>> newsList = new ArrayList<>();

    private MyOpenHelper2 mHelper;
    // 数据库辅助类

    private SQLiteDatabase mDB;
    // 数据库封装类

    private int totalCount;
    // 新闻的总条数，包括加载的和未加载的

    private int loadedCount;
    // 已加载的新闻条数

    private int lastItem;
    // 显示可见的最后一项的序号

    private View footView;
    // 显示底部加载信息的控件

    private int loadItemNum = 4;
    // 每次加载的项数

    private android.os.Handler myHandler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        mHelper = new MyOpenHelper2(this, "news.db", null, 2);
//         获取数据库
        mDB = mHelper.getWritableDatabase();

        mDB.execSQL("insert into news_tb (image,title,info) values(?,?,?)", new Object[]{R.drawable.image1 + "", "你好", "今天天气很好"});
        mDB.execSQL("insert into news_tb (image,title,info) values(?,?,?)", new Object[]{R.drawable.image1 + "", "你好", "今天天气很好"});
        mDB.execSQL("insert into news_tb (image,title,info) values(?,?,?)", new Object[]{R.drawable.image1 + "", "你好", "今天天气很好"});
        mDB.execSQL("insert into news_tb (image,title,info) values(?,?,?)", new Object[]{R.drawable.image1 + "", "你好", "今天天气很好"});
        mDB.execSQL("insert into news_tb (image,title,info) values(?,?,?)", new Object[]{R.drawable.image1 + "", "你好", "今天天气很好"});
        mDB.execSQL("insert into news_tb (image,title,info) values(?,?,?)", new Object[]{R.drawable.image1 + "", "你好", "今天天气很好"});
        mDB.execSQL("insert into news_tb (image,title,info) values(?,?,?)", new Object[]{R.drawable.image1 + "", "你好", "今天天气很好"});
        mDB.execSQL("insert into news_tb (image,title,info) values(?,?,?)", new Object[]{R.drawable.image1 + "", "你好", "今天天气很好"});


        mDB = mHelper.getReadableDatabase();
        //listView 的id
        news = findViewById(R.id.news);
        // 将布局文件转换成View控件，并添加到列表中
        footView = getLayoutInflater().inflate(R.layout.guide_load, null);
        news.addFooterView(footView);

        newsList = getData1("select * from news_tb order by _id limit 0,4", null);

//        newsList = getData1("select * from news_tb where title = ?", new String[]{"你好"});

        simpleAdapter1 = new SimpleAdapter(this, newsList, R.layout.guide_item,
                new String[]{"image", "title", "info"}, new int[]{R.id.newsimage, R.id.newsname, R.id.newsinfo});
        news.setAdapter(simpleAdapter1);


        totalCount = getCount();
        // 创建滚动事件监听器
        MyOnScrollListener myListener = new MyOnScrollListener();
        // 为列表添加滚动事件监听器
        news.setOnScrollListener(myListener);


    }

    private class MyOnScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            System.out.println("totalItemCount" + totalItemCount);
            lastItem = firstVisibleItem + visibleItemCount - 1;
            loadedCount = simpleAdapter1.getCount();// 记录已加载的项
            if (loadedCount >= totalCount) {// 如果所有项都加载完，不再显示加载信息
                news.removeFooterView(footView);
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // 注意footView也算一项，当显示footView时，lastItem的值就与loadedCount的值相等
            //为什么要加lastItem == loadedCount这个判断，当滚动条不能再滚动的时候，有两种情况，一是向下到底，一是向上到顶，加上这个表示向下到底
            if (lastItem == loadedCount
                    && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                if (loadedCount < totalCount) {// 如果没有完全加载完，则记载新项
                    myHandler.postDelayed(new Runnable() {// 延迟3秒执行
                        @Override
                        public void run() {
                            if (loadedCount + loadItemNum > totalCount) {
                                loadMore(totalCount - loadedCount);// 加载最后几项数据
                            } else {
                                loadMore(loadItemNum);// 加载默认多个数据
                            }
                        }
                    }, 3000);
                }
            }
        }
    }

    public void loadMore(int num) {
        List<Map<String, Object>> list = getData1(
                "select * from news_tb order by _id limit ?,?", new String[]{
                        loadedCount + "", num + ""});
        newsList.addAll(list);// 将新加载的新闻添加到新闻列表中
        simpleAdapter1.notifyDataSetChanged();// 更新列表显示
    }

    // 根据查询语句获取新闻数据

    public List<Map<String, Object>> getData1(String sql, String[] args) {
        List<Map<String, Object>> list = new ArrayList<>();

        // 查询符合条件的记录
        Cursor cursor = mDB.rawQuery(sql, args);

        // 循环遍历每条记录，获取相应数据，并将数据保存到集合中
        while (cursor.moveToNext()) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", cursor.getString(cursor.getColumnIndex("image")));
            item.put("title", cursor.getString(cursor.getColumnIndex("title")));
            item.put("info", cursor.getString(cursor.getColumnIndex("info")));
            list.add(item);
        }
        return list;
    }

    // 查询数据库中，一共有多少条记录

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

}

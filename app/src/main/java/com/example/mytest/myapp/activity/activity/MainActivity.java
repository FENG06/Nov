package com.example.mytest.myapp.activity.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.mytest.myapp.R;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter simpleAdapter;
    private int[] icon = {R.drawable.data_add, R.drawable.data_query, R.drawable.data_upload, R.drawable.data_setting, R.drawable.data_info, R.drawable.data_user};
    private String[] iconName = {"数据记录", "数据查询", "透析机型", "设置密码", "透析指南", "关于我们"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        gridView = findViewById(R.id.gridView);

        //新建List
        data_list = new ArrayList<>();

        //获取数据
        getData();

        //新建适配器
        String[] getData = {"image", "text"};
        int[] to = {R.id.image, R.id.text};

        simpleAdapter = new SimpleAdapter(this, data_list, R.layout.item, getData, to);


        gridView.setAdapter(simpleAdapter);


        gridView.setOnItemClickListener(new MyItemListener());


    }

    public List<Map<String, Object>> getData() {

        for (int i = 0; i < icon.length; i++) {

            Map<String, Object> map = new HashMap<>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }


    private class MyItemListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            switch (position) {

                case 0: {
                    Intent intent1 = new Intent();
                    intent1.setClass(MainActivity.this, ActivityRecord.class);
                    startActivity(intent1);
                    break;
                }
                case 1: {
                    Intent intent1 = new Intent();
                    intent1.setClass(MainActivity.this, ActivityQuery.class);
                    startActivity(intent1);
                    break;
                }
                case 2: {
                    Intent intent2 = new Intent();
                    intent2.setClass(MainActivity.this, TypeListActivity.class);
                    startActivity(intent2);
                    break;
                }
                case 3: {
                    Intent intent3 = new Intent();
                    intent3.setClass(MainActivity.this, ActivitySetting.class);
                    startActivity(intent3);
                    break;
                }

                case 4: {
                    Intent intent4 = new Intent();
                    intent4.setClass(MainActivity.this, ActivityGuide.class);
                    startActivity(intent4);
                    break;
                }
                case 5: {
                    Intent intent5 = new Intent();
                    intent5.setClass(MainActivity.this, ActivityAbout.class);
                    startActivity(intent5);
                    break;
                }
                default:
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            //创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            //设置对话框标题
            isExit.setTitle("系统提示");
            //设置对话框消息
            isExit.setMessage("确定要退出吗？");
            //添加选择按钮并注册监听
            isExit.setButton(DialogInterface.BUTTON_POSITIVE, "确定", listener);
            isExit.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", listener);
            isExit.show();
        }
        return false;
    }

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            switch (which) {
                // "确认"按钮退出程序
                case AlertDialog.BUTTON_POSITIVE:
                    finish();
                    break;
                // "取消"按钮取消对话框
                case AlertDialog.BUTTON_NEGATIVE:
                    break;
                default:
                    break;
            }
        }
    };


}

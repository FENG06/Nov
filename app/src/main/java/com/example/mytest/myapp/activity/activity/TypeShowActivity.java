package com.example.mytest.myapp.activity.activity;

import android.app.Activity;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mytest.myapp.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Zhou
 * @date 2017/12/19
 */

public class TypeShowActivity extends Activity {

    private TextView title, content;
    private ImageView imageView;
    private ClipDrawable clipDrawable;
    private Handler myhandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_show);
        imageView = findViewById(R.id.typeimage);
        content = findViewById(R.id.typecontent);
        title = findViewById(R.id.typetitle);
//     获取传过来的数据，包括标题、图片ID、详情文件ID
        String titleStr = getIntent().getStringExtra("name");
        int image = getIntent().getIntExtra("image", R.drawable.image1);
        int info = getIntent().getIntExtra("content", R.raw.type1);
        title.setText(titleStr);

//        创建ClipDrawable对象，指定裁剪的图片、方向等
        clipDrawable = new ClipDrawable(getResources().getDrawable(image), Gravity.CENTER, ClipDrawable.HORIZONTAL);
        imageView.setImageDrawable(clipDrawable);
//        启动线程，开始裁剪
        startThread();
//    根据文件ID获取输入流
        InputStream inputStream = getResources().openRawResource(info);
        content.setText(getStringFromInputStream(inputStream));

//        创建Handler对象
        myhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                clipDrawable.setLevel(clipDrawable.getLevel() + 400);
            }
        };

    }

    private String getStringFromInputStream(InputStream inputStream) {
//         从输入流中读取字符串
        byte[] buffer = new byte[1024];
//          记录读取的个数
        int hasRead = 0;
        StringBuilder result = new StringBuilder("");
        try {

            while ((hasRead = inputStream.read(buffer)) != -1) {
                result.append(new String(buffer, 0, hasRead, "GBK"));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }

    private void startThread() {
        clipDrawable.setLevel(0);
        new Thread() {
            @Override
            public void run() {
                while (clipDrawable.getLevel() < 10000) {
                    try {
                        Thread.sleep(300);
                        myhandler.sendEmptyMessage(0x11);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }
}

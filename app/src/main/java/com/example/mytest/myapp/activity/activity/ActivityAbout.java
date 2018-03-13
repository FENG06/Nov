package com.example.mytest.myapp.activity.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mytest.myapp.R;

/**
 * Created by Zhou on 2017/11/28.
 */

public class ActivityAbout extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}

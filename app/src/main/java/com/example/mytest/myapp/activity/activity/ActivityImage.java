package com.example.mytest.myapp.activity.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.mytest.myapp.R;

/**
 * Created by Zhou on 2017/11/28.
 */

public class ActivityImage extends Activity {

    /*
    * 这一部分要做详细更改
    * */


    private int[] image = new int[]{R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);

        final ImageSwitcher mImageSwitcher = findViewById(R.id.img_switcher);
        TextView imaText = findViewById(R.id.img_health);
        imaText.setText("常用透析机一览");
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView mImageView = new ImageView(ActivityImage.this);
                mImageView.setLayoutParams(new ImageSwitcher.LayoutParams(250, 250));
                mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return mImageView;
            }
        });


        mImageSwitcher.setImageResource(image[0]);



        Gallery mGallary = findViewById(R.id.img_gallery);

        BaseAdapter baseAdapter = new BaseAdapter() {

            @Override
            public View getView(int position, View view, ViewGroup viewGroup) {

                ImageView imageView = new ImageView(ActivityImage.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
                imageView.setImageResource(image[position % image.length]);
                imageView.setBackgroundColor(getResources().getColor(R.color.gray));

                return imageView;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return image[position];
            }

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }
        };


        mGallary.setAdapter(baseAdapter);
        mGallary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mImageSwitcher.setImageResource(image[position % image.length]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}

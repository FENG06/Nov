package com.example.mytest.myapp.activity.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mytest.myapp.R;
import com.example.mytest.myapp.activity.javabean.RecordData;
import com.example.mytest.myapp.activity.view.DrawableTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Zhou on 2017/11/21.
 */

public class ActivityRecord extends Activity {

    public EditText startDate;
    public EditText endDate;
    private EditText feel;
    private EditText type;
    private EditText subType;

    private Spinner hospitalspinner;
    private Button saveButton;
    private static String hospital;

    private List<String> hospitallist;
    private ArrayAdapter<String> hospitalarrayAdapter;
    private DrawableTextView drawableTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_record);

        startDate = findViewById(R.id.start_time);
        endDate = findViewById(R.id.end_time);
        feel = findViewById(R.id.record_feel);
        type = findViewById(R.id.record_euipment);
        subType = findViewById(R.id.record_euipment1);
        hospitalspinner = findViewById(R.id.hospital_spinner);
        saveButton = findViewById(R.id.btn_save);
//        drawableTextView = findViewById(R.id.record_title);

//        drawableTextView.setDrawableLeftClick(new DrawableTextView.DrawableLeftClickListener() {
//            @Override
//            public void onDrawableLeftClickListener(DrawableTextView drawableTextView) {
//
//                Intent intent1 = new Intent(ActivityRecord.this, MainActivity.class);
//                startActivity(intent1);
//
//            }
//        });


        //开始时间
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(ActivityRecord.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        startDate.setText(year + "-" + format(monthOfYear + 1) + "-" + format(dayOfMonth));

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        //结束时间
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(ActivityRecord.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        endDate.setText(year + "-" + format(monthOfYear + 1) + "-" + format(dayOfMonth));

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        String[] hospitallist = new String[]{"西南医院", "新桥医院", "大坪医院", "重庆医科大学附属第一医院",
                "重庆医科大学附属第二医院", "重庆医科大学附属儿童医院", "重庆市肿瘤医院"};

        //定义适配器，layout必须是TextView！
        hospitalarrayAdapter = new ArrayAdapter<>(ActivityRecord.this, R.layout.hospitalresult, hospitallist);


        //设置下拉样式
        hospitalarrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //绑定adapter到控件
        hospitalspinner.setAdapter(hospitalarrayAdapter);

        //加载适配器
        hospitalspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ActivityRecord.hospital = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save();


    }


    private String format(int time) {
        String str = time + "";
        if (str.length() == 1) {
            str = "0" + str;
        }
        return str;
    }


    private void save() {
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                final String username1 = BmobUser.getCurrentUser().getUsername();
                final String startDate1 = startDate.getText().toString().trim();
                final String endDate1 = endDate.getText().toString().trim();
                final String feel1 = feel.getText().toString().trim();
                final String type1 = type.getText().toString().trim();
                final String subType1 = subType.getText().toString().trim();
                String hospital1 = ActivityRecord.hospital;

                if (checkInput()) {
                    RecordData recordData = new RecordData();
                    recordData.setUsername(username1);
                    recordData.setStartDate(startDate1);
                    recordData.setEndDate(endDate1);
                    recordData.setFeel(feel1);
                    recordData.setType(type1);
                    recordData.setSubType(subType1);
                    recordData.setHospital(hospital1);

                    recordData.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                Toast.makeText(ActivityRecord.this, "添加数据成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityRecord.this, "添加数据失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }


    private boolean checkInput() {


        final String startDate1 = startDate.getText().toString().trim();
        final String endDate1 = endDate.getText().toString().trim();
        final String feel1 = feel.getText().toString().trim();
        final String type1 = type.getText().toString().trim();
        final String subType1 = subType.getText().toString().trim();

        if ("".equals(startDate1)) {
            Toast.makeText(this, "请输入开始时间", Toast.LENGTH_SHORT).show();
            return false;
        } else if ("".equals(endDate1)) {
            Toast.makeText(this, "请输入结束时间", Toast.LENGTH_SHORT).show();
            return false;
        } else if ("".equals(feel1)) {
            Toast.makeText(this, "请输入透析感受", Toast.LENGTH_SHORT).show();
            return false;
        } else if ("".equals(type1)) {
            Toast.makeText(this, "请输入透析机型号", Toast.LENGTH_SHORT).show();
            return false;
        } else if ("".equals(subType1)) {
            Toast.makeText(this, "请输入透析柱型号", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

}


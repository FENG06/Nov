package com.example.mytest.myapp.activity.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytest.myapp.R;
import com.example.mytest.myapp.activity.javabean.RecordData;
import com.example.mytest.myapp.activity.view.DrawableTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Zhou on 2017/11/21.
 */

public class ActivityQuery extends Activity {

    private TextView querytime;
    private EditText queryStart;
    private EditText queryEnd;
    private Button querybtn;
    private RelativeLayout query_relativeLayout;
    private LinearLayout mtitle;
    private ListView mlistView;
    private DrawableTextView drawableTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

//        querytime = findViewById(R.id.query_time);
//        queryStart = findViewById(R.id.query_start);
//        queryEnd = findViewById(R.id.query_end);
        querybtn = findViewById(R.id.search_btn);
        query_relativeLayout = findViewById(R.id.query_show);
        mtitle = findViewById(R.id.title);
        mlistView = findViewById(R.id.result);

        drawableTextView = findViewById(R.id.query_title);


        drawableTextView.setDrawableLeftClick(new DrawableTextView.DrawableLeftClickListener() {
            @Override
            public void onDrawableLeftClickListener(DrawableTextView drawableTextView) {

                Intent intent1 = new Intent(ActivityQuery.this, MainActivity.class);
                startActivity(intent1);

            }
        });


//        calendarClick();
        queryConfirm();

    }

//    private void calendarClick() {
//
//
//        //开始时间点击显示
//        queryStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar calendar = Calendar.getInstance();
//                new DatePickerDialog(ActivityQuery.this, new DatePickerDialog.OnDateSetListener() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                        queryStart.setText(year + "-" + format(month + 1) + "-" + format(day));
//                    }
//                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//
//        //结束时间点击显示
//        queryEnd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar calendar = Calendar.getInstance();
//                new DatePickerDialog(ActivityQuery.this, new DatePickerDialog.OnDateSetListener() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                        queryEnd.setText(year + "-" + format(month + 1) + "-" + format(day));
//                    }
//                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//
//    }
//
//    private String format(int time) {
//
//        String str = time + "";
//        if (str.length() == 1) {
//            str = "0" + str;
//        }
//
//        return str;
//    }


    private void queryConfirm() {

        querybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query_relativeLayout.setVisibility(View.VISIBLE);
                mtitle.setVisibility(View.VISIBLE);
                mlistView.setVisibility(View.VISIBLE);


                BmobQuery<RecordData> bmobQuery = new BmobQuery<>();

                RecordData recordData1 = new RecordData();
                String username = recordData1.getUsername();
                String username2 = BmobUser.getCurrentUser().getUsername();
                final List<BmobQuery<RecordData>> records = new ArrayList<>();

//
//                    bmobQuery.addWhereLessThan("startDate", startdate.compareTo(String.valueOf(bmobQuery.addWhereExists("startDate"))));
//                    bmobQuery.addWhereGreaterThan("startDate", startdate.compareTo(String.valueOf(bmobQuery.addWhereExists("startDate"))));


                bmobQuery.addWhereEqualTo("username", username2);
                bmobQuery.findObjects(new FindListener<RecordData>() {
                    @Override
                    public void done(List<RecordData> object, BmobException e) {
                        if (e == null) {


                            final ArrayList<Map<String, Object>> list = new ArrayList<>();

                            for (RecordData recordData : object) {

                                String queryStartdate = recordData.getStartDate();
                                String queryEnddate = recordData.getEndDate();
                                String queryFeel = recordData.getFeel();
                                String queryType = recordData.getType();
                                String querySubtype = recordData.getSubType();
                                String queryHospital = recordData.getHospital();

                                HashMap<String, Object> map = new HashMap<>();
                                map.put("queryStart", queryStartdate);
                                map.put("queryEnd", queryEnddate);
                                map.put("queryFeel", queryFeel);
                                map.put("queryType", queryType);
                                map.put("querySubType", querySubtype);
                                map.put("queryHospital", queryHospital);

                                list.add(map);
                            }


                            SimpleAdapter adapter = new SimpleAdapter(ActivityQuery.this, list, R.layout.query_item, new String[]{"queryStart", "queryEnd", "queryFeel", "queryType", "querySubType", "queryHospital"},
                                    new int[]{R.id.result_startdate, R.id.result_enddate, R.id.result_feel, R.id.result_type, R.id.result_type2, R.id.result_hospital});
                            System.out.println("测试");
                            mlistView.setAdapter(adapter);

                            Toast.makeText(ActivityQuery.this, "查询成功：共" + object.size() + "条数据", Toast.LENGTH_SHORT).show();

                        } else {
                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
            }
        });
    }
}

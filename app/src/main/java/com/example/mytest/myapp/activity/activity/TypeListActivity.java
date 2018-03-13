package com.example.mytest.myapp.activity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.mytest.myapp.R;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Zhou
 * @date 2017/12/18
 */

public class TypeListActivity extends Activity {


    private ListView myType;
    private List<Map<String, Object>> list = new ArrayList<>();
    private int[] imgIds = new int[]{R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};
    private String[] typeNames = new String[]{"测试1", "测试2", "测试3", "测试4"};
    private String[] briefs = new String[]{"费森尤斯费森尤斯费森尤斯费森尤斯费森尤斯费森尤斯", "费森尤斯费森尤斯费森尤斯费森尤斯费森尤斯费森尤斯",
            "费森尤斯费森尤斯费森尤斯费森尤斯费森尤斯费森尤斯", "费森尤斯费森尤斯费森尤斯费森尤斯费森尤斯费森尤斯"};
    private int[] contentsIds = new int[]{R.raw.type1, R.raw.type2, R.raw.type3, R.raw.type4};


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        myType = findViewById(R.id.equiptype);
        init();
//        创建Adapter对象，将数据源与显示的控件关联起来
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.type_item, new String[]{"img", "name", "brief"}, new int[]{R.id.image, R.id.typename, R.id.typebrief});
        myType.setAdapter(adapter);

//        为列表添加列表项单击事件监听器
        myType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(TypeListActivity.this, TypeShowActivity.class);
                intent.putExtra("name", (String) list.get(position).get("name"));
                intent.putExtra("image", (Integer) list.get(position).get("img"));
                intent.putExtra("content", (Integer) list.get(position).get("content"));
                startActivity(intent);
            }
        });

    }

    private void init() {

        for (int i = 0; i < imgIds.length; i++) {

            Map<String, Object> item = new HashMap<>();
            item.put("img", imgIds[i]);
            item.put("name", typeNames[i]);
            item.put("brief", briefs[i]);
            item.put("content", contentsIds[i]);
            list.add(item);
        }
    }
}

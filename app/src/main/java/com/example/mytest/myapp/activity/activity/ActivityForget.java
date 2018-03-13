package com.example.mytest.myapp.activity.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mytest.myapp.R;
import com.example.mytest.myapp.activity.javabean.RecordData;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * @author Zhou
 * @date 2017/10/17
 */
public class ActivityForget extends Activity {

    private EditText forgetUsername;
    private EditText forgetMobile;
    private EditText oldpwd;
    private EditText newpwd;
    private EditText confirmpwd;
    private Button resetbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        finish();


    }

    private void resetpwd() {


//        if ("".equals(username) || "".equals(usermobile) || "".equals(usernewpwd) || "".equals(userconfirmpwd)) {
//            Toast.makeText(this, "请完整输入上方信息！", Toast.LENGTH_SHORT).show();
//        }

        //Bmob查询数据

        resetbtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                final String username = forgetUsername.getText().toString().trim();
                String usermobile = forgetMobile.getText().toString().trim();
                final String useroldpwd = oldpwd.getText().toString().trim();
                final String usernewpwd = newpwd.getText().toString().trim();
                String userconfirmpwd = confirmpwd.getText().toString().trim();

                final BmobQuery<BmobUser> query = new BmobQuery<>();
                System.out.println("测试输出1");
                query.addWhereEqualTo("username", username);
                System.out.println("测试输出2" + username);
                query.findObjects(new FindListener<BmobUser>() {
                    @Override
                    public void done(List<BmobUser> list, BmobException e) {
                        if (e == null) {

                            System.out.println("测试输出3" + usernewpwd);

                            BmobUser.updateCurrentUserPassword(useroldpwd, usernewpwd, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        System.out.println("测试输出4" + String.valueOf(BmobUser.getObjectByKey("password")));

                                        Log.i("bmob", "更新成功");
                                    } else {
                                        Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                                    }
                                }
                            });

                        } else {

                            Toast.makeText(ActivityForget.this, "您输入的用户名有误，请检查！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }


}

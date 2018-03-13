package com.example.mytest.myapp.activity.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mytest.myapp.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Zhou on 2017/11/27.
 */

public class ActivitySetting extends Activity {

    private EditText forgetUsername;
    private EditText forgetMobile;
    private EditText oldpwd;
    private EditText newpwd;
    private EditText confirmpwd;
    private Button resetbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        forgetUsername = findViewById(R.id.forget_username);
        forgetMobile = findViewById(R.id.forget_mobile);
        oldpwd = findViewById(R.id.forget_oldpwd);
        newpwd = findViewById(R.id.forget_pwd);
        confirmpwd = findViewById(R.id.forget_repwd);
        resetbtn = findViewById(R.id.resetcomfirm);

        resetpwd();

    }

    private void resetpwd() {


        resetbtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (checkInfo()) {

                    final String username = forgetUsername.getText().toString().trim();
                    String usermobile = forgetMobile.getText().toString().trim();
                    final String useroldpwd = oldpwd.getText().toString().trim();
                    final String usernewpwd = newpwd.getText().toString().trim();
                    String userconfirmpwd = confirmpwd.getText().toString().trim();

                    BmobUser.updateCurrentUserPassword(useroldpwd, usernewpwd, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.i("bmob", "更新成功");
                            } else {
                                Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                                Toast.makeText(ActivitySetting.this, "您输入的原始密码有误，请检查！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


//                    final BmobQuery<BmobUser> query = new BmobQuery<>();
//                    query.addWhereEqualTo("username", username);
//                    query.findObjects(new FindListener<BmobUser>() {
//                        @Override
//                        public void done(List<BmobUser> list, BmobException e) {
//                            if (e == null) {
//                                BmobUser.updateCurrentUserPassword(useroldpwd, usernewpwd, new UpdateListener() {
//                                    @Override
//                                    public void done(BmobException e) {
//                                        if (e == null) {
//                                            Log.i("bmob", "更新成功");
//                                        } else {
//                                            Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
//                                            Toast.makeText(ActivitySetting.this, "您输入的原始密码有误，请检查！", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                            } else {
//
//                                Toast.makeText(ActivitySetting.this, "您输入的原始密码有误，请检查！", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });


                }

            }
        });
    }

    private boolean checkInfo() {

        final String username = forgetUsername.getText().toString().trim();
        String usermobile = forgetMobile.getText().toString().trim();
        final String useroldpwd = oldpwd.getText().toString().trim();
        final String usernewpwd = newpwd.getText().toString().trim();
        String userconfirmpwd = confirmpwd.getText().toString().trim();


        String netusername = BmobUser.getCurrentUser().getUsername();
        System.out.println("测试 " + netusername);
        String netusermobile = BmobUser.getCurrentUser().getMobilePhoneNumber();

        if (!username.equals(netusername)) {
            Toast.makeText(this, "请检查用户名！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!usermobile.equals(netusermobile)) {
            Toast.makeText(this, "请检查手机号！", Toast.LENGTH_SHORT).show();
            return false;
        } else if ("".equals(useroldpwd)) {
            Toast.makeText(this, "原始密码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        } else if ("".equals(usernewpwd) || "".equals(userconfirmpwd)) {
            Toast.makeText(this, "前后两次密码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!usernewpwd.equals(userconfirmpwd)) {
            Toast.makeText(this, "前后两次密码不一致！", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


}

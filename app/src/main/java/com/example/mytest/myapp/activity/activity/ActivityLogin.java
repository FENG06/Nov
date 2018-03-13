package com.example.mytest.myapp.activity.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytest.myapp.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by Zhou on 2017/11/15.
 */

public class ActivityLogin extends Activity {

    private Button btnLogin;
    private EditText edtUser;
    private EditText edtPass;
    private TextView tvReg;
    private TextView tvFog;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private CheckBox rem_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "75ed11263bb9c7457ae1cef1eba79c13");
        BmobInstallation.getCurrentInstallation().save();
        initViews();

    }

    private void initViews() {

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        btnLogin = findViewById(R.id.btn_login);
        edtUser = findViewById(R.id.edt_username);
        edtPass = findViewById(R.id.edt_pass);
        tvReg = findViewById(R.id.tv_register);
        tvFog = findViewById(R.id.tv_forget);
        rem_pwd = findViewById(R.id.rem_user);

        boolean isRemember = preferences.getBoolean("remember_password", false);

        if (isRemember) {
//            将账号和密码置于文本框中
            String user_name = preferences.getString("user_name", "");
            String user_pwd = preferences.getString("user_pwd", "");
            edtUser.setText(user_name);
            edtPass.setText(user_pwd);
            rem_pwd.setChecked(true);
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!checkNet()) {
                    Toast.makeText(ActivityLogin.this, "您好像没有网络，请检查网络状况！", Toast.LENGTH_SHORT).show();
                } else if (isUserNameandPwdValid()) {
                    final String name = edtUser.getText().toString().trim();
                    final String password = edtPass.getText().toString().trim();

                    BmobUser.loginByAccount(name, password, new LogInListener<BmobUser>() {
                        @Override
                        public void done(BmobUser user, BmobException e) {
                            if (user != null) {

                                editor = preferences.edit();
                                if (rem_pwd.isChecked()) {
                                    editor.putBoolean("remember_password", true);
                                    editor.putString("user_name", name);
                                    editor.putString("user_pwd", password);
                                } else {
                                    editor.clear();
                                }
                                editor.apply();
                                Toast.makeText(ActivityLogin.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(ActivityLogin.this, "登陆失败，请输入正确的用户名与密码！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
                startActivity(intent);
            }
        });


        tvFog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivityForget.class);
                startActivity(intent);
            }
        });


    }

    public boolean isUserNameandPwdValid() {
        if ("".equals(edtUser.getText().toString().trim())) {
            Toast.makeText(this, "用户名为空！", Toast.LENGTH_SHORT).show();
            return false;
        } else if ("".equals(edtPass.getText().toString().trim())) {
            Toast.makeText(this, "密码为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private boolean checkNet() {
        ConnectivityManager manager = (ConnectivityManager) this
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {

            return false;
        }
        return true;
    }


}

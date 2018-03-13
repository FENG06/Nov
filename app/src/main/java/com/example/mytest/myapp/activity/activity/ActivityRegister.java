package com.example.mytest.myapp.activity.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mytest.myapp.R;
import com.example.mytest.myapp.activity.view.DrawableTextView;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Zhou on 2017/11/15.
 */

public class ActivityRegister extends Activity {

    private EditText edtName;
    private EditText edtPhone;
    private EditText edtNum;
    private EditText edtPass;
    private Button btnNum;
    private Button btnReg;
    private AlertDialog.Builder builder;
    private DrawableTextView drawableTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        register();
    }

    private void initViews() {
        edtName = findViewById(R.id.edt_name1);
        edtPhone = findViewById(R.id.edt_phone1);
        edtNum = findViewById(R.id.edt_num1);
        edtPass = findViewById(R.id.edt_password1);
        btnNum = findViewById(R.id.btn_num1);
        btnReg = findViewById(R.id.btn_reg1);
        drawableTextView = findViewById(R.id.reg_title);

        drawableTextView.setDrawableLeftClick(new DrawableTextView.DrawableLeftClickListener() {
            @Override
            public void onDrawableLeftClickListener(DrawableTextView drawableTextView) {

                Intent intent1 = new Intent(ActivityRegister.this, ActivityLogin.class);
                startActivity(intent1);

            }
        });


    }

    public void register() {
        btnNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInfo()&& checkUseInfo() ) {

                    String phone = edtPhone.getText().toString().trim();
                    BmobSMS.requestSMSCode(phone, "bmobTest", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer smsId, BmobException e) {
                            if (e == null) {
                                Log.i("smile", "短信id:" + smsId);
                                System.out.println("发送");
                            }
                        }
                    });

                    Toast.makeText(ActivityRegister.this, "验证码已发到手机", LENGTH_SHORT).show();
                }
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInfo1()) {


                    final String phone = edtPhone.getText().toString().trim();
                    final String num = edtNum.getText().toString().trim();

                    BmobSMS.verifySmsCode(phone, num, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {


                            if (e == null) {
                                Log.d("bmobtest", "验证成功");


                                Toast.makeText(ActivityRegister.this, "注册成功", Toast.LENGTH_LONG).show();

                                BmobUser user = new BmobUser();
                                String name = edtName.getText().toString().trim();
                                String password = edtPass.getText().toString().trim();
                                user.setUsername(name);
                                user.setPassword(password);
                                user.setMobilePhoneNumber(phone);
                                user.setMobilePhoneNumberVerified(true);


                                user.signUp(new SaveListener<BmobUser>() {
                                    @Override
                                    public void done(BmobUser bmobUser, BmobException e) {
                                        if (e == null) {
                                        }
                                    }
                                });
                            } else {
                                Log.i("验证失败", "code=" + e.getErrorCode() + "msg=" + e.getLocalizedMessage());
                            }

                            showDialog(btnReg);
//                                Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
//                                startActivity(intent);
                        }
                    });
                }
            }
        });
    }


    private boolean checkInfo1() {

        String userName = this.edtName.getText().toString().trim();
        String userPwd = this.edtPass.getText().toString().trim();
        String userPhone = this.edtPhone.getText().toString().trim();
        String userCode = this.edtNum.getText().toString().trim();

        if ("".equals(userName)) {
            Toast.makeText(ActivityRegister.this, "请输入用户名", LENGTH_SHORT).show();
            System.out.println("未输入用户名");
            return false;
        } else if ("".equals(userPwd)) {
            Toast.makeText(ActivityRegister.this, "请输入密码", LENGTH_SHORT).show();
            System.out.println("未输入密码");
            return false;
        } else if ("".equals(userPhone)) {
            Toast.makeText(ActivityRegister.this, "请输入手机号", LENGTH_SHORT).show();
            return false;
        } else if ("".equals(userCode)) {
            Toast.makeText(ActivityRegister.this, "请输入验证码", LENGTH_SHORT).show();
            return false;
        } else if (!JudgeTel()) {
            Toast.makeText(ActivityRegister.this, "您输入的手机号码错误", LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public boolean checkInfo() {
        String userName = this.edtName.getText().toString().trim();
        String userPwd = this.edtPass.getText().toString().trim();
        String userPhone = this.edtPhone.getText().toString().trim();

        if ("".equals(userName)) {
            Toast.makeText(ActivityRegister.this, "请输入用户名", LENGTH_SHORT).show();
            System.out.println("未输入用户名");
            return false;
        } else if ("".equals(userPwd)) {
            Toast.makeText(ActivityRegister.this, "请输入密码", LENGTH_SHORT).show();
            System.out.println("未输入密码");

            return false;
        } else if ("".equals(userPhone)) {
            Toast.makeText(ActivityRegister.this, "请输入手机号", LENGTH_SHORT).show();
            return false;
        } else if (!JudgeTel()) {
            Toast.makeText(ActivityRegister.this, "您输入的手机号码错误", LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public boolean checkUseInfo() {
        String userName = this.edtName.getText().toString().trim();
        String userPwd = this.edtPass.getText().toString().trim();
        String userPhone = this.edtPhone.getText().toString().trim();

        if (BmobUser.getCurrentUser().getMobilePhoneNumber().equals(userPhone)) {
            Toast.makeText(ActivityRegister.this, "您输入的手机号码已被注册！", LENGTH_SHORT).show();
            return false;
        } else if (BmobUser.getCurrentUser().getUsername().equals(userName)) {
            Toast.makeText(ActivityRegister.this, "您输入的用户名已被注册！", LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean JudgeTel() {
        String userTel = edtPhone.getText().toString().trim();
        int mobileNum = 11;
        String[] nk = new String[]{"130", "131", "132", "133", "134", "135",
                "136", "137", "138", "139", "150", "151", "152", "153", "154",
                "155", "156", "157", "158", "159", "170", "180", "181", "182",
                "183", "184", "185", "186", "187", "188", "189"};
        if (userTel.length() == mobileNum) {
            String num = userTel.substring(0, 3);
            int i;
            for (i = 0; i < nk.length; i++) {
                if (nk[i].equals(num)) {
                    break;
                }
            }
            return i < nk.length;
        } else {
            return false;
        }
    }

    public void showDialog(View view) {

        builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.app_icon);
        builder.setTitle("注册成功！");
        builder.setMessage("恭喜您，已经成功注册！");


        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "返回到登录页面", LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();

    }


}

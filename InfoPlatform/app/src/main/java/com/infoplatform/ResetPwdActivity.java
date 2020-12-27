package com.infoplatform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.infoplatform.data.User;
import com.infoplatform.util.MyDatabase;

//修改登录密码
public class ResetPwdActivity extends AppCompatActivity {

    private EditText mPwd_old;
    private EditText mPwd_new;
    private EditText mPwd_newenter;
    private Button mSureButton;
    private Button mCancelButton;
    private ImageView iv_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);

        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPwd_old = (EditText) findViewById(R.id.oldpwd);
        mPwd_new = (EditText) findViewById(R.id.newpwd);
        mPwd_newenter = (EditText) findViewById(R.id.enternewpwd);

        mSureButton = (Button) findViewById(R.id.resetpwd_btn_sure);
        mCancelButton = (Button) findViewById(R.id.resetpwd_btn_cancel);

        mSureButton.setOnClickListener(m_resetpwd_Listener);
        mCancelButton.setOnClickListener(m_resetpwd_Listener);
    }

    View.OnClickListener m_resetpwd_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.resetpwd_btn_sure:
                    //确认按钮的监听事件
                    resetpwd_check();
                    break;
                case R.id.resetpwd_btn_cancel:
                    //取消按钮的监听事件
                    finish();
                    break;
            }
        }
    };

    //确认按钮的监听事件
    public void resetpwd_check() {
        if (isUserNameAndPwdValid()) {
            //获取旧密码
            String userPwd_old = mPwd_old.getText().toString().trim();
            //获取新密码
            String userPwd_new = mPwd_new.getText().toString().trim();
            SharedPreferences sp = getSharedPreferences("userinfo", 0);
            //从数据库里取出密码
            int uid = sp.getInt("uid",0);
            User user = MyDatabase.getInstance(this).getUserDao().getUserById(uid);
            String pwd_query=user.getPassword();
            //输入密码和数据库里的密码进行比对
            if(pwd_query.equals(userPwd_old)){
                //更新密码
                user.setPassword(userPwd_new);
                MyDatabase.getInstance(this).getUserDao().update(user);
                Toast.makeText(this, "密码修改成功！",Toast.LENGTH_SHORT).show();
                //跳转到登录页面
                Intent intent_Register_to_Login = new Intent(ResetPwdActivity.this,LoginActivity.class) ;
                startActivity(intent_Register_to_Login);
                finish();
            }else{
                Toast.makeText(this,"旧密码输入错误！",Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    //对输入值进行校验
    public boolean isUserNameAndPwdValid() {
        if (mPwd_old.getText().toString().trim().equals("")) {
            Toast.makeText(this, "旧密码不能为空！",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mPwd_new.getText().toString().trim().equals("")) {
            Toast.makeText(this, "新密码不能为空！",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mPwd_newenter.getText().toString().trim().equals("")) {
            Toast.makeText(this, "确认密码不能为空！",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!mPwd_new.getText().toString().trim().equals(mPwd_newenter.getText().toString().trim())){
            Toast.makeText(this, "两次密码输入不一致！",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mPwd_new.length()<6){
            Toast.makeText(this, "新密码长度不能小于6位！",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
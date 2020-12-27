package com.infoplatform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.infoplatform.data.User;
import com.infoplatform.data.Admin;
import com.infoplatform.util.MyDatabase;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.List;

//登录
public class LoginActivity extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{

    private Button loginBtn;
    private TextView regBtn;
    private EditText usernameTxt;
    private EditText pwdTxt;
    private String username,password;
    private RadioGroup radioGroup;
    private String role="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取控件
        usernameTxt = findViewById(R.id.username);
        pwdTxt = findViewById(R.id.password);
        pwdTxt.setInputType(129);
        radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(this);
        loginBtn = findViewById(R.id.login);
        loginBtn.setOnClickListener(this);
        regBtn = findViewById(R.id.regbtn);
        regBtn.setOnClickListener(this);

        initDB();
    }


    private void initDB(){
        List<Admin> adminlist = MyDatabase.getInstance(this).getAdminDao().getAdmins();
        if(adminlist.size()==0){
            Admin admin = new Admin();
            admin.setName("admin");
            admin.setPassword("admin");
            MyDatabase.getInstance(this).getAdminDao().insert(admin);

            Admin admin1 = new Admin();
            admin1.setName("admin123");
            admin1.setPassword("123456");
            MyDatabase.getInstance(this).getAdminDao().insert(admin1);
        }
    }

    //按钮点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                //获取用户名
                username=usernameTxt.getText().toString();
                //获取密码
                password = pwdTxt.getText().toString();
                if(username.equals("")||password.equals("")){
                    Toast.makeText(this,"请填写账号或者密码",Toast.LENGTH_LONG).show();
                }
                else if(role.equals("")){
                    Toast.makeText(LoginActivity.this, "请选择登录身份", Toast.LENGTH_SHORT).show();
                }
                else {
                    //从数据库中获取密码并判断是否相同
                    if (role.equals("管理员")) {
                        Admin admin = MyDatabase.getInstance(LoginActivity.this).getAdminDao().getAdminByName(username);
                        if (admin == null) {
                            Toast.makeText(this, "该用户不存在", Toast.LENGTH_LONG).show();
                        } else if (password.equals(admin.getPassword())) {
                            //将信息存储本地
                            SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
                            Editor editor = sp.edit();
                            editor.putBoolean("islogin", true);
                            editor.putInt("uid",admin.getId());
                            editor.putInt("role",1);
                            editor.commit();
                            Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                            //跳转主页
                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                        } else {
                            Toast.makeText(this, "密码错误", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        User user = MyDatabase.getInstance(LoginActivity.this).getUserDao().getUser(username);
                        if (user == null) {
                            Toast.makeText(this, "该用户不存在", Toast.LENGTH_LONG).show();
                        } else if (password.equals(user.getPassword())) {
                            if(user.getStatus()!=1) {
                                SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
                                Editor editor = sp.edit();
                                editor.putBoolean("islogin", true);
                                editor.putInt("uid", user.getId());
                                editor.putInt("role", 0);
                                editor.putInt("status",user.getStatus());
                                editor.commit();
                                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                                //跳转主页
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }else{
                                Toast.makeText(this, "您已经被锁定，请联系管理员解锁", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(this, "密码错误", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;
            case R.id.regbtn:
                //跳转注册页面
                Intent intent=new Intent(LoginActivity.this,RegActivity.class);
                startActivity(intent);
                break;
        }
    }

    //单选按钮点击事件
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton rb = (RadioButton) findViewById(i);
        //获取登录角色
        role=rb.getText().toString();
        if(role.equals("管理员")){
            regBtn.setVisibility(View.GONE);
        }else{
            regBtn.setVisibility(View.VISIBLE);
        }
    }
}
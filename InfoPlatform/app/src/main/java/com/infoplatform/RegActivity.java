package com.infoplatform;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.List;
import com.infoplatform.util.DatePickerDialog;
import com.infoplatform.util.DateUtil;
import android.app.Dialog;
import android.widget.Toast;
import com.infoplatform.data.User;
import com.infoplatform.util.MyDatabase;
import android.content.Intent;

//注册
public class RegActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    EditText name_edit,truename_edit,paswd_edit,enterpaswd_edit,phone_edit;
    Button btn;
    private RadioGroup radioGroup;
    private String sex;
    private TextView birthTxt;
    private Dialog dateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        //获取页面控件
        name_edit = findViewById(R.id.username);
        truename_edit = findViewById(R.id.truename);
        paswd_edit = findViewById(R.id.password);
        paswd_edit.setInputType(129);
        enterpaswd_edit = findViewById(R.id.enterpassword);
        enterpaswd_edit.setInputType(129);
        phone_edit = findViewById(R.id.phone);
        birthTxt = findViewById(R.id.birth);
        birthTxt.setOnClickListener(this);

        btn = (Button) findViewById(R.id.regbtn);
        btn.setOnClickListener(this);
        radioGroup = findViewById(R.id.sex);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.birth:
                //选择生日
                showDateDialog(DateUtil.getDateForString("1990-01-01"));
                break;
            case R.id.regbtn:
                //点击注册按钮弹出确认框
                new AlertDialog.Builder(RegActivity.this).setTitle("系统提示")
                        .setMessage("是否确定提交？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                addUser();
                            }
                        }).setNegativeButton("返回", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                }).show();
                break;
        }
    }

    //性别单选按钮点击事件
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton rb = (RadioButton) findViewById(i);
        sex=rb.getText().toString();
    }

    //弹出选择生日框
    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {

                birthTxt.setText(dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                        + (dates[2] > 9 ? dates[2] : ("0" + dates[2])));

            }

            @Override
            public void onCancel() {

            }
        })
                .setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1);

        builder.setMaxYear(DateUtil.getYear());
        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        dateDialog = builder.create();
        dateDialog.show();
    }

    private void addUser(){
        String un = name_edit.getText().toString().trim();
        String pw = paswd_edit.getText().toString().trim();
        String psag = enterpaswd_edit.getText().toString().trim();
        String name = truename_edit.getText().toString().trim();
        String phone = phone_edit.getText().toString().trim();
        if (un.equals("") || pw.equals("") || psag.equals("") || name.equals("") || phone.equals("")) {
            Toast.makeText(RegActivity.this,"请完善信息",Toast.LENGTH_SHORT).show();
        }else if(pw.length()<6){
            Toast.makeText(RegActivity.this,"密码长度太短",Toast.LENGTH_SHORT).show();
        }else if (!pw.equals(psag)) {
            Toast.makeText(RegActivity.this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
        }
        else {
            //插入数据库
            User user = new User();
            user.setName(un);
            user.setPassword(pw);
            user.setUsername(name);
            user.setPhone(phone);
            user.setSex(sex);
            user.setBirth(birthTxt.getText().toString());
            user.setStatus(0);
            MyDatabase.getInstance(RegActivity.this).getUserDao().insert(user);
            //跳转到登录页面
            startActivity(new Intent(RegActivity.this, LoginActivity.class));
        }
    }
}
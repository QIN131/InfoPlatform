package com.infoplatform.fragment;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infoplatform.LoginActivity;
import com.infoplatform.R;
import com.infoplatform.data.Admin;
import com.infoplatform.util.DatePickerDialog;
import com.infoplatform.util.DateUtil;
import com.infoplatform.util.MyDatabase;
import com.infoplatform.util.PersonalItemView;
import android.widget.Button;
import com.infoplatform.data.User;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;
import android.os.Handler;
import android.content.Intent;
import com.infoplatform.ResetPwdActivity;

import java.util.List;

//用户中心
public class MyFragment extends Fragment implements View.OnClickListener{

    private PersonalItemView pv_account;
    private PersonalItemView pv_truename;
    private PersonalItemView pv_phone;
    private PersonalItemView pv_sex;
    private PersonalItemView pv_birth;
    private PersonalItemView pv_pwd;
    private Button logoutBtn;
    private User user;
    private Admin admin;
    SharedPreferences sp;
    private Dialog dateDialog;

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        pv_account = view.findViewById(R.id.item_account);
        pv_account.setOnClickListener(this);
        sp = getContext().getSharedPreferences("userinfo", 0);
        int uid = sp.getInt("uid",0);
        int role = sp.getInt("role",0);
        if(role==0) {
            user = MyDatabase.getInstance(getActivity()).getUserDao().getUserById(uid);
            pv_account.setData(user.getName());
        }else{
            admin = MyDatabase.getInstance(getActivity()).getAdminDao().getAdmin(uid);
            pv_account.setData(admin.getName());
        }

        pv_truename = view.findViewById(R.id.item_truename);
        pv_phone = view.findViewById(R.id.item_phone);
        pv_sex = view.findViewById(R.id.item_sex);
        pv_birth = view.findViewById(R.id.item_birth);

        if(role==0) {
            pv_truename.setData(user.getUsername());
            pv_truename.setOnClickListener(this);
            pv_phone.setData(user.getPhone());
            pv_phone.setOnClickListener(this);
            pv_sex.setData(user.getSex());
            pv_sex.setOnClickListener(this);
            pv_birth.setData(user.getBirth());
            pv_birth.setOnClickListener(this);
        }else{
            pv_truename.setVisibility(View.GONE);
            pv_phone.setVisibility(View.GONE);
            pv_sex.setVisibility(View.GONE);
            pv_birth.setVisibility(View.GONE);
        }

        pv_pwd = view.findViewById(R.id.item_editpwd);
        pv_pwd.setOnClickListener(this);

        logoutBtn = view.findViewById(R.id.logout);
        logoutBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_account:
                final EditText inputServer = new EditText(getActivity());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("修改账号").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                        .setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        String newaccount=inputServer.getText().toString();
                        user.setName(newaccount);
                        MyDatabase.getInstance(getActivity()).getUserDao().update(user);
                        Toast.makeText(getActivity(), "修改成功!", Toast.LENGTH_SHORT).show();
                        pv_account.setData(newaccount);
                    }
                });
                builder.show();
                break;
            case R.id.item_truename:
                final EditText input_name = new EditText(getActivity());
                AlertDialog.Builder builder_name = new AlertDialog.Builder(getActivity());
                builder_name.setTitle("修改姓名").setIcon(android.R.drawable.ic_dialog_info).setView(input_name)
                        .setNegativeButton("取消", null);
                builder_name.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        String newname=input_name.getText().toString();
                        user.setUsername(newname);
                        MyDatabase.getInstance(getActivity()).getUserDao().update(user);
                        Toast.makeText(getActivity(), "修改成功!", Toast.LENGTH_SHORT).show();
                        pv_truename.setData(newname);
                    }
                });
                builder_name.show();
                break;
            case R.id.item_phone:
                final EditText input_phone = new EditText(getActivity());
                AlertDialog.Builder builder_phone = new AlertDialog.Builder(getActivity());
                builder_phone.setTitle("修改手机").setIcon(android.R.drawable.ic_dialog_info).setView(input_phone).setNegativeButton("取消",null);
                builder_phone.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        String newphone = input_phone.getText().toString();
                        user.setPhone(newphone);
                        MyDatabase.getInstance(getActivity()).getUserDao().update(user);
                        Toast.makeText(getActivity(), "修改成功!", Toast.LENGTH_SHORT).show();
                        pv_phone.setData(newphone);
                    }
                });
                builder_phone.show();
                break;
            case R.id.item_sex:
                //修改性别
                final EditText input_sex = new EditText(getActivity());
                AlertDialog.Builder builder_sex = new AlertDialog.Builder(getActivity());
                builder_sex.setTitle("修改性别").setIcon(android.R.drawable.ic_dialog_info).setView(input_sex)
                        .setNegativeButton("取消", null);
                builder_sex.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        String cursex=input_sex.getText().toString();
                        user.setSex(cursex);
                        MyDatabase.getInstance(getActivity()).getUserDao().update(user);
                        Toast.makeText(getActivity(), "修改成功!", Toast.LENGTH_SHORT).show();
                        pv_sex.setData(cursex);
                    }
                });
                builder_sex.show();
                break;
            case R.id.item_birth:
                //修改生日
                showDateDialog(DateUtil.getDateForString(user.getBirth()));
                break;
            case R.id.item_editpwd:
                startActivity(new Intent(getActivity(),ResetPwdActivity.class));
                break;
            case R.id.logout:
                //点击退出系统
                Handler handler=new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("islogin", false);
                        editor.putString("uid", "");
                        editor.commit();

                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                },3000);

                break;
        }
    }

    //弹出选择生日框
    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(getActivity());
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {
                user.setBirth(dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                        + (dates[2] > 9 ? dates[2] : ("0" + dates[2])));
                MyDatabase.getInstance(getActivity()).getUserDao().update(user);
                Toast.makeText(getActivity(), "修改成功!", Toast.LENGTH_SHORT).show();
                pv_birth.setData(dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
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

}
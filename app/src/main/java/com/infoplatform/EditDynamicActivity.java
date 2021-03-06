package com.infoplatform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import me.nereo.multi_image_selector.MultiImageSelector;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.infoplatform.data.Dynamic;
import com.infoplatform.util.MyDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//修改动态
public class EditDynamicActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_title;
    private EditText et_content;
    private Button selBtn;
    private Button submitBtn;
    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private ArrayList<String> mSelectPath;
    private String selimgs="";
    Dynamic dynamic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dynamic);

        ImageView iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        et_title=findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        selBtn = findViewById(R.id.selbtn);
        selBtn.setOnClickListener(this);
        submitBtn=findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);

        String id = getIntent().getStringExtra("id");
        dynamic = MyDatabase.getInstance(this).getDynamicDao().getById(Integer.parseInt(id));
        et_title.setText(dynamic.getTitle());
        et_content.setText(dynamic.getContent());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selbtn:
                //选择图片
                pickImage();
                break;
            case R.id.submitBtn:
                //提交
                String title=et_title.getText().toString().trim();
                String content = et_content.getText().toString().trim();
                if(title.length()==0||content.length()==0){
                    Toast.makeText(this,"请输入标题和内容",Toast.LENGTH_LONG).show();
                }
                else {
                    //存入数据库
                    dynamic.setTitle(title);
                    dynamic.setContent(content);
                    if(!selimgs.equals(""))
                    dynamic.setImgs(selimgs);
                    MyDatabase.getInstance(this).getDynamicDao().update(dynamic);
                    Toast.makeText(this,"修改成功",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("id",""+dynamic.getId());
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
        }
    }

    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            MultiImageSelector selector = MultiImageSelector.create(EditDynamicActivity.this);
            selector.showCamera(true);
            selector.count(3);
            selector.multi();
            selector.origin(mSelectPath);
            selector.start(EditDynamicActivity.this, REQUEST_IMAGE);
        }
    }

    //获取权限
    private void requestPermission(final String permission, String rationale, final int requestCode){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(EditDynamicActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    //获取权限返回结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickImage();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //选择图片返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){
            if(resultCode == RESULT_OK){
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for(String p: mSelectPath){
                    sb.append(p);
                    sb.append(",");
                }
                selimgs=sb.toString();
                Toast.makeText(this,"您已经选择了"+mSelectPath.size()+"张图片",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
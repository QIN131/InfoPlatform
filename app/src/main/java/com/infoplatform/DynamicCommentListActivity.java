package com.infoplatform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.infoplatform.adapter.DynamicCommentAdapter;
import com.infoplatform.data.DynamicComment;
import com.infoplatform.util.MyDatabase;

import java.util.ArrayList;
import java.util.List;

//动态信息评论列表
public class DynamicCommentListActivity extends AppCompatActivity {

    ListView listView;
    private List<DynamicComment> list = new ArrayList<>();
    private DynamicCommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_comment_list);
        String did = getIntent().getStringExtra("did");
        list = MyDatabase.getInstance(this).getDynamicCommentDao().getList(Integer.parseInt(did));
        listView = findViewById(R.id.lv_comment);
        adapter = new DynamicCommentAdapter(this,list);
        listView.setAdapter(adapter);
        ImageView iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tv_nodata=findViewById(R.id.nodata);
        if(list.size()>0){
            listView.setVisibility(View.VISIBLE);
            tv_nodata.setVisibility(View.GONE);
        }else{
            listView.setVisibility(View.GONE);
            tv_nodata.setVisibility(View.VISIBLE);
        }
    }
}
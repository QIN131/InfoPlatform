package com.infoplatform.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.infoplatform.R;
import com.infoplatform.data.Dynamic;
import com.infoplatform.util.MyDatabase;

import java.util.ArrayList;
import java.util.List;
import com.infoplatform.adapter.MyDynamicAdapter;
import com.infoplatform.AddDynamicActivity;

import static android.app.Activity.RESULT_OK;
import com.infoplatform.EditDynamicActivity;

//自己的动态信息列表
public class MyDynamicFragment extends Fragment implements View.OnClickListener, MyDynamicAdapter.InnerItemOnclickListener{

    private ListView dynamicsView;
    private List<Dynamic> dynamicList;
    private MyDynamicAdapter adapter;
    SharedPreferences sp;
    private int editp;

    public MyDynamicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_dynamic, container, false);
        dynamicsView = (ListView) view.findViewById(R.id.listview);
        dynamicList = new ArrayList<Dynamic>();
        sp = getContext().getSharedPreferences("userinfo", 0);
        int uid = sp.getInt("uid",0);
        dynamicList = MyDatabase.getInstance(getContext()).getDynamicDao().getMyDynamics(uid);
        adapter = new MyDynamicAdapter(getActivity(),dynamicList);
        adapter.setOnInnerItemOnClickListener(this);
        dynamicsView.setAdapter(adapter);

        Button addbtn = view.findViewById(R.id.addbtn);
        addbtn.setOnClickListener(this);

        return view;
    }

    //响应点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addbtn:
                int status=sp.getInt("status",0);
                if(status==0) {
                    //跳转到添加页面
                    Intent intent = new Intent(getActivity(), AddDynamicActivity.class);
                    startActivityForResult(intent, 1001);
                }else{
                    Toast.makeText(getActivity(),"您已经被禁止操作，请联系管理员",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //响应点击事件
    @Override
    public void itemClick(View v) {
        int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.editbtn:
                //获取点击位置
                editp=position;
                //跳转到编辑页面
                Intent intent = new Intent(getActivity(),EditDynamicActivity.class);
                intent.putExtra("id",dynamicList.get(position).getId()+"");
                startActivityForResult(intent,1002);
                break;
            case R.id.delbtn:
                //删除数据库里的数据
                MyDatabase.getInstance(getContext()).getDynamicDao().deleteByID(dynamicList.get(position).getId());
                dynamicList.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "删除成功!",
                        Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == RESULT_OK) {
            Dynamic newdynamic = MyDatabase.getInstance(getContext()).getDynamicDao().getLast();
            dynamicList.add(newdynamic);
        }
        if(requestCode==1002&&resultCode == RESULT_OK){
            Dynamic dynamic =  MyDatabase.getInstance(getContext()).getDynamicDao().getById(dynamicList.get(editp).getId());
            dynamicList.remove(editp);
            dynamicList.add(editp,dynamic);
        }
        adapter.notifyDataSetChanged();
    }
}
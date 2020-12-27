package com.infoplatform.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.infoplatform.R;
import com.infoplatform.adapter.UserManagerAdapter;
import com.infoplatform.data.User;
import com.infoplatform.util.MyDatabase;

import java.util.List;

//用户管理页面
public class UserManagerFragment extends Fragment implements UserManagerAdapter.CheckClickListener{

    private List<User> list;
    private ListView lv_user;
    private UserManagerAdapter adapter;

    public UserManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_manager, container, false);
        list = MyDatabase.getInstance(getContext()).getUserDao().getAllUsers();
        lv_user = view.findViewById(R.id.lv_user);
        adapter = new UserManagerAdapter(getContext(),list);
        adapter.setCheckClickListener(this);
        lv_user.setAdapter(adapter);
        return view;
    }

    //响应点击事件
    @Override
    public void itemClick(View v) {
        int position = (Integer) v.getTag();

        switch (v.getId()) {
            case R.id.op1:
                makeuser(position,0);
                break;
            case R.id.op2:
                makeuser(position,2);
                break;
            case R.id.op3:
                makeuser(position,1);
                break;
        }
    }

    private void makeuser(int p,int newstatus){
        User user = list.get(p);
        user.setStatus(newstatus);
        MyDatabase.getInstance(getContext()).getUserDao().update(user);
        list.remove(p);
        list.add(p,user);
        adapter.notifyDataSetChanged();
        Toast.makeText(getActivity(),"操作成功",Toast.LENGTH_SHORT).show();
    }
}
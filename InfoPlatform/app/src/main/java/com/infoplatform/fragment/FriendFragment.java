package com.infoplatform.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.infoplatform.R;
import com.infoplatform.adapter.FriendListAdapter;
import com.infoplatform.data.Friend;
import com.infoplatform.util.MyDatabase;

import java.util.List;

//好友管理
public class FriendFragment extends Fragment implements FriendListAdapter.OpOnclickListener{

    private List<Friend> list;
    private ListView lv_friend;
    private FriendListAdapter adapter;
    int uid;

    public FriendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        SharedPreferences sp = getContext().getSharedPreferences("userinfo", 0);
        uid = sp.getInt("uid",0);
        list = MyDatabase.getInstance(getContext()).getFriendDao().getAllFriends(uid);
        lv_friend = (ListView)view.findViewById(R.id.lv_friend);
        adapter = new FriendListAdapter(getActivity(),list);
        adapter.setOnOpOnclickListener(this);
        lv_friend.setAdapter(adapter);
        return view;
    }

    //响应点击事件
    @Override
    public void itemClick(View v) {
        int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.agree:
                //同意
                Friend friend = list.get(position);
                friend.setStatus(1);
                MyDatabase.getInstance(getContext()).getFriendDao().update(friend);
                Friend friend1=MyDatabase.getInstance(getContext()).getFriendDao().getFriend(friend.getFuid(),friend.getUid());
                friend1.setStatus(1);
                MyDatabase.getInstance(getContext()).getFriendDao().update(friend1);
                Toast.makeText(getActivity(),"已经是好友啦，快去打声招呼吧",Toast.LENGTH_SHORT).show();
                list.remove(position);
                list.add(position,friend);
                adapter.notifyDataSetChanged();
                break;
            case R.id.noagree:
                //不同意
                Friend friend3 = list.get(position);
                MyDatabase.getInstance(getContext()).getFriendDao().deleteFriend(friend3.getUid(),friend3.getFuid());
                MyDatabase.getInstance(getContext()).getFriendDao().deleteFriend(friend3.getFuid(),friend3.getUid());
                Toast.makeText(getActivity(),"已经拒绝",Toast.LENGTH_SHORT).show();
                list.remove(position);
                adapter.notifyDataSetChanged();
                break;
            case R.id.delfriend:
                //删除
                Friend friend2 = list.get(position);
                MyDatabase.getInstance(getContext()).getFriendDao().deleteFriend(friend2.getUid(),friend2.getFuid());
                Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
                list.remove(position);
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
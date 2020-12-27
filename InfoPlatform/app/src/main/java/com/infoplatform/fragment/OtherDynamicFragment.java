package com.infoplatform.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.infoplatform.R;
import com.infoplatform.adapter.MyDynamicAdapter;
import com.infoplatform.adapter.OtherDynamicAdapter;
import com.infoplatform.data.Dynamic;
import com.infoplatform.data.DynamicComment;
import com.infoplatform.data.Friend;
import com.infoplatform.data.LoveDynamic;
import com.infoplatform.data.Notice;
import com.infoplatform.data.User;
import com.infoplatform.util.MyDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.infoplatform.DynamicCommentListActivity;

//其他人的动态信息列表
public class OtherDynamicFragment extends Fragment implements OtherDynamicAdapter.ItemOnclickListener{

    private ListView dynamicsView;
    private List<Dynamic> dynamicList;
    private OtherDynamicAdapter adapter;
    private int uid=0;
    SharedPreferences sp;

    public OtherDynamicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_dynamic, container, false);
        dynamicsView = (ListView) view.findViewById(R.id.listview);
        dynamicList = new ArrayList<Dynamic>();
        sp = getContext().getSharedPreferences("userinfo", 0);
        uid = sp.getInt("uid",0);
        dynamicList = MyDatabase.getInstance(getContext()).getDynamicDao().getOtherDynamics(uid);
        adapter = new OtherDynamicAdapter(getActivity(),dynamicList);
        adapter.setOnItemOnClickListener(this);
        dynamicsView.setAdapter(adapter);
        return view;
    }

    //响应点击事件
    @Override
    public void itemClick(View v) {
        final int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.addbtn:
                //加好友
                int status=sp.getInt("status",0);
                if(status==0) {
                    //加为好友
                    User selfinfo = MyDatabase.getInstance(getContext()).getUserDao().getUserById(uid);
                    User friendinfo = MyDatabase.getInstance(getContext()).getUserDao().getUserById(dynamicList.get(position).getUid());
                    Friend friend = new Friend();
                    friend.setUid(uid);
                    friend.setUname(selfinfo.getUsername());
                    friend.setFuid(friendinfo.getId());
                    friend.setFname(friendinfo.getUsername());
                    friend.setStatus(0);
                    MyDatabase.getInstance(getContext()).getFriendDao().insert(friend);
                    Friend friend1 = new Friend();
                    friend1.setUid(friendinfo.getId());
                    friend1.setUname(friendinfo.getUsername());
                    friend1.setFuid(uid);
                    friend1.setFname(selfinfo.getUsername());
                    friend1.setStatus(0);
                    MyDatabase.getInstance(getContext()).getFriendDao().insert(friend1);
                    Toast.makeText(getActivity(), "申请成功，等待对方处理", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getActivity(),"您已经被禁止操作，请联系管理员",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lovebtn:
                if(position>4000){
                    //取消点赞
                    MyDatabase.getInstance(getContext()).getLoveDynamicDao().deleteByID(position-4000);
                }else{
                    //点赞
                    LoveDynamic loveDynamic = new LoveDynamic();
                    loveDynamic.setUid(uid);
                    loveDynamic.setDynamicid(dynamicList.get(position-1000).getId());
                    MyDatabase.getInstance(getContext()).getLoveDynamicDao().insert(loveDynamic);
                    Notice notice = new Notice();
                    notice.setUid(dynamicList.get(position-1000).getUid());
                    notice.setWords("有人给你点赞啦");
                    MyDatabase.getInstance(getContext()).getNoticeDao().insert(notice);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.commentbtn:
                //进行评论
                final EditText input_sex = new EditText(getActivity());
                AlertDialog.Builder builder_sex = new AlertDialog.Builder(getActivity());
                builder_sex.setTitle("评论").setIcon(android.R.drawable.ic_dialog_info).setView(input_sex)
                        .setNegativeButton("取消", null);
                builder_sex.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        String comment=input_sex.getText().toString();
                        DynamicComment dynamicComment = new DynamicComment();
                        dynamicComment.setComment(comment);
                        dynamicComment.setDynamicid(dynamicList.get(position-2000).getId());
                        dynamicComment.setUid(uid);
                        User user = MyDatabase.getInstance(getContext()).getUserDao().getUserById(uid);
                        dynamicComment.setUname(user.getUsername());
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                        Date curDate =  new Date(System.currentTimeMillis());
                        String curtime= formatter.format(curDate);
                        dynamicComment.setSendtime(curtime);
                        MyDatabase.getInstance(getActivity()).getDynamicCommentDao().insert(dynamicComment);
                        Toast.makeText(getActivity(), "评论成功!", Toast.LENGTH_SHORT).show();
                        Notice notice = new Notice();
                        notice.setUid(dynamicList.get(position-2000).getUid());
                        notice.setWords("有新的评论了，快去看看吧");
                        MyDatabase.getInstance(getContext()).getNoticeDao().insert(notice);
                    }
                });
                builder_sex.show();
                break;
            case R.id.seebtn:
                //查看评论列表
                Intent intent = new Intent(getActivity(),DynamicCommentListActivity.class);
                intent.putExtra("did",dynamicList.get(position-3000).getId()+"");
                startActivity(intent);
                break;
        }
    }

}
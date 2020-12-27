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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infoplatform.DynamicCommentListActivity;
import com.infoplatform.EditDynamicActivity;
import com.infoplatform.R;
import com.infoplatform.adapter.UserListAdapter;
import com.infoplatform.data.Dynamic;
import com.infoplatform.data.DynamicComment;
import com.infoplatform.data.Friend;
import com.infoplatform.data.LoveDynamic;
import com.infoplatform.data.Notice;
import com.infoplatform.data.User;
import com.infoplatform.util.MyDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import com.infoplatform.adapter.SearchDynamicAdapter;

import static android.app.Activity.RESULT_OK;

//搜索用户或者动态
public class SeachFragment extends Fragment implements Spinner.OnItemSelectedListener,SearchDynamicAdapter.QueryItemOnclickListener,View.OnClickListener,UserListAdapter.ItemClickListener {

    Spinner spinner;
    String[] v_search = new String[]{"用户","动态"};
    private String search_str="";
    private EditText et_search;
    private Button querybtn;
    int uid=0;
    private List<User> ulist;
    private UserListAdapter user_adapter;
    private List<Dynamic> dlist;
    private SearchDynamicAdapter d_adapter;
    private int editp;
    private TextView tv_nodata;
    private ListView lv_searchrs;
    SharedPreferences sp;

    public SeachFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seach, container, false);
        //绑定组件
        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,v_search);
        spinner.setAdapter(adapter);
        tv_nodata = view.findViewById(R.id.nosearchrs);
        lv_searchrs = view.findViewById(R.id.lv_searchrs);
        et_search = (EditText) view.findViewById(R.id.et_key);
        querybtn = view.findViewById(R.id.querybtn);
        querybtn.setOnClickListener(this);

        sp = getContext().getSharedPreferences("userinfo", 0);
        uid = sp.getInt("uid",0);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View view, int i,
                               long l) {
        search_str = v_search[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    //查询按钮点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.querybtn:
                String string = et_search.getText().toString().trim();
                if(string.equals("")){
                    Toast.makeText(getActivity(),"请输入关键字",Toast.LENGTH_SHORT).show();
                }else{
                    if(search_str.equals("用户")){
                        //检索用户
                        ulist=MyDatabase.getInstance(getContext()).getUserDao().searchUsers(string);
                        if(ulist.size()==0){
                            tv_nodata.setVisibility(View.VISIBLE);
                            lv_searchrs.setVisibility(View.GONE);
                        }else{
                            tv_nodata.setVisibility(View.GONE);
                            user_adapter = new UserListAdapter(getActivity(),ulist);
                            user_adapter.setItemClickListener(this);
                            lv_searchrs.setAdapter(user_adapter);
                            lv_searchrs.setVisibility(View.VISIBLE);
                        }
                    }else{
                        //检索动态
                        dlist = MyDatabase.getInstance(getContext()).getDynamicDao().searchdynamics(string);
                        if(dlist.size()==0){
                            tv_nodata.setVisibility(View.VISIBLE);
                            lv_searchrs.setVisibility(View.GONE);
                        }
                        else{
                            tv_nodata.setVisibility(View.GONE);
                            d_adapter = new SearchDynamicAdapter(getActivity(),dlist);
                            d_adapter.setQueryItemOnclickListener(this);
                            lv_searchrs.setAdapter(d_adapter);
                            lv_searchrs.setVisibility(View.VISIBLE);
                        }
                    }
                }
                break;
        }
    }

    //响应点击事件
    @Override
    public void itemClick(View v) {
        final int position;
        position = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.addbtn:
                int status=sp.getInt("status",0);
                if(status==0) {
                    //加为好友
                    User selfinfo = MyDatabase.getInstance(getContext()).getUserDao().getUserById(uid);
                    User friendinfo = MyDatabase.getInstance(getContext()).getUserDao().getUserById(ulist.get(position).getId());
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
                    user_adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getActivity(),"您已经被禁止操作，请联系管理员",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.editbtn:
                //获取点击位置
                editp=position;
                //跳转到编辑页面
                Intent intent = new Intent(getActivity(), EditDynamicActivity.class);
                intent.putExtra("id",dlist.get(position).getId()+"");
                startActivityForResult(intent,1002);
                break;
            case R.id.delbtn:
                //删除数据库里的数据
                MyDatabase.getInstance(getContext()).getDynamicDao().deleteByID(dlist.get(position).getId());
                dlist.remove(position);
                d_adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "删除成功!",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.lovebtn:
                if(position>4000){
                    //取消点赞
                    MyDatabase.getInstance(getContext()).getLoveDynamicDao().deleteByID(position);
                }else{
                    //点赞
                    LoveDynamic loveDynamic = new LoveDynamic();
                    loveDynamic.setUid(uid);
                    loveDynamic.setDynamicid(dlist.get(position-1000).getId());
                    MyDatabase.getInstance(getContext()).getLoveDynamicDao().insert(loveDynamic);
                    Notice notice = new Notice();
                    notice.setUid(dlist.get(position).getUid());
                    notice.setWords("有人给你点赞啦");
                    MyDatabase.getInstance(getContext()).getNoticeDao().insert(notice);
                }
                d_adapter.notifyDataSetChanged();
                break;
            case R.id.commentbtn:
                int status1=sp.getInt("status",0);
                if(status1==0) {
                    //进行评论
                    final EditText input_sex = new EditText(getActivity());
                    AlertDialog.Builder builder_sex = new AlertDialog.Builder(getActivity());
                    builder_sex.setTitle("评论").setIcon(android.R.drawable.ic_dialog_info).setView(input_sex)
                            .setNegativeButton("取消", null);
                    builder_sex.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            String comment = input_sex.getText().toString();
                            DynamicComment dynamicComment = new DynamicComment();
                            dynamicComment.setComment(comment);
                            dynamicComment.setDynamicid(dlist.get(position - 2000).getId());
                            dynamicComment.setUid(uid);
                            User user = MyDatabase.getInstance(getContext()).getUserDao().getUserById(uid);
                            dynamicComment.setUname(user.getUsername());
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                            Date curDate = new Date(System.currentTimeMillis());
                            String curtime = formatter.format(curDate);
                            dynamicComment.setSendtime(curtime);
                            MyDatabase.getInstance(getActivity()).getDynamicCommentDao().insert(dynamicComment);
                            Toast.makeText(getActivity(), "评论成功!", Toast.LENGTH_SHORT).show();
                            Notice notice = new Notice();
                            notice.setUid(dlist.get(position-2000).getUid());
                            notice.setWords("有新的评论了，快去看看吧");
                            MyDatabase.getInstance(getContext()).getNoticeDao().insert(notice);
                        }
                    });
                    builder_sex.show();
                }else{
                    Toast.makeText(getActivity(),"您已经被禁止操作，请联系管理员",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.seebtn:
                //查看评论列表
                Intent intent1 = new Intent(getActivity(), DynamicCommentListActivity.class);
                intent1.putExtra("did",dlist.get(position-3000).getId()+"");
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1002&&resultCode == RESULT_OK){
            Dynamic dynamic =  MyDatabase.getInstance(getContext()).getDynamicDao().getById(dlist.get(editp).getId());
            dlist.remove(editp);
            dlist.add(editp,dynamic);
        }
        d_adapter.notifyDataSetChanged();
    }
}
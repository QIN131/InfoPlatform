package com.infoplatform.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.infoplatform.R;
import com.infoplatform.data.Friend;
import com.infoplatform.data.User;
import com.infoplatform.util.MyDatabase;

import java.util.List;

public class UserListAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<User> list;
    private ItemClickListener mListener;

    public UserListAdapter(Context _context,List<User> _list){
        super();
        this.context = _context;
        this.list = _list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.user_list_item, null);
        } else {
            view = convertView;
        }
        //从list中取出一行数据，position相当于数组下标,可以实现逐行取数据
        User user = list.get(position);
        TextView tv_name = view.findViewById(R.id.tv_name);
        tv_name.setText(user.getUsername());
        TextView addbtn = view.findViewById(R.id.addbtn);
        addbtn.setTag(position);
        addbtn.setOnClickListener(this);
        addbtn.setVisibility(View.VISIBLE);
        SharedPreferences sp = context.getSharedPreferences("userinfo", 0);
        int uid = sp.getInt("uid",0);
        if(user.getId()==uid){
            addbtn.setVisibility(View.GONE);
        }
        Friend friend = MyDatabase.getInstance(context).getFriendDao().getFriend(uid,user.getId());
        if(friend!=null){
            addbtn.setVisibility(View.GONE);
        }
        return view;
    }

    public interface ItemClickListener{
        void itemClick(View v);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
    }
}

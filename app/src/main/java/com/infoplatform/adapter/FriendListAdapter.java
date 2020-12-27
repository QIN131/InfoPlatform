package com.infoplatform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.infoplatform.R;
import com.infoplatform.data.Friend;

import java.util.List;

public class FriendListAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private List<Friend> list;
    private OpOnclickListener mListener;

    public FriendListAdapter(Context _context,List<Friend> _list){
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
            view = inflater.inflate(R.layout.friend_item, null);
        } else {
            view = convertView;
        }
        Friend friend = list.get(position);
        TextView tv_friend = view.findViewById(R.id.tv_friend);
        tv_friend.setText(friend.getFname());

        TextView tv_agree = view.findViewById(R.id.agree);
        TextView tv_noagree = view.findViewById(R.id.noagree);
        TextView tv_del = view.findViewById(R.id.delfriend);
        if(friend.getStatus()==0){
            tv_del.setVisibility(View.GONE);
            tv_agree.setVisibility(View.VISIBLE);
            tv_noagree.setVisibility(View.VISIBLE);
            tv_agree.setTag(position);
            tv_agree.setOnClickListener(this);
            tv_noagree.setTag(position);
            tv_noagree.setOnClickListener(this);
        }else{
            tv_del.setVisibility(View.VISIBLE);
            tv_del.setTag(position);
            tv_del.setOnClickListener(this);
            tv_agree.setVisibility(View.GONE);
            tv_noagree.setVisibility(View.GONE);
        }
        return view;
    }

    public interface OpOnclickListener {
        void itemClick(View v);
    }

    public void setOnOpOnclickListener(OpOnclickListener listener){
        this.mListener=listener;
    }

    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
    }
}

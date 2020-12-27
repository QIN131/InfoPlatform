package com.infoplatform.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.infoplatform.R;
import com.infoplatform.data.User;

import java.util.List;

public class UserManagerAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private List<User> list;
    private CheckClickListener mListener;

    public UserManagerAdapter(Context _context,List<User> _list){
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
            view = inflater.inflate(R.layout.usermanager_list_item, null);
        } else {
            view = convertView;
        }
        //从list中取出一行数据，position相当于数组下标,可以实现逐行取数据
        User user = list.get(position);
        TextView tv_name = view.findViewById(R.id.tv_name);
        tv_name.setText(user.getUsername());
        TextView op1 = view.findViewById(R.id.op1);
        op1.setTag(position);
        op1.setOnClickListener(this);
        TextView op2 = view.findViewById(R.id.op2);
        op2.setTag(position);
        op2.setOnClickListener(this);
        TextView op3 = view.findViewById(R.id.op3);
        op3.setTag(position);
        op3.setOnClickListener(this);

        if(user.getStatus()==0){
            op1.setTextColor(Color.RED);
            op2.setTextColor(Color.BLACK);
            op3.setTextColor(Color.BLACK);
        }else if(user.getStatus()==2){
            op2.setTextColor(Color.RED);
            op1.setTextColor(Color.BLACK);
            op3.setTextColor(Color.BLACK);
        }else if(user.getStatus()==1){
            op3.setTextColor(Color.RED);
            op1.setTextColor(Color.BLACK);
            op2.setTextColor(Color.BLACK);
        }
        return view;
    }

    public interface CheckClickListener{
        void itemClick(View v);
    }

    public void setCheckClickListener(CheckClickListener listener){
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
    }
}
package com.infoplatform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.infoplatform.R;
import com.infoplatform.data.DynamicComment;

import java.util.List;

public class DynamicCommentAdapter extends BaseAdapter {
    private List<DynamicComment> list;
    private Context context;

    public DynamicCommentAdapter(Context _context,List<DynamicComment> _list){
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
            view = inflater.inflate(R.layout.comment_listitem, null);
        } else {
            view = convertView;
        }
        //从list中取出一行数据，position相当于数组下标,可以实现逐行取数据
        DynamicComment dynamicComment = list.get(position);
        TextView tv_senduser = (TextView)view.findViewById(R.id.senduser);
        tv_senduser.setText(dynamicComment.getUname()+"： ");
        TextView tv_comment = (TextView)view.findViewById(R.id.commentwords);
        tv_comment.setText(dynamicComment.getComment());
        TextView tv_sendtime = (TextView)view.findViewById(R.id.sendtime);
        tv_sendtime.setText(dynamicComment.getSendtime());
        return view;
    }
}

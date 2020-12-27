package com.infoplatform.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.infoplatform.R;

public class DynamicImgsAdapter extends BaseAdapter {
    Context context;
    String [] imgs;

    public DynamicImgsAdapter(Context _context,String [] _imgs){
        this.imgs = _imgs;
        this.context = _context;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int position) {
        return imgs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.imggrid_item, null);
        ImageView bgView = (ImageView) convertView.findViewById(R.id.img);
        Bitmap bitmap= BitmapFactory.decodeFile(imgs[position]);
        bgView.setImageBitmap(bitmap);
        return convertView;
    }
}

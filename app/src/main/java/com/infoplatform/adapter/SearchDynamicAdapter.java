package com.infoplatform.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infoplatform.R;
import com.infoplatform.data.Dynamic;
import com.infoplatform.data.LoveDynamic;
import com.infoplatform.util.MyDatabase;

import java.util.List;

public class SearchDynamicAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<Dynamic> list;
    private QueryItemOnclickListener mListener;

    public SearchDynamicAdapter(Context _context,List<Dynamic> _list){
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
            view = inflater.inflate(R.layout.searchdynamic_list_item, null);
        } else {
            view = convertView;
        }
        //从list中取出一行数据，position相当于数组下标,可以实现逐行取数据
        Dynamic dynamic = list.get(position);
        TextView tv_title = (TextView)view.findViewById(R.id.title);
        tv_title.setText(dynamic.getTitle());
        TextView tv_content = (TextView)view.findViewById(R.id.content);
        tv_content.setText(dynamic.getContent());
        HorizontalScrollView scrollview = view.findViewById(R.id.scrollview);
        GridView gview = view.findViewById(R.id.imgsgrid);
        String strimg = dynamic.getImgs();
        if(strimg.length()!=0){
            String [] imgs = strimg.split(",");
            int listSize = imgs.length;
            // 得到像素密度
            DisplayMetrics outMetrics = new DisplayMetrics();
            WindowManager WM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            WM.getDefaultDisplay().getMetrics(outMetrics);
            float density = outMetrics.density; // 像素密度
            // 根据item的数目，动态设定gridview的宽度,现假定每个item的宽度和高度均为150dp，列间距为10dp
            ViewGroup.LayoutParams params_jieduan = gview.getLayoutParams();
            int itemWidth_jieduan = (int) (150 * density);
            int spacingWidth = (int) (10 * density);
            params_jieduan.width = itemWidth_jieduan * listSize + (listSize - 1) * spacingWidth;
            gview.setStretchMode(GridView.NO_STRETCH); // 设置为禁止拉伸模式
            gview.setNumColumns(listSize);
            gview.setHorizontalSpacing(spacingWidth);
            gview.setColumnWidth(itemWidth_jieduan);
            gview.setLayoutParams(params_jieduan);
            DynamicImgsAdapter adapter = new DynamicImgsAdapter(context,imgs);
            gview.setAdapter(adapter);
            scrollview.setVisibility(View.VISIBLE);
        }
        else{
            scrollview.setVisibility(View.GONE);
        }

        SharedPreferences sp = context.getSharedPreferences("userinfo", 0);
        int uid = sp.getInt("uid",0);
        LinearLayout mylayout = view.findViewById(R.id.my_layout);
        LinearLayout otherlayout = view.findViewById(R.id.other_layout);
        if(dynamic.getUid()==uid){
            //自己的
            mylayout.setVisibility(View.VISIBLE);
            otherlayout.setVisibility(View.GONE);

            TextView tv_checkstatus = view.findViewById(R.id.checkstatus);
            if(dynamic.getIscheck()==0){
                tv_checkstatus.setText("待审核");
            }
            else if(dynamic.getIscheck()==1){
                tv_checkstatus.setText("审核通过");
            }
            else if(dynamic.getIscheck()==2){
                tv_checkstatus.setText("审核不通过");
            }

            TextView editBtn = view.findViewById(R.id.editbtn);
            if(dynamic.getIscheck()==0) {
                editBtn.setTag(position);
                editBtn.setOnClickListener(this);
            }else{
                editBtn.setVisibility(View.GONE);
            }
            TextView delbtn =(TextView) view.findViewById(R.id.delbtn);
            delbtn.setTag(position);
            delbtn.setOnClickListener(this);

        }else{
            //其他人的
            mylayout.setVisibility(View.GONE);
            otherlayout.setVisibility(View.VISIBLE);
            TextView loveBtn = view.findViewById(R.id.lovebtn);

            LoveDynamic loveDynamic = MyDatabase.getInstance(context).getLoveDynamicDao().getData(uid,dynamic.getId());
            if(loveDynamic == null) {
                loveBtn.setBackgroundResource(R.drawable.love_grey);
                loveBtn.setTag(1000 + position);
            }else{
                loveBtn.setBackgroundResource(R.drawable.love_red);
                loveBtn.setTag(loveDynamic.getId());
            }
            loveBtn.setOnClickListener(this);

            TextView commentbtn =(TextView) view.findViewById(R.id.commentbtn);
            commentbtn.setTag(2000+position);
            commentbtn.setOnClickListener(this);

            TextView seecommentbtn = (TextView) view.findViewById(R.id.seebtn);
            seecommentbtn.setTag(3000+position);
            seecommentbtn.setOnClickListener(this);
        }
        return view;
    }

    public interface QueryItemOnclickListener {
        void itemClick(View v);
    }

    public void setQueryItemOnclickListener(QueryItemOnclickListener listener){
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        mListener.itemClick(v);
    }
}

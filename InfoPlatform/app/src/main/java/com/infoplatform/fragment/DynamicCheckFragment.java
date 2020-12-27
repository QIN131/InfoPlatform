package com.infoplatform.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.infoplatform.R;
import com.infoplatform.adapter.DynamicCheckAdapter;
import com.infoplatform.data.Dynamic;
import com.infoplatform.util.MyDatabase;

import java.util.ArrayList;
import java.util.List;

//动态审核页面
public class DynamicCheckFragment extends Fragment implements DynamicCheckAdapter.DCheckClickListener{

    //声明控件
    ListView listView;
    private List<Dynamic> list = new ArrayList<>();
    private DynamicCheckAdapter adapter;
    private int opindex;

    public DynamicCheckFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic_check, container, false);
        listView = view.findViewById(R.id.listview);
        list = MyDatabase.getInstance(getContext()).getDynamicDao().getNoCheckDynamics();
        adapter = new DynamicCheckAdapter(getContext(),list);
        adapter.setDCheckClickListener(this);
        listView.setAdapter(adapter);
        return view;
    }

    //响应点击事件
    @Override
    public void itemClick(View v) {
        int position = (Integer) v.getTag();
        Dynamic dynamic = list.get(position);
        switch (v.getId()) {
            case R.id.check_agree:
                // 通过
                makecheck(dynamic,1);
                break;
            case R.id.check_noagree:
                //不通过
                makecheck(dynamic,2);
                break;
            default:
                break;
        }
    }

    private void makecheck(Dynamic dynamic,int checkrs){
        dynamic.setIscheck(checkrs);
        MyDatabase.getInstance(getContext()).getDynamicDao().update(dynamic);
        list.remove(opindex);
        adapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "审核成功!",
                Toast.LENGTH_LONG).show();
    }
}
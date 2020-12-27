package com.infoplatform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

import android.graphics.Color;
import android.os.Bundle;

import com.infoplatform.adapter.MainViewPagerAdapter;

//普通用户主页
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQLiteStudioService.instance().start(this);
        setContentView(R.layout.activity_main);
        initTabUI();
        //getNotice();
    }

    /**
     * 初始化页面布局
     */
    private void initTabUI() {

        //底部导航
        PageNavigationView tab = findViewById(R.id.tab);

        NavigationController navigationController = tab.custom()
                .addItem(newItem(R.drawable.dynamic_grey, R.drawable.dynamic_red, "动态"))
                .addItem(newItem(R.drawable.search_grey, R.drawable.search_red, "搜索"))
                .addItem(newItem(R.drawable.friend_grey, R.drawable.friend_red, "好友"))
                .addItem(newItem(R.drawable.my_grey, R.drawable.my_red, "我的"))
                .build();

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(), navigationController.getItemCount()));


        navigationController.setupWithViewPager(viewPager);



//        //设置消息数
//        navigationController.setMessageNumber(1, 8);
//
//        //设置显示小圆点
//        navigationController.setHasMessage(0, true);
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(0xFF009688);
        return normalItemView;
    }


}
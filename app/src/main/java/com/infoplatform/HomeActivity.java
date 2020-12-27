package com.infoplatform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;

import android.graphics.Color;
import android.os.Bundle;

import com.infoplatform.adapter.HomeViewPagerAdapter;

//管理员主页
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initTabUI();
    }

    /**
     * 初始化页面布局
     */
    private void initTabUI() {

        //底部导航
        PageNavigationView tab = findViewById(R.id.tab);

        NavigationController navigationController = tab.custom()
                .addItem(newItem(R.drawable.infocheck_grey, R.drawable.infocheck_red, "信息审核"))
                .addItem(newItem(R.drawable.usermanage_grey, R.drawable.usermanage_red, "用户管理"))
                .addItem(newItem(R.drawable.my_grey, R.drawable.my_red, "个人中心"))
                .build();

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager(), navigationController.getItemCount()));

        //自动适配ViewPager页面切换
        navigationController.setupWithViewPager(viewPager);
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
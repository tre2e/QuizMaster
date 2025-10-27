package com.example.quizmaster.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.quizmaster.R;
import com.example.quizmaster.ui.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNav; // 正确声明

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏默认 ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        // 初始化控件
        viewPager = findViewById(R.id.viewPager);
        bottomNav = findViewById(R.id.bottomNav); // 正确绑定

        // 设置 ViewPager
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);

        // === 同步 ViewPager 与 BottomNavigationView ===
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNav.getMenu().getItem(position).setChecked(true);
            }
        });

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                viewPager.setCurrentItem(0);
            } else if (id == R.id.nav_practice) {
                viewPager.setCurrentItem(1);
            } else if (id == R.id.nav_favorite) {
                viewPager.setCurrentItem(2);
            } else if (id == R.id.nav_wrong) {
                viewPager.setCurrentItem(3);
            } else if (id == R.id.nav_profile) {
                viewPager.setCurrentItem(4);
            }
            return true;
        });
    }

    // 暴露给 Fragment 使用（可选）
    public ViewPager2 getViewPager() {
        return viewPager;
    }
}
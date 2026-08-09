package com.example.caronrentrenter.TNC;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.caronrentrenter.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainTNC extends AppCompatActivity{

    PageAdapter pageAdapter;
     TabLayout tabLayout;
     ViewPager2 viewPager;
    //private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tnc_main);
        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager2);
        pageAdapter=new PageAdapter(this);
        viewPager.setAdapter(pageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }
}

//toolbar=(Toolbar)findViewById(R.id.toolbar);
//  toolbar.setTitle("Fragment Tab");
//  setSupportActionBar(toolbar);
//
//        tabLayout=findViewById(R.id.tablayout);
//        tabLayout.addTab(tabLayout.newTab().setText("english"));
//        tabLayout.addTab(tabLayout.newTab().setText("gujarati"));
//        tabLayout.addTab(tabLayout.newTab().setText("marathi"));
//        tabLayout.addTab(tabLayout.newTab().setText("tamil"));
//        tabLayout.addTab(tabLayout.newTab().setText("hindi"));
//
//        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
//        viewPager=findViewById(R.id.viewpager);
//        //PageAdapter adapter = new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
//        //viewPager.setAdapter(adapter);
//        tabLayout.setOnTabSelectedListener(this);
//
//    }
//
//    @Override
//    public void onTabSelected(TabLayout.Tab tab) {
//    }
//
//    @Override
//    public void onTabUnselected(TabLayout.Tab tab) {
//
//    }
//
//    @Override
//    public void onTabReselected(TabLayout.Tab tab) {
//
//    }
//}

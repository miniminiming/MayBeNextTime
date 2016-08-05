package com.example.administrator.myapplication.activity.coorinatorlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class CollapsingToolbarLayoutActivity extends BaseActivity {

    private RecyclerView recyclerView;
    android.support.v7.widget.Toolbar mToolbar;
    private CoorAdapter coorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collaspingt);

        initView();
        initData();
    }

    private void initData() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<String> strs=new ArrayList<>();
        for (int x=0;x<100;x++){
            strs.add("item"+x);
        }
        coorAdapter=new CoorAdapter(strs,this);
        recyclerView.setAdapter(coorAdapter);
    }

    private void initView() {
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setTitle("MayBeNextTime");

        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置扩张时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
    }
}

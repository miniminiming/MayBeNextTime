package com.example.administrator.myapplication.activity.coorinatorlayout;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class CoordinatorLayoutActivity extends BaseActivity {
    private ImageView iv_head;
    private RecyclerView recyclerView;
    private ImageView iv_foot;
    private CoordinatorLayout behavior_demo_coordinatorLayout;

    private CoorAdapter coorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        initView();
        initData();
    }

    private void initData() {
        List<String> strs=new ArrayList<>();
        for (int x=0;x<100;x++){
            strs.add("item"+x);
        }
        coorAdapter=new CoorAdapter(strs,this);
        recyclerView.setAdapter(coorAdapter);
    }

    private void initView() {
        iv_head = (ImageView) findViewById(R.id.iv_head);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        iv_foot = (ImageView) findViewById(R.id.iv_foot);
        behavior_demo_coordinatorLayout = (CoordinatorLayout) findViewById(R.id.behavior_demo_coordinatorLayout);
    }
}

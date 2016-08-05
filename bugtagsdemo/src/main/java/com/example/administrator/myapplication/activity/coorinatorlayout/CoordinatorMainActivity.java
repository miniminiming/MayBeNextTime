package com.example.administrator.myapplication.activity.coorinatorlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.BaseActivity;
import com.example.administrator.myapplication.adapter.BaseQuickAdapter;
import com.example.administrator.myapplication.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorMainActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private MainAdapter mAdapter;
    private List<String> strs;

    private String[] names =
            new String[]{
                    "AvatarImageBeHavior",
                    "easyTest",
                    "AppBarLayout",
                    "CollapsingToolbarLayout",
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        registerListener();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void initData() {
        strs = new ArrayList<>();

        for (int x = 0; x < names.length; x++) {
            strs.add(names[x]);
        }
        mAdapter = new MainAdapter(strs, this);
        recyclerView.setAdapter(mAdapter);
    }

    private void registerListener() {
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        startActivity(new Intent(CoordinatorMainActivity.this,CoordinatorLayoutActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(CoordinatorMainActivity.this,EasyCoordinatorLayoutActivity.class));

                        break;
                    case 2:
                        startActivity(new Intent(CoordinatorMainActivity.this,AppBarLayoutActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(CoordinatorMainActivity.this,CollapsingToolbarLayoutActivity.class));
                        break;
                }
            }
        });
    }
}

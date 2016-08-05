package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.coorinatorlayout.CoordinatorMainActivity;
import com.example.administrator.myapplication.adapter.BaseQuickAdapter;
import com.example.administrator.myapplication.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private MainAdapter mAdapter;
    private List<String> strs;

    private String[] names =
            new String[]{
                    "CoordinatorLayout",
                    "按钮水波纹",
                    "落叶进度条",
                    "vectorTest",
                    "手指动画",
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
                        startActivity(new Intent(MainActivity.this,CoordinatorMainActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,RippleTestActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this,ProgressTestActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this,VectorTestActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this,FingerActivity.class));
                        break;
                }
            }
        });
    }
}

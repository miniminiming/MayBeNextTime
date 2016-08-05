package com.example.administrator.myapplication.activity;

import android.os.Bundle;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2016/7/13 0013.
 */
public class FingerActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);
        requestNotAddFingerLayout();
    }
}

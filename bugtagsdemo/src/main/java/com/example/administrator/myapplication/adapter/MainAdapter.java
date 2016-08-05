package com.example.administrator.myapplication.adapter;

import android.content.Context;

import com.example.administrator.myapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class MainAdapter extends BaseQuickAdapter<String> {

    public MainAdapter(List<String> data, Context cxt) {
        super(data, cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.item_main;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item, int position) {
        helper.setText(R.id.tv_name,item);
    }
}

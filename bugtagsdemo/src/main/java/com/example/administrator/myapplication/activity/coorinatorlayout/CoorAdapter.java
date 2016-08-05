package com.example.administrator.myapplication.activity.coorinatorlayout;

import android.content.Context;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.BaseQuickAdapter;
import com.example.administrator.myapplication.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12 0012.
 */
public class CoorAdapter extends BaseQuickAdapter<String> {

    public CoorAdapter(List<String> data, Context cxt) {
        super(data, cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.item_coordinator;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item, int position) {
        helper.setText(R.id.textView,item);
    }
}

package com.example.administrator.myapplication.utils;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.example.administrator.myapplication.weidget.FingerLayout;
import com.example.administrator.myapplication.weidget.WaterCircleView;

/**
 * Created by Administrator on 2016/7/22 0022.
 */
public class ViewUtils {

    /**
     * 给activity的根布局外添加一个手指特效布局
     * 必须在activity的onCreate方法后执行，或者在子类的setContentView之后执行
     * @param activity
     */
    public static void addFingerlayout(Activity activity) {
        //获取的其实是个FrameLayout，也就是DectorView
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        //这个是setContentView时获取到XML中的根布局对象
        ViewGroup childRootView = (ViewGroup) rootView.getChildAt(0);

//        Log.i("rootView", childRootView.getClass().getSimpleName());
        //从父view移除
        rootView.removeView(childRootView);

        //添加到FingerLayout
        FingerLayout fingerLayout = new FingerLayout(activity);
        fingerLayout.addView(childRootView);

        //添加水波纹的view
        //本来这个view是要添加在FingerLayout布局中的，但是这样会导致波纹布局处于最下层
        //因为你先添加了波纹布局,所以要把波纹放在后面添加
        WaterCircleView circleView = new WaterCircleView(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        fingerLayout.addView(circleView, params);
        //把FingerLayout加回去
        rootView.addView(fingerLayout);
    }
}

package com.example.administrator.myapplication.activity.coorinatorlayout;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class MyHeadBehavior extends CoordinatorLayout.Behavior<View> {
    private final String TAG="MyHeadBehavior";
    public MyHeadBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    int mViewY=0;
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child,
                                       View directTargetChild, View target, int nestedScrollAxes) {

        mViewY=child.getMeasuredHeight();
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;//判断是否竖直滚动
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child,
                                  View target, int dx, int dy, int[] consumed) {

        child.setTranslationY(mViewY-=dy);
    }
}

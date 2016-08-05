package com.example.administrator.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.bugtags.library.Bugtags;
import com.example.administrator.myapplication.utils.ViewUtils;

public class BaseActivity extends FragmentActivity {

    private Handler mHandler;

    protected final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();

    }


    @Override
    protected void onResume() {
        super.onResume();
        addFingerLayout(this);
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    /**
     * 在安全的线程toast
     *
     * @param msg
     */
    protected void toast(final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //手指特效是否被添加过的标记位
    private boolean IsAdded=false;
    protected void addFingerLayout(Activity activity) {
        //因为放在了onStart中，所以设置一个标记，只添加一次，防止多次添加
        if (!IsAdded){
            ViewUtils.addFingerlayout(activity);
            IsAdded=true;
        }
    }

    /**
     * 请求不添加手指特效布局
     * 请在onCreate中调用
     */
    protected void requestNotAddFingerLayout(){
        IsAdded=true;
    }

}

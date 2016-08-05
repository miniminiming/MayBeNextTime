package com.example.administrator.myapplication.application;

import android.app.Application;

import com.bugtags.library.Bugtags;

/**
 * Created by Administrator on 2016/7/9 0009.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //在这里初始化
        Bugtags.start("ac7b72226c43b959d73e4a7ba72b5ff8", this, Bugtags.BTGInvocationEventBubble);
    }
}

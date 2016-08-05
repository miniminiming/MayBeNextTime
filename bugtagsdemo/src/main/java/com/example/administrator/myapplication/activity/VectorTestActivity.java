package com.example.administrator.myapplication.activity;

import android.annotation.TargetApi;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;

/**
 * Created by wfg on 2016/7/13 0013.
 * <p>
 * vector矢量drawable
 * 所以基本流程就是这样了
 * ImageView引用animator-vector
 * <p>
 * animator-vector负责把vector和animator结合起来形成一个drawable
 * <p>
 * 在vector里面，有group和pathdata属性，它们都有自己的名字，
 * 在amimator-vector中根据name来和animator结合
 * <p>
 * <p>
 * 注意点：
 * 使用之前需要定义出来一个坐标系
 * 分为两种动画，
 * 一种是按路径从头到尾走，
 * propertyName要被设置为trimPathEnd
 * <p>
 * 另外一种是由一种路径从头变换到另外一种路径
 * 在针对pathdata的animator中，propertyName要被设置为pathdata
 * 然后的值可以对应为线条的起始值和结束值
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class VectorTestActivity extends BaseActivity {
    private ImageView iv_vector_move;
    private ImageView iv_vector_heart;
    private ImageView iv_vector_flower;
    private ImageView iv_heart;
    private ImageView iv_mystar;
    private ImageView iv_myscrew;
    private ImageView iv_mystr_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);
        initView();
    }

    boolean change = false;

    boolean isTick2=false;
    private void initView() {
        iv_vector_move = (ImageView) findViewById(R.id.iv_vector_move);
        iv_vector_heart = (ImageView) findViewById(R.id.iv_vector_heart);

        iv_vector_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = iv_vector_move.getDrawable();
                // Animated drawables are supported on Lollipop and higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (drawable instanceof Animatable)
                        ((Animatable) drawable).start();
                    Log.i(TAG, "drawable=================top");
                } else {
                    iv_vector_move.startAnimation(AnimationUtils.loadAnimation(VectorTestActivity.this,
                            R.anim.appear_rotate));
                    Log.i(TAG, "Animation=================top");
                }
            }
        });

        iv_vector_heart.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Drawable drawable = iv_vector_heart.getDrawable();
                        // Animated drawables are supported on Lollipop and higher
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (drawable instanceof Animatable)
                                ((Animatable) drawable).start();
                            Log.i(TAG, "drawable=================bottom");
                        } else {
                            iv_vector_heart.startAnimation(AnimationUtils.loadAnimation(VectorTestActivity.this,
                                    R.anim.appear_rotate));
                            Log.i(TAG, "Animation=================bottom");
                        }

                    }

                }
        );
        iv_vector_flower = (ImageView) findViewById(R.id.iv_vector_flower);
        iv_vector_flower.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Drawable drawable = iv_vector_flower.getDrawable();
                        // Animated drawables are supported on Lollipop and higher
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (drawable instanceof Animatable)
                                ((Animatable) drawable).start();
                            Log.i(TAG, "drawable=================bottom");
                        } else {
                            iv_vector_flower.startAnimation(AnimationUtils.loadAnimation(VectorTestActivity.this,
                                    R.anim.appear_rotate));
                            Log.i(TAG, "Animation=================bottom");
                        }

                    }

                }
        );
        iv_heart = (ImageView) findViewById(R.id.iv_heart);
        iv_heart.setOnClickListener(
                new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {

                        //心形动画采用了两种动画样式，由空到满和由满到空
                        //此处就从资源文件中加载出来，然后设置给了ImgaeView
                        ImageView imageView = (ImageView) view;

                        AnimatedVectorDrawable tickToCross = (AnimatedVectorDrawable) getDrawable(R.drawable.heart_full_anim);
                        AnimatedVectorDrawable crossToTick = (AnimatedVectorDrawable) getDrawable(R.drawable.heart_empty_anim);

                        AnimatedVectorDrawable animDrawable = isTick2 ? crossToTick: tickToCross;
                        imageView.setImageDrawable(animDrawable);
                        if (animDrawable != null) {
                            animDrawable.start();
                        }
                        isTick2 = !isTick2;

//                        Drawable drawable = iv_heart.getDrawable();
//                        // Animated drawables are supported on Lollipop and higher
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            if (drawable instanceof Animatable)
//                                ((Animatable) drawable).start();
//                            Log.i(TAG, "drawable=================bottom");
//                        } else {
//                            iv_heart.startAnimation(AnimationUtils.loadAnimation(VectorTestActivity.this,
//                                    R.anim.appear_rotate));
//                            Log.i(TAG, "Animation=================bottom");
//                        }

                    }

                }
        );
        iv_mystar = (ImageView) findViewById(R.id.iv_mystar);
        iv_mystar.setOnClickListener(
                new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {
                        Drawable drawable = iv_mystar.getDrawable();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (drawable instanceof Animatable)
                                ((Animatable) drawable).start();
                        } else {
                            iv_mystar.startAnimation(AnimationUtils.loadAnimation(VectorTestActivity.this,
                                    R.anim.appear_rotate));
                        }

                    }

                }
        );
        iv_myscrew = (ImageView) findViewById(R.id.iv_myscrew);
        iv_myscrew.setOnClickListener(
                new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {
                        Drawable drawable = iv_myscrew.getDrawable();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (drawable instanceof Animatable)
                                ((Animatable) drawable).start();
                        } else {
                            iv_myscrew.startAnimation(AnimationUtils.loadAnimation(VectorTestActivity.this,
                                    R.anim.appear_rotate));
                        }

                    }

                }
        );
        iv_mystr_left = (ImageView) findViewById(R.id.iv_mystr_left);
        iv_mystr_left.setOnClickListener(
                new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {
                        Drawable drawable = iv_mystr_left.getDrawable();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (drawable instanceof Animatable)
                                ((Animatable) drawable).start();
                        } else {
                            iv_mystr_left.startAnimation(AnimationUtils.loadAnimation(VectorTestActivity.this,
                                    R.anim.appear_rotate));
                        }

                    }

                }
        );
    }

    VectorDrawable vd=new VectorDrawable();

}

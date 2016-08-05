package com.example.administrator.myapplication.weidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by miniminiming on 2016/6/2.
 */
public class ProgressView extends View {
    public ProgressView(Context context) {
        super(context);
        init(context);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // 背景画笔
    private Paint mBgPaint = new Paint();
    // 风扇画笔
    private Paint mWindPaint = new Paint();
    // 进度画笔
    private Paint mProgressPaint = new Paint();
    // 进度画笔
    private Paint mTextPaint = new Paint();

    //进度条左右长度
    private float mWidth = 500;

    //进度条上下高度
    private float mHeight = 100;

    //当前进度百分比,默认最大1.0f
    private float mCurrentPercent = 0;

    //进度内容与边框的缝隙
    private final float mGapDistance = 10f;

    //进度条总长度
    private float mTotalProgress = mWidth + mHeight - mGapDistance * 2;

    private Bitmap mLeafBitmap;

    private final int PREPARE = 101;

    private final int PROGRESSING = 102;
    private final int FANSCALING = 103;
    private final int TEXTSCALING = 104;
    private final int PROGRESSEND = 105;

    private int mCurrentState = PREPARE;

    /**
     * 风扇旋转角度
     */
    float mFanAngle = 0;
    /**
     * 风扇旋转速度
     */
    final float mFanRate = 5;

    float mScale = 9;//缩放角度

    // 2.初始化画笔
    private void init(Context context) {
        mBgPaint.setColor(Color.GRAY);       //设置画笔颜色
        mBgPaint.setStyle(Paint.Style.STROKE);  //设置画笔模式
        mBgPaint.setStrokeWidth(5f);         //设置画笔宽度
        mBgPaint.setAntiAlias(true);

        mWindPaint.setAntiAlias(true);

        Color.rgb(0xF5, 0XA4, 0X18);//树叶黄
        mProgressPaint.setColor(Color.rgb(0xF5, 0XA4, 0X18));
        mProgressPaint.setStyle(Paint.Style.FILL);  //设置画笔模式
        mProgressPaint.setAntiAlias(true);

        //图有点小，我把它放大一下
        mLeafBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaf);
        Matrix matrix = new Matrix();
        matrix.postScale(1.32f, 1.32f);
        mLeafBitmap = Bitmap.createBitmap(mLeafBitmap, 0, 0,
                mLeafBitmap.getWidth(), mLeafBitmap.getHeight(), matrix, false);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);       //设置画笔颜色
        mTextPaint.setStyle(Paint.Style.STROKE);  //设置画笔模式
        mTextPaint.setStrokeWidth(3f);         //设置画笔宽度
        mTextPaint.setTextSize(35);
        mTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        drawProgressBG(canvas);

        drawCurrentProgress(canvas);

        drawLeafs(canvas);

        drawFan(canvas);

        if (mCurrentState == TEXTSCALING) {
            drawText(canvas);
        }
        if (mCurrentState != PROGRESSEND) {
            postInvalidateDelayed(30);
        }
    }

    /**
     * 画背景
     *
     * @param canvas
     */
    private void drawProgressBG(Canvas canvas) {
        canvas.save();//先保存它的原始状态，然后一系列操作之后，再从原始状态开始做

        /** -------------------------画整体背景-------------------------  */
        canvas.translate(getWidth() / 2, getHeight() / 2);//平移到中心

        //先画个整体背景
        Path bgPath = new Path();
        RectF leftArcRECTF = new RectF(-mWidth / 2 - mHeight / 2, -mHeight / 2,
                -mWidth / 2 + mHeight / 2, mHeight / 2);
        bgPath.addArc(leftArcRECTF, 90, 180);//左边半个弧线

        bgPath.lineTo(mWidth / 2, -mHeight / 2);//上方直线


        bgPath.moveTo(mWidth / 2, mHeight / 2);

        bgPath.lineTo(-mWidth / 2, mHeight / 2);//底边线

        canvas.drawPath(bgPath, mBgPaint);
    }

    /**
     * 画当前进度
     *
     * @param canvas
     */
    private void drawCurrentProgress(Canvas canvas) {
        /** -------------------------画进度------------------------  */
        canvas.restore();//恢复初始状态
        canvas.save();
        //检验进度，不会让它超总数
        if (mCurrentPercent >= 1.0f) {
            mCurrentPercent = 1.0f;
            if (mCurrentState == PROGRESSING) {//从正在加载，到加载完成跳到电扇缩放状态
                mCurrentState = FANSCALING;
            }
        }
        //平移到进度条中心
        canvas.translate(getWidth() / 2, getHeight() / 2);//平移到中心
        Path progressPath = new Path();
        //与外边同心但是半径小一点
        RectF leftProgressArc = new RectF(-mWidth / 2 - mHeight / 2 + mGapDistance, -mHeight / 2 + mGapDistance,
                -mWidth / 2 + mHeight / 2 - mGapDistance, mHeight / 2 - mGapDistance);


        float degree;
        if (mCurrentPercent < mHeight / 2 / mTotalProgress) {

            //当进度条没过弧线的时候，是不需要画线的
            //此时要计算弧度，也就是左边圆弧的起始角度和扫过的角度
            double angle = Math.acos((mHeight / 2 - (mTotalProgress * mCurrentPercent)) / (mHeight / 2));
//            Log.i("test", "angle=========" + angle);
            degree = (float) (angle * 180 / Math.PI);
            progressPath.addArc(leftProgressArc, 180 - degree, degree * 2);//左边半个弧线

        } else {//大于一定值才会去有右边的柱形图
            degree = 90;
            progressPath.addArc(leftProgressArc, 180 - degree, degree * 2);//左边半个弧线

            progressPath.lineTo(-mWidth / 2 - mHeight / 2 + mTotalProgress * mCurrentPercent, -mHeight / 2 + mGapDistance);
            progressPath.lineTo(-mWidth / 2 - mHeight / 2 + mTotalProgress * mCurrentPercent, mHeight / 2 - mGapDistance);
            progressPath.lineTo(-mWidth / 2, mHeight / 2 - mGapDistance);


        }
        canvas.drawPath(progressPath, mProgressPaint);
//        mCurrentPercent += 0.001f;
    }

    private void drawFan(Canvas canvas) {
        /** -------------------------画可转动的风扇-------------------------  */
        mWindPaint.setColor(Color.GRAY);
        mWindPaint.setStyle(Paint.Style.STROKE);  //设置画笔模式
        mWindPaint.setStrokeWidth(5f);         //设置画笔宽度
        canvas.restore();//恢复上次的原始状态
        canvas.save();
        //平移画布到风扇中心处
        canvas.translate(getWidth() / 2 + mWidth / 2, getHeight() / 2);
        //画风扇外周
        canvas.drawCircle(0, 0, mHeight / 2, mWindPaint);
        //画风扇内背景
        mWindPaint.setColor(Color.GREEN);
        mWindPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, mHeight / 2 - mWindPaint.getStrokeWidth() / 2, mWindPaint);
        if (mCurrentState == TEXTSCALING) {
            return;
        }
        //画风扇的扇页边缘
        RectF upWindRectf = new RectF(-mHeight / 6 + mWindPaint.getStrokeWidth(), -mHeight / 2 + mWindPaint.getStrokeWidth(),
                mHeight / 6 - mWindPaint.getStrokeWidth(), -mHeight / 6 - mWindPaint.getStrokeWidth());

        RectF downWindRectf = new RectF(-mHeight / 6 + mWindPaint.getStrokeWidth(), -2 * mHeight / 3 + mWindPaint.getStrokeWidth(),
                mHeight / 6 - mWindPaint.getStrokeWidth(), -mWindPaint.getStrokeWidth());
        //画风扇的扇页内容
        RectF upWindRectfCon = new RectF(-mHeight / 6 + mWindPaint.getStrokeWidth() * 2, -mHeight / 2 + mWindPaint.getStrokeWidth() * 2,
                mHeight / 6 - mWindPaint.getStrokeWidth() * 2, -mHeight / 6 - mWindPaint.getStrokeWidth() * 2);

        RectF downWindRectfCon = new RectF(-mHeight / 6 + mWindPaint.getStrokeWidth() * 2, -2 * mHeight / 3 + mWindPaint.getStrokeWidth() * 2,
                mHeight / 6 - mWindPaint.getStrokeWidth() * 2, -mWindPaint.getStrokeWidth() * 2);

        Path windPath = new Path();
        windPath.moveTo(-mHeight / 6, -mHeight / 3);
        windPath.addArc(upWindRectf, 180, 180);//上半部分
        windPath.addArc(downWindRectf, 0, 180);//下半部分

        Path conPath = new Path();
        conPath.moveTo(-mHeight / 6, -mHeight / 3);
        conPath.addArc(upWindRectfCon, 180, 180);//上半部分
        conPath.addArc(downWindRectfCon, 0, 180);//下半部分
        for (int x = 0; x < 4; x++) {
            canvas.rotate(90 * x + mFanAngle);//转的是坐标系啊，与之前画的内容无关呢
            if (mCurrentState == FANSCALING) {
                canvas.scale(mScale / 10f, mScale / 10f);
            }
            mWindPaint.setColor(Color.WHITE);
            mWindPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(windPath, mWindPaint);
            //画扇叶内容
            mWindPaint.setColor(Color.YELLOW);
            mWindPaint.setStyle(Paint.Style.FILL);
            canvas.drawPath(conPath, mWindPaint);
            canvas.rotate(-90 * x - mFanAngle);
            if (mCurrentState == FANSCALING) {
                canvas.scale(10f / mScale, 10f / mScale);
                mScale -= 0.1f;
                //缩放结束,进入文字缩放状态
                if (mScale <= 1f) {
                    mScale = 1f;
                    mCurrentState = TEXTSCALING;
                    break;//不再进行循环
                }
            }
        }
        mFanAngle += mFanRate;
    }

    /**
     * 画可缩放的文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        canvas.restore();//恢复上次的原始状态
        canvas.save();
        //平移画布到风扇中心处
        canvas.translate(getWidth() / 2 + mWidth / 2, getHeight() / 2);
        canvas.scale(mScale / 10f, mScale / 10f);
        canvas.drawText("100%", -40, 12, mTextPaint);
        mScale += 0.2f;
        if (mScale >= 10) {
            mCurrentState = PROGRESSEND;
            if (mUpdateListener!=null){
                mUpdateListener.onComplete();
            }
        }
    }

    /**
     * 画树叶
     *
     * @param canvas
     */
    private void drawLeafs(Canvas canvas) {
        canvas.restore();//恢复上次的原始状态
        canvas.save();
        //平移画布到view中心处
        canvas.translate(getWidth() / 2, getHeight() / 2);
        //根据树叶集合中的叶子的当前状态来画出所有的树叶
        for (int x = 0; x < mLeftList.size(); x++) {
            Leaf leaf = mLeftList.get(x);
            Matrix matrix = leaf.matrix;
            //平移到图片中心
            matrix.postTranslate(-leaf.leftBitmap.getWidth() / 2,
                    -leaf.leftBitmap.getHeight() / 2);//加上这个可以让它从中心转
            matrix.postRotate(leaf.rotateAngle);

            matrix.postTranslate(leaf.drawX,
                    leaf.drawY);//平移到它应该在的位置

            canvas.drawBitmap(leaf.leftBitmap, matrix, null);

            matrix.reset();
            //刷新数据
            leaf.drawX -= leaf.XRate;
            //正弦曲线的振幅由括号外的系数决定，越大越大
            //波长由括号内的除数决定，越大越大
            //这个可以根据要求的效果随意设置
            leaf.drawY = (float) Math.sin((leaf.drawX) /
                    (mHeight / 2 - mGapDistance) - leaf.bornPointX)
                    * (mHeight / 2 - mGapDistance - leaf.leftBitmap.getHeight());
            leaf.rotateAngle += leaf.rotateRate;

            //树叶到达进度条最左端,或者进入进度条的时候从集合中移除
            if (leaf.drawX <= -mWidth / 2 - mHeight / 5 ||
                    leaf.drawX <= mCurrentPercent * mTotalProgress - mWidth / 2 - mHeight / 2) {
                mLeftList.remove(x);
                x--;
                //树叶融进进度，进度就增长
                mCurrentPercent += 0.02f;
            }
        }

    }

    class Leaf {
        public Bitmap leftBitmap;
        public float drawX;
        public float drawY;
        public float XRate;
        public float YRate;

        public float rotateAngle;
        public float rotateRate;

        public float bornPointX;//出生地点

        public Matrix matrix;
    }

    private List<Leaf> mLeftList = new ArrayList<>();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            updateProgress();
        }
        return true;
    }

    /**
     * 更新进度
     */
    public void updateProgress() {
        Leaf leaf = generateLeaf();
        mLeftList.add(leaf);
        mCurrentState = PROGRESSING;
    }

    public void resetProgress() {
        if (mCurrentState == PROGRESSEND) {
            invalidate();
        }
        mCurrentPercent = 0f;
        mLeftList.clear();
        mCurrentState = PREPARE;
    }

    /**
     * 生成树叶
     *
     * @return
     */
    private Leaf generateLeaf() {
        Random random = new Random();
        Leaf leaf = new Leaf();
        leaf.leftBitmap = mLeafBitmap;
        leaf.drawX = mWidth / 2;
        leaf.drawY = 0;
//        leaf.XRate = random.nextInt(3) + 1;
        leaf.XRate = 8;
        leaf.YRate = random.nextInt(3) + 1;

        leaf.rotateAngle = random.nextInt(360);
        leaf.rotateRate = random.nextInt(21) - 10;
//        leaf.rotateRate = 5;
        leaf.bornPointX = random.nextInt((int) (mHeight / 2 - mGapDistance));
        leaf.matrix=new Matrix();
        return leaf;
    }

    private IonUpdateListener mUpdateListener;

    public void setmUpdateListener(IonUpdateListener mUpdateListener) {
        this.mUpdateListener = mUpdateListener;
    }

    public interface IonUpdateListener {
        void onComplete();
    }
}

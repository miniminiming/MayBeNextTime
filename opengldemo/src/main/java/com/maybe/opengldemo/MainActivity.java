package com.maybe.opengldemo;

import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    private boolean supportsEs2;
    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkSupported();

        if (supportsEs2) {

            glSurfaceView = new GLSurfaceView(this);
            glSurfaceView.setRenderer(new GLRenderer());

            setContentView(glSurfaceView);
        } else {
            setContentView(R.layout.activity_main);
            Toast.makeText(this, "当前设备不支持OpenGL ES 2.0!", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * 检测当前手机是否支持OpenGl2.0
     */
    private void checkSupported() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        supportsEs2 = configurationInfo.reqGlEsVersion >= 0x2000;

        boolean isEmulator = Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86"));

        supportsEs2 = supportsEs2 || isEmulator;
    }


    public class GLRenderer implements GLSurfaceView.Renderer {

        //三维数据，因此这里每个坐标由三个数值表示
        // 此处表示一个三角形
        private float[] mTriangleArray = {
                0f, 1f, 0f,
                -1f, -1f, 0f,
                1f, -1f, 0f
        };

        //三角形各顶点颜色(三个顶点)
        private float[] mColor = new float[]{
                1, 1, 0, 1,
                0, 1, 1, 1,
                1, 0, 1, 1
        };

        private FloatBuffer mTriangleBuffer;
        private FloatBuffer mColorBuffer;

        public GLRenderer() {
            //把float[]对象转为java.nio.Buffer对象

            //先初始化buffer，数组的长度*4，因为一个float占4个字节
            ByteBuffer bb = ByteBuffer.allocateDirect(mTriangleArray.length * 4);
            //以本机字节顺序来修改此缓冲区的字节顺序
            bb.order(ByteOrder.nativeOrder());
            mTriangleBuffer = bb.asFloatBuffer();
            //将给定float[]数据从当前位置开始，依次写入此缓冲区
            mTriangleBuffer.put(mTriangleArray);
            //设置此缓冲区的位置。如果标记已定义并且大于新的位置，则要丢弃该标记。
            mTriangleBuffer.position(0);

            //颜色相关
            ByteBuffer bb2 = ByteBuffer.allocateDirect(mColor.length * 4);
            bb2.order(ByteOrder.nativeOrder());
            mColorBuffer = bb2.asFloatBuffer();
            mColorBuffer.put(mColor);
            mColorBuffer.position(0);

        }

        //这里的三个回调方法类似于SurfaceView的调用方法
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置清屏颜色
            //这里颜色值是RGBA，这里是白色，它们的取值都是0到1
            gl.glClearColor(1f, 1f, 1f, 1f);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            float ratio=((float)width)/((float)height);
            //设置绘制的矩形区域的大小
            // 设置OpenGL场景的大小,
            gl.glViewport(0, 0, width, height);

            //设置投影矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //重置投影矩阵，这个好像每次设置完都要重置，不然会对后面造成影响
            gl.glLoadIdentity();

            //设置视口大小
            gl.glFrustumf(-ratio,ratio,-1,1,1,10);

            //声明变换针对对象，此处针对的是模型（也就是我们的图形）
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();//重置


        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //真正的画东西
            //清除屏幕和深度缓存
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            //重置当前的模型观察矩阵
            gl.glLoadIdentity();

            //允许设置顶点  GL10.GL_VERTEX_ARRAY  顶点数组
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            //允许设置颜色  GL10.GL_COLOR_ARRAY  颜色数组
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

            //将三角形在z轴上移动
            gl.glTranslatef(0f, 0f, -2f);

            //设置三角形的坐标和颜色数据
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mTriangleBuffer);
            gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
            //绘制三角形
            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

            //取消颜色和顶点设置
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

            //绘制结束
            gl.glFinish();


        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (glSurfaceView != null) {
            glSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (glSurfaceView != null) {
            glSurfaceView.onResume();
        }
    }
}

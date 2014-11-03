package com.camera360.yuyidong.fragment;

import android.app.Activity;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;


public class MyActivity extends Activity implements SurfaceHolder.Callback {
    /**
     * SurfaceView
     */
    private SurfaceView mSurfaceView;
    /**
     * SurfaceHolder
     */
    private SurfaceHolder mSurfaceHolder;
    /**
     * Camera
     */
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_my);
        //找ID
        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);


    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        mCamera = new Camera();
        
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}

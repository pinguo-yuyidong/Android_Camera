package com.camera360.yuyidong.fragment;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.IOException;


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
    /**
     * 拍照按钮
     */
    private ImageView btn_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_my);
        //找ID
        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
        btn_photo = (ImageView) findViewById(R.id.btn_photo);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        MyButtonClickListener myButtonClickListener = new MyButtonClickListener();
        btn_photo.setOnClickListener(myButtonClickListener);

    }



    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mCamera = Camera.open();
        try {
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(mSurfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    /**
     * 点击拍照
     */
    class MyButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mCamera.takePicture(null,null,jpeg);
        }
    }

    /**
     * 长点击聚焦
     */
    class MyButtonLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            mCamera.autoFocus(autoFocus);

            return true;
        }
    }

    /**
     * 拍照
     */
    private Camera.PictureCallback jpeg = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            System.out.println("Camera.PictureCallback,,,,,Camera.PictureCallback");
            mCamera.startPreview();
        }
    };

    private Camera.AutoFocusCallback autoFocus = new Camera.AutoFocusCallback()
    {

        @Override
        public void onAutoFocus(boolean b, Camera camera) {
            System.out.println("Camera.AutoFocusCallback,,,,Camera.AutoFocusCallback");
        }
    };

}



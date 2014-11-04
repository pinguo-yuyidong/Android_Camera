package com.camera360.yuyidong.fragment.ui;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;



/**
 * Created by yuyidong on 14-11-4.
 */
public class ButtonImage extends ImageView implements View.OnTouchListener,Runnable {

    private Camera mCamera;
    /**
     * 记lu按xia时jian
     */
    private long downTime;
    /**
     * 按xia状态
     */
    private static final int MODE_DOWN=1;
    /**
     * 抬起状态
     */
    private static final int MODE_UP=2;
    /**
     * handler yong来到达时jian就去聚焦
     */
    private static final int PHOTO_OK=200;
    /**
     * chushihua mode 状态
     */
    private int mode=0;
    /**
     * 防止thread老循环跳不出来
     */
    private boolean photoFlag=false;
    /**
     * handler
     */
    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case PHOTO_OK:
                    photoFlag=false;
                    mCamera.autoFocus(new Camera.AutoFocusCallback() {
                        @Override
                        public void onAutoFocus(boolean b, Camera camera) {
                            Log.v("ButtonImage","autoFocusautoFocusautoFocusautoFocus");
                        }
                    });
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public ButtonImage(Context context) {
        super(context);
        this.setOnTouchListener(this);
    }

    public ButtonImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
    }

    public ButtonImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
                mode = MODE_DOWN;
                new Thread(this).start();
                break;
            case MotionEvent.ACTION_UP:
                mode = MODE_UP;
                mCamera.takePicture(null,null,new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {
                        Log.v("ButtonImage","takePicturetakePicturetakePicture");
                        mCamera.startPreview();
                    }
                });
                break;
        }
        return true;
    }

    @Override
    public void run() {
        while(true) {
            long nowTime = System.currentTimeMillis();
            if (nowTime - downTime >2000 && photoFlag == false && mode == MODE_DOWN) {
                setPhotoFlag(true);
                handler.sendEmptyMessage(PHOTO_OK);
                break;
            }else if(photoFlag == true || mode != MODE_DOWN)
            {
                break;
            }
        }
    }

    /**
     * yong同步去设置photoFlag
     * @param bool
     */
    private synchronized void setPhotoFlag(boolean bool)
    {
        this.photoFlag = bool;
    }

    public void setmCamera(Camera mCamera) {
        this.mCamera = mCamera;
    }
}

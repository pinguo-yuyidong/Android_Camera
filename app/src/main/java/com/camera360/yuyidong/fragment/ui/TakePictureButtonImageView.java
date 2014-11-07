package com.camera360.yuyidong.fragment.ui;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.camera360.yuyidong.fragment.R;
import com.camera360.yuyidong.fragment.util.CameraManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by yuyidong on 14-11-4.
 */
public class TakePictureButtonImageView extends ImageView implements View.OnTouchListener,Runnable {

    private Camera mCamera;
    /**
     * 记录按下的时候的时间戳
     */
    private long mDownTime;
    /**
     * 按下状态
     */
    private static final int MODE_DOWN=1;
    /**
     * 抬起状态
     */
    private static final int MODE_UP=2;
    /**
     * handler 子线程，到达2000，发送message
     */
    private static final int PHOTO_OK=200;
    /**
     * 初始化 mode 状态
     */
    private int mMode=0;
    /**
     * 单线程池,去运行Runnable
     */
    ExecutorService mSinglePool = Executors. newSingleThreadExecutor();
    /**
     * handler
     */
    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case PHOTO_OK:
                    CameraManager.cameraAutoFocus();//聚焦
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public TakePictureButtonImageView(Context context) {
        super(context);
        this.setOnTouchListener(this);
    }



    public TakePictureButtonImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
    }

    public TakePictureButtonImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mDownTime = System.currentTimeMillis();//按下时间
                mMode = MODE_DOWN;//现在为按下的状态
                mSinglePool.execute(this);
                this.setImageDrawable(getResources().getDrawable(R.drawable.btn_cam_pressed));//按压的button图片
                break;
            case MotionEvent.ACTION_UP:
                mMode = MODE_UP;//现在为抬起的状态
                CameraManager.cameraTakePicture();//拍照
                this.setImageDrawable(getResources().getDrawable(R.drawable.btn_cam));//normal的button图片
                break;
        }
        return true;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(mMode == MODE_DOWN){
            handler.sendEmptyMessage(PHOTO_OK);
        }

    }

}

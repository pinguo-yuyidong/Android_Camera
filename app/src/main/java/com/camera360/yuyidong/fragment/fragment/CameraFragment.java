package com.camera360.yuyidong.fragment.fragment;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.camera360.yuyidong.fragment.R;
import com.camera360.yuyidong.fragment.ui.ButtonImage;

import java.io.IOException;

public class CameraFragment extends Fragment implements SurfaceHolder.Callback{

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
     * imageView, but the way to use it is to click,like button
     */
    private ButtonImage btn_photo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_camera,null);
        //找ID
        mSurfaceView = (SurfaceView) v.findViewById(R.id.surface);
        btn_photo = (ButtonImage) v.findViewById(R.id.btn_photo);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        return v;

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
        //给按钮de OnTouch
        btn_photo.setmCamera(mCamera);

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mCamera.stopPreview();
        mCamera.release();
        mCamera=null;
    }


    /**
     * take picture
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

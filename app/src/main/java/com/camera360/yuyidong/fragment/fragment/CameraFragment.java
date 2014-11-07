package com.camera360.yuyidong.fragment.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.camera360.yuyidong.fragment.MyActivity;
import com.camera360.yuyidong.fragment.R;
import com.camera360.yuyidong.fragment.listener.SeekBarChangeListener;
import com.camera360.yuyidong.fragment.ui.ShowCameraSurfaceView;
import com.camera360.yuyidong.fragment.util.CameraManager;

public class CameraFragment extends Fragment implements SurfaceHolder.Callback,MyActivity.VolumeCallBack{

    /**
     * SurfaceView
     */
    private ShowCameraSurfaceView mSurfaceView;
    /**
     * SurfaceHolder
     */
    private SurfaceHolder mSurfaceHolder;
    /**
     * seekbar
     */
    private SeekBar mSeekBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_camera,null);
        //找ID
        mSurfaceView = (ShowCameraSurfaceView) v.findViewById(R.id.surface_show);
        mSeekBar = (SeekBar) v.findViewById(R.id.sb_zoom);
        //holder
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        //传参数
        mSurfaceView.setmSeekBar(mSeekBar);
        mSurfaceView.setmFragmentActivity(getActivity());
        //设置seekbar监听器
        mSeekBar.setOnSeekBarChangeListener(new SeekBarChangeListener(mSurfaceView));
        return v;

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        CameraManager.cameraOpen(surfaceHolder);

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        CameraManager.cameraPitureSize();
        CameraManager.cameraShow();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        CameraManager.cameraClose();
    }


    @Override
    public void getVolumeKey(int keyCode) {
        int zoomNum = CameraManager.getZoom();
        switch (keyCode) {
            // 音量减小
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if(zoomNum<=5 && zoomNum>=0)
                {
                    CameraManager.setZoom(0);
                    mSeekBar.setProgress(0);
                }
                else
                {
                    CameraManager.setZoom(zoomNum-5);
                    mSeekBar.setProgress(zoomNum-5);
                }
                break;
            // 音量增大
            case KeyEvent.KEYCODE_VOLUME_UP:
                if(zoomNum>=95 && zoomNum<=100)
                {
                    CameraManager.setZoom(100);
                    mSeekBar.setProgress(100);
                }
                else
                {
                    CameraManager.setZoom(zoomNum+5);
                    mSeekBar.setProgress(zoomNum+5);
                }
                break;
        }
    }
}

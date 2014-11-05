package com.camera360.yuyidong.fragment.listener;

import android.support.v4.app.Fragment;
import android.widget.SeekBar;

import com.camera360.yuyidong.fragment.util.CameraManager;

/**
 * Created by yuyidong on 14-11-5.
 */
public class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

    private StatusCallBack mStatusCallBack;

    public SeekBarChangeListener(StatusCallBack mStatusCallBack) {
        this.mStatusCallBack = mStatusCallBack;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        CameraManager.setZoom(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mStatusCallBack.getStatus(true);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mStatusCallBack.getStatus(false);
    }

    public interface StatusCallBack
    {
        public abstract void getStatus(boolean status);
    }
}

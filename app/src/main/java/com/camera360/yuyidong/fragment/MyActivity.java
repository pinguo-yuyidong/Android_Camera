package com.camera360.yuyidong.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.camera360.yuyidong.fragment.fragment.CameraFragment;
import com.camera360.yuyidong.fragment.fragment.PicturesFragment;
import com.camera360.yuyidong.fragment.fragment.ShowPictureFragment;
import com.camera360.yuyidong.fragment.util.DialogLoadingManager;


public class MyActivity extends ActionBarActivity {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment mCameraFragment;
    private VolumeCallBack mVolumeCallBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_my);
        //设置Fragment
        mCameraFragment = new CameraFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        mFragmentTransaction.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
        mFragmentTransaction.replace(R.id.frame_show,mCameraFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
        //设置音量键的回调
        mVolumeCallBack = (VolumeCallBack) mCameraFragment;
        //设置loading的dialog
        DialogLoadingManager.setActivity(MyActivity.this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if (mFragmentManager.getBackStackEntryCount() == 1) {
                finish();
            }else{
                mFragmentManager.popBackStack();
            }
        }
        mVolumeCallBack.getVolumeKey(keyCode);
        return true;
    }

    /**
     * 音量键的回调
     */
    public interface VolumeCallBack
    {
        public abstract void getVolumeKey(int keyCode);
    }
}



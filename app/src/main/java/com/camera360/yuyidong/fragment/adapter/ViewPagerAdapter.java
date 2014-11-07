package com.camera360.yuyidong.fragment.adapter;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.camera360.yuyidong.fragment.R;
import com.camera360.yuyidong.fragment.ui.ZoomPictureImageView;
import com.camera360.yuyidong.fragment.util.DialogLoadingManager;
import com.camera360.yuyidong.fragment.util.PicturesManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuyidong on 14-11-6.
 */
public class ViewPagerAdapter extends PagerAdapter {
    /**
     * 图片地址
     */
    private String[] mPaths;
    /**
     * 存放实例话了的Fragment
     */
    private Map<Integer, ZoomPictureImageView> mMap;
    /**
     * 为了打出inflate
     */
    private FragmentActivity mActivity;
    /**
     * 只有第一次加载的时候才显示loading界面
     */
    private boolean mFirstTimeLoadingFlag = true;

    /**
     * activity是为了inflate
     * @param activity activity是为了inflate
     */
    public ViewPagerAdapter(FragmentActivity activity) {
        mPaths = PicturesManager.getPaths();
        mMap = new HashMap<Integer, ZoomPictureImageView>();
        this.mActivity = activity;
    }

    @Override
    public int getCount() {
        return mPaths.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = mActivity.getLayoutInflater().inflate(R.layout.item_viewpager_picture,null);
        ZoomPictureImageView zoomPictureImageView = (ZoomPictureImageView) v.findViewById(R.id.img_zoomimage);
        ImageLoader.getInstance().displayImage("file:"+ File.separator+mPaths[position], zoomPictureImageView,new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String s, View view) {
                if(mFirstTimeLoadingFlag){
                    DialogLoadingManager.showDialogLoading();
                    mFirstTimeLoadingFlag = false;
                }
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                DialogLoadingManager.cancelDialogLoading();
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }
        });
        mMap.put(position, zoomPictureImageView);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(mMap.containsKey(position)){
            container.removeView(mMap.get(position));
            mMap.remove(position);
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

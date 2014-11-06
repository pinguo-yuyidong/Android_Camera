package com.camera360.yuyidong.fragment.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.camera360.yuyidong.fragment.R;
import com.camera360.yuyidong.fragment.ui.ZoomView;
import com.camera360.yuyidong.fragment.util.CameraManager;
import com.camera360.yuyidong.fragment.util.PicturesManager;
import com.nostra13.universalimageloader.core.ImageLoader;

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
    private Map<Integer,ZoomView> mMap;
    /**
     * 为了打出inflate
     */
    private FragmentActivity mActivity;

    /**
     * activity是为了inflate
     * @param activity activity是为了inflate
     */
    public ViewPagerAdapter(FragmentActivity activity) {
        mPaths = PicturesManager.getPaths();
        mMap = new HashMap<Integer, ZoomView>();
        this.mActivity = activity;
    }

    @Override
    public int getCount() {
        return mPaths.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = mActivity.getLayoutInflater().inflate(R.layout.frag_infopicture,null);
        ZoomView zoomView = (ZoomView) v.findViewById(R.id.img_zoomimage);
       // zoomView.setmBitmap(PicturesManager.getBitmapFromSD(CameraManager.getDir()+"20141106102834.jpg"));
        ImageLoader.getInstance().displayImage("file:"+ File.separator+mPaths[position],zoomView);
        mMap.put(position,zoomView);
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

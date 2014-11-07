package com.camera360.yuyidong.fragment.listener;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;

import com.camera360.yuyidong.fragment.R;
import com.camera360.yuyidong.fragment.fragment.ShowPictureFragment;

/**
 * Created by yuyidong on 14-11-6.
 */
public class GridViewItemClickListener implements AdapterView.OnItemClickListener {
    /**
     * 用来得到FM和FT的
     */
    private FragmentActivity mFragmentActivity;

    public GridViewItemClickListener(FragmentActivity mFragmentActivity) {
        this.mFragmentActivity = mFragmentActivity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        ShowPictureFragment showPicturesFragment = ShowPictureFragment.getInstance(position);
        FragmentManager fragmentManager = mFragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.add(R.id.frame_show,showPicturesFragment);//这样可以保存前一个fragment的状态
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

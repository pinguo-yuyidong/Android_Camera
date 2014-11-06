package com.camera360.yuyidong.fragment.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.camera360.yuyidong.fragment.R;
import com.camera360.yuyidong.fragment.adapter.ViewPagerAdapter;

/**
 * Created by yuyidong on 14-11-6.
 */
public class ShowPicturesFragment extends Fragment {
    /**
     * 显示哪一个
     */
    private int mPosition;
    /**
     * ViewPager
     */
    private ViewPager mViewPager;

    /**
     * 给一个让viewpager显示的位置
     * @param position 位置
     * @return 对象
     */
    public static ShowPicturesFragment getInstance(int position){
        ShowPicturesFragment showPicturesFragment = new ShowPicturesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        showPicturesFragment.setArguments(bundle);
        return showPicturesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            mPosition = bundle.getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_showpicture,null);
        mViewPager = (ViewPager) v.findViewById(R.id.viewpager_showpicture);
        mViewPager.setAdapter(new ViewPagerAdapter(getActivity()));
        mViewPager.setCurrentItem(mPosition);
        return v;
    }
}

package com.camera360.yuyidong.fragment.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by yuyidong on 14-11-7.
 */
public class ShowPictureViewPager extends ViewPager {
    public ShowPictureViewPager(Context context) {
        super(context);
    }

    public ShowPictureViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean bool = false;
        try {
            bool = super.onInterceptTouchEvent(ev);
        }catch (IllegalArgumentException e)
        {
            Log.v("ShowPictureViewPager","onInterceptTouchEvent");
            e.getStackTrace();
        }
        return bool;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean bool = false;
        try {
            bool = super.onTouchEvent(ev);
        }catch (IllegalArgumentException e)
        {
            Log.v("ShowPictureViewPager","onInterceptTouchEvent");
            e.getStackTrace();
        }
        return bool;
    }
}

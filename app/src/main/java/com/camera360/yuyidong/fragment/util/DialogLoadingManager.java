package com.camera360.yuyidong.fragment.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.camera360.yuyidong.fragment.R;

/**
 * Created by yuyidong on 14-11-7.
 */
public class DialogLoadingManager {
    //这个dialog只针对MyActivity
    //因为context是MyActivity的
    /**
     * 设置Activity,为了inflate
     */
    private static Activity mActivity;
    /**
     * 要显示的dialog
     */
    private static Dialog mDialog;

    /**
     * 设置Activity,为了inflate
     * @param activity 设置Activity,为了inflate
     */
    public static void setActivity(Activity activity){
        mActivity = activity;
        initDialogLoading();
    }

    /**
     * 初始化dialog
     */
    private static void initDialogLoading(){
        if(mActivity != null){
            View v = mActivity.getLayoutInflater().inflate(R.layout.dialog_loading,null);
            ImageView imageView = (ImageView) v.findViewById(R.id.img_loading);
            LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.liner_dialog);
            Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.dialog_loading);
            imageView.setAnimation(animation);
            mDialog = new Dialog(mActivity, R.style.loading_dialog);
            mDialog.setCancelable(false);
            mDialog.setContentView(linearLayout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT));
        }
    }
    /**
     * 显示dialog
     */
    public static void showDialogLoading(){
        mDialog.show();
    }

    /**
     * 关闭dailog
     */
    public static  void cancelDialogLoading(){
        mDialog.cancel();
    }






}

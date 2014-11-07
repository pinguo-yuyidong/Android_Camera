package com.camera360.yuyidong.fragment.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by yuyidong on 14-11-6.
 */
public class ZoomPictureImageView extends ImageView {
    /**
     * 初始状态
     */
    private static final int STATUS_INIT = 1;
    /**
     * 放大状态
     */
    private static final int STATUS_BIG = 2;
    /**
     * 缩小状态
     */
    private static final int STATUS_SMALL = 3;
    /**
     * 移动状态
     */
    private static  final int STATUS_MOVE = 4;
    /**
     * 状态
     */
    private  int mCurrentStatus = 0;
    /**
     * view宽度
     */
    private int mViewWidth;
    /**
     * view高度
     */
    private int mViewHeight;
    /**
     * 要使用的bitmap
     */
    private Bitmap mBitmap;
    /**
     * 两手指放下的距离
     */
    private double mFingersDownDis;
    /**
     * 滑动的X的距离
     */
    private float mMoveDistanceX;
    /**
     * 滑动的Y的距离
     */
    private float mMoveDistanceY;
    /**
     * 上次滑动后的X坐标
     */
    private float mOneFingerMoveX = -1;
    /**
     * 上次滑动后的Y坐标
     */
    private float mOneFingerMoveY = -1;
    /**
     * 当前的bitmap的宽度，随缩放变化
     */
    private float mCurrenBitmapWidth;
    /**
     * 当前的bitmap的高度，随缩放变化
     */
    private float mCurrenBitmapHeight;
    /**
     * 两个手指之间的中心X坐标
     */
    private float mCenterPointX;
    /**
     * 两个手指之间的中心Y坐标
     */
    private float mCenterPointY;
    /**
     * 记录缩放比例
     */
    private float mRatioTotal;
    /**
     * 记录滑动时候缩放比例
     */
    private float mRatioScale;
    /**
     * 初始的缩放比例
     */
    private float mRatioInit;
    /**
     * 矩阵初始化
     */
    private Matrix mMatrix = new Matrix();
    /**
     * 再矩阵上的X上的偏移量
     */
    private float mTranslateTotalX;
    /**
     * 再矩阵上的Y上的偏移量
     */
    private float mTranslateTotalY;

    public ZoomPictureImageView(Context context) {
        super(context);
        mCurrentStatus = STATUS_INIT;
    }

    public ZoomPictureImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCurrentStatus = STATUS_INIT;
    }

    public ZoomPictureImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCurrentStatus = STATUS_INIT;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed){
            //layout之后获得高度和宽度
            mViewWidth = getWidth();
            mViewHeight = getHeight();
        }
    }

    /**
     * 设置bitmap到imageview上
     * @param mBitmap 传入图片
     */
    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
        invalidate();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        this.mBitmap = bm;
        invalidate();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        BitmapDrawable bd = (BitmapDrawable) drawable;
        mBitmap = bd.getBitmap();
        invalidate();
    }

    /**
     * 计算两手指之间的距离
     * @param event 事件
     * @return 两手指之间距离
     */
    private double getDistanceBetweenTowFingers(MotionEvent event){
        float disX = Math.abs(event.getX(0) - event.getX(1));
        float disY= Math.abs(event.getY(0) - event.getX(1));
        return Math.sqrt(disX*disX + disY*disY);
    }

    /**
     * 计算两手指中间的坐标
     * @param event  事件
     */
    private void getCenterPointBetweenWeoFingers(MotionEvent event){
        float point0X = event.getX(0);
        float point0Y = event.getY(0);
        float point1X = event.getX(1);
        float point1Y = event.getY(1);
        mCenterPointX = (point0X + point1X)/2;
        mCenterPointY = (point0Y + point1Y)/2;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //touch事件分发处理
        if (mRatioInit == mRatioTotal) {
            getParent().requestDisallowInterceptTouchEvent(false);
        } else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() == 2) {
                    //计算两个手指之间的距离
                    mFingersDownDis = getDistanceBetweenTowFingers(event);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //单个手指,滑动照片
                if (event.getPointerCount() == 1) {
                    float moveX = event.getX();
                    float moveY = event.getY();
                    if (mOneFingerMoveX == -1 && mOneFingerMoveY == -1) {
                        //第一次move
                        mOneFingerMoveX = moveX;
                        mOneFingerMoveY = moveY;
                    }
                    mCurrentStatus = STATUS_MOVE;
                    mMoveDistanceX = moveX - mOneFingerMoveX;
                    mMoveDistanceY = moveY - mOneFingerMoveY;
                    //检查边界
                    if (mTranslateTotalX + mMoveDistanceX > 0) {
                        mMoveDistanceX = 0;
                    } else if (mViewWidth - (mTranslateTotalX + mMoveDistanceX) > mCurrenBitmapWidth) {
                        mMoveDistanceX = 0;
                    }
                    if (mTranslateTotalY + mMoveDistanceY > 0) {
                        mMoveDistanceY = 0;
                    } else if (mViewHeight - (mTranslateTotalY + mMoveDistanceY) > mCurrenBitmapHeight) {
                        mMoveDistanceY = 0;
                    }
                    //重新绘图
                    invalidate();
                    mOneFingerMoveX = moveX;
                    mOneFingerMoveY = moveY;
                }//两个手指，放大缩小
                else if (event.getPointerCount() == 2) {
                    getCenterPointBetweenWeoFingers(event);
                    double fingersDis = getDistanceBetweenTowFingers(event);//计算出手指滑动的距离
                    if (fingersDis > mFingersDownDis) {
                        mCurrentStatus = STATUS_BIG;//放大
                    } else {
                        mCurrentStatus = STATUS_SMALL;//缩小
                    }
                    //如果是放大状态，那么不能大于4倍，如果是缩小状态，不能小于原始状态
                    if ((mCurrentStatus == STATUS_BIG && mRatioTotal < 4 * mRatioInit) || (mCurrentStatus == STATUS_SMALL && mRatioTotal > mRatioInit)) {
                        mRatioScale = (float) (fingersDis / mFingersDownDis);//计算scaleRatio
                        mRatioTotal = mRatioTotal * mRatioScale;//更新totalRatio
                        if (mRatioTotal > mRatioInit * 4) {
                            mRatioTotal = mRatioInit * 4;//最大不超过4倍
                        } else if (mRatioTotal < mRatioInit) {
                            mRatioTotal = mRatioInit;//最小不超过原始
                        }
                        invalidate();//重新绘制
                        mFingersDownDis = fingersDis;//赋值给他的原因是当move放大之后又要缩小的话，，，，就起作用了
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerCount() == 2) {
                    //手指离开的时候将临时值还原
                    mOneFingerMoveX = -1;
                    mOneFingerMoveY = -1;
                }
                break;
            case MotionEvent.ACTION_UP:
                //手指离开的时候将临时值还原
                mOneFingerMoveX = -1;
                mOneFingerMoveY = -1;
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //重新绘制的时候的status判断
        switch (mCurrentStatus){
            case STATUS_BIG:
            case STATUS_SMALL:
                //zoom操作
                zoomView(canvas);
                break;
            case STATUS_INIT:
                //初始化操作
                initView(canvas);
                break;
            case STATUS_MOVE:
                //move操作
                moveView(canvas);
                break;
            default:
                canvas.drawBitmap(mBitmap, mMatrix, null);
                break;
        }
    }

    /**
     * 移动图片
     * @param canvas 画布，重新绘制bitmap用
     */
    private void moveView(Canvas canvas){
        if(mRatioTotal != mRatioInit) {
            mMatrix.reset();
            float translateX = mTranslateTotalX + mMoveDistanceX;
            float translateY = mTranslateTotalY + mMoveDistanceY;
            mMatrix.postScale(mRatioTotal, mRatioTotal);//先按总比例放大缩小,也就是缩放不变
            mMatrix.postTranslate(translateX, translateY);
            mTranslateTotalX = translateX;
            mTranslateTotalY = translateY;
            canvas.drawBitmap(mBitmap, mMatrix, null);
        }
    }


    /**
     * 对图片进行缩放
     * @param canvas 画布，重新绘制bitmap用
     */
    private void zoomView(Canvas canvas){
        mMatrix.reset();//复原矩阵
        mMatrix.postScale(mRatioTotal,mRatioTotal);//先按总比例放大缩小
        float scaleWidth = mBitmap.getWidth() * mRatioTotal;
        float scaleHeight = mBitmap.getHeight() * mRatioTotal;
        float translateX = 0f;
        float translateY = 0f;
        // 如果当前图片宽度小于view宽度，则按屏幕中心的横坐标进行水平缩放。否则按两指的中心点的横坐标进行水平缩放
        if(mCurrenBitmapWidth < mViewWidth){
            translateX = (mViewWidth - scaleWidth)/2f;
        }else{
            translateX = mTranslateTotalX * mRatioScale + mCenterPointX * (1 - mRatioScale);
            //边界检查
            if(translateX > 0){
                translateX = 0;
            }else if(mViewWidth - translateX > scaleWidth){
                translateX = mViewWidth - scaleWidth;
            }
        }
        // 如果当前图片高度小于View高度，则按屏幕中心的纵坐标进行垂直缩放。否则按两指的中心点的纵坐标进行垂直缩放
        if (mCurrenBitmapHeight < mViewHeight){
            translateY = (mViewHeight - scaleHeight)/2f;
        }else{
            translateY = mTranslateTotalY * mRatioScale + mCenterPointY * (1 - mRatioScale);
            //边界检查
            if(translateY > 0){
                translateY = 0;
            }else if(mViewHeight - translateY > scaleHeight){
                translateY = mViewHeight - scaleHeight;
            }
        }
        //然后进行对图片偏移的操作
        mMatrix.postTranslate(translateX, translateY);
        mTranslateTotalX = translateX;
        mTranslateTotalY = translateY;
        mCurrenBitmapWidth = scaleWidth;
        mCurrenBitmapHeight = scaleHeight;
        canvas.drawBitmap(mBitmap,mMatrix,null);
    }

    /**
     * 初始化View，居中显示
     * @param canvas 画布，重新绘制bitmap用
     */
    private void initView(Canvas canvas)
    {
        if(mBitmap != null) {
            mMatrix.reset();
            int bitmapWidth = mBitmap.getWidth();
            int bitmapHeight = mBitmap.getHeight();
            if ((bitmapWidth - mViewWidth) > (bitmapHeight - mViewHeight)) {//正负都没有关系
                //图片宽度与view宽度相减的值大于图片高度与view高度相减的值，说明宽度占满view的宽度，以宽度为准，等比例压缩
                float ratio = mViewWidth / (bitmapWidth * 1.0f);
                mMatrix.postScale(ratio, ratio);
                float translateY = (mViewHeight - (bitmapHeight * ratio)) / 2f;//要偏移的量
                mMatrix.postTranslate(0, translateY);//剧中
                mTranslateTotalY = translateY;
                mRatioTotal = mRatioInit = ratio;
            } else {
                //高度占满iew的高度，以高度为准，等比例压缩
                float ratio = mViewHeight / (bitmapHeight * 1.0f);;
                mMatrix.postScale(ratio, ratio);
                float translateX = (mViewWidth - bitmapWidth * ratio) / 2f;//要偏移的量
                mMatrix.postTranslate(translateX, 0);//剧中
                mTranslateTotalX = translateX;
                mRatioTotal = mRatioInit = ratio;
            }

            mCurrenBitmapWidth = bitmapWidth * mRatioInit;
            mCurrenBitmapHeight = bitmapWidth * mRatioInit;
            canvas.drawBitmap(mBitmap,mMatrix,null);
        }
    }
}

package com.camera360.yuyidong.fragment.adapter;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.camera360.yuyidong.fragment.R;
import com.camera360.yuyidong.fragment.util.CameraManager;
import com.camera360.yuyidong.fragment.util.PicturesManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yuyidong on 14-11-5.
 */
public class GridViewAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    /**
     * 目录下图片的路径
     */
    private String mPaths[];
    /**
     * inflater
     */
    private LayoutInflater mInflater;
/*    *//**
     *缓存
     *//*
    private LruCache<String, Bitmap> mLruCahce;*/
    /**
     * 显示的gridView
     */
    private GridView mGridView;
/*    *//**
     * 是不是第一次进入
     *//*
    private boolean mFirstTimeFlag = true;
    *//**
     * 记录显示出来的第一张图片的位置
     *//*
    private int mFirstPicture;
    *//**
     * 记录显示的有多少张照片
     *//*
    private int mVisiblePicture;
    *//**
     * 下载队列，用set是为了防止重复
     *//*
    private Set<LoadPictureAsync> mQueue;*/

    public GridViewAdapter(LayoutInflater inflater,GridView gridView) {
        //得到文件夹下的照片
        mPaths = PicturesManager.getPaths();

        this.mInflater = inflater;
        this.mGridView = gridView;

/*        mFirstTimeFlag=true;

        int maxMemory = (int) Runtime.getRuntime().maxMemory();// 获取应用程序最大可用内存
        int cacheSize = maxMemory / 8;//设置缓存大小
        mLruCahce = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };*/
        //加监听器
        mGridView.setOnScrollListener(this);
/*        //初始化队列
        mQueue = new HashSet<LoadPictureAsync>();*/
    }

    @Override
    public int getCount() {
        return mPaths.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        if(view == null)
        {
            v = (View) mInflater.inflate(R.layout.item_grid_picture,null);
        }
        else{
            v = (View) view;
        }
        ImageView imageView = (ImageView) v.findViewById(R.id.img_grid_item);
/*        v.setTag(mPaths[i]);//加Tag
        setImageView(mPaths[i],v);//设置图片*/
        ImageLoader.getInstance().displayImage("file:"+File.separator+mPaths[i],imageView);
        return v;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {

    }

/*    *//**
     * 图片还没用加载完的话就显示默认图片，加载完了就显示加载完的图片
     * @param path
     * @param imageView
     *//*
    private void setImageView(String path, ImageView imageView)
    {
        Bitmap bitmap =  getFromLruCache(path);
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);//从缓存中显示图片
        }else{
            imageView.setImageResource(R.drawable.ic_launcher);//显示默认图片
        }
    }



    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE){
            loadBitmap(mFirstPicture, mVisiblePicture);
        }else{
            cancelAllQueue();//正在滑动的话，取消掉所有的异步
        }
    }

    *//**
     * 取消掉所有的异步加载
     *//*
    private void cancelAllQueue()
    {
        for(LoadPictureAsync async : mQueue){
            async.cancel(false);
        }
    }

    *//**
     *  异步加载图片
     * @param first 第一张图的位置
     * @param visible 可以显示出多少张图
     *//*
    private void loadBitmap(int first, int visible)
    {
        for(int i=first;i<first+visible;i++){
            Bitmap bitmap = getFromLruCache(mPaths[i]);
            if(bitmap == null){//内存中每一天那个图片
                LoadPictureAsync async = new LoadPictureAsync();
                mQueue.add(async);//将异步加入队列
                async.execute(mPaths[i]);//执行异步去获取
            }else {//再缓存中那个图片的话
                ImageView image = (ImageView) mGridView.findViewWithTag(mPaths[i]);//通过tag找出来
                if(image != null && bitmap != null){//防止没有设置Tag出Bug
                    image.setImageBitmap(bitmap);
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int first, int visible, int total) {
        mFirstPicture = first;
        mVisiblePicture = visible;
        if(mFirstTimeFlag && visible > 0)
        {
            loadBitmap(mFirstPicture, mVisiblePicture);//刚进去的时候没有发生onScrollStateChanged的操作，所有要用这个来加载图片
            mFirstTimeFlag = false;
        }
    }


    *//**
     * 加到缓存中
     * @param path 地址
     * @param bitmap 图
     * @return
     *//*
    private void addToLruCache(String path, Bitmap bitmap)
    {
        if(mLruCahce.get(path) == null){
            mLruCahce.put(path,bitmap);
        }

    }

    *//**
     * 从缓存中获取图片
     * @param path 地址
     * @return 有就返回bitmap，没有就返回null
     *//*
    private Bitmap getFromLruCache(String path){
        return mLruCahce.get(path);
    }



    class LoadPictureAsync extends AsyncTask<String, Void, Bitmap> {
        *//**
         * 设置path
         *//*
        private String path;
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView imageView = (ImageView) mGridView.findViewWithTag(path);
            if(imageView != null && bitmap != null)
            {
                imageView.setImageBitmap(bitmap);
            }
            mQueue.remove(this);
            super.onPostExecute(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            path = strings[0];
            Bitmap bitmap = loadBitmapFromSD(path);
            if(bitmap != null){
                addToLruCache(path,bitmap);
            }
            return bitmap;
        }

        *//**
         * 从SD卡读取
         * @param path
         * @return
         *//*
        private Bitmap loadBitmapFromSD(String path) {

            File file = new File(path);
            InputStream is = null;
            Bitmap bitmap = null;
            try {
                is = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return bitmap;
        }
    }*/
}

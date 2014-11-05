package com.camera360.yuyidong.fragment.adapter;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yuyidong on 14-11-5.
 */
public class GridViewAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    /**
     * 目录下图片的路径
     */
    private String paths[];
    /**
     * inflater
     */
    private LayoutInflater inflater;
    /**
     *缓存
     */
    private LruCache<String, Bitmap> mLruCahce;
    /**
     * 显示的gridView
     */
    private GridView mGridView;
    /**
     * 是不是第一次进入
     */
    private boolean mFirstTimeFlag = true;
    /**
     * 记录显示出来的第一张图片的位置
     */
    private int mFirstPicture;

    public GridViewAdapter(LayoutInflater inflater,GridView gridView) {
        File file=new File(CameraManager.getDir());
        paths=file.list();

        this.inflater = inflater;
        this.mGridView = gridView;

        mFirstTimeFlag=true;

        int maxMemory = (int) Runtime.getRuntime().maxMemory();// 获取应用程序最大可用内存
        int cacheSize = maxMemory / 8;//设置缓存大小
        mLruCahce = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

        mGridView.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return 100;
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
        ImageView v;
        if(view == null)
        {
            v = (ImageView) inflater.inflate(R.layout.item_grid_picture,null);
        }
        else{
            v = (ImageView) view;
        }
        v.setImageResource(R.drawable.ic_launcher);
        //ImageLoader.getInstance().displayImage("file:"+File.separator+CameraManager.getDir()+paths[i],v);
        return v;
    }




    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE){
            //加载图片
        }else{
            //放弃所有图片加载
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int first, int visible, int total) {
        mFirstPicture = first;
        if(mFirstTimeFlag && visible > 0)
        {
            //加载图片
            mFirstTimeFlag = false;
        }
    }

    class LoadPictureAsync extends AsyncTask<String, Void, Bitmap> {
        /**
         * 设置path
         */
        private String path;
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {




            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            path = strings[0];




            return null;
        }

        /**
         */
        private Bitmap loadBitmap(String imageUrl) throws IOException {
            Bitmap bitmap = null;
            InputStream is = null;

            File file = new File(CameraManager.getDir()+path);
            is = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(is);

            is.close();

            return bitmap;
        }
    }
}

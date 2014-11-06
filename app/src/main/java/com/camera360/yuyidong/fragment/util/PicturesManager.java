package com.camera360.yuyidong.fragment.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yuyidong on 14-11-6.
 */
public class PicturesManager {

    /**
     * 得到文件名
     * @return  文件名
     */
    public static String[] getNames(){
        String dir = CameraManager.getDir();
        File file = new File(dir);
        String[] names = file.list();
        return names;
    }

    /**
     * 得到所有照片完整地址
     * @return 照片路径
     */
    public static String[] getPaths(){
        String[] names = getNames();
        String dir = CameraManager.getDir();
        String[] paths = new String[names.length];
        for (int i=0;i<names.length;i++){
            paths[i] = dir + names[i];
        }
        return paths;
    }



    /**
     * 通过路径获取bitmap
     * @param path 路径
     * @return 图片
     */
    public static Bitmap getBitmapFromSD(String path)
    {
        File file = null;
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            file = new File(path);
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


    /**
     * 获得单个path，用于imageloader的
     * @param position 第几个
     * @return 地址
     */
    public static String getImageLoaderPath(int position)
    {
        String[] paths = getPaths();
        return "file:"+File.separator+paths[position];
    }



}

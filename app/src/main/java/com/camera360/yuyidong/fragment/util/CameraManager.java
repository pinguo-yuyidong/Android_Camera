package com.camera360.yuyidong.fragment.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yuyidong on 14-11-5.
 */
public class CameraManager {
    /**
     * 相机camera
     */
    private static Camera sCamera;
    /**
     * 相机是否开启的判断标志
     */
    private static boolean sShowFlag=false;
    /**
     * 总共的Zoom大小
     */
    private static int sZoomTotal;
    /**
     * 保存图片的path
     */
    private static String sDir;
    /**
     * cache线程池
     */
    private static ExecutorService sCachePool = Executors.newCachedThreadPool();

    /**
     * 打开Camera
     * @param surfaceHolder surfaceHolder
     */
    public static void cameraOpen(SurfaceHolder surfaceHolder)
    {
        //判断是否打开
        if(!sShowFlag) {
            sShowFlag = true;
            try {
                sCamera = Camera.open();
                sCamera.setDisplayOrientation(90);
                sCamera.setPreviewDisplay(surfaceHolder);

            } catch (IOException e) {
                e.printStackTrace();
                sShowFlag = false;
            }
            sZoomTotal=getTotalZoom();
            getDirPath();//初始化保存图片的地址
        }
    }

    /**
     * 关闭Camera
     */
    public static void cameraClose()
    {
        //判断是否打开
        if(sShowFlag) {
            sCamera.stopPreview();
            sCamera.release();
            sCamera = null;
            sShowFlag = false;
        }
    }

    /**
     * 开始呈现Camera
     */
    public static void cameraShow()
    {
        //判断是否打开
        if(sShowFlag) {
            sCamera.startPreview();
        }
    }

    /**
     * 拍照
     */
    public static void cameraTakePicture()
    {
        try{
            if(sShowFlag) {
                sCamera.takePicture(null, null, jpeg);
            }
        }catch (RuntimeException e ){
            //防止点击完拍照后又连续点击拍照的BUG
            Log.v("CameraManager","cameraTakePicture");
            e.getStackTrace();
        }

    }

    /**
     * 对焦
     */
    public static void cameraAutoFocus()
    {
        if(sShowFlag) {
            sCamera.autoFocus(autoFocus);
        }
    }

    /**
     * 设置拍照的图片的大小
     */
    public static void cameraPitureSize()
    {
        Camera.Parameters param = sCamera.getParameters();
        param.setJpegQuality(100);
        List<Camera.Size> list = param.getSupportedPictureSizes();
        //Camera.Size size = list.get(list.size()-1);
        Camera.Size size = list.get(1);
        param.setPictureSize(size.width,size.height);
        sCamera.setParameters(param);
    }

    /**
     * 获得最大的Zoom的值
     * @return 返回Zoom最大值
     */
    public static int getTotalZoom()
    {
        Camera.Parameters param = sCamera.getParameters();
        return param.getMaxZoom();
    }

    /**
     * 设置Zoom
     * @param zoomNum seekbar的值
     */
    public static void setZoom(int zoomNum)
    {
        Camera.Parameters param = sCamera.getParameters();
        param.setZoom((zoomNum*sZoomTotal)/100);
        sCamera.setParameters(param);
    }

    /**
     * 得到当前的zoom的值
     * @return 当前zoom值
     */
    public static int getZoom()
    {
        if(sCamera != null) {
            Camera.Parameters param = sCamera.getParameters();
            return (param.getZoom() * 100) / sZoomTotal;
        }else{
            return 0;
        }
    }


    /**
     * 拍照回调
     */
    private static Camera.PictureCallback jpeg = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            final byte[] b = bytes;
            sCachePool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        savePicture(b);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            sCamera.startPreview();
        }
    };

    /**
     * 自动对焦回调
     */
    private static Camera.AutoFocusCallback autoFocus = new Camera.AutoFocusCallback()
    {

        @Override
        public void onAutoFocus(boolean b, Camera camera) {
            System.out.println("Camera.AutoFocusCallback,,,,Camera.AutoFocusCallback");
        }
    };

    /**
     * 获得SD目录
     */
    private static void getDirPath()
    {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            sDir = Environment.getExternalStorageDirectory().toString() + File.separator + "Camera360Test" + File.separator;
        }
    }

    /**
     * 获得目录地址
     * @return 返回目录地址
     */
    public static String getDir()
    {//TODO:这里需要处理，，地址没有的话，，，，稍后处理
        getDirPath();
        return sDir.toString();
    }

    /**
     * 保存照片
     * @param bytes 照片的byte数组
     */
    private synchronized static void savePicture(byte[] bytes) throws IOException {
        if(null != sDir)//有SD卡
        {
            //文件名的准备
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化时间
            String pictureName = format.format(date) + ".jpg";
            //目录是不是存在
            File dirfile = new File(sDir);
            if(!dirfile.exists())
            {
                dirfile.mkdir();
            }
            //目录+文件的File
            File file = new File( dirfile, pictureName);
            //转成bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            //转90度
            Matrix m = new Matrix();
            m.postRotate(90);
            Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            //输出
            FileOutputStream outputStream = new FileOutputStream(file); // 文件输出流
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);// 把数据写入文件
            outputStream.close(); // 关闭输出流
        }
        else
        {
            System.out.println("没有SD，保存不成功");
        }

    }



}

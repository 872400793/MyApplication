package com.example.wm.myapplication;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.widget.ImageView;

public class MainActivity9 extends AppCompatActivity
{
    private ImageView img_test;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        img_test = (ImageView) findViewById(R.id.img_test);

        // 获取单个activity对应的内存大小
        final int memorySize =
                ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).getMemoryClass();
        System.out.println("--------------------" + memorySize);
        // 单个activity内存的1/8作为图片缓存大小
        final int cacheSize = memorySize / 8 * 1024 * 1024;
        // 创建图片缓存
        LruCache<String, Bitmap> cache = new LruCache<>(cacheSize);
        // 存入缓存和从缓存中取出图片可以类比于一般map的get和set方法
    }

    /**
     * 获取重新采样后的位图
     */
    private Bitmap getNewBitmap(Resources res, int resId, int reqWidth, int reqHeight)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = getInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 计算位图的采样比例
     */
    private int getInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        while (height / inSampleSize > reqHeight || width / inSampleSize > reqWidth)
        {
            // 如果宽高的任意一方的缩放比例没有达到要求，都继续增大缩放比例
            // inSampleSize应该为2的n次幂，如果给inSampleSize设置的数字不是2的n次幂，那么系统会就近取值
            inSampleSize *= 2;
        }
        return inSampleSize;
    }
}

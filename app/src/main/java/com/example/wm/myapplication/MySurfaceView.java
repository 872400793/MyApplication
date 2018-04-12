package com.example.wm.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by wm on 2017/12/10.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
    private SurfaceHolder holder;
    private Thread thread;

    public MySurfaceView(Context context)
    {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);

        thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Canvas canvas = null;
                for (int i = 0; i < 100; i++)
                {
                    try
                    {
                        // 多线程，使用同步块处理
                        synchronized (holder)
                        {
                            // 锁定画布
                            canvas = holder.lockCanvas();
                            canvas.drawColor(Color.WHITE);
                            Paint p = new Paint();
                            p.setTextSize(50);
                            p.setColor(Color.RED);
                            p.setStyle(Paint.Style.FILL);

                            canvas.drawRect(0, 0, 200, 200, p);
                            canvas.drawText("test" + i, 100, 250, p);

                            Thread.sleep(100);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        // 结束锁定画布，并提交改变
                        if(null != canvas)
                        {
                            holder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
    }
}

package com.example.wm.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * Created by wm on 2017/12/10.
 */
public class TestDraw extends View
{
    public TestDraw(Context context)
    {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);

        // 画笔
        Paint paint = new Paint();
        // 去锯齿
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        // 空心
        paint.setStyle(Paint.Style.STROKE);

        // 画线
        canvas.drawLine(0, 0, 300, 300, paint);

        // 三角
        Path path = new Path();
        path.moveTo(0, 100);
        path.lineTo(300, 100);
        path.lineTo(150, 300);
        path.close();
        canvas.drawPath(path, paint);
    }
}

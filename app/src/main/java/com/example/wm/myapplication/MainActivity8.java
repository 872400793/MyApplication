package com.example.wm.myapplication;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;

import java.io.IOException;

public class MainActivity8 extends AppCompatActivity implements SurfaceHolder.Callback
{
    private MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(this));
    }

    /**
     * 点击图片
     *
     * @param view
     */
    public void onClick_animator(View view)
    {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.object_anim);
        // 设置目标对象
        animator.setTarget(view);
        animator.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        player = new MediaPlayer();
        try
        {
            player.setDataSource(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)+"/a.dep");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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

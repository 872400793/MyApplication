package com.example.wm.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

public class MainActivity10 extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);

        Cursor cursor = getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (null != cursor)
        {
            while (cursor.moveToNext())
            {
                // 音频路径
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                // 音频名称
                String displayName = cursor.getString(
                        cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                // 音频时长
                String duration =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            }
        }

        String path = "";
        String movieName = "";

        MediaRecorder recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(path);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try
        {
            recorder.prepare();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        recorder.start();

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path, movieName)));
        // 填0视频质量最差，填1视频质量最好
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, 100);
    }

    public void onClick_start(View view)
    {
        Intent intent = new Intent();
        intent.setPackage(getPackageName());
        intent.setAction(MusicService.MUSIC_ACTION_START);
        startService(intent);
    }

    public void onClick_stop(View view)
    {
        Intent intent = new Intent();
        intent.setPackage(getPackageName());
        intent.setAction(MusicService.MUSIC_ACTION_STOP);
        startService(intent);
    }

    @Override
    public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        switch (level)
        {
            case TRIM_MEMORY_UI_HIDDEN:
                // 需要执行内存释放操作
                // ...
                SparseArray<String> array = new SparseArray<>();
                array.put(1, "test");
                break;
        }
    }

    private int a = 1;

    public void test()
    {
        int b = this.a;

        System.out.println(b);
    }
}

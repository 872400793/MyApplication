package com.example.wm.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

/**
 * 当正在播放的音频成为噪音时，停止播放音乐
 */
public class MusicReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        //通知音乐停止播放
        if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(action))
        {
            Intent intent2 = new Intent();
            intent2.setPackage(context.getPackageName());
            intent2.setAction(MusicService.MUSIC_ACTION_STOP);
            context.startService(intent2);
        }
    }
}

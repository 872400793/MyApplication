package com.example.wm.myapplication;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v7.app.NotificationCompat;

import java.io.IOException;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, AudioManager.OnAudioFocusChangeListener
{
    // 开始播放
    public static final String MUSIC_ACTION_START = "com.wm.music.start";
    // 停止播放
    public static final String MUSIC_ACTION_STOP = "com.wm.music.stop";

    private MediaPlayer player;

    private WifiManager.WifiLock wifiLock;

    @Override
    public void onCreate()
    {
        super.onCreate();

        initMusic();
    }

    /**
     * 初始化音频
     */
    private void initMusic()
    {
        // 保持WIFI不被休眠
        wifiLock = ((WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE))
                .createWifiLock(WifiManager.WIFI_MODE_FULL, "myLock");
        wifiLock.acquire();

        player = new MediaPlayer();
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setOnPreparedListener(this);

        sendNotification();
    }

    /**
     * 发送前台通知
     */
    private void sendNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        // 消息图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        // 消息标题
        builder.setContentTitle("我的音乐播放器");
        // 消息内容
        builder.setContentText("音乐正在播放中");
        // 没下拉时显示的消息提示
        builder.setTicker("我的音乐播放器");
        // 点击进入页面
        // 参数：上下文，请求编码， intent意图，创建PendingIntent的方式
        // PendingIntent.FLAG_CANCEL_CURRENT 取消当前的 pendingIntent,创建新的
        // PendingIntent.FLAG_NO_CREATE 如果有就使用，没有不创建
        // PendingIntent.FLAG_ONE_SHOT 只使用一次
        // PendingIntent.FLAG_UPDATE_CURRENT 如果有，更新 pendingIntent
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this, 100, new Intent(this, MainActivity10.class),
                        PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();

        startForeground(0x2, notification);

        // 我自己测速时，不使用notify也可以弹出前台消息，前台消息最好添加关闭按钮
        // NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // manager.notify(0x1, notification);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        stopForeground(true);

        if (null != player)
        {
            player.release();
        }
        if (null != wifiLock)
        {
            wifiLock.release();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        String action = intent.getAction();
        // 开始播放
        if (MUSIC_ACTION_START.equals(action))
        {
            try
            {
                // 获取音频焦点
                AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
                am.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                player.reset();
                player.setDataSource("http://sc1.111ttt.cn/2017/1/05/09/298092036084.mp3");
                player.prepareAsync();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        // 停止播放
        else if (MUSIC_ACTION_STOP.equals(action))
        {
            if (player.isPlaying())
            {
                player.stop();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onPrepared(MediaPlayer mp)
    {
        mp.start();
    }

    @Override
    public void onAudioFocusChange(int focusChange)
    {
        switch (focusChange)
        {
            // 已获取音频焦点
            case AudioManager.AUDIOFOCUS_GAIN:
                break;
            // 长时间失去焦点
            case AudioManager.AUDIOFOCUS_LOSS:
                if (player.isPlaying())
                {
                    player.stop();
                }
                break;
        }
    }
}

package com.example.wm.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver
{
    public SMSReceiver()
    {
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        abortBroadcast();
        Log.e("wm","-------------");

        // 通过pdus获得接收到的所有短信消息，获取短信内容
        Object[] pduList = (Object[]) intent.getExtras().get("pdus");
        // 构建短信对象数组
        SmsMessage[] msgList = new SmsMessage[pduList.length];

        for (int i = 0; i < pduList.length; i++)
        {
            // 获取单条短信内容，以pdu格式存，并生成短信对象
            msgList[i] = SmsMessage.createFromPdu((byte[]) pduList[i]);
        }

        for (int i = 0; i < msgList.length; i++)
        {
            // 获得短信发送者
            String sender = msgList[i].getDisplayOriginatingAddress();
            // 获得短信内容
            String content = msgList[i].getMessageBody();
        }
    }
}

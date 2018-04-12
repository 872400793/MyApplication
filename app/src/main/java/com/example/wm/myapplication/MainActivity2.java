package com.example.wm.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ArrayList<Uri> list = new ArrayList<>();
        Uri uri = Uri.parse("手机里的图片路径");
        list.add(uri);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, list);
        //        startActivity(intent);
        // 强制使用选择器
        startActivity(Intent.createChooser(intent, "标题"));

        receiveContent();
    }

    private void receiveContent()
    {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && null != type)
        {
            if ("text/plain".equals(type))
            {
                String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            }
        }

        Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
            }
        };

        // 发送一条空消息（简写）
        handler.sendEmptyMessage(100);

        // 发送一条空消息（完整写法）
        Message message = handler.obtainMessage();
        message.what = 100;
        handler.sendMessage(message);

        // 在指定时间后发送空消息
        handler.sendEmptyMessageAtTime(100, System.currentTimeMillis() + 3000);

        // 延迟发送空消息
        handler.sendEmptyMessageDelayed(100, 3000);

        try
        {
            URL url = new URL("http://cdnq.duitang.com/uploads/item/201409/18/20140918231127_LZQjr.png");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            // 获取文件大小
            int size = conn.getContentLength();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private MyHandler handler = new MyHandler(this);

    private static class MyHandler extends Handler
    {
        WeakReference<MainActivity2> weakReference;
        public MyHandler(MainActivity2 activity)
        {
            weakReference = new WeakReference<MainActivity2>(activity);
        }
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            MainActivity2 activity = weakReference.get();
            if(null != activity)
            {

            }
        }
    }
}

package com.example.wm.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity12 extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bmob.initialize(this, "44dc377e76c814f064fdfe59fd7666f8");
        setContentView(R.layout.activity_main12);
    }

    public void onClick_add(View view)
    {
        Person person = new Person();
        person.setName("名字1");
        person.setAddress("地址1");
        person.save(new SaveListener<String>()
        {
            @Override
            public void done(String objectId, BmobException e)
            {
                if (e == null)
                {
                    Toast.makeText(MainActivity12.this, "添加数据成功，返回objectId为：" + objectId,
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity12.this, "创建数据失败：" + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

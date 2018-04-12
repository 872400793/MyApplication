package com.example.wm.myapplication;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity5 extends AppCompatActivity
{

    private DataBaseAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        dbAdapter = new DataBaseAdapter(this);
    }

    /**
     * 添加
     *
     * @param view
     */
    public void onClick_add(View view)
    {
        Dog dog = new Dog("doge", 5);
        dbAdapter.add(dog);
    }

    /**
     * 修改
     *
     * @param view
     */
    public void onClick_update(View view)
    {
        Dog dog = new Dog(1, "wangWang", 5);
        dbAdapter.update(dog);
    }

    /**
     * 删除
     *
     * @param view
     */
    public void onClick_delete(View view)
    {
        dbAdapter.delete(1);
    }

    /**
     * 根据id查询
     *
     * @param view
     */
    public void onClick_searchById(View view)
    {
        Dog dog = dbAdapter.searchById(1);
        System.out.println(dog);
    }

    /**
     * 查询所有
     *
     * @param view
     */
    public void onClick_searchAll(View view)
    {
        List<Dog> dogs = dbAdapter.searchAll();

        int size = dogs.size();
        for (int i = 0; i < size; i++)
        {
            System.out.println(dogs.get(i));
        }
    }

    public void onClick_searchMobile(View view)
    {
        ContentResolver resolver = getContentResolver();
        // 获取所有的联系人id
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID}, null, null, null);

        String id = null;
        String mimeType = null;
        while (cursor.moveToNext())
        {
            id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            Cursor contractInfoCursor = resolver.query(ContactsContract.Data.CONTENT_URI,
                    new String[]{ContactsContract.Data.MIMETYPE, ContactsContract.Data.DATA1},
                    ContactsContract.Data.CONTACT_ID + "=?", new String[]{id}, null);

            while (contractInfoCursor.moveToNext())
            {
                mimeType = contractInfoCursor.getString(contractInfoCursor.getColumnIndex(
                        ContactsContract.Data.MIMETYPE));
                String value = contractInfoCursor.getString(contractInfoCursor.getColumnIndex(
                        ContactsContract.Data.DATA1));

                if(mimeType.contains("/name"))
                {
                    System.out.println("姓名="+value);
                }
                else if(mimeType.contains("/im"))
                {
                    System.out.println("聊天帐号="+value);
                }
                else if(mimeType.contains("/email"))
                {
                    System.out.println("邮箱="+value);
                }
                else if(mimeType.contains("/phone"))
                {
                    System.out.println("电话="+value);
                }
                else if(mimeType.contains("/postal"))
                {
                    System.out.println("邮编="+value);
                }
                else if(mimeType.contains("/group"))
                {
                    System.out.println("组="+value);
                }
            }
            contractInfoCursor.close();
        }
        cursor.close();
    }
}

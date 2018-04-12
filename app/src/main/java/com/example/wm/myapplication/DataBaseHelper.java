package com.example.wm.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteDatabase助手类
 */
public class DataBaseHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "pet.db";
    private static final int version = 1;
    private static final String CREATE_TABLE_DOG =
            "CREATE TABLE dog(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER);";
    private static final String DROP_TABLE_DOG = "DROP TABLE IF EXISTS dog;";

    public DataBaseHelper(Context context)
    {
        // 第三个参数是游标，使用null即使用系统默认游标
        super(context, DB_NAME, null, version);
    }

    /**
     * 如果数据库表不存在，会调用此方法
     *
     * @param db SQLiteDatabase 用于操作数据库的工具类
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_DOG);
    }

    /**
     * 版本升级
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DROP_TABLE_DOG);
        db.execSQL(CREATE_TABLE_DOG);
    }
}

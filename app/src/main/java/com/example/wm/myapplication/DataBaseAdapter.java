package com.example.wm.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLiteDatabase适配器
 */
public class DataBaseAdapter
{
    private DataBaseHelper dbHelper;

    public DataBaseAdapter(Context context)
    {
        dbHelper = new DataBaseHelper(context);
    }

    /**
     * 新增数据
     */
    public void add(Dog dog)
    {
        // 获取操作数据库的工具类
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PetMetaData.PetTable.NAME, dog.getName());
        values.put(PetMetaData.PetTable.AGE, dog.getAge());

        // 参数（表名，可以为空（null）的列名，ContentValues）
        db.insert(PetMetaData.PetTable.TABLE_NAME, null, values);
        db.close();
    }

    /**
     * 更新数据
     */
    public void update(Dog dog)
    {
        // 获取操作数据库的工具类
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PetMetaData.PetTable.NAME, dog.getName());
        values.put(PetMetaData.PetTable.AGE, dog.getAge());

        String whereClause = PetMetaData.PetTable._ID + "=?";

        String[] whereArgs = {String.valueOf(dog.getId())};

        // 参数（表名，ContentValues，条件，条件的值）
        db.update(PetMetaData.PetTable.TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }

    /**
     * 删除数据
     */
    public void delete(int id)
    {
        // 获取操作数据库的工具类
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String whereClause = PetMetaData.PetTable._ID + "=?";

        String[] whereArgs = {String.valueOf(id)};

        // 参数（表名，条件，条件的值）
        db.delete(PetMetaData.PetTable.TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    /**
     * 根据id查询数据
     */
    public Dog searchById(int id)
    {
        // 获取操作数据库的工具类
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns =
                {PetMetaData.PetTable._ID, PetMetaData.PetTable.NAME, PetMetaData.PetTable.AGE};

        String selection = PetMetaData.PetTable._ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        // 参数（是否去除，表名，要查询的列，查询条件，查询条件的值，分组条件，分组条件的值，排序，分页条件）
        Cursor cursor =
                db.query(true, PetMetaData.PetTable.TABLE_NAME, columns, selection, selectionArgs,
                        null, null, null, null);
        Dog dog = new Dog();
        if (cursor.moveToNext())
        {
            dog.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PetMetaData.PetTable._ID)));
            dog.setName(cursor.getString(cursor.getColumnIndexOrThrow(PetMetaData.PetTable.NAME)));
            dog.setAge(cursor.getInt(cursor.getColumnIndexOrThrow(PetMetaData.PetTable.AGE)));
        }
        cursor.close();
        db.close();

        return dog;
    }

    /**
     * 查询所有数据
     */
    public List<Dog> searchAll()
    {
        // 获取操作数据库的工具类
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns =
                {PetMetaData.PetTable._ID, PetMetaData.PetTable.NAME, PetMetaData.PetTable.AGE};

        // 参数（是否去除，表名，要查询的列，查询条件，查询条件的值，分组条件，分组条件的值，排序，分页条件）
        Cursor cursor =
                db.query(true, PetMetaData.PetTable.TABLE_NAME, columns, null, null, null, null,
                        null, null);
        List<Dog> dogs = new ArrayList<>();
        Dog dog = null;
        while (cursor.moveToNext())
        {
            dog = new Dog();
            dog.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PetMetaData.PetTable._ID)));
            dog.setName(cursor.getString(cursor.getColumnIndexOrThrow(PetMetaData.PetTable.NAME)));
            dog.setAge(cursor.getInt(cursor.getColumnIndexOrThrow(PetMetaData.PetTable.AGE)));
            dogs.add(dog);
        }
        cursor.close();
        db.close();

        return dogs;
    }
}
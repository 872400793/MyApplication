package com.example.wm.myapplication;

import android.provider.BaseColumns;

/**
 * 定义数据库的元数据
 */
public final class PetMetaData
{
    private PetMetaData()
    {
    }

    /**
     * Dog表的定义
     */
    public static abstract class PetTable implements BaseColumns
    {
        public static final String TABLE_NAME = "dog";
        public static final String NAME = "name";
        public static final String AGE = "age";
    }
}

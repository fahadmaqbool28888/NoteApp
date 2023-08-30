package com.jip.noteapp.Database;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter
{

    @TypeConverter
    public static Long totimestamp(Date date)
    {
        return date==null? null : date.getTime();
    }


    @TypeConverter
    public static Date toDate(long timestamp)
    {
        return timestamp==0? null : new Date(timestamp);
    }
}

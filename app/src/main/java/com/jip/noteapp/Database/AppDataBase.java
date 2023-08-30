package com.jip.noteapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jip.noteapp.Interface.NoteDAO;
import com.jip.noteapp.model.NoteEntity;

@Database(entities = {NoteEntity.class},version = 1,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDataBase extends RoomDatabase
{
    public static final String DATABASE_NAME="ROOM_DATABASE";
    public static volatile AppDataBase instance;
    public static final Object lock=new Object();

    public abstract NoteDAO noteDAO();

    public static AppDataBase getInstance(Context context)
    {
        if (instance==null)
        {
            synchronized (lock)
            {
                if (instance==null)
                {
                    instance= Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class,DATABASE_NAME).build();

                }
            }
        }

        return instance;
    }
}

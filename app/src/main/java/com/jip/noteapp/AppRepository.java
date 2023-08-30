package com.jip.noteapp;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.jip.noteapp.Database.AppDataBase;
import com.jip.noteapp.model.NoteEntity;
import com.jip.noteapp.utils.SampleDataProvider;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository
{
    public LiveData<List<NoteEntity>> noteEntities;


    private AppDataBase mdatabase;
    private static  AppRepository ourInstance ;
    Executor mexExecutor= Executors.newSingleThreadExecutor();
    public static AppRepository getInstance(Context context)
    {

        return ourInstance=new AppRepository(context);
    }

    public AppRepository(Context context)
    {
        mdatabase=AppDataBase.getInstance(context);
        noteEntities= getNoteEntities();

    }

    public void addsampleData()
    {
        mexExecutor.execute(new Runnable() {
            @Override
            public void run() {

                mdatabase.noteDAO().insertAll(SampleDataProvider.getentitylist());
            }
        });
    }
    private LiveData<List<NoteEntity>> getNoteEntities()
    {
        return mdatabase.noteDAO().selectNotes();
    }

    public void deleteAllNotes()
    {
        mexExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int i=mdatabase.noteDAO().deleteNotes();


            }
        });
    }

    public NoteEntity fetchNote(int id)
    {

        return mdatabase.noteDAO().selectNote_BYID(id);
    }

    public void setnoteandexist(NoteEntity noteEntity)
    {
        mexExecutor.execute(new Runnable() {
            @Override
            public void run()
            {
                mdatabase.noteDAO().insert(noteEntity);

            }
        });
    }
}

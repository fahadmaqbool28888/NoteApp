package com.jip.noteapp.ViewModelPackage;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.jip.noteapp.AppRepository;
import com.jip.noteapp.model.NoteEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditactivityViewModel extends AndroidViewModel {
    AppRepository appRepository;
    Executor editExecutor= Executors.newSingleThreadExecutor();
  public   MutableLiveData<NoteEntity> entityMutableLiveData=new MutableLiveData<>();

    public EditactivityViewModel(@NonNull Application application) {
        super(application);
        appRepository=AppRepository.getInstance(application.getApplicationContext());
    }

    public void fetechNote(int id)
    {
        editExecutor.execute(new Runnable() {
            @Override
            public void run() {

                NoteEntity noteEntity=appRepository.fetchNote(id);
                entityMutableLiveData.postValue(noteEntity);
            }
        });

    }

    public void saveNoteAndExist(String str)
    {

        NoteEntity noteEntity=entityMutableLiveData.getValue();
        if (noteEntity==null)
        {
            if (TextUtils.isEmpty(str))
            {
                return;
            }
            else {
                noteEntity=new NoteEntity(new Date(),str.trim());
            }


        }
        else {
            noteEntity.setText(str.trim());
            noteEntity.setDate(new Date());

        }
        appRepository.setnoteandexist(noteEntity);
    }

    public void deleteNote()
    {
        appRepository.deleteNote(entityMutableLiveData.getValue());
    }
}

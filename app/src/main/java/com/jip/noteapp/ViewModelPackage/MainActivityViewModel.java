package com.jip.noteapp.ViewModelPackage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.jip.noteapp.AppRepository;
import com.jip.noteapp.model.NoteEntity;
import com.jip.noteapp.utils.SampleDataProvider;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    AppRepository appRepository;
    public LiveData<List<NoteEntity>> noteEntities;
    public MainActivityViewModel(Application application) {
        super(application);
        appRepository=AppRepository.getInstance(application.getApplicationContext());
        noteEntities=appRepository.noteEntities;
    }

    public void addSampleData()
    {
        appRepository.addsampleData();
    }

    public void deleteAllNote()
    {
        appRepository.deleteAllNotes();
    }

    public void deleteNote(NoteEntity noteEntity)
    {
        appRepository.deleteNote(noteEntity);
    }
}

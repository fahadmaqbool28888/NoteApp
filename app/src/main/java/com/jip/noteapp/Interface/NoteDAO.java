package com.jip.noteapp.Interface;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jip.noteapp.model.NoteEntity;

import java.util.List;

@Dao
public interface NoteDAO
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteEntity noteEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NoteEntity> noteEntities);

    @Delete
    void deleteNote(NoteEntity noteEntity);

    @Query("select * from notestable where id= :id")
    NoteEntity selectNote_BYID(int id);

    @Query("select * from notestable ORDER BY date desc")
    LiveData<List<NoteEntity>> selectNotes();

    @Query("delete from notestable")
    int deleteNotes();

    @Query("select count(*) from notestable")
    int countNotes();
}

package com.gtm.pustak.notes.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesDao {
    @Insert
    void insertNotes(Notes notes);

    @Query("Select *from Notes order by created_time desc")
    LiveData<List<Notes>> getAllNotes();

    @Delete()
    void deleteNote(Notes note);
}

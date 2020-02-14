package com.gtm.pustak.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
interface WordDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertWord(Word word);


    @Query("DELETE FROM word_table")
    void deleteAll();


    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();
}
package com.gtm.pustak.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Word.class}, version = 1)
public abstract class WordDataBase extends RoomDatabase {

    private static WordDataBase wordDataBase;


    public abstract WordDao wordDao();


    /**
     * Room should not have multiple instances.
     *
     * @param context
     * @return
     */
    public static WordDataBase getWordDataBase(Context context) {
        if (wordDataBase == null) {
            wordDataBase = Room.databaseBuilder(context.getApplicationContext(), WordDataBase.class, "Word_DataBase")
                    .build();
        }
        return wordDataBase;
    }
}

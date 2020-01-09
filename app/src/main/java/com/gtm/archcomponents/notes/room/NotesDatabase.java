package com.gtm.archcomponents.notes.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


/**
 * Core part of room, all configs mentioned here.
 */
@Database(entities = {Notes.class}, version = 3)
abstract class NotesDatabase extends RoomDatabase {
    abstract NotesDao getNotesDao();

    private static NotesDatabase INSTANCE;

    /**
     * For D/B migration
     */
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE NOTES "
                    + "ADD COLUMN notes_title TEXT");
        }
    };


    public static NotesDatabase getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(application.getApplicationContext(), NotesDatabase.class, "NOTES")
                    .addMigrations(MIGRATION_2_3)
                    .build();
        }
        return INSTANCE;
    }


}

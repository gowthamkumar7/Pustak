package com.gtm.pustak.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


//By default Class name is the table name, it can be overriden using tableName (tableName = "your table_name")
@Entity(tableName = "word_table")
public class Word {
    //Atleast one key has to be primary key
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word") //By default variable name will be used, to override use  @ColumnInfo
    private String mWord;

    public Word(String mWord) {
        this.mWord = mWord;
    }

    public String getWord() {
        return mWord;
    }
}

package com.gtm.archcomponents.notes.room;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "Notes", indices = {@Index(value =
        {"id"}, unique = true)})
public class Notes {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String note;
    @ColumnInfo(name = "notes_title")
    private String noteTitle;
    private String created_time;
    @ColumnInfo(name = "modified_time")
    private String modified_time;

    public Notes(String note, String noteTitle, String created_time, String modified_time) {
        this.note = note;
        this.noteTitle = noteTitle;
        this.created_time = created_time;
        this.modified_time = modified_time;
    }


    @NonNull
    public String getNote() {
        return note;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getCreated_time() {
        return created_time;
    }

    public String getModified_time() {
        return modified_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

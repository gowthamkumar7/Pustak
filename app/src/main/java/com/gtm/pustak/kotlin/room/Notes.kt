package com.gtm.pustak.kotlin.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "Notes", indices = [Index("value", unique = true)])
data class Notes(
        @PrimaryKey(autoGenerate = true) var id: Int,
        @NonNull var note: String,
        @ColumnInfo(name = "notes_title") var noteTitle: String
)
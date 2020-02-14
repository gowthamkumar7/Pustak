package com.gtm.pustak.kotlin.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.gtm.pustak.notes.room.Notes
import com.gtm.pustak.notes.room.NotesRepository

class NotesDashBoardViewModel(application: Application) : AndroidViewModel(application) {

    var mAllNotes: LiveData<List<Notes>>
    var repository: NotesRepository? = null

    init {
        this.repository = NotesRepository(application)
        mAllNotes = repository!!.allNotes
    }


    fun deleteSelectedNote(note: Notes) {
        repository?.deleteNote(note)
    }
}
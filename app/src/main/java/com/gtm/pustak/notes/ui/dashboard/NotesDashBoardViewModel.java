package com.gtm.pustak.notes.ui.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gtm.pustak.notes.room.Notes;
import com.gtm.pustak.notes.room.NotesRepository;

import java.util.List;

public class NotesDashBoardViewModel extends AndroidViewModel {
    public LiveData<List<Notes>> mAllNotes;
    private NotesRepository repository;

    public NotesDashBoardViewModel(@NonNull Application application) {
        super(application);
        this.repository = new NotesRepository(application);
        mAllNotes = repository.getAllNotes();
    }


    public void deleteSelectedNote(Notes note) {

        repository.deleteNote(note);


    }

}

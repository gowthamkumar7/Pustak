package com.gtm.archcomponents.notes.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {


    private NotesRepository notesRepository;

    public LiveData<List<Notes>> mAllNotes;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepository = new NotesRepository(application);
        mAllNotes = notesRepository.getAllNotes();
    }

    public void getAllNotes() {
        mAllNotes = notesRepository.getAllNotes();
    }

    public void insertNote(Notes notes) {
        //Viewmodel dispatches the request to repository
        notesRepository.insertNote(notes);
    }
}

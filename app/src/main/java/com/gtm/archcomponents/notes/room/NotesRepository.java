package com.gtm.archcomponents.notes.room;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;


/**
 * Repository pattern for
 */
public class NotesRepository {

    private NotesDatabase notesDatabase;
    private NotesDao notesDao;
    private LiveData<List<Notes>> mAllNotes;

    public NotesRepository(Application application) {
        notesDatabase = NotesDatabase.getInstance(application);
        notesDao = notesDatabase.getNotesDao();
        mAllNotes = notesDao.getAllNotes();

    }


    @SuppressLint("CheckResult")
    public void insertNote(final Notes notes) {
        //Insertion should happen at the background.
        new InsertAsync(notes).execute();


      /*  Single.fromCallable(new Callable<Notes>() {
            @Override
            public Notes call() throws Exception {
                notesDao.insertNotes(notes);
                return null;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());*/


    }

    public void deleteNote(Notes note) {

        new DeleteNoteAsync(note).execute();
    }

    //TODO to be replaced with RxJava
    @SuppressLint("StaticFieldLeak")
    private class InsertAsync extends AsyncTask<Void, Void, Void> {


        Notes notes;

        public InsertAsync(Notes notes) {
            this.notes = notes;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notesDao.insertNotes(notes);
            return null;
        }
    }

    private class DeleteNoteAsync extends AsyncTask<Void, Void, Void> {

        Notes note;

        public DeleteNoteAsync(Notes note) {
            this.note = note;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            notesDao.deleteNote(note);
            return null;
        }
    }

    public LiveData<List<Notes>> getAllNotes() {
        return mAllNotes;
    }
}

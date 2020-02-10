package com.gtm.archcomponents.notes.room;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


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
        Completable.fromAction(() -> notesDao.insertNotes(notes)
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {


            }

            @Override
            public void onError(Throwable e) {
                //Todo Handle error

            }
        });

    }

    public void deleteNote(Notes note) {
        Completable.fromAction(() -> notesDao.deleteNote(note)
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {


            }

            @Override
            public void onError(Throwable e) {
                //Todo Handle error

            }
        });
    }


    public LiveData<List<Notes>> getAllNotes() {
        return mAllNotes;
    }
}

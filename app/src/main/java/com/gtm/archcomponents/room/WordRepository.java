package com.gtm.archcomponents.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application application) {
        WordDataBase db = WordDataBase.getWordDataBase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    public void insert(Word word) {
        new InsertionAsync(mWordDao).execute(word);

    }


    public void deleteData() {
        new DeleteAsync(mWordDao).execute();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    //TODO: Replace it with RxJava
    private static class DeleteAsync extends AsyncTask<Word, Void, Void> {
        WordDao mWordDao;

        public DeleteAsync(WordDao mWordDao) {
            this.mWordDao = mWordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mWordDao.deleteAll();
            return null;
        }
    }


    //TODO: Replace it with RxJava
    private static class InsertionAsync extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;

        public InsertionAsync(WordDao mWordDao) {
            mAsyncTaskDao = mWordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {

            mAsyncTaskDao.insertWord(words[0]);
            return null;
        }
    }


    public static void main(String[] args) {
        System.out.println("Hello world");
    }

}

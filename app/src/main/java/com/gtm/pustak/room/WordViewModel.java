package com.gtm.pustak.room;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {


    private WordRepository wordRepository;
    public LiveData<List<Word>> mAllWords;


    public WordViewModel(@NonNull Application application) {
        super(application);

        wordRepository = new WordRepository(application);
        Log.d("Hello", "Constructor called");
        mAllWords = wordRepository.getAllWords();
    }

    public void insert(Word word) {
        wordRepository.insert(word);
    }

    public void deleteAll() {
        wordRepository.deleteData();
    }


}

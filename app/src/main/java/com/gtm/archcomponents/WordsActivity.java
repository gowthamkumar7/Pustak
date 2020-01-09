package com.gtm.archcomponents;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gtm.archcomponents.adapter.WordListAdapter;
import com.gtm.archcomponents.room.Word;
import com.gtm.archcomponents.room.WordViewModel;

import java.util.List;
import java.util.Random;

public class WordsActivity extends AppCompatActivity {

    private WordViewModel wordViewModel;
    WordListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        FloatingActionButton floatingActionButton_1 = findViewById(R.id.fab_1);

        adapter = new WordListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        floatingActionButton_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordViewModel.deleteAll();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random random = new Random();

                Word word = new Word("Gowtham" + random.nextInt());

                wordViewModel.insert(word);
            }
        });


        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);


        wordViewModel.mAllWords.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                adapter.setWords(words);
            }
        });
    }
}

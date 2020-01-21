package com.gtm.archcomponents.notes.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gtm.archcomponents.R;
import com.gtm.archcomponents.base.BaseActivity;
import com.gtm.archcomponents.notes.room.Notes;
import com.gtm.archcomponents.notes.ui.adapter.NotesAdapter;
import com.gtm.archcomponents.notes.ui.new_note.AddNewNoteActivity;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class NotesDashBoardActivity extends BaseActivity {

    private NotesAdapter notesAdapter;
    private CompositeDisposable compositeDisposable;
    private boolean isDeleted;
    private int selectedPosition;
    private NotesDashBoardViewModel notesDashBoardViewModel;
    private Menu mMenu;
    private List<Notes> mNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView mTvHint = findViewById(R.id.id_tv_hint_no_notes);
        getSupportActionBar().setTitle(getTitle());
        setTitle(getString(R.string.app_name));
        //Create a new NOTE
        FloatingActionButton fabNewNote = findViewById(R.id.fab_new_note);
        fabNewNote.setOnClickListener(v -> startActivity(new Intent(NotesDashBoardActivity.this, AddNewNoteActivity.class)));


        // Display existing NOTES
        RecyclerView mRcvNotes = findViewById(R.id.recyclerView);
        notesDashBoardViewModel = new ViewModelProvider(this).get(NotesDashBoardViewModel.class);
        notesAdapter = new NotesAdapter(this);
        mRcvNotes.setLayoutManager(new GridLayoutManager(this, 2));
        mRcvNotes.setAdapter(notesAdapter);

        Disposable disposable = notesAdapter.getLongClickedItemPosition().subscribe(position ->
                {
                    this.selectedPosition = position;
                    showMenu();
                    Log.d("NotesDashBoardActivity", "" + position);
                }

        );

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(disposable);
        notesDashBoardViewModel.mAllNotes.observe(this, notes -> {

            this.mNotes = notes;
            if ((notes.size() == 0)) {
                mTvHint.setVisibility(View.VISIBLE);
            } else {
                mTvHint.setVisibility(View.GONE);
            }

            notesAdapter.setNotes(notes);
        });

    }


    @Override
    public int getLayoutResource() {
        return R.layout.activity_notes_dash_board;
    }

    /**
     * Show the delete option menu
     */
    private void showMenu() {
        mMenu.findItem(R.id.menu_delete).setVisible(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_delete) {
            deleteSelectedNote();
        }


        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes_list, menu);

        mMenu = menu;
        return true;
    }


    /**
     * Retrieve the data from DB and delete the selected note.
     */
    private void deleteSelectedNote() {

        Notes note = this.mNotes.get(selectedPosition);

        int noteId = note.getId();


        notesDashBoardViewModel.deleteSelectedNote(note);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        compositeDisposable.dispose();

    }
}

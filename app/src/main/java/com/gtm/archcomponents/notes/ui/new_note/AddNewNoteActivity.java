package com.gtm.archcomponents.notes.ui.new_note;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.gtm.archcomponents.R;
import com.gtm.archcomponents.base.BaseActivity;
import com.gtm.archcomponents.notes.room.Notes;
import com.gtm.archcomponents.notes.room.NotesViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNewNoteActivity extends BaseActivity {
    private NotesViewModel notesViewModel;
    private EditText mEdNote;
    private EditText mEdNoteTitle;
    private TextView mTvNoteDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.text_create_note));

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        Button btnAdd = findViewById(R.id.btn_add);
        mEdNote = findViewById(R.id.ed_note);
        mEdNoteTitle = findViewById(R.id.ed_note_title);
        mTvNoteDate = findViewById(R.id.ed_note_date);

        mTvNoteDate.setText(getCurrentDateAndTime());
       /* notesViewModel.mAllNotes.observe(this, notes -> {
            for (int i = 0; i < notes.size(); i++) {
                Toast.makeText(AddNewNoteActivity.this, "" + notes.get(i).getNote(), Toast.LENGTH_SHORT).show();
            }

        });*/

        btnAdd.setOnClickListener(v -> {


        });


    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_add_new_note;
    }

    /**
     * Get current time and date
     *
     * @return current date and time
     */
    private String getCurrentDateAndTime() {
        Date date = Calendar.getInstance().getTime();
        return new SimpleDateFormat("dd MMM hh:mm aa", Locale.getDefault()).format(date);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_save) {
            String note = mEdNote.getText().toString();
            String noteTitle = mEdNoteTitle.getText().toString();
            String currentTime = getCurrentDateAndTime();
            String modifiedTime = getCurrentDateAndTime();
            // New note call to viewmodel
            Notes notes = new Notes(note, noteTitle, currentTime, modifiedTime);
            notesViewModel.insertNote(notes);

            finish();

        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_note, menu);
        return true;


    }
}

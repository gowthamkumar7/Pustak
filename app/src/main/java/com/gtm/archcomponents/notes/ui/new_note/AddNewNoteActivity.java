package com.gtm.archcomponents.notes.ui.new_note;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.lifecycle.ViewModelProvider;

import com.gtm.archcomponents.R;
import com.gtm.archcomponents.base.BaseActivity;
import com.gtm.archcomponents.notes.room.Notes;
import com.gtm.archcomponents.notes.room.NotesViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNewNoteActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private NotesViewModel notesViewModel;
    private EditText mEdNote;
    private EditText mEdNoteTitle;
    private TextView mTvNoteDate;
    private Calendar calendarInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.text_create_note));
        calendarInstance = Calendar.getInstance();
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        mEdNote = findViewById(R.id.ed_note);
        mEdNoteTitle = findViewById(R.id.ed_note_title);
        mTvNoteDate = findViewById(R.id.ed_note_date);
        mTvNoteDate.setText(getCurrentDateAndTime());




       /* notesViewModel.mAllNotes.observe(this, notes -> {
            for (int i = 0; i < notes.size(); i++) {
                Toast.makeText(AddNewNoteActivity.this, "" + notes.get(i).getNote(), Toast.LENGTH_SHORT).show();
            }

        });*/


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

        } else if (item.getItemId() == R.id.menu_remind) {

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewNoteActivity.this, this, calendarInstance
                    .get(Calendar.YEAR), calendarInstance.get(Calendar.MONTH),
                    calendarInstance.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
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

    @Override
    public void onDateSet(DatePicker view, int onDateSet, int month, int dayOfMonth) {
        Log.d("AddNewNite", "onDateSet: " + onDateSet + "-" + month + "-" + dayOfMonth);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewNoteActivity.this, this,
                calendarInstance.get(Calendar.HOUR_OF_DAY), calendarInstance.get(Calendar.MINUTE), false);
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


        Log.d("AddNewNite", "onTimeSet: " + hourOfDay + ":" + minute);
    }

}

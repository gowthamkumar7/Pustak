package com.gtm.pustak.notes.ui.new_note;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.lifecycle.ViewModelProvider;

import com.gtm.pustak.R;
import com.gtm.pustak.base.BaseActivity;
import com.gtm.pustak.notes.room.Notes;
import com.gtm.pustak.notes.room.NotesViewModel;
import com.gtm.pustak.notes.utility.NotificationBroadCast;
import com.gtm.pustak.notes.utility.Util;

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
    private int hour;
    private int min;
    private int year;
    private int month;
    private int date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getString(R.string.text_create_note));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.menu_save) {
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


    /**
     * Add a notification reminder
     */
    private void addReminder() {
        Calendar alarmStartTime = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, this.hour);
        alarmStartTime.set(Calendar.MINUTE, this.min);
        alarmStartTime.set(Calendar.DATE, this.date);
        alarmStartTime.set(Calendar.SECOND, 0);
        alarmStartTime.set(Calendar.YEAR, this.year);
        alarmStartTime.set(Calendar.MONTH, this.month);


        Intent intent = new Intent(".notes.utility.NotificationBroadCast");
        intent.putExtra(Util.KEY_TITLE, mEdNoteTitle.getText().toString());
        intent.putExtra(Util.KEY_NOTE, mEdNote.getText().toString());
        intent.setClass(this, NotificationBroadCast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                234, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

        am.set(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), pendingIntent);
       /* am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0,
                1000 * 60, pendingIntent);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_note, menu);
        return true;


    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewNoteActivity.this, this,
                calendarInstance.get(Calendar.HOUR_OF_DAY), calendarInstance.get(Calendar.MINUTE), false);
        timePickerDialog.show();
        this.year = year;
        this.month = month;
        this.date = dayOfMonth;


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        this.hour = hourOfDay;
        this.min = minute;
        addReminder();
    }

}

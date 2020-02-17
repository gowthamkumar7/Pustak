package com.gtm.pustak.kotlin.ui.new_note

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProvider
import com.gtm.pustak.R
import com.gtm.pustak.kotlin.Utils
import com.gtm.pustak.kotlin.base.BaseActivity
import com.gtm.pustak.notes.room.Notes
import com.gtm.pustak.notes.room.NotesViewModel
import com.gtm.pustak.notes.utility.NotificationBroadCast
import com.gtm.pustak.notes.utility.Util
import java.text.SimpleDateFormat
import java.util.*

class AddNewNoteActivity : BaseActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    private var calendarInstance: Calendar? = null
    private var notesViewModel: NotesViewModel? = null
    private var mEdNote: EditText? = null
    private var mEdNoteTitle: EditText? = null
    private var mTvNoteDate: TextView? = null


    private var year: Int = 0
    private var month: Int = 0
    private var dayOfMonth: Int = 0
    private var hourOfDay: Int = 0
    private var minute: Int = 0

    override fun getLayoutResource(): Int {
        return R.layout.activity_add_new_note
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.text_create_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        calendarInstance = Calendar.getInstance()
        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        mEdNote = findViewById(R.id.ed_note)
        mEdNoteTitle = findViewById(R.id.ed_note_title)
        mTvNoteDate = findViewById(R.id.ed_note_date)
        mTvNoteDate?.text = Utils.getTimeWithoutSeconds(getCurrentDateAndTime())
    }


    /**
     * Get current time and date
     *
     * @return current date and time
     */

    private fun getCurrentDateAndTime(): String {
        var date: Date = Calendar.getInstance().time
        return SimpleDateFormat("dd MMM hh:mm:ss aa", Locale.getDefault()).format(date)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.new_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val itemId = item?.itemId

        when (itemId) {
            android.R.id.home -> onBackPressed()

            R.id.menu_save -> {
                val note = mEdNote?.text.toString()
                val noteTitle = mEdNoteTitle?.text.toString()
                val currentTime = getCurrentDateAndTime()
                val modifiedTime = getCurrentDateAndTime()

                val notes = Notes(note, noteTitle, currentTime, modifiedTime)
                notesViewModel?.insertNote(notes)

                finish()
            }

            R.id.menu_remind -> {
                val datePickerDialog = DatePickerDialog(this@AddNewNoteActivity, this, calendarInstance!!.get(Calendar.YEAR), calendarInstance!!.get(Calendar.MONTH),
                        calendarInstance!!.get(Calendar.DAY_OF_MONTH))
                datePickerDialog.datePicker.minDate = System.currentTimeMillis()
                datePickerDialog.show()

            }
        }

        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        } else if (item?.itemId == R.id.menu_save) {
            val note = mEdNote?.text.toString()
            val noteTitle = mEdNoteTitle?.text.toString()
            val currentTime = getCurrentDateAndTime()
            val modifiedTime = getCurrentDateAndTime()

            val notes = Notes(note, noteTitle, currentTime, modifiedTime)
            notesViewModel?.insertNote(notes)

            finish()
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val timePickerDialog = TimePickerDialog(this@AddNewNoteActivity, this, calendarInstance!!.get(Calendar.HOUR_OF_DAY),
                calendarInstance!!.get(Calendar.MINUTE), false)
        timePickerDialog.show()

        this.year = year
        this.month = month
        this.dayOfMonth = dayOfMonth

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        this.hourOfDay = hourOfDay
        this.minute = minute
        addReminder()
    }

    /**
     * Add a notification reminder
     */
    fun addReminder() {
        val alarmStartTime = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, this.hourOfDay)
        alarmStartTime.set(Calendar.MINUTE, this.minute)
        alarmStartTime.set(Calendar.DATE, this.dayOfMonth)
        alarmStartTime.set(Calendar.SECOND, 0)
        alarmStartTime.set(Calendar.YEAR, this.year)
        alarmStartTime.set(Calendar.MONTH, this.month)

        val intent = Intent(".notes.utility.NotificationBroadCast");
        intent.putExtra(Util.KEY_NOTE, mEdNote?.text.toString())
        intent.putExtra(Util.KEY_TITLE, mEdNoteTitle?.text.toString())
        intent.setClass(this, NotificationBroadCast::class.java)

        val pendingIntent = PendingIntent.getBroadcast(this, 234, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime.timeInMillis, pendingIntent)

    }
}

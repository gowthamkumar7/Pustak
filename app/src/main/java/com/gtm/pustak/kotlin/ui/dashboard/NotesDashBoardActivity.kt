package com.gtm.pustak.kotlin.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.gtm.pustak.R
import com.gtm.pustak.kotlin.base.BaseActivity
import com.gtm.pustak.kotlin.ui.adapter.NotesAdapter
import com.gtm.pustak.kotlin.ui.new_note.AddNewNoteActivity
import com.gtm.pustak.notes.room.Notes
import io.reactivex.disposables.Disposable

class NotesDashBoardActivity : BaseActivity() {

    var selectedPosition = 0
    var mNotes: List<Notes>? = null
    lateinit var mMenu: Menu
    lateinit var notesDashBoardViewModel: NotesDashBoardViewModel
    override fun getLayoutResource(): Int {
        return R.layout.activity_notes_dash_board
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mTvHint: TextView = findViewById(R.id.id_tv_hint_no_notes)
        supportActionBar?.setTitle(title)
        title = getString(R.string.app_name)
        val fabNewNote = findViewById<FloatingActionButton>(R.id.fab_new_note)

        fabNewNote.setOnClickListener {
            startActivity(Intent(this@NotesDashBoardActivity, AddNewNoteActivity::class.java))
        }

        val mRcvNotes = findViewById<RecyclerView>(R.id.recyclerView)

        notesDashBoardViewModel = ViewModelProvider(this).get(NotesDashBoardViewModel::class.java)

        val notesAdapter = NotesAdapter(this)
        mRcvNotes.layoutManager = GridLayoutManager(this, 2)
        mRcvNotes.adapter = notesAdapter


        val disposable: Disposable = notesAdapter.getlongClickedItemPosition().subscribe { position ->
            this.selectedPosition = position
            showMenu()
            Log.d("NotesDashBoardActivity", "" + position)
        }
        compositeDisposable.add(disposable)

        notesDashBoardViewModel.mAllNotes.observe(this@NotesDashBoardActivity, Observer { notes ->
            mNotes = notes



            if (notes.isEmpty()) {
                mTvHint.visibility = View.VISIBLE
            } else {
                mTvHint.visibility = View.GONE
            }
            notesAdapter.setNotes(notes)
        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notes_list, menu)
        if (menu != null) {
            mMenu = menu
        }
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_delete) {
            deleteSelectedNote()
            hideMenu()
        }
        return true
    }

    /**
     * hide the delete menu
     */
    private fun hideMenu() {
        mMenu.findItem(R.id.menu_delete).setVisible(false)
    }

    /**
     * Show the delete option menu
     */
    private fun showMenu() {
        mMenu.findItem(R.id.menu_delete).setVisible(true)

    }


    /**
     * Retrieve the data from DB and delete the selected note.
     */
    fun deleteSelectedNote() {
        var notes = mNotes!!.get(selectedPosition)
        notesDashBoardViewModel.deleteSelectedNote(notes)
    }

}

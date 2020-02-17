package com.gtm.pustak.kotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gtm.pustak.R
import com.gtm.pustak.kotlin.Utils
import com.gtm.pustak.notes.room.Notes
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class NotesAdapter(context: Context) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var selectedPosition: Int = -1
    var positionPublisher: PublishSubject<Int> = PublishSubject.create<Int>()
    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var notesList: List<Notes>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(layoutInflater.inflate(R.layout.list_item_note, parent, false))
    }

    override fun getItemCount(): Int {
        return if (notesList == null) 0 else (notesList!!.size)

    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        if (selectedPosition == position) {
            holder.noteSelection.visibility = View.VISIBLE
            selectedPosition = -1
        } else {
            holder.noteSelection.visibility = View.GONE
        }

        holder.notesBody.text = notesList!!.get(position).note
        holder.notesTitle.text = (notesList!!.get(position).getNoteTitle())

        var createdTime = Utils.getTimeWithoutSeconds(notesList!!.get(position).created_time)

        holder.notesTime.text = (createdTime)
    }

    fun setNotes(notes: List<Notes>) {
        this.notesList = notes
        notifyDataSetChanged()

    }


    fun getlongClickedItemPosition(): Observable<Int> {
        return positionPublisher
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var notesTitle: TextView
        var notesBody: TextView
        var notesTime: TextView
        var noteSelection: ImageView

        init {
            notesTitle = itemView.findViewById(R.id.tv_note_title);
            notesBody = itemView.findViewById(R.id.tv_note_body)
            notesTime = itemView.findViewById(R.id.id_tv_time)
            noteSelection = itemView.findViewById(R.id.id_im_selection)
            itemView.setOnLongClickListener { v ->

                if (selectedPosition == adapterPosition) {
                    selectedPosition = -1
                    notifyDataSetChanged()
                } else {
                    selectedPosition = adapterPosition
                    positionPublisher.onNext(selectedPosition)
                    notifyDataSetChanged()
                }
                false
            }

        }


    }

}
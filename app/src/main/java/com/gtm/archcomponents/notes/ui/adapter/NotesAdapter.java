package com.gtm.archcomponents.notes.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gtm.archcomponents.R;
import com.gtm.archcomponents.notes.room.Notes;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Notes> notes;
    private final LayoutInflater mInflater;
    private View mView;
    private PublishSubject<Integer> position = PublishSubject.create();
    private int selectedPosition = -1;

    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView notesTitle;
        TextView notesBody;
        TextView notesTime;
        ImageView noteSelection;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            notesTitle = itemView.findViewById(R.id.tv_note_title);
            notesBody = itemView.findViewById(R.id.tv_note_body);
            notesTime = itemView.findViewById(R.id.id_tv_time);
            noteSelection = itemView.findViewById(R.id.id_im_selection);


            itemView.setOnLongClickListener(v -> {
                        if (selectedPosition == getAdapterPosition()) {
                            selectedPosition = -1;
                            notifyDataSetChanged();
                        } else {
                            selectedPosition = getAdapterPosition();
                            position.onNext(getAdapterPosition());
                            notifyDataSetChanged();
                        }
                        return true;
                    }
            );
        }
    }


    public NotesAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }


    public Observable<Integer> getLongClickedItemPosition() {

        return position;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mView = this.mInflater.inflate(R.layout.list_item_note, parent, false);

        return new NotesViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        if (selectedPosition == position) {
            holder.noteSelection.setVisibility(View.VISIBLE);
            selectedPosition = -1;
        } else {
            holder.noteSelection.setVisibility(View.GONE);
        }

        holder.notesBody.setText(notes.get(position).getNote());
        holder.notesTitle.setText(notes.get(position).getNoteTitle());
        holder.notesTime.setText(notes.get(position).getCreated_time());


    }

    @Override
    public int getItemCount() {
        return (notes == null) ? 0 : notes.size();
    }

    public void setNotes(List<Notes> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }


}

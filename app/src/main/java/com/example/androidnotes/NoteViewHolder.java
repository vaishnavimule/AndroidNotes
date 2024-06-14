package com.example.androidnotes;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    TextView noteTitle;
    TextView lastSaveDate;
    TextView noteText;

    public NoteViewHolder(View itemView) {
        super(itemView);
        noteTitle = itemView.findViewById(R.id.note_title);
        lastSaveDate = itemView.findViewById(R.id.last_save_date);
        noteText = itemView.findViewById(R.id.note_text);
    }
}

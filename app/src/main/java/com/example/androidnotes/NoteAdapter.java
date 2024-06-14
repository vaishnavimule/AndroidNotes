package com.example.androidnotes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder>{

    private final MainActivity mainAct;
    private ArrayList<Note> noteList = new ArrayList<>();
    private static final String TAG = "NoteAdapter";


    public NoteAdapter(MainActivity mainAct, ArrayList<Note> noteList){
        this.mainAct = mainAct;
        this.noteList = noteList;
    }
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder: MAKING NEW MyViewHolder");

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_entry, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: FILLING VIEW HOLDER Employee " + position);

        Note note = noteList.get(position);

        holder.noteTitle.setText(note.getTitle());
        String text = note.getText();
        if(text.length() > 80){
            String subText = text.substring(0,80) + "...";
            holder.noteText.setText(subText);
        }
        else{
            holder.noteText.setText(text);
        }

        //holder.noteText.setText(note.getText());
        holder.lastSaveDate.setText(note.getLastSaveDate());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}

package com.example.androidnotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    private Note note;
    int pos;
    EditText title;
    EditText text;
    private static final String TAG = "EditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Android Notes");
        title = findViewById(R.id.editText_Title);
        text = findViewById(R.id.editText_Note);
        try {
            Intent intent = getIntent();
            if (intent.hasExtra("Note")) {
                note = (Note) intent.getSerializableExtra("Note");
                pos = intent.getIntExtra("Pos",0);
                if (note != null){
                    title.setText(note.getTitle());
                    text.setText(note.getText());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @SuppressLint("SimpleDateFormat")
    public void onSaveNote(MenuItem item){
        try {
            String newTitle = title.getText().toString();
            if(newTitle.isEmpty()){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setPositiveButton("CANCEL", (dialogInterface, i) -> {
                });
                builder.setNegativeButton("OK", (dialogInterface, i) -> finish());
                builder.setMessage("Note will not be saved without title \n Discard current note?");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                saveNote();
            }
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
        }
    }

    public void saveNote(){

        try {
            String newTitle = title.getText().toString();
            String newText = text.getText().toString();
            String oldTitle = "";
            String oldText = "";
            if(note != null){
                oldTitle = note.getTitle();
                oldText = note.getText();
            }
            if(title.getText().toString().isEmpty())
                Toast.makeText(this, "Please enter the note Title!", Toast.LENGTH_SHORT).show();
            else if(note!=null && newTitle.equals(oldTitle) && newText.equals(oldText)){
                finish();
            }
            else {
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d, h:mm a");
                String date = dateFormat.format(calendar.getTime());
                Note note = new Note(newTitle, newText, date);
                Intent intent = new Intent();
                intent.putExtra("NOTE", note);
                intent.putExtra("POS", pos);
                setResult(RESULT_OK, intent);
                Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
                finish();
            }
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
        }
    }

    @Override
    public void onBackPressed() {

        try {
            String newTitle = title.getText().toString();
            String newText = text.getText().toString();
            String oldTitle = "";
            String oldText = "";
            if(note != null){
                oldTitle = note.getTitle();
                oldText = note.getText();
            }
            if(note!=null && newTitle.equals(oldTitle) && newText.equals(oldText)){
                finish();
            }
            else if(newTitle.isEmpty()){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setPositiveButton("CANCEL", (dialogInterface, i) -> {
                });
                builder.setNegativeButton("OK", (dialogInterface, i) -> finish());
                builder.setMessage("Note will not be saved without title \n Discard current note?");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setPositiveButton("YES", (dialogInterface, i) -> saveNote());
                builder.setNegativeButton("NO", (dialogInterface, i) -> finish());
                builder.setMessage("Your note is not saved!\nSave note '" + title.getText().toString() + "'?");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
        }
    }
}
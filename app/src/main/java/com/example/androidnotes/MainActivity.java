package com.example.androidnotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "MainActivity";
    private final ArrayList<Note> noteList = new ArrayList<>();

    private RecyclerView recyclerView; // Layout's recyclerview
    private NoteAdapter nAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private ActivityResultLauncher<Intent> editActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            recyclerView = findViewById(R.id.noteRecycler);

            nAdapter = new NoteAdapter(this,noteList);
            recyclerView.setAdapter(nAdapter);

            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);

            activityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    this::handleResult);

            editActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    this::handleNewNoteResult);

            noteList.addAll(loadFile());
            setTitle("Android Notes (" + noteList.size() + ")");
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
        }

    }

    @Override
    protected void onPause() {
        try {
            saveNote(noteList);
            super.onPause();
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
        }
    }

    @Override
    protected void onResume() {
        try {
            noteList.clear();
            noteList.addAll(loadFile());
            super.onResume();
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
        }
    }

    public void handleNewNoteResult(ActivityResult result){

        try {
            if (result == null || result.getData() == null) {
                Log.d(TAG, "handleResult: NULL ActivityResult received");
                return;
            }
            Intent data = result.getData();
            if (result.getResultCode() == RESULT_OK) {
                Note note = (Note) data.getSerializableExtra("NOTE");
                if (note != null) {
                    noteList.add(0,note);
                    updateRecyclerView(noteList,0);
                }
            } else {
                Toast.makeText(this, "OTHER result not OK!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
        }
    }

    public void handleResult(ActivityResult result) {
        try {
            if (result == null || result.getData() == null) {
                Log.d(TAG, "handleResult: NULL ActivityResult received");
                return;
            }
            Intent data = result.getData();
            if (result.getResultCode() == RESULT_OK) {
                Note note = (Note) data.getSerializableExtra("NOTE");
                int pos = data.getIntExtra("POS",0);
                if (note != null) {
                    noteList.remove(pos);
                    noteList.add(0,note);
                    updateRecyclerView(noteList,pos);
                }
            } else {
                Toast.makeText(this, "OTHER result not OK!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
        }
    }

    public void updateRecyclerView(ArrayList<Note> noteList, int pos){
        try {
            recyclerView = findViewById(R.id.noteRecycler);
            nAdapter = new NoteAdapter(this,noteList);
            recyclerView.setAdapter(nAdapter);
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            setTitle("Android Notes (" + noteList.size() + ")");
            saveNote(noteList);
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;
    }

    private ArrayList<Note> loadFile() {
        try {
            Log.d(TAG, "loadFile: Loading JSON File");
            ArrayList<Note> noteList = new ArrayList<>();
            try {
                InputStream is = getApplicationContext().openFileInput(getString(R.string.file_name));
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                JSONArray jsonArray = new JSONArray(sb.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("title");
                    String text = jsonObject.getString("text");
                    String lastSaveDate = jsonObject.getString("lastSaveDate");
                    Note note = new Note(title, text, lastSaveDate);
                    noteList.add(note);
                }
            } catch (FileNotFoundException e) {
                Log.d(TAG, "loadFile: No AndroidNotes JSON file found");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return noteList;
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
            return noteList;
        }
    }

    private void saveNote(ArrayList<Note> noteList) {
        try {
            Log.d(TAG, "saveProduct: Saving JSON File");
                FileOutputStream fos = getApplicationContext().
                        openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

                PrintWriter printWriter = new PrintWriter(fos);
                printWriter.print(noteList);
                printWriter.close();
                fos.close();
                Log.d(TAG, "saveProduct: JSON:\n" + noteList.toString());
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
        }
    }

    @Override
    public void onClick(View view) {
        try {
            int pos = recyclerView.getChildLayoutPosition(view);
            Note n = noteList.get(pos);
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("Note", n);
            intent.putExtra("Pos", pos);
            activityResultLauncher.launch(intent);
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        try {
            int pos = recyclerView.getChildLayoutPosition(v);
            Note n = noteList.get(pos);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setPositiveButton("YES", (dialogInterface, i) -> {
                noteList.remove(pos);
                updateRecyclerView(noteList,pos);
            });
            builder.setNegativeButton("NO", (dialogInterface, i) -> {
            });
            builder.setMessage("Delete Note ' " + n.getTitle() + "'?");
            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
            return true;
        }

    }

    public void openAboutActivity(MenuItem item){
        try {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
        }
    }

    public void openEditActivity(MenuItem item){
        try {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("Type", "New");
            editActivityResultLauncher.launch(intent);
        }catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e);
        }
    }
}
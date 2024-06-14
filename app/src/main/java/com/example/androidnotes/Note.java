package com.example.androidnotes;

import android.util.JsonWriter;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

public class Note implements Serializable {
    private String title;
    private String text;
    private String lastSaveDate;


    public Note(String title, String text, String lastSaveDate) {
        this.title = title;
        this.text = text;
        this.lastSaveDate = lastSaveDate;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getLastSaveDate() {
        return lastSaveDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLastSaveDate(String lastSaveDate) {
        this.lastSaveDate = lastSaveDate;
    }

    @NonNull
    public String toString() {

        try {
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(sw);
            jsonWriter.setIndent("  ");
            jsonWriter.beginObject();
            jsonWriter.name("title").value(getTitle());
            jsonWriter.name("text").value(getText());
            jsonWriter.name("lastSaveDate").value(getLastSaveDate());
            jsonWriter.endObject();
            jsonWriter.close();
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}

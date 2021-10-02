package com.example.a_notes.domain;

import androidx.annotation.Nullable;

public class NoteEntity {

    @Nullable
    private Integer id;

    private String title;
    private String content;
    private String date;

    public NoteEntity(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }


}

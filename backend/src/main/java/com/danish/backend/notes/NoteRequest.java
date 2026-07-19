package com.danish.backend.notes;

import jakarta.validation.constraints.NotBlank;

public class NoteRequest {


    @NotBlank(message ="title can't be empty")
    private String title;


    private String content;


    public NoteRequest() {
    }

    public NoteRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}

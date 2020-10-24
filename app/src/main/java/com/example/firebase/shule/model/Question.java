package com.example.firebase.shule.model;

import java.io.Serializable;

public class Question implements Serializable {
    private String id;
    private String question;
    private String hint;

    public Question() {
    }

    public Question(String id, String question, String hint) {
        this.id = id;
        this.question = question;
        this.hint = hint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}

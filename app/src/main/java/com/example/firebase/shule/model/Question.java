package com.example.firebase.shule.model;

import java.io.Serializable;
import java.util.Set;

public class Question implements Serializable {
    private String id;
    private String question;
    private String hint;
    private Set<Answer> answer;

    public Question() {
    }

    public Question(String question, String hint, Set<Answer> answer) {
        this.id = id;
        this.question = question;
        this.hint = hint;
        this.answer = answer;
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

    public Set<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(Set<Answer> answer) {
        this.answer = answer;
    }
}

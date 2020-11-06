package com.example.firebase.shule.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Question implements Serializable {
    private String id;
    private String question;
    private String hint;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String topicId;

    public Question() {
    }

    public Question(String question, String hint, String optionA, String optionB, String optionC, String optionD, String topicId) {
        this.id = id;
        this.question = question;
        this.hint = hint;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.topicId = topicId;
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

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    @NonNull
    @Override
    public String toString() {
        String s = "\nquestion " + question + "\nOptionA " + optionA + "\nOptionB "
                + optionB+ "\nOptionC " + optionC + "\nOptionD " + optionD +"\nHint "+ hint;
        return s;
    }
}

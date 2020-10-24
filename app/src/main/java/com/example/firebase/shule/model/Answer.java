package com.example.firebase.shule.model;

import java.io.Serializable;

public class Answer implements Serializable {

    private String answer;
    private String isWrong;
    private String status;

    public Answer(String answer, String isWrong, String status) {
        this.answer = answer;
        this.isWrong = isWrong;
        this.status = status;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIsWrong() {
        return isWrong;
    }

    public void setIsWrong(String isWrong) {
        this.isWrong = isWrong;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

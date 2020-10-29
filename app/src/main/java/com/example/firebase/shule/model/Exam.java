package com.example.firebase.shule.model;

public class Exam extends Topic {
    private Question question;

    public Exam(Question question) {
        this.question = question;
    }

    public Exam(String topic, String imageUri, String imageName, Question question) {
        super();
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}

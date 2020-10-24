package com.example.firebase.shule.model;

public class Exam extends Topic {
    private Question question;
    private Answer answer;

    public Exam(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    public Exam(String topic, String imageUri, String imageName, Question question, Answer answer) {
        super(topic, imageUri, imageName);
        this.question = question;
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}

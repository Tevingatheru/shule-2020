package com.example.firebase.shule.model;

import java.util.Set;

public class Exam extends Topic {
    private Question question;
    private Set<Answer> answer;

    public Exam(Question question, Set<Answer> answer) {
        this.question = question;
        this.answer = answer;
    }

    public Exam(String topic, String imageUri, String imageName, Question question, Set<Answer> answer) {
        super();
        this.question = question;
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Set<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(Set<Answer> answer) {
        this.answer = answer;
    }
}

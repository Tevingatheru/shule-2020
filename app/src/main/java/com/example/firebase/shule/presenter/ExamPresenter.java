package com.example.firebase.shule.presenter;

import com.example.firebase.shule.activity.ExamActivity;
import com.example.firebase.shule.contract.ExamContract;
import com.example.firebase.shule.model.Answer;
import com.example.firebase.shule.model.Exam;
import com.example.firebase.shule.model.Question;
import com.example.firebase.shule.util.FirebaseUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Set;

public class ExamPresenter  implements ExamContract.Presenter {
    public static DatabaseReference databaseReference;
    private static FirebaseDatabase firebaseDatabase;

    @Override
    public void getExam() {

    }

    @Override
    public void addExam(String topic, String imageUri, String imageName, Question Question, Set<Answer> answers) {
        Exam exam = new Exam(topic, imageUri, imageName, Question, answers);
        databaseReference.push().setValue(topic);
    }

    @Override
    public void removeExam() {

    }

    @Override
    public void editExam() {

    }

    @Override
    public void listenToFb() {
        FirebaseUtil.openExamReference("exam", new ExamActivity());
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;
    }
}

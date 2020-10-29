package com.example.firebase.shule.activity;

import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.shule.R;
import com.example.firebase.shule.contract.QuestionContract;
import com.example.firebase.shule.model.Question;
import com.example.firebase.shule.presenter.QuestionPresenter;
import com.example.firebase.shule.util.FirebaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnswerActivity extends AppCompatActivity implements QuestionContract.View {
    private TextView tvQuestion;
    private QuestionPresenter presenter;
    private ArraySet<Question> questionSet;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        FirebaseUtil.openFbReference("question", new SubjectActivity());
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;
        questionSet = FirebaseUtil.questionUtilList;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Question question;
                question = snapshot.getValue(Question.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        presenter = new AnswerPresenter(this);
//        presenter.setView();

        Log.i("Question:", "question size is :" + questionSet.size());
        System.out.println(questionSet);
    }

    @Override
    public void shouldSetView() {
//        final AnswerAdapter adapter = new AnswerAdapter();
//        final LinearLayoutManager linearLayoutManager =
//                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvAnswerList);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(adapter);
    }

    @Override
    public void shouldInitialize(Question question) {
        tvQuestion = (TextView) findViewById(R.id.tvQuestion) ;
        tvQuestion.setText(question.getQuestion());
    }
}
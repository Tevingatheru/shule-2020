package com.example.firebase.shule.activity;

import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.shule.R;
import com.example.firebase.shule.contract.QuestionContract;
import com.example.firebase.shule.model.Question;
import com.example.firebase.shule.presenter.QuestionPresenter;
import com.example.firebase.shule.util.FirebaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AnswerActivity extends AppCompatActivity implements QuestionContract.View {
    private TextView tvQuestion;
    private TextView tvOptionA;
    private TextView tvOptionB;
    private TextView tvOptionC;
    private TextView tvOptionD;

    public QuestionPresenter presenter;
    public ArraySet<Question> questionSet;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        presenter = new QuestionPresenter(this);
        presenter.openRef();
        presenter.listenToFb();

        presenter.initializeFields();
    }

    @Override
    public void shouldInitializeFields() {
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvOptionA = (TextView) findViewById(R.id.tvOptionA);
        tvOptionB = (TextView) findViewById(R.id.tvOptionB);
        tvOptionC = (TextView) findViewById(R.id.tvOptionC);
        tvOptionD = (TextView) findViewById(R.id.tvOptionD);

        tvOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),
                        "'Option A' button clicked", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        tvOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),
                        "'Option B' button clicked", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        tvOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),
                        "'Option C' button clicked", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        tvOptionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),
                        "'Option D' button clicked", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public int shouldCountItems() {
        int size = questionSet.size();
        Log.i("Question:", "question size is :" + size);
        System.out.println(size);
        return size;
    }

    @Override
    public void shouldOpenRef() {
        FirebaseUtil.openQuestionReference("question", new SubjectActivity());
    }

    @Override
    public void shouldSetQuestion() {
        if (questionSet != null && !questionSet.isEmpty()) {
//            tvQuestion.setText(question.getQuestion());
//            tvOptionA.setText(question.getOptionA());
//            tvOptionB.setText(question.getOptionB());
//            tvOptionC.setText(question.getOptionC());
//            tvOptionD.setText(question.getOptionD());
        }
    }

    @Override
    public boolean shouldCheckIfAnswerSelectedIsCorrect() {

        return false;
    }

    @Override
    public void shouldListenToFb() {
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;
        questionSet = FirebaseUtil.questionUtilList;
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Question question;
                try {
                    question = snapshot.getValue(Question.class);
                } catch (RuntimeException e) {
                    Log.e("Question Adapter: ", e.getMessage());
                    throw new RuntimeException(e.getMessage(), e);
                }

                Log.i("Question Adapter: ", question.getQuestion());
                question.setId(snapshot.getKey());
                Log.i("Question", question.getId());
                questionSet.add(question);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);
        
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int size = presenter.countItems();
        assert size > 0;
        for(int i = 0; i < size; i++) {
            presenter.setQuestion();
            presenter.isAnswerCorrect();
        }
        FirebaseUtil.attachListener();
    }
}
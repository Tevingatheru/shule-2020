package com.example.firebase.shule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
    private TextView tvHint;

    public QuestionPresenter presenter;
    public static ArraySet<Question> questionSet;

    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference databaseReference;
    private static ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        presenter = new QuestionPresenter(this);
        presenter.openRef();
        presenter.listenToFb();
        presenter.initializeFields();

        int size = presenter.countItems();
        Log.i("Answer: ", "Size Of Question List" + size);
        for(Question mQuestion: questionSet) {
            presenter.setQuestion(mQuestion);
        }
    }

    @Override
    public void shouldInitializeFields() {
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvOptionA = (TextView) findViewById(R.id.tvOptionA);
        tvOptionB = (TextView) findViewById(R.id.tvOptionB);
        tvOptionC = (TextView) findViewById(R.id.tvOptionC);
        tvOptionD = (TextView) findViewById(R.id.tvOptionD);
        tvHint = (TextView) findViewById(R.id.tvHint);
        tvHint.setVisibility(View.INVISIBLE);

        tvOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = tvOptionA.getText().toString();
                String hint = tvHint.getText().toString();
                presenter.isAnswerCorrect(v, answer, hint);
            }
        });

        tvOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = tvOptionB.getText().toString();
                String hint = tvHint.getText().toString();
                presenter.isAnswerCorrect(v, answer, hint);
            }
        });

        tvOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = tvOptionC.getText().toString();
                String hint = tvHint.getText().toString();
                presenter.isAnswerCorrect(v, answer, hint);
            }
        });

        tvOptionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = tvOptionD.getText().toString();
                String hint = tvHint.getText().toString();
                presenter.isAnswerCorrect(v, answer, hint);
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
    public void shouldSetQuestion(Question mQuestion) {
        if (questionSet != null && !questionSet.isEmpty()) {
            tvQuestion.setText(mQuestion.getQuestion());
            tvOptionA.setText(mQuestion.getOptionA());
            tvOptionB.setText(mQuestion.getOptionB());
            tvOptionC.setText(mQuestion.getOptionC());
            tvOptionD.setText(mQuestion.getOptionD());
            tvHint.setText(getString(R.string.hint) + mQuestion.getHint());
        }
    }

    @Override
    public void shouldCheckIfAnswerSelectedIsCorrect(View v, String answer, String hint) {
        if (hint == answer) {
            Toast.makeText(v.getContext(),
                    "You Got It", Toast.LENGTH_SHORT)
                    .show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent insertActivity = new Intent(AnswerActivity.this, SubjectActivity.class);
                    startActivity(insertActivity);
                }
            }, 2000);
        } else {
            Toast.makeText(v.getContext(),
                    "Wrong, Try Again ", Toast.LENGTH_SHORT)
                    .show();
            tvHint.setVisibility(View.VISIBLE);
        }
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
        FirebaseUtil.attachListener();
    }
}
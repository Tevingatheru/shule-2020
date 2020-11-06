package com.example.firebase.shule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AnswerActivity extends AppCompatActivity implements QuestionContract.View {
    TextView tvQuestion;
    TextView tvOptionA;
    TextView tvOptionB;
    TextView tvOptionC;
    TextView tvOptionD;
    TextView tvActualAnswer;
    TextView tvHint;

    QuestionPresenter presenter;

    ArrayList<Question> questionArrayList;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        presenter = new QuestionPresenter(this);
        presenter.initializeFields();
        presenter.openRef();
        presenter.listenToFb();
    }

    @Override
    public void shouldInitializeFields() {
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvOptionA = (TextView) findViewById(R.id.tvOptionA);
        tvOptionB = (TextView) findViewById(R.id.tvOptionB);
        tvOptionC = (TextView) findViewById(R.id.tvOptionC);
        tvOptionD = (TextView) findViewById(R.id.tvOptionD);
        tvActualAnswer = (TextView) findViewById(R.id.tvActualAnswer);
        tvHint = (TextView) findViewById(R.id.tvHint);
        tvActualAnswer.setVisibility(View.INVISIBLE);
        tvHint.setVisibility(View.INVISIBLE);
        questionArrayList = new ArrayList<>();

        tvOptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = tvOptionA.getText().toString();
                String hint = tvActualAnswer.getText().toString();
                presenter.isAnswerCorrect(v, answer, hint);
            }
        });

        tvOptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = tvOptionB.getText().toString();
                String hint = tvActualAnswer.getText().toString();
                presenter.isAnswerCorrect(v, answer, hint);
            }
        });

        tvOptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = tvOptionC.getText().toString();
                String hint = tvActualAnswer.getText().toString();
                presenter.isAnswerCorrect(v, answer, hint);
            }
        });

        tvOptionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = tvOptionD.getText().toString();
                String hint = "Hint: " + tvActualAnswer.getText().toString();
                presenter.isAnswerCorrect(v, answer, hint);
            }
        });
    }

    @Override
    public int shouldCountItems() {
        int size = questionArrayList.size();
        Log.i("Question:", "question size is :" + size);
        System.out.println(size);
        return size;
    }

    @Override
    public void shouldOpenRef() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("question");
    }

    @Override
    public void shouldSetQuestion(Question mQuestion) {
        if (questionArrayList != null && !questionArrayList.isEmpty()) {
            tvQuestion.setText(mQuestion.getQuestion());
            tvOptionA.setText(mQuestion.getOptionA());
            tvOptionB.setText(mQuestion.getOptionB());
            tvOptionC.setText(mQuestion.getOptionC());
            tvOptionD.setText(mQuestion.getOptionD());
            tvActualAnswer.setText(mQuestion.getHint());
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
            tvActualAnswer.setVisibility(View.VISIBLE);
            tvHint.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void shouldListenToFb() {
        Question[] questionFb = {new Question()};

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("Question Adapter: ", "item got" + snapshot.getKey());

                try {
                    questionFb[0] = snapshot.getValue(Question.class);
                    if (questionFb[0] == null)
                    {
                        Log.d("Question Adapter: ", "key" +  snapshot.getKey() + "value" + snapshot.getValue(Question.class));

                        throw new NullPointerException("key" +  snapshot.getKey() + "value" + snapshot.getValue(Question.class));
                    }
                    presenter.setQuestion(questionFb[0]);

                } catch (RuntimeException e) {
                    Log.e("Question Adapter: ", e.getMessage());
                    throw new RuntimeException(e.getMessage(), e);
                }


                questionArrayList.add(questionFb[0]);
                if (questionArrayList.size() > 0) {
                    System.out.println(questionFb[0]);

                    int size = presenter.countItems();
                    Log.i("Answer: ", "Size Of Question List" + size);
                    presenter.setQuestion(questionFb[0]);
                }else{
                    Log.e("Question Adapter: ", "no question was added from: "+ questionFb[0]);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e("Question Adapter: ", "OnChange: "+ snapshot.getKey());

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.e("Question Adapter: ", "OnRemove: "+ snapshot.getKey());

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e("Question Adapter: ", "OnMoved: "+ snapshot.getKey());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Question Adapter: ", "onCancelled: "+ error);

            }
        }
        );
        Log.d("Question Adapter: ", "item got" + questionFb[0]);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
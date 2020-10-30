package com.example.firebase.shule.activity;

import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.ValueEventListener;

public class AnswerActivity extends AppCompatActivity implements QuestionContract.View {
    private TextView tvQuestion;
    private TextView tvOptionA;
    private TextView tvOptionB;
    private TextView tvOptionC;
    private TextView tvOptionD;
    private QuestionPresenter presenter;
    private  static ArraySet<Question> questionSet;

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static Question question;
    public static ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        presenter = new QuestionPresenter(this);
        presenter.initializeFields();
        FirebaseUtil.openQuestionReference("question", new SubjectActivity());
    }

    @Override
    public void shouldInitializeFields() {
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvOptionA = (TextView) findViewById(R.id.tvOptionA);
        tvOptionB = (TextView) findViewById(R.id.tvOptionB);
        tvOptionC = (TextView) findViewById(R.id.tvOptionC);
        tvOptionD = (TextView) findViewById(R.id.tvOptionD);
        questionSet = new ArraySet<Question>();

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
        if (questionSet.isEmpty()) {
            databaseReference= FirebaseUtil.databaseReference;

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Question questionFb;
                        Log.i("Answer", "onChildAdded:" + snapshot.getKey());

                        try {
                            questionFb = snapshot.getValue(Question.class);
                            Log.i("Question: ","question found" + questionFb.getQuestion());
                        } catch (RuntimeException e) {
                            Log.e("Answer Adapter: ", e.getMessage());
                            throw new RuntimeException(e.getMessage(), e);
                        }

                        assert questionFb != null;
//                        Log.i("Answer Adapter: ", questionFb.getQuestion());
                        questionFb.setId(snapshot.getKey());
                        questionSet.add(questionFb);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("Error", "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            };
            databaseReference.addValueEventListener(valueEventListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.listenToFb();
        int size = presenter.countItems();
        assert size > 0;
        for(int i = 0; i < size; i++) {
            presenter.setQuestion();
            presenter.isAnswerCorrect();
        }
        FirebaseUtil.attachListener();
    }

}
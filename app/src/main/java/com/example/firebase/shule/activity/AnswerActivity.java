package com.example.firebase.shule.activity;

import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.widget.TextView;

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
    private QuestionPresenter presenter;
    private static ArraySet<Question> questionSet;
    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference databaseReference;
    private static Question question;
    private static ChildEventListener childEventListener;
    private TextView tvOptionA;
    private TextView tvOptionB;
    private TextView tvOptionC;
    private TextView tvOptionD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        presenter = new QuestionPresenter(this);
        presenter.listenToFb();
        question = new Question("data question", "01","02","02","03","04");
        questionSet.add(question);
        presenter.setQuestion(question);

        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvOptionA = (TextView) findViewById(R.id.tvOptionA);
        tvOptionB = (TextView) findViewById(R.id.tvOptionB);
        tvOptionC = (TextView) findViewById(R.id.tvOptionC);
        tvOptionD = (TextView) findViewById(R.id.tvOptionD);
    }

    @Override
    public void shouldSetQuestion(Question question) {
        //        databaseReference = FirebaseUtil.databaseReference;
//        questionSet = new ArraySet<Question>();
//
//        childEventListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Question questionFb;
//                Log.d("Answer", "onChildAdded:" + snapshot.getKey());
//
//                try{
//                    questionFb = snapshot.getValue(Question.class);
//                } catch (RuntimeException e){
//                    Log.e("Answer Adapter: ", e.getMessage());
//                    throw new RuntimeException(e.getMessage(), e);
//                }
//
//                Log.i("Answer Adapter: ", questionFb.getOptionC());
//                questionFb.setId(snapshot.getKey());
//                questionSet.add(questionFb);
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//        databaseReference.addChildEventListener(childEventListener);

        databaseReference = FirebaseDatabase.getInstance()
                .getReference()
                .child("question");
        // My top posts by number of stars
        ValueEventListener valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Question questionFb;
                    Log.d("Answer", "onChildAdded:" + snapshot.getKey());

                    try {
                        questionFb = snapshot.getValue(Question.class);
                    } catch (RuntimeException e) {
                        Log.e("Answer Adapter: ", e.getMessage());
                        throw new RuntimeException(e.getMessage(), e);
                    }

                    assert questionFb != null;
                    Log.i("Answer Adapter: ", questionFb.getQuestion());
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

        if (questionSet != null && !questionSet.isEmpty()) {
            Log.i("Question:", "question size is :" + questionSet.size());
            System.out.println(questionSet.size());
            for(Question mQuestion :questionSet) {
            }
        }


        tvQuestion.setText(question.getQuestion());
        tvOptionA.setText(question.getOptionA());
        tvOptionB.setText(question.getOptionB());
        tvOptionC.setText(question.getOptionC());
        tvOptionD.setText(question.getOptionD());
    }

    @Override
    public boolean shouldCheckIfAnswerSelectedIsCorrect() {
        return false;
    }

    @Override
    public void shouldListenToFb() {
        FirebaseUtil.openQuestionReference("question", new SubjectActivity());

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
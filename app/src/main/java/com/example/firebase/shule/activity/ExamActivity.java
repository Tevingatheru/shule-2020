package com.example.firebase.shule.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.shule.R;
import com.example.firebase.shule.model.Answer;
import com.example.firebase.shule.model.Exam;
import com.example.firebase.shule.model.Question;
import com.example.firebase.shule.util.FirebaseUtil;
import com.example.firebase.shule.util.MenuUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ExamActivity extends AppCompatActivity {
    EditText etTopic;
    EditText etQuestion;
    EditText etOptionA;
    EditText etOptionB;
    EditText etOptionC;
    EditText etOptionD;

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static final int PICTURE_RESULT = 42;
    private Exam exam;
    private String uri;
    private Intent intent;
    private MenuUtil menuUtil;
    private String imageName;
    private Question question;
    private Set<Answer> answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        listenToFB();
        initializeContent();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("Menu Option:", "item: " + item.getItemId() + "was selected");
        switch (item.getItemId()) {
            case R.id.save_option:
                if (exam.getId() == null) {
                    saveExam();
                    Toast.makeText(this, "Deal Saved", Toast.LENGTH_LONG).show();
                    cleanView();
                } else {
                    editExam();
                    Toast.makeText(this, "Deal Edited", Toast.LENGTH_LONG).show();
                }
                startListActivity();
                return true;

            case R.id.delete_option:
                deleteExam();
                Toast.makeText(this, "Deal Deleted", Toast.LENGTH_LONG).show();
                cleanView();
                startListActivity();
                return true;

            case R.id.view_deals_option:
                cleanView();
                startListActivity();
                return true;

            case R.id.logout_option:
                cleanView();
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_insert_menu, menu);
        if (FirebaseUtil.isMember) {
            enableEditText(false);
        } else {
            enableEditText(true);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE_RESULT && resultCode == RESULT_OK) {
            assert data != null;
            Uri imageUri = data.getData();
            StorageReference reference = FirebaseUtil.topicPicture.child(Objects.requireNonNull(imageUri.getLastPathSegment()));
            reference.putFile(imageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uri = taskSnapshot.getUploadSessionUri().toString();
                    imageName = taskSnapshot.getStorage().getPath();

                }
            });
        }
    }

    private void cleanView() {
        etTopic.setText("");
        etOptionA.setText("");
        etOptionB.setText("");
        etOptionC.setText("");
        etOptionD.setText("");
        etQuestion.setText("");

        uri = null;

        etTopic.requestFocus();
    }

    private void saveExam() {


//        topic = new Topic(etTopic.getText().toString(), uri, imageName);
//        databaseReference.push().setValue(topic);
    }

    private void initializeContent() {
        etTopic = findViewById(R.id.txtTopic);
        etQuestion = findViewById(R.id.txtQuestion);
        etOptionA = findViewById(R.id.txtOption_a);
        etOptionB = findViewById(R.id.txtOption_b);
        etOptionC = findViewById(R.id.txtOption_c);
        etOptionD = findViewById(R.id.txtOption_d);

        intent = getIntent();
        this.exam = (Exam) intent.getSerializableExtra("Deal");
        if(exam != null) {
            question = getQuestion();
            answers = getAnswers();
            exam = new Exam(question, answers);
        }
        setTravelDeal();
    }

    private Set<Answer> getAnswers() {
        return null;
    }

    private Question getQuestion() {
        return null;
    }

    private void listenToFB() {
        FirebaseUtil.openFbReference("exam");
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;
    }

    private void enableEditText(boolean isEnabled) {
        etTopic.setEnabled(isEnabled);
        etOptionA.setEnabled(isEnabled);
        etQuestion.setEnabled(isEnabled);
    }

    private void startListActivity() {
        Intent listIntent = new Intent(ExamActivity.this, TopicActivity.class);
        startActivity(listIntent);
    }

    private void deleteExam() {
        if(exam.getId()==null) {
            Toast.makeText(this, "Deal does not exist", Toast.LENGTH_LONG).show();
            Log.d("Delete: ", "failed");

            return;
        }
        Log.d("Delete: ", exam.getId());
        databaseReference.child(exam.getId()).removeValue();
        deleteImage();
    }

    private void logout() {
        menuUtil = new MenuUtil();
        menuUtil.logoutOption(this);
    }

    private void editExam() {
        exam.setTopic(etTopic.getText().toString());

        exam.setImageUri(uri);
        if(exam.getId()==null) {
            throw new NullPointerException();
        }
        Log.d("Deal: ", "Edit: " + exam.getId());

        databaseReference.child(exam.getId()).setValue(exam);
    }

    private void setTravelDeal() {
        Log.d("Deal: ", "Selected: "+ exam.getId());

//        etTopic.setText(exam.getTopic());
//        etQuestion.setText(exam.getQuestion().getQuestion());
//        etOptionA.setText(exam.getAnswer());
    }



    private void deleteImage() {
        if (exam.getImageName() != null && !exam.getImageName().isEmpty()) {
            Log.d("Image: ", "Deleted " + exam.getImageName());
            FirebaseUtil.topicPicture.child(exam.getImageName()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("Delete","Success");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Delete","Failure with: "+ e.getMessage());
                }
            });
        }
    }
}

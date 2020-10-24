package com.example.firebase.shule.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.firebase.shule.model.Topic;
import com.example.firebase.shule.util.FirebaseUtil;
import com.example.firebase.shule.util.MenuUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.Set;

public class AnswerActivity extends AppCompatActivity {
    EditText etTopic;
    EditText etQuestion;
    EditText etOptionA;
    EditText etOptionB;
    EditText etOptionC;
    EditText etOptionD;
    ImageView imageView;

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static final int PICTURE_RESULT = 42;
    private Button btnImage;
    private Exam exam;
    private String uri;
    private Intent intent;
    private MenuUtil menuUtil;
    private String imageName;
    private Question question;
    private Set<Answer> answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        listenToFB();
        initializeContent();

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent.createChooser(intent, "Insert Picture"), PICTURE_RESULT);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("Menu Option:", "item: " + item.getItemId() + "was selected");
        switch (item.getItemId()) {
            case R.id.save_option:
                if (exam.getId() == null) {
                    saveDeal();
                    Toast.makeText(this, "Deal Saved", Toast.LENGTH_LONG).show();
                    clean();
                } else {
                    editDeal();
                    Toast.makeText(this, "Deal Edited", Toast.LENGTH_LONG).show();
                }
                startListActivity();
                return true;

            case R.id.delete_option:
                deleteDeal();
                Toast.makeText(this, "Deal Deleted", Toast.LENGTH_LONG).show();
                clean();
                startListActivity();
                return true;

            case R.id.view_deals_option:
                clean();
                startListActivity();
                return true;

            case R.id.logout_option:
                clean();
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
                    showImage(uri);
                }
            });
        }
    }

    private void clean() {
        etTopic.setText("");
        etOptionA.setText("");
        etOptionB.setText("");
        etOptionC.setText("");
        etOptionD.setText("");
        etQuestion.setText("");

        uri = null;

        etTopic.requestFocus();
    }

    private void saveDeal() {

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
            answer = getAnswers();
            exam = new Exam(question, answer);
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
        FirebaseUtil.openFbReference("traveldeals", new TopicActivity());
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;
    }

    private void enableEditText(boolean isEnabled) {
        etTopic.setEnabled(isEnabled);
        etOptionA.setEnabled(isEnabled);
        etQuestion.setEnabled(isEnabled);
    }

    private void startListActivity() {
        Intent listIntent = new Intent(AnswerActivity.this, TopicActivity.class);
        startActivity(listIntent);
    }

    private void deleteDeal() {
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

    private void editDeal() {
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

        etTopic.setText(exam.getTopic());
        etQuestion.setText(exam.get());
        etOptionA.setText(exam.getPrice());
        showImage(uri);
    }

    private void showImage(String url) {
        if (url != null && !url.isEmpty()) {
            Log.d("Image: ", url);
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            Picasso.with(this)
                    .load(url)
                    .resize(width, width*2/3)
                    .centerCrop()
                    .into(imageView);
        }
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

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
import com.example.firebase.shule.domain.Questions;
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

public class QuestionActivity extends AppCompatActivity {
    EditText title;
    EditText description;
    EditText price;
    ImageView imageView;

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static final int PICTURE_RESULT = 42;
    private Button btnImage;
    private Questions deal;
    private String uri;
    private Intent intent;
    private MenuUtil menuUtil;
    private String imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

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
                if (deal.getId() == null) {
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
            StorageReference reference = FirebaseUtil.storageReference.child(Objects.requireNonNull(imageUri.getLastPathSegment()));
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
        title.setText("");
        price.setText("");
        description.setText("");
        uri = null;

        title.requestFocus();
    }

    private void saveDeal() {
        String txtTitle = title.getText().toString();
        String txtDescription = description.getText().toString();
        String txtPrice = price.getText().toString();

        deal = new Questions(txtTitle, txtPrice, txtDescription, uri, imageName);
        databaseReference.push().setValue(deal);
    }

    private void initializeContent() {
        title = findViewById(R.id.insert_title);
        description = findViewById(R.id.insert_description);
        price = findViewById(R.id.insert_price);
        imageView = findViewById(R.id.imageView);
        btnImage = findViewById(R.id.btnImage);

        intent = getIntent();
        this.deal = (Questions) intent.getSerializableExtra("Deal");
        if(deal == null) {
            deal = new Questions();
        }
        setTravelDeal();
    }

    private void listenToFB() {
        FirebaseUtil.openFbReference("traveldeals", new TopicActivity());
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;
    }

    private void enableEditText(boolean isEnabled) {
        title.setEnabled(isEnabled);
        price.setEnabled(isEnabled);
        description.setEnabled(isEnabled);
    }

    private void startListActivity() {
        Intent listIntent = new Intent(QuestionActivity.this, TopicActivity.class);
        startActivity(listIntent);
    }

    private void deleteDeal() {
        if(deal.getId()==null) {
            Toast.makeText(this, "Deal does not exist", Toast.LENGTH_LONG).show();
            Log.d("Delete: ", "failed");

            return;
        }
        Log.d("Delete: ", deal.getId());
        databaseReference.child(deal.getId()).removeValue();
        deleteImage();
    }

    private void logout() {
        menuUtil = new MenuUtil();
        menuUtil.logoutOption(this);
    }

    private void editDeal() {
        deal.setTitle(title.getText().toString());
        deal.setDescription(description.getText().toString());
        deal.setPrice(price.getText().toString());
        deal.setImageUri(uri);
        if(deal.getId()==null) {
            throw new NullPointerException();
        }
        Log.d("Deal: ", "Edit: " +deal.getId());

        databaseReference.child(deal.getId()).setValue(deal);
    }

    private void setTravelDeal() {
        Log.d("Deal: ", "Selected: "+ deal.getId());

        title.setText(deal.getTitle());
        description.setText(deal.getDescription());
        price.setText(deal.getPrice());
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
        if (deal.getImageName() != null && !deal.getImageName().isEmpty()) {
            Log.d("Image: ", "Deleted " + deal.getImageName());
            FirebaseUtil.storageReference.child(deal.getImageName()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
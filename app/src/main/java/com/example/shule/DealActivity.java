package com.example.firebase.basics.activity;

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

import com.example.firebase.basics.R;
import com.example.firebase.basics.contract.DealActivityContract;
import com.example.firebase.basics.domain.TravelDeal;
import com.example.firebase.basics.model.MenuModel;
import com.example.firebase.basics.presenter.DealActivityPresenter;
import com.example.firebase.basics.util.FirebaseUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DealActivity extends AppCompatActivity implements DealActivityContract.View {
    EditText title;
    EditText description;
    EditText price;
    ImageView imageView;

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static final int PICTURE_RESULT = 42;
    private Button btnImage;
    private TravelDeal deal;
    private String uri;
    private Intent intent;
    private MenuModel menuModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        listenToFB();
        initializeContent();

        if(deal != null) {
            setTravelDeal();
        }

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
                } else {
                    editDeal();
                    Toast.makeText(this, "Deal Edited", Toast.LENGTH_LONG).show();
                }
                clean();
//                startListActivity();
                return true;

            case R.id.delete_option:
                deleteDeal();
                Toast.makeText(this, "Deal Deleted", Toast.LENGTH_LONG).show();
                startListActivity();
                return true;

            case R.id.view_deals_option:
                startListActivity();
                return true;

            case R.id.logout_option:
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
                    showImage(uri);
                }
            });
        }
    }

    private void clean() {
        title.setText("");
        price.setText("");
        description.setText("");

        title.requestFocus();
    }

    private void saveDeal() {
        String txtTitle = title.getText().toString();
        String txtDescription = description.getText().toString();
        String txtPrice = price.getText().toString();

        deal = new TravelDeal(txtTitle, txtPrice, txtDescription, uri);
        databaseReference.push().setValue(deal);
    }

    private void initializeContent() {
        title = findViewById(R.id.insert_title);
        description = findViewById(R.id.insert_description);
        price = findViewById(R.id.insert_price);
        imageView = findViewById(R.id.imageView);
        btnImage = findViewById(R.id.btnImage);

        intent = getIntent();
        this.deal = (TravelDeal) intent.getSerializableExtra("Deal");
    }

    private void listenToFB() {
        FirebaseUtil.openFbReference("traveldeals", new ListActivity());
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;
    }

    private void enableEditText(boolean isEnabled) {
        title.setEnabled(isEnabled);
        price.setEnabled(isEnabled);
        description.setEnabled(isEnabled);
    }

    private void startListActivity() {
        Intent listIntent = new Intent(DealActivity.this, ListActivity.class);
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
    }

    private void logout() {
        menuModel = new MenuModel();
        menuModel.logoutOption(this);
    }

    private void editDeal() {
        deal.setTitle(title.getText().toString());
        deal.setDescription(description.getText().toString());
        deal.setPrice(price.getText().toString());
        if(deal.getId()==null) {
            throw new NullPointerException();
        }
        Log.d("Edit: ", deal.getId());

        databaseReference.child(deal.getId()).setValue(deal);
    }

    private void setTravelDeal() {
        title.setText(deal.getTitle());
        description.setText(deal.getDescription());
        price.setText(deal.getPrice());
        showImage(uri);
    }

    private void showImage(String url) {
        if (url != null && !url.isEmpty()) {
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            Picasso.with(this)
                    .load(url)
                    .resize(width, width*2/3)
                    .centerCrop()
                    .into(imageView);
        }
    }
}
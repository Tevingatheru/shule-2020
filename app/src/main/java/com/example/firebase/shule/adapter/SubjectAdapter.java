package com.example.firebase.shule.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.shule.R;
import com.example.firebase.shule.model.Subject;
import com.example.firebase.shule.util.FirebaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
    private static Set<Subject> subjectSet;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    private ChildEventListener childEventListener;


    public SubjectAdapter() {
        listenToFb();
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Subject subject;
                try {
                    subject = snapshot.getValue(Subject.class);
                } catch (RuntimeException e) {
                    Log.e("Subject Adapter: ", e.getMessage());
                    throw new RuntimeException(e.getMessage(), e);
                }

                Log.i("Subject Adapter: ", subject.getSubject());
                subject.setId(snapshot.getKey());
                subjectSet.add(subject);
                notifyItemInserted(subjectSet.size() - 1);
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
        Log.i("Subject Adapter: ", "Set On Child Listener");
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        try {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.subject_rv_row, parent, false);
        } catch (RuntimeException e) {
            throw new RuntimeException("error.inflating.view", e);
        }
        Log.i("Subject Adapter: ", "Create View");

        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Log.d("Position: ", "Position Of Subject" + position);
        List<Subject> subjectlist = new ArrayList<>(subjectSet);
        Subject subject = subjectlist.get(position);
        holder.bind(subject);
    }

    @Override
    public int getItemCount() {
        int count = subjectSet.size();
        Log.d("Subject RecyclerView Count","Subject Count: " + count);
        return count;
    }

    private void listenToFb() {
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;
        subjectSet = FirebaseUtil.subjectUtilList;
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivImageSubject;
        TextView tvSubject;

        public  SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubject = (TextView) itemView.findViewById(R.id.row_subject_tv);
            ivImageSubject = (ImageView) itemView.findViewById(R.id.row_subject_iv);
        }

        @Override
        public void onClick(View v) {

        }

        public void bind (Subject subject) {
            tvSubject.setText(subject.getSubject());
            if (subject.getImageUri() != null && !subject.getImageUri().isEmpty()) {
                showImage(subject.getImageUri());
            }
        }

        private void showImage(String imageUri) {
            File fileUri = new File(imageUri);
            if (imageUri != null && !imageUri.isEmpty()) {
                Log.d("Image: ", imageUri);
                Picasso.get()
                        .load(fileUri)
//                        .resize(100,100)
//                        .centerCrop()
                        .into(ivImageSubject);
            }
        }
    }
}

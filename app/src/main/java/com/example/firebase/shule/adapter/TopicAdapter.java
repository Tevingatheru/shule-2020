package com.example.firebase.shule.adapter;

import android.content.Intent;
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
import com.example.firebase.shule.model.Question;
import com.example.firebase.shule.model.Topic;
import com.example.firebase.shule.util.FirebaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {
    private static Set<Topic> topicSet;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private static ArrayList<Topic> topicList;

    public TopicAdapter() {
        listenToFb();
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Topic topic;
                try {
                    topic = snapshot.getValue(Topic.class);
                } catch (RuntimeException e) {
                    Log.e("Topic Adapter: ", e.getMessage());
                    throw new RuntimeException(e.getMessage(), e);
                }

                Log.i("Topic Adapter: ", topic.getTopic());
                topic.setId(snapshot.getKey());
                topicSet.add(topic);
                notifyItemInserted(topicSet.size() - 1);
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
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        try {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.topic_rv_row, parent, false);
        } catch (RuntimeException e) {
            throw new RuntimeException("error.inflating.view", e);
        }
        Log.i("Topic Adapter: ", "Create View");

        return new TopicAdapter.TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Log.d("Position: ", "Position Of Topic" + position);
        topicList = new ArrayList<>(topicSet);
        Topic topic = topicList.get(position);
        holder.bind(topic);
    }

    @Override
    public int getItemCount() {
        int count = topicSet.size();
        Log.d("Topic RecyclerView Count", "Topic Count: " + count);

        return count;
    }

    private void listenToFb() {
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;
        topicSet = FirebaseUtil.topicUtilList;
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvTopic;
        private ImageView ivImageTopic;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTopic = (TextView) itemView.findViewById(R.id.row_topic_tv);
            ivImageTopic = (ImageView) itemView.findViewById(R.id.row_topic_iv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d("Position: ", "Position Clicked is: " + position);
            Topic topic = topicList.get(position);
            Intent intent = new Intent(v.getContext(), Question.class);
            intent.putExtra("Topic", topic);
            v.getContext().startActivity(intent);
        }

        public void bind(Topic topic) {
            tvTopic.setText(topic.getTopic());
            if (topic.getImageUri() != null && !topic.getImageUri().isEmpty()) {
                showImage(topic.getImageUri());
            }
        }

        private void showImage(String imageUri) {
            File fileUri = new File(imageUri);
            if (imageUri != null && !imageUri.isEmpty()) {
                Log.d("Image: ", imageUri);
                Picasso.get()
                        .load(fileUri)
                        .into(ivImageTopic);
            }
        }
    }
}
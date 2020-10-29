package com.example.firebase.shule.adapter;

import android.content.res.Resources;
import android.util.ArraySet;
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
import com.example.firebase.shule.model.Topic;
import com.example.firebase.shule.model.Topic;
import com.example.firebase.shule.util.FirebaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private ArraySet<Topic> topicArraySet;


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
                            topicArraySet.add(topic);
                notifyItemInserted(topicArraySet.size() - 1);
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
        Log.i("Topic Adapter: ", "Set On Child Listener");
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        int count = topicArraySet.size();
        Log.d("Topic RecyclerView Count","Topic Count: " + count);
        return count;
    }

    private void listenToFb() {
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;

        topicArraySet = FirebaseUtil.topicUtilList;
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivImageTopic;
        TextView tvTopic;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTopic = (TextView) itemView.findViewById(R.id.row_topic_tv);
            ivImageTopic = (ImageView) itemView.findViewById(R.id.row_topic_iv);
        }

        @Override
        public void onClick(View v) {

        }

        public void bind (Topic topic) {
            tvTopic.setText(topic.getTopic());
            if (topic.getImageUri() != null && !topic.getImageUri().isEmpty()) {
                showImage(topic.getImageUri());
            }
        }

        private void showImage(String imageUri) {
            if (imageUri != null && !imageUri.isEmpty()) {
                Log.d("Image: ", imageUri);
                int width = Resources.getSystem().getDisplayMetrics().widthPixels;
                Picasso.get()
                        .load(imageUri)
                        .resize(120,120)
                        .centerCrop()
                        .into(ivImageTopic);
            }
        }
    }
}

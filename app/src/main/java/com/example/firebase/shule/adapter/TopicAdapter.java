package com.example.firebase.shule.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import com.example.firebase.shule.activity.ExamActivity;
import com.example.firebase.shule.model.Topic;
import com.example.firebase.shule.util.FirebaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.DealViewHolder> {

    ArrayList<Topic> topicList;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    public TopicAdapter() {

        listenToFB();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Topic topic;
                try {
                    topic = snapshot.getValue(Topic.class);
                } catch (RuntimeException e) {
                    Log.e("error.initializing.topic", e.getMessage(), e);
                    throw new RuntimeException(e.getMessage(), e);
                }

                Log.i("Deal: ", topic.getId());
                topic.setId(snapshot.getKey());
                topicList.add(topic);
                notifyItemInserted(topicList.size() - 1);
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
    }

    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        try {
            view = LayoutInflater.from(context).inflate(R.layout.rv_row, parent, false);
        } catch (RuntimeException e) {
            Log.e("error.inflating.view", e.getMessage(), e);
            throw new RuntimeException("error.inflating.view", e);
        }
        return new DealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {
        Topic topic = topicList.get(position);
        holder.bind(topic);
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    private void listenToFB() {
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;
        topicList = FirebaseUtil.topicUtilList;
    }

    public class DealViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView tvTopic;
        ImageView ivImageTopic;

        public DealViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTopic = (TextView) itemView.findViewById(R.id.row_topic);

            ivImageTopic = (ImageView) itemView.findViewById(R.id.row_image);

            itemView.setOnClickListener(this);
        }

        public void bind (Topic topic) {
            tvTopic.setText(topic.getTopic());

            if (topic.getImageUri() != null && !topic.getImageUri().isEmpty()) {
                showImage(topic.getImageUri());
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d("Position: ", String.valueOf(position));
            Topic topic = topicList.get(position);
            Intent intent = new Intent(v.getContext(), ExamActivity.class);
            intent.putExtra("Deal", topic);
            v.getContext().startActivity(intent);
        }

        private void showImage(String url) {
            if (url != null && !url.isEmpty()) {
                Log.d("Image: ", url);
                int width = Resources.getSystem().getDisplayMetrics().widthPixels;
                Picasso.with(ivImageTopic.getContext())
                        .load(url)
                        .resize(120,120)
                        .centerCrop()
                        .into(ivImageTopic);
            }
        }
    }
}

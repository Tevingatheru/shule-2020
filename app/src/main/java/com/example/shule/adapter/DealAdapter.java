package com.example.firebase.basics.adapter;

import android.content.Context;
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

import com.example.firebase.basics.R;
import com.example.firebase.basics.activity.DealActivity;
import com.example.firebase.basics.domain.TravelDeal;
import com.example.firebase.basics.util.FirebaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.DealViewHolder> {

    ArrayList<TravelDeal> dealList;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    public DealAdapter() {

        listenToFB();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TravelDeal travelDeal;
                try {
                    travelDeal = snapshot.getValue(TravelDeal.class);
                } catch (RuntimeException e) {
                    Log.e("error.initializing.traveldeal", e.getMessage(), e);
                    throw new RuntimeException(e.getMessage(), e);
                }

                Log.i("Deal: ", travelDeal.getTitle());
                travelDeal.setId(snapshot.getKey());
                dealList.add(travelDeal);
                notifyItemInserted(dealList.size() - 1);
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
        TravelDeal deal = dealList.get(position);
        holder.bind(deal);
    }

    @Override
    public int getItemCount() {
        return dealList.size();
    }

    private void listenToFB() {
        firebaseDatabase = FirebaseUtil.firebaseDatabase;
        databaseReference = FirebaseUtil.databaseReference;
        dealList = FirebaseUtil.deals;
    }

    public class DealViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView tvTitle;
        TextView tvPrice;
        TextView tvDescription;
        ImageView ivImageDeal;

        public DealViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.row_title);
            tvPrice = (TextView) itemView.findViewById(R.id.row_price);
            tvDescription = (TextView) itemView.findViewById(R.id.row_description);
            ivImageDeal = (ImageView) itemView.findViewById(R.id.row_image);

            itemView.setOnClickListener(this);
        }

        public void bind (TravelDeal travelDeal) {
            tvTitle.setText(travelDeal.getTitle());
            tvPrice.setText(travelDeal.getPrice());
            tvDescription.setText(travelDeal.getDescription());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d("Position: ", String.valueOf(position));
            TravelDeal travelDeal = dealList.get(position);
            Intent intent = new Intent(v.getContext(), DealActivity.class);
            intent.putExtra("Deal", travelDeal);
            v.getContext().startActivity(intent);
        }
    }
}

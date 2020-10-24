package com.example.firebase.basics.util;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firebase.basics.activity.ListActivity;
import com.example.firebase.basics.domain.TravelDeal;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {
    private static final int RC_SIGN_IN = 123;

    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseAuth firebaseAuth;
    private static FirebaseUtil firebaseUtil;
    private static ListActivity caller;
    public static boolean isMember;

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static ArrayList<TravelDeal> deals;
    public static FirebaseStorage firebaseStorage;
    public static StorageReference storageReference;

    private FirebaseUtil () {
    };

    public static void attachListener() {
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public static void detachListener() {
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    public static void openFbReference(String ref, final ListActivity activity){
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();
            caller = activity;

            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if(firebaseAuth.getCurrentUser() == null) {
                        FirebaseUtil.signIn();
                        Toast.makeText(caller.getBaseContext(), "Welcome", Toast.LENGTH_LONG).show();
                    } else {
                        String userId = firebaseAuth.getUid();
                        checkMember(userId);
                    }
                }
            };
            connectStorage();
        }
        deals = new ArrayList<TravelDeal>();
        databaseReference = firebaseDatabase.getReference().child(ref);
    }

    private static void checkMember(String userId) {
        FirebaseUtil.isMember = true;
        DatabaseReference reference = firebaseDatabase.getReference().child("administrators").child(userId);
        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FirebaseUtil.isMember = false;
                Log.d("Member","Member Logged In: " + false);
                caller.showMenu();
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
        reference.addChildEventListener(listener);
    }

    private static void signIn() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    public static void connectStorage() {
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("deals_pictures");
    }
}

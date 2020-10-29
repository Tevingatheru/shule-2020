package com.example.firebase.shule.util;

import android.util.ArraySet;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firebase.shule.activity.SubjectActivity;
import com.example.firebase.shule.model.Question;
import com.example.firebase.shule.model.Subject;
import com.example.firebase.shule.model.Topic;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {
    private static final int RC_SIGN_IN = 123;

    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseAuth firebaseAuth;
    private static FirebaseUtil firebaseUtil;
    private static SubjectActivity caller;
    public static boolean isMember;

    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static ArraySet<Topic> topicUtilList;
    public static ArraySet<Question> questionUtilList;
    public static ArraySet<Subject> subjectUtilList;

    public static FirebaseStorage firebaseStorage;
    public static StorageReference topicPicture;
    public static StorageReference subjectPicture;

    private FirebaseUtil () {
    };

    public static void attachListener() {
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public static void detachListener() {
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    private static FirebaseAuth.AuthStateListener checkAuth() {
        return new FirebaseAuth.AuthStateListener() {
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
    }

    public static void openFbReference(String ref, final SubjectActivity activity){
        if (firebaseUtil == null) {
            initializeFirebase();
            caller = activity;
            authStateListener = checkAuth();
            connectTopicStorage();
            connectSubjectStorage();
        }
        initializeLists();
        databaseReference = firebaseDatabase.getReference().child(ref);
    }

    public static void openQuestionReference(String ref, final SubjectActivity activity){
        if (firebaseUtil == null) {
            initializeFirebase();
            caller = activity;
            authStateListener = checkAuth();
        }

        questionUtilList = new ArraySet<Question>();
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

    public static void connectTopicStorage() {
        firebaseStorage = FirebaseStorage.getInstance();
        topicPicture = firebaseStorage.getReference().child("topic_pictures");
    }

    public static void connectSubjectStorage() {
        firebaseStorage = FirebaseStorage.getInstance();
        subjectPicture = firebaseStorage.getReference().child("subject_pictures");
    }

    private static void initializeFirebase() {
        firebaseUtil = new FirebaseUtil();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private static void initializeLists() {
        topicUtilList = new ArraySet<Topic>();
        subjectUtilList = new ArraySet<Subject>();
    }
}

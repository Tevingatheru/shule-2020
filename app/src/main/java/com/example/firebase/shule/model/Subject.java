package com.example.firebase.shule.model;

import java.io.Serializable;

public class Subject implements Serializable {
    private String id;
    private String subject;
    private String imageUri;
    private String imageName;

    public Subject() {
    }

    public Subject(String subject, String imageUri, String imageName) {
        this.id = id;
        this.subject = subject;
        this.imageUri = imageUri;
        this.imageName = imageName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}

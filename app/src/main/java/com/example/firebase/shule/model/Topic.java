package com.example.firebase.shule.model;

import java.io.Serializable;

public class Topic implements Serializable {
    private String id;
    private String topic;
    private String imageUri;
    private String imageName;

    public Topic() {
    }

    public Topic(String topic, String imageUri, String imageName) {
        this.id = id;
        this.topic = topic;
        this.imageUri = imageUri;
        this.imageName = imageName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

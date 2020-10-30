package com.example.firebase.shule.model;

import java.io.Serializable;

public class Topic implements Serializable {
    private String id;
    private String subjectId;
    private String topic;
    private String imageUri;
    private String imageName;

    public Topic() {
    }

    public Topic(String subjectId,  String topic, String imageUri, String imageName) {
        this.id = id;
        this.subjectId = subjectId;
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

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

}

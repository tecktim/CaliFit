package com.example.CaliFit;

import java.io.Serializable;

public class Exercise implements Serializable {


    public enum Category {
        Core, Legs, Pull, Push

    }

    public String name;
    String description;
    String linkToVideo;
    public Category category;

    public Exercise(String name, String description, String linkToVideo, Category category) {
        this.name = name;
        this.description = description;
        this.linkToVideo = linkToVideo;
        this.category = category;

    }

    public Exercise() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void printName() {
        System.out.println(this.name);
    }

    public void printDescription() {
        System.out.println(this.description);
    }

    public void printLinkToVideo() {
        System.out.println(this.linkToVideo);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLinkToVideo(String linkToVideo) {
        this.linkToVideo = linkToVideo;
    }

    public void setCategory(Exercise.Category category) {
        this.category = category;
    }
}

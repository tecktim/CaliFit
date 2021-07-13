package com.example.CaliFit;

import java.io.Serializable;

public class Exercise implements Serializable {


    public enum Category {
        Core, Legs, Pull, Push

    }

    public String name;
    String description;
    String linkToVideo;
    String sets;
    String reps;
    public Category category;
    public boolean draw;
    public boolean added;


    public Exercise(String name, String description, String linkToVideo, Category category) {
        this.name = name;
        this.description = description;
        this.linkToVideo = linkToVideo;
        this.category = category;
        this.draw = false;
        this.added = false;
    }

    public Exercise() {
    }

    public void setSets(String sets){
        this.sets = sets;
    }

    public void setReps(String reps){
        this.reps = reps;
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

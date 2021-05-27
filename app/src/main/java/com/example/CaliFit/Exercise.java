package com.example.CaliFit;

public class Exercise {
    public enum Category {
        Push, Pull, Legs, Core
    }

    String name;
    String description;
    String linkToVideo;
    Category category;

    public Exercise(String name, Category category) {
        this.name = name;
        this.category = category;
    }
}

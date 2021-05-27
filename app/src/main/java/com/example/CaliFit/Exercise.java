package com.example.CaliFit;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Exercise implements Serializable {

    public enum Category {
        Push, Pull, Legs, Core
    }

    public String name;
    String description;
    String linkToVideo;
    public Category category;

    public Exercise(String name, Category category) {
        this.name = name;
        this.category = category;
    }

}

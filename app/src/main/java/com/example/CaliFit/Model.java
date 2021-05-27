package com.example.CaliFit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Model implements Parcelable {
    private int mData;

    public Model(){

    }

    List<Exercise> PushList = new ArrayList<Exercise>();
    List<Exercise> PullList = new ArrayList<Exercise>();
    List<Exercise> LegsList = new ArrayList<Exercise>();
    List<Exercise> CoreList = new ArrayList<Exercise>();

    public void addListItem(Exercise exercise) {

        switch(exercise.category) {
            case Push: PushList.add(exercise);
                break;
            case Pull: PullList.add(exercise);
                break;
            case Legs: LegsList.add(exercise);
                break;
            case Core: CoreList.add(exercise);
                break;
            default: return;
        }
    }

    public List<Exercise> getList(Exercise.Category category) {
        switch(category) {
            case Push: return PushList;
            case Pull: return PullList;
            case Legs: return LegsList;
            case Core: return CoreList;
            default: return null;
        }
    }

    // Parcellable made with help from https://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Model> CREATOR = new Parcelable.Creator<Model>() {
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Model(Parcel in) {
        mData = in.readInt();
    }
}

package com.example.CaliFit;

import java.util.ArrayList;
import java.util.List;

public class Model {
    List<Exercise> PushList = new ArrayList<>();
    List<Exercise> PullList = new ArrayList<>();
    List<Exercise> LegsList = new ArrayList<>();
    List<Exercise> CoreList = new ArrayList<>();

    public Model getModel() {
        return this;
    }

    public void setPushList(ArrayList<Exercise> list){
        this.PushList = list;
    }
    public void setPullList(ArrayList<Exercise> list){
        this.PullList = list;
    }
    public void setLegsList(ArrayList<Exercise> list){
        this.LegsList = list;
    }
    public void setCoreList(ArrayList<Exercise> list){
        this.CoreList = list;
    }


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
        switch (category) {
            case Push:
                return PushList;
            case Pull:
                return PullList;
            case Legs:
                return LegsList;
            case Core:
                return CoreList;
            default:
                return null;
        }
    }
}

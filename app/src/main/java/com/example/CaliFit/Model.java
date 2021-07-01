package com.example.CaliFit;

import java.util.ArrayList;
import java.util.List;

public class Model {
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
}

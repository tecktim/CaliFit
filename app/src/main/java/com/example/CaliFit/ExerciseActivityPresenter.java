package com.example.CaliFit;

import android.support.v7.widget.ButtonBarLayout;

public class ExerciseActivityPresenter {
    private Model model;
    private ViewInterface viewInterface;

    public ExerciseActivityPresenter(Model model, ViewInterface viewInterface) {
        this.model = model;
        this.viewInterface = viewInterface;
    }

    public void addExercise(Exercise exercise) {
        model.addListItem(exercise);
    }

    public Model getModel() {
        return this.model;
    }


    public interface ViewInterface{
    }
}

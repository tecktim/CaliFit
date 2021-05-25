package com.example.CaliFit;

public class WorkoutActivityPresenter {
    private WorkoutModel workoutModel;
    private ViewInterface viewInterface;

    public WorkoutActivityPresenter(WorkoutActivityPresenter.ViewInterface viewInterface) {
        this.workoutModel = new WorkoutModel();
        this.viewInterface = viewInterface;
    }

    public interface ViewInterface{

    }
}

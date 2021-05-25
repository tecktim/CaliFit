package com.example.CaliFit;

public class WorkoutActivityPresenter {
    private Model model;
    private ViewInterface viewInterface;

    public WorkoutActivityPresenter(WorkoutActivityPresenter.ViewInterface viewInterface) {
        this.model = new Model();
        this.viewInterface = viewInterface;
    }

    public interface ViewInterface{

    }
}

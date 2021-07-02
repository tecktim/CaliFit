package com.example.CaliFit;

import java.util.ArrayList;

public class WorkoutActivityPresenter {
    private Model model;
    private ViewInterface viewInterface;

    public WorkoutActivityPresenter(WorkoutActivityPresenter.ViewInterface viewInterface) {
        this.model = new Model();
        this.viewInterface = viewInterface;
    }

    public Model getModel() {
        return this.model;
    }

    public void setModel(Model model){
        this.model = model;
    }

    public void debugPrint() {
        System.out.println(model.PushList.get(0).name);
    }

    public void setPushList(ArrayList<Exercise> list){
        model.PushList = list;
    }
    public void setPullList(ArrayList<Exercise> list){
        model.PullList = list;
    }
    public void setLegsList(ArrayList<Exercise> list){
        model.LegsList = list;
    }
    public void setCoreList(ArrayList<Exercise> list){
        model.CoreList = list;
    }

    public interface ViewInterface{
    }
}

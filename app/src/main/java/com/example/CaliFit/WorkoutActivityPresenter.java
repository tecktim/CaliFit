package com.example.CaliFit;

import java.util.List;

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



    public String printName(Exercise.Category category, int i) {
        List<Exercise> listToPrint = this.model.getList(category);
        if(i > listToPrint.size()) {
            return "failed";
        }
        return listToPrint.get(i).name;
    }

    public interface ViewInterface{
    }
}

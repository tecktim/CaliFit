package com.example.CaliFit;

public class HomeActivityPresenter {
    private CounterModel counterModel;
    private ViewInterface viewInterface;

    public HomeActivityPresenter(ViewInterface viewInterface) {
        this.counterModel = new CounterModel();
        this.viewInterface = viewInterface;
    }
    public void incrementCounter(){
        counterModel.modifyCounter(1);
        viewInterface.updateCounter(counterModel.getCounter());
    }
    public void decrementCounter(){
        counterModel.modifyCounter(-1);
        viewInterface.updateCounter(counterModel.getCounter());
    }
    public void resetCounter(){
        counterModel.resetCounter();
        viewInterface.updateCounter(counterModel.getCounter());
    }
    public int getCounter(){
        return counterModel.getCounter();
    }

    public interface ViewInterface{
        void updateCounter(int counter);
    }
}

package com.example.CaliFit;

public class HomeActivityPresenter {
    private HomeModel homeModel;
    private ViewInterface viewInterface;

    public HomeActivityPresenter(ViewInterface viewInterface) {
        this.homeModel = new HomeModel();
        this.viewInterface = viewInterface;
    }

    public void incrementCounter(){
        homeModel.modifyCounter(1);
        viewInterface.updateCounter(homeModel.getCounter());
    }
    public void decrementCounter(){
        homeModel.modifyCounter(-1);
        viewInterface.updateCounter(homeModel.getCounter());
    }
    public void resetCounter(){
        homeModel.resetCounter();
        viewInterface.updateCounter(homeModel.getCounter());
    }
    public int getCounter(){
        return homeModel.getCounter();
    }

    public interface ViewInterface{
        void updateCounter(int counter);
    }
}

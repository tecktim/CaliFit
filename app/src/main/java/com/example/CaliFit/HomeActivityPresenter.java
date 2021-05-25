package com.example.CaliFit;

public class HomeActivityPresenter {
    private Model model;
    private ViewInterface viewInterface;

    public HomeActivityPresenter(ViewInterface viewInterface) {
        this.model = new Model();
        this.viewInterface = viewInterface;
    }

    public interface ViewInterface{

    }
}

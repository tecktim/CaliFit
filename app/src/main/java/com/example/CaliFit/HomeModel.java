package com.example.CaliFit;

public class HomeModel {
    private int counter;
    void CounterModel() {
        counter = 0;
    }
    int getCounter() {
        return counter;
    }
    void resetCounter() {
        counter = 0;
    }
    void modifyCounter(int inc) {
        counter += inc;
    }
}

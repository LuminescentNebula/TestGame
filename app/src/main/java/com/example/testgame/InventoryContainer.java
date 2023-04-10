package com.example.testgame;

import com.example.testgame.interfaces.InventoryContainerListener;

public class InventoryContainer{
    private int fuel=0;
    private int food=0;
    private int material1=0;
    private int material2=0;

    private InventoryContainerListener inventoryContainerListener;


    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setInventoryContainerListener(InventoryContainerListener inventoryContainerListener) {
        this.inventoryContainerListener = inventoryContainerListener;
    }

    public void updateTopBar() {
        inventoryContainerListener.setFuel(fuel);
        inventoryContainerListener.setFood(food);
    }

    public void changeFuel(int amount) {
        fuel+=amount;
        updateTopBar();
    }
    public int changeFood(int amount) {
        food+=amount;
        updateTopBar();
        return food;
    }
}

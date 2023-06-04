package com.example.testgame.models;

import com.example.testgame.PlaceHolder;
import com.example.testgame.interfaces.InventoryContainerListener;

import java.util.Dictionary;

public class InventoryContainer{
    private int fuel=0;
    private int food=0;
    private Dictionary<String,PlaceHolder> materials;
    private transient InventoryContainerListener inventoryContainerListener;


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
    public void changeFood(int amount) {
        food+=amount;
        updateTopBar();
    }
}

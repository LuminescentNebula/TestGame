package com.example.testgame;

import android.util.Log;
import android.widget.Toast;

import com.example.testgame.PlaceHolder;

import java.util.ArrayList;

public class Prisoner{
    //Limit parameters
    //Todo: добавить изображение персонажа

    private String name;
    private Integer maxHealth=100;
    private Integer maxHunger=100;
    private Integer maxStress=100;

    //Current parameters
    private Integer health;
    private Integer hunger;
    private Integer stress;

    private boolean isDisplayed;

    public Integer getHealth() {
        return health;
    }

    public Integer getHunger() {
        return hunger;
    }

    public Integer getStress() {
        return stress;
    }

    public Integer getCost() {
        return cost;
    }

    private Integer cost;
    private ArrayList<PlaceHolder> quirks;
    private PlaceHolder room;

    public Prisoner(Integer health, Integer hunger, Integer stress, Integer cost,String name) {
        this.health = health;
        this.hunger = hunger;
        this.stress = stress;
        this.cost = cost;
        this.name=name;
    }

    //TODO: Добавить связь с модификатором
    //TODO: Убрать уход в минус при большом дельта
    public void update(int delta){
        Log.i("DELTA",String.valueOf(-delta));
        if (hunger<=0){
            changeHealth( -delta);
            //Log.i("LIVE",String.valueOf(health));
        }
        else {
            changeHunger( -delta);
            //Log.i("DYING",String.valueOf(hunger));
        }
    }

    public void changeHealth(Integer delta) {
        if (this.health+delta<maxHealth) {
            this.health += delta;
            if (this.health<0) {
                die();
            }
        }
    }
    public void changeHunger(Integer delta) {
        if (!isDead()) {
            hunger = (hunger + delta);
            if (hunger > maxHunger) {
                hunger = maxHunger;
            }
        }
    }
    public void changeStress(Integer delta) {
        if (!isDead()) {
            stress = (stress + delta);
            if (stress > maxStress) {
                stress = maxStress;
            }
        }
    }

    private void die() {
        Log.i("DEAD","He is dead");
    }

    public void setHealth(Integer health) {
        this.health = health;
    }
    public void setHunger(Integer hunger) {
        this.hunger = hunger;
    }
    public void setStress(Integer stress) {
        this.stress = stress;
    }

    public boolean isDead() {
        return health==0;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    public void setDisplayed(boolean displayed) {
        isDisplayed = displayed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


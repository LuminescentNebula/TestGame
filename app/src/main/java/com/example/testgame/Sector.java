package com.example.testgame;

import java.util.ArrayList;

public class Sector {
    float hungerMod;
    float stressMod;
    float damageMod;
    PlaceHolder event;


    //x,y
    ArrayList<PlaceHolder> activities;
    //x,r
    ArrayList<PlaceHolder> planets;
    //x,y
    ArrayList<PlaceHolder> enemies;

    public Sector(float hungerMod, float stressMod, float damageMod,
                  PlaceHolder event,
                  ArrayList<PlaceHolder> activities,
                  ArrayList<PlaceHolder> planets,
                  ArrayList<PlaceHolder> enemies) {
        this.hungerMod = hungerMod;
        this.stressMod = stressMod;
        this.damageMod = damageMod;
        this.event = event;
        this.activities = activities;
        this.planets = planets;
        this.enemies = enemies;
    }

    //for preview
    public void getResources(){}

    public float getHungerMod() {
        return hungerMod;
    }

    public float getStressMod() {
        return stressMod;
    }

    public float getDamageMod() {
        return damageMod;
    }

    public PlaceHolder getEvent() {
        return event;
    }
}

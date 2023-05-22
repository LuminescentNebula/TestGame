package com.example.testgame.models;

import android.graphics.Color;
import android.util.Log;

import com.example.testgame.PlaceHolder;

import java.util.ArrayList;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

public class Sector {
    @Getter
    float hungerMod,stressMod,damageMod;

    @Getter
    private String name;
    @Getter
    private int x,y,size,color;
    private static final Random random= new Random();
    @Getter
    ArrayList<Integer> connections= new ArrayList<>();

//    PlaceHolder event;
//    //x,y
//    ArrayList<PlaceHolder> activities;
//    //x,r
//    ArrayList<PlaceHolder> planets;
//    //x,y
//    ArrayList<PlaceHolder> enemies;


    public Sector(int x, int y){
        this.x=x;
        this.y=y;
    }
    public Sector(float hungerMod, float stressMod, float damageMod,
                  PlaceHolder event,
                  ArrayList<PlaceHolder> activities,
                  ArrayList<PlaceHolder> planets,
                  ArrayList<PlaceHolder> enemies) {
        this.hungerMod = hungerMod;
        this.stressMod = stressMod;
        this.damageMod = damageMod;
//        this.event = event;
//        this.activities = activities;
//        this.planets = planets;
//        this.enemies = enemies;
    }

    public void add(int point){
        connections.add(point);
    }

    public static boolean distance(Sector point1,Sector point2, int distance) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2)+
                Math.pow(point1.y - point2.y, 2)) >= distance;
    }

    public void fill() {
        //Call generate methods
        name=generateName();
        this.size=10+random.nextInt(20);
        color=generateColor();
    }


    private PlaceHolder generateEvents(){
        return new PlaceHolder();
    }
    private PlaceHolder generatePlanets(){
        return new PlaceHolder();
    }

    private String generateName(){
        //TODO:Сформировать словарь и по нему генерировать, Словарь в статичную перемнную

        return "Сектор "+(1000+random.nextInt(8999));
    }
    private int generateColor(){
        //TODO:Проанализировать полученные параметры и выбрать цвет
        int color =
                (255 << 24) |
                (random.nextInt(255) << 16) |
                (random.nextInt(255) <<  8) |
                (random.nextInt(255));
        Log.d("Color", String.valueOf(color));
    return color;
    }



    //for preview
    public void getResources(){}
}

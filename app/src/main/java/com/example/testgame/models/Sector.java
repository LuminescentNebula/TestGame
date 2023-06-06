package com.example.testgame.models;

import android.util.Log;

import com.example.testgame.PlaceHolder;

import java.util.ArrayList;
import java.util.Random;

public class Sector {

    public static final int STAR = 1;
    public static final int DOT = 2;
    public static final int BLACK = 3;
    public static final int NOVA = 4;

    float hungerMod,stressMod,damageMod;

    private String name;
    private int x,y,size,color, type;
    private static final Random random= new Random();
    ArrayList<Integer> connections= new ArrayList<>();
    ArrayList<Planet> planets;


//    PlaceHolder event;
//    //x,y
//    ArrayList<PlaceHolder> activities;

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
        this.size=15+random.nextInt(15);
        type = random.nextInt(5)+1;
        Log.d("TAG", String.valueOf(type));

        name = generateName();
        generateStarColor();
        generatePlanets();
    }


    private PlaceHolder generateEvents(){
        return new PlaceHolder();
    }
    private void generatePlanets(){
        planets=new ArrayList<Planet>();
        for (int i=0;i < random.nextInt(3)+3;i++){
            planets.add(new Planet(random,generateName()));
        };
    }

    private String generateName(){
        //TODO:Сформировать словарь и по нему генерировать, Словарь в статичную перемнную

        return  "Сектор "+(1000+random.nextInt(8999));
    }
    private void generateStarColor(){
        //TODO:Проанализировать полученные параметры и выбрать цвет
        int[] colors= {0xFF9DB4FF,0xFFA2B9FF,0xFFA7BCFF,0xFFAABFFF,0xFFBACCFF,
         0xFFC0D1FF,0xFFCAD8FF,0xFFC4E8FF,0xFFEDEEFF,0xFFFFF9F9,
         0xFFFFF5EC,0xFFFFF4E8,0xFFFFF1DF,0xFFFFEBD1,0xFFFFD7AE,
         0xFFFFC690,0xFFFBE690,0xFFFFBE7F,0xFFFFBB7B};
    color= colors[random.nextInt(19)];
    }



    //for preview
    public void getResources(){}

    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    public Planet getPlanet(int i) {
        return planets.get(i);
    }

    public int getPlanetsSize() {
        return planets.size();
    }

    public float getHungerMod() {
        return hungerMod;
    }

    public float getStressMod() {
        return stressMod;
    }

    public float getDamageMod() {
        return damageMod;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getColor() {
        return color;
    }

    public int getType() {
        return type;
    }

    public ArrayList<Integer> getConnections() {
        return connections;
    }
}

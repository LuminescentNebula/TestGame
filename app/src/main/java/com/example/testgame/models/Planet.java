package com.example.testgame.models;

import java.util.Random;

public class Planet {

    public static final int GAS = 1;
    public static final int STONE = 2;
    public static final int ASTEROID = 3;
    public static final int STAR = 4;

    private float x,r,rotation;
    private int seed, type,size;
    private String name;


    public Planet(Random random,String name) {
        x=random.nextInt();
        r=random.nextInt();
        type=GAS;//TODO: random
        size= random.nextInt(120)+50;
        seed= random.nextInt();
        rotation=random.nextInt(360)-180;
        this.name=name; //TODO: Custom name generator?
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRotation() {
        return rotation;
    }
}

package com.example.testgame.data;


import android.util.Log;

import com.example.testgame.models.Sector;

import java.util.ArrayList;
import java.util.Random;

public  class  StarMapGenerator {

    private static final int minimumDistance = 170;
    private static final int number = 75;
    private static final int areaWidth = 3120;
    private static final int areaHeight = 1440;
    public static ArrayList<Sector> generate(long seed) {
        Random random = new Random(seed);
        Sector firstPoint = new Sector(random.nextInt(areaWidth), random.nextInt(areaHeight));

        ArrayList<Sector> activePoints = new ArrayList<>();

        activePoints.add(firstPoint);
        int n = 0;
        while (activePoints.size() < number && n < 400000) {
            n++;
            //Log.i("Tag", "Generating" + n);
            Sector point = new Sector(
                    Math.min(Math.max(40 + random.nextInt(15), random.nextInt(areaWidth)), areaWidth - 40 - random.nextInt(15)),
                    Math.min(Math.max(40 + random.nextInt(15), random.nextInt(areaHeight)), areaHeight - 40 - random.nextInt(15)));
            boolean isDistributed = true;
            for (int i = 0; i < activePoints.size(); i++) {
                if (!Sector.distance(point, activePoints.get(i), minimumDistance)) {
                    isDistributed = false;
                    break;
                } else if (!Sector.distance(point, activePoints.get(i), (int) (minimumDistance * 1.7))) {
                    point.add(i);
                }
            }
            if (isDistributed) {
                for (int i: point.getConnections()){
                    activePoints.get(i).add(activePoints.size());
                }
                point.fill();
                activePoints.add(point);
            }
        }
        return activePoints;
    }
}




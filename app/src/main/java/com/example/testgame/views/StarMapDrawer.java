package com.example.testgame.views;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.testgame.R;
import com.example.testgame.interfaces.StarMapDrawerListener;
import com.example.testgame.models.Sector;

import java.util.ArrayList;

public class StarMapDrawer extends MovableDrawer {
    private StarMapThread starMapThread;
    private int selected;
    private ArrayList<Sector> map;
    private StarMapDrawerListener starMapDrawerListener;

    public StarMapDrawer(Context context,ArrayList<Sector> map,StarMapDrawerListener starMapDrawerListener) {
        super(context);
        this.starMapDrawerListener=starMapDrawerListener;
        this.map=map;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
        selected=-1;

        starMapThread = new StarMapThread();
        starMapThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        starMapThread.stopThread();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN && map!=null) {
            selected = -1;
            for (int i = 0; i < map.size(); i++) {
                if (Math.abs(event.getX() - (map.get(i).getX() + xMod)) < 48
                        && Math.abs(event.getY() - (map.get(i).getY() + yMod)) < 48) {
                    Log.d("Touch", String.valueOf(i));
                    selected = i;
                    break;
                }
            }
            starMapDrawerListener.onSectorTouch(selected,xMod,yMod);
        }
        return true;
    }

    private class StarMapThread extends Thread {
        private boolean running = true;
        private int rotation=0;
        private Paint circles, circlesBlur,lines,select;

        private final float starSide=4/3f;


        public StarMapThread(){
            circles = new Paint();
            circles.setAntiAlias(true);
            circles.setColor(Color.WHITE);

            circlesBlur = new Paint();
            circlesBlur.setAntiAlias(true);
            circlesBlur.setColor(Color.WHITE);
            circlesBlur.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));

            lines = new Paint();
            lines.setStyle(Paint.Style.FILL_AND_STROKE);
            lines.setColor(0x44FFFFFF);
            lines.setStrokeWidth(8);

            select = new Paint();
            select.setStyle(Paint.Style.STROKE);
            select.setColor(0xFFFF2A00);
            select.setStrokeWidth(5);
            select.setPathEffect(new DashPathEffect(new float[]{25, 10}, 0));
        }

        @Override
        public void run() {
            while (running) {
                if (holder.getSurface().isValid()) {
                    Canvas canvas = holder.lockCanvas();

                    if (canvas != null) {
                        canvas.drawColor(getResources().getColor(R.color.space,null));
                        canvas.save();

                        for (Sector point : map) {
                            for (int i : point.getConnections()) {
                                canvas.drawLine(
                                        point.getX()+xMod,
                                        point.getY()+yMod,
                                        map.get(i).getX()+xMod,
                                        map.get(i).getY()+yMod,
                                        lines);
                            }
                        }
                        canvas.restore();
                        if (map!=null) {
                            for (Sector point: map){
                                if (point.getType()== Sector.STAR) {
                                    Path path = new Path();
                                    //TODO: Offset and Matrix size
                                    path.moveTo(point.getX()+xMod,
                                            point.getY()+yMod-(
                                                    (starSide+starSide/3)
                                                            * point.getSize()));
                                    //TODO: Нормальный размер звезд
                                    float side=starSide * point.getSize();
                                    float corner=(starSide/3) * point.getSize();
                                    path.rLineTo(corner, side);
                                    path.rLineTo(side, corner);
                                    path.rLineTo(-side, corner);
                                    path.rLineTo(-corner, side);
                                    path.rLineTo(-corner, -side);
                                    path.rLineTo(-side, -corner);
                                    path.rLineTo(side, -corner);
                                    path.rLineTo(corner, -side);
                                    path.close();
                                    circlesBlur.setColor(point.getColor());
                                    circles.setColor(point.getColor());
                                    canvas.drawPath(path, circlesBlur);
                                    canvas.drawPath(path, circles);
                                } else {
                                    circles.setColor(point.getColor());
                                    circlesBlur.setColor(point.getColor());
                                    canvas.drawCircle(point.getX()+xMod, point.getY()+yMod, point.getSize(), circlesBlur);
                                    canvas.drawCircle(point.getX()+xMod, point.getY()+yMod, point.getSize() - 1, circles);
                                }
                            }
                            if (selected >= 0) {
                                canvas.rotate(rotation,
                                        map.get(selected).getX()+xMod,
                                        map.get(selected).getY()+yMod);
                                rotation = (rotation + 1) % 360;
                                canvas.drawCircle(
                                        map.get(selected).getX()+xMod,
                                        map.get(selected).getY()+yMod,
                                        40,
                                        select);
                            }
                            canvas.save();
                        }

                        holder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
        public void stopThread() {
            running = false;
        }
    }
}
package com.example.testgame.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.testgame.R;
import com.example.testgame.interfaces.StarMapDrawerListener;
import com.example.testgame.models.Sector;

import java.util.ArrayList;

public class StarMapDrawer extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private BallThread ballThread;
    private int selected;
    private ArrayList<Sector> map;
    private StarMapDrawerListener starMapDrawerListener;
    private int width,height;
    private float x,y;
    private float xMod,yMod;

    public StarMapDrawer(Context context,ArrayList<Sector> map,StarMapDrawerListener starMapDrawerListener) {
        super(context);
        this.starMapDrawerListener=starMapDrawerListener;
        setWillNotDraw(false);
        holder = getHolder();
        holder.addCallback(this);
        this.map=map;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        selected=-1;

        width=getWidth();
        height=getHeight();

        xMod=width/3120f;
        yMod=height/1440f;

        ballThread = new BallThread();
        ballThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        ballThread.stopThread();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && map!=null) {
            x = event.getX();
            y = event.getY();
            Log.d("Touch", "onTouchEvent: "+(x+xMod)+" "+(y+yMod));
            selected = -1;
            for (int i = 0; i < map.size(); i++) {
                if (Math.abs(event.getX() - (map.get(i).getX() + xMod)) < 48
                        && Math.abs(event.getY() - (map.get(i).getY() + yMod)) < 48) {
                    Toast.makeText(getContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
                    selected = i;
                    break;
                }
            }
            //Log.d("Touch", String.valueOf(selected));
            starMapDrawerListener.onSectorTouch(selected,xMod,yMod);
        } else if (event.getAction()==MotionEvent.ACTION_MOVE) {
            xMod+=event.getX()-x;
            yMod+=event.getY()-y;
            xMod=Math.min(75,Math.max(xMod,-width));
            yMod=Math.min(75,Math.max(yMod,-height));
            //Log.d("w/h", getWidth() +" "+ getHeight());
            //Log.d("MOD", xMod +" "+ yMod);
            x=event.getX();
            y=event.getY();
        }
        return true;
    }

    private class BallThread extends Thread {
        private boolean running = true;
        private int rotation=0;
        private Paint circles = new Paint();
        private Paint lines = new Paint();
        private Paint select = new Paint();

        public BallThread(){
            circles.setAntiAlias(true);
            circles.setColor(Color.WHITE);

            lines.setStyle(Paint.Style.FILL_AND_STROKE);
            lines.setColor(0x88FFFFFF);
            lines.setStrokeWidth(8);

            select.setStyle(Paint.Style.STROKE);
            select.setColor(getResources().getColor(R.color.important,null));
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

                        if (map!=null) {
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
                            for (Sector point: map){
                                circles.setColor(point.getColor());
                                canvas.drawCircle(
                                        point.getX()+xMod,
                                        point.getY()+yMod,
                                        point.getSize(),
                                        circles);
                            }
                            canvas.restore();
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
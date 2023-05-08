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
    Context context;
    private int selected;
    private ArrayList<Sector> map;

    private StarMapDrawerListener starMapDrawerListener;

    //    private float x,y;
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

        xMod=getWidth()/3120f;
        yMod=getHeight()/1440f;

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
//            x = event.getX();
//            y = event.getX();
            boolean found = false;
            selected = -1;
            for (int i = 0; i < map.size(); i++) {
                if (Math.abs(event.getX() - (map.get(i).getX() *xMod)) < 48
                        && Math.abs(event.getY() - (map.get(i).getY() *yMod)) < 48) {
                    Toast.makeText(getContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
                    selected = i;
                    found = true;
                    starMapDrawerListener.onSectorTouch(i);
                    break;
                }
            }
        }
//        } else if (event.getAction()==MotionEvent.ACTION_MOVE) {
//            xMod+=event.getX()-x;
//            //yMod+=event.getY()-y;
//            xMod=Math.min(75,Math.max(xMod,-130));
//            //yMod=Math.min(75,Math.max(yMod,-400));
//            Log.i("MOD", String.valueOf(yMod));
//            Log.i("MODy", String.valueOf(y));
//
//            x=event.getX();
//            y=event.getY();
//        }
        return true;
    }

    private class BallThread extends Thread {
        private boolean running = true;
        private int rotation=0;

        @Override
        public void run() {
            Paint circles = new Paint();
            circles.setAntiAlias(true);
            circles.setColor(Color.WHITE);

            Paint lines = new Paint();
            lines.setStyle(Paint.Style.FILL_AND_STROKE);
            lines.setColor(0x88FFFFFF);
            lines.setStrokeWidth(8);

            Paint select = new Paint();
            select.setStyle(Paint.Style.STROKE);
            select.setColor(getResources().getColor(R.color.important,null));
            select.setStrokeWidth(5);

            while (running) {
                if (holder.getSurface().isValid()) {
                    Canvas canvas = holder.lockCanvas();

                    if (canvas != null) {
                        canvas.drawColor(0xFF272744);
                        canvas.save();

                        if (map!=null) {
                            for (Sector point : map) {
                                canvas.drawCircle(point.getX()*xMod, point.getY()*yMod, point.getSize(), circles);
                                for (int i : point.getConnections()) {
                                    canvas.drawLine(point.getX()*xMod, point.getY()*yMod,
                                            map.get(i).getX()*xMod, map.get(i).getY()*yMod, lines);
                                    select.setPathEffect(new DashPathEffect(new float[]{25, 10}, 0));
                                }
                            }
                            canvas.restore();
                            if (selected >= 0) {
                                canvas.rotate(rotation, map.get(selected).getX()*xMod, map.get(selected).getY()*yMod);
                                rotation = (rotation + 1) % 360;
                                canvas.drawCircle(map.get(selected).getX()*xMod, map.get(selected).getY()*yMod, 40, select);
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
package com.example.testgame.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MovableDrawer extends SurfaceView implements SurfaceHolder.Callback{

    protected SurfaceHolder holder;

    protected int width,height;
    protected float xMod,yMod;

    protected float xBoundMin,yBoundMin,xBoundMax,yBoundMax;

    protected float x,y;


    public MovableDrawer(@NonNull Context context) {
        super(context);
        setWillNotDraw(false);
        holder = getHolder();
        holder.addCallback(this);
        xBoundMin=75;
        yBoundMin=75;

    }

    public MovableDrawer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        setWillNotDraw(false);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        width=getWidth();
        height=getHeight();
        if (xBoundMax == 0) {
            xBoundMax = -width;
            yBoundMax = -height;
            xMod = width / 3120f;
            yMod = height / 1440f;
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
            Log.d("MovableDrawer", "onTouchEvent: x="+(x+xMod)+" y="+(y+yMod));
        } else if (event.getAction()==MotionEvent.ACTION_MOVE) {
            xMod+=event.getX()-x;
            yMod+=event.getY()-y;
            xMod=Math.min(xBoundMin,Math.max(xMod,xBoundMax));
            yMod=Math.min(yBoundMin,Math.max(yMod,yBoundMax));
            x=event.getX();
            y=event.getY();

        }
        return true;
    }
}

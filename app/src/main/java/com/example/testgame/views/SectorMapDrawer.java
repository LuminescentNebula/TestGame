package com.example.testgame.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.testgame.R;
import com.example.testgame.interfaces.StarMapDrawerListener;
import com.example.testgame.models.Sector;

import java.util.ArrayList;

public class SectorMapDrawer extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    SectorMapThread sectorMapThread;
    Sector sector;
    private int width, height;


    public SectorMapDrawer(Context context, ArrayList<Sector> map, StarMapDrawerListener starMapDrawerListener) {
        super(context);
        setWillNotDraw(false);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        width = getWidth();
        height = getHeight();

        sectorMapThread = new SectorMapThread();
        sectorMapThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        sectorMapThread.stopThread();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    private class SectorMapThread extends Thread {
        private boolean running = true;


        public SectorMapThread() {
        }

        @Override
        public void run() {
            while (running) {
                if (holder.getSurface().isValid()) {
                    Canvas canvas = holder.lockCanvas();

                    if (canvas != null) {
                        canvas.drawColor(getResources().getColor(R.color.space, null));
                        canvas.save();


                    }

                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
        public void stopThread() {
            running = false;
        }
    }

}

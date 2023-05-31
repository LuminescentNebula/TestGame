package com.example.testgame.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.testgame.R;


public class PrisonerDrawer extends SurfaceView implements SurfaceHolder.Callback{

    private SurfaceHolder holder;
    private PrisonerDrawerThread prisonerDrawerThread;

    public PrisonerDrawer(Context context, AttributeSet attrs) {
        super(context,attrs);
        setWillNotDraw(false);
        holder = getHolder();
        holder.addCallback(this);
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {


        prisonerDrawerThread = new PrisonerDrawerThread();
        prisonerDrawerThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        prisonerDrawerThread.stopThread();
    }

    private class PrisonerDrawerThread extends Thread {
        private boolean running = true;


        public PrisonerDrawerThread(){

        }
        //Todo: добавить изображение персонажа

        @Override
        public void run() {
            while (running) {
                if (holder.getSurface().isValid()) {
                    Canvas canvas = holder.lockCanvas();

                    if (canvas != null) {
                        canvas.drawColor(getResources().getColor(R.color.menu_third,null));
                        canvas.save();
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

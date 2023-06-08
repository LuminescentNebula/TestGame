package com.example.testgame.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.example.testgame.R;
import com.example.testgame.data.PlanetGenerator;
import com.example.testgame.models.Planet;
import com.example.testgame.models.Sector;

import java.util.Random;

public class SectorMapDrawer extends MovableDrawer {

    private static final String TAG = "SectorMapDrawer";
    SectorMapThread sectorMapThread;
    private Sector sector;
    protected float[] orbits={1, 1.5f, 2, 3, 5};
    protected float starSize;
    protected int planetAmount;
    Drawable ship;
    private float shipX, shipY, shipSpeed, shipSpeedX, shipSpeedY,touchX,touchY;


    public SectorMapDrawer(@NonNull Context context,Sector sector) {
        super(context);
        this.sector=sector;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        super.surfaceCreated(holder);

        ship = ResourcesCompat.getDrawable(getResources(), R.drawable.ship, null);
        shipX = 0;
        shipY = 0;
        shipSpeed = 8;

        Log.d(TAG,sector.getName()+" "+sector.getPlanetsSize());
        setSector(sector);
        sectorMapThread = new SectorMapThread();
        sectorMapThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        sectorMapThread.stopThread();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (Planet planet: sector.getPlanets()){
                Log.d(TAG,"planetX "+planet.getX()+" planetY "+planet.getY()+" planetS "+planet.getSize());
                if (Math.abs((event.getX()+xMod)-(planet.getX()+xMod))<planet.getSize() &&
                        Math.abs((event.getY()+yMod)-(planet.getY()+yMod))<planet.getSize()){
                    Log.d(TAG, planet.getName());
                    touchX = event.getX();
                    touchY = event.getY();
                    float dx = touchX+xMod - shipX;
                    float dy = touchY+yMod - shipY;
                    double direction = Math.atan2(dy, dx);
                    shipSpeedX = (float) (shipSpeed * Math.cos(direction));
                    shipSpeedY = (float) (shipSpeed * Math.sin(direction));
                    break;
                }
            }
        }
        return true;
    }
    public void setSector(Sector sector){
        this.sector=sector;
        planetAmount=sector.getPlanetsSize();
        starSize=sector.getSize()*7;

        yBoundMin=((width / 2f - starSize) / planetAmount + sector.getPlanet(planetAmount - 1).getSize()) * orbits[planetAmount - 1]
                + starSize
                + 125
                -height/2f;
        yBoundMax=-yBoundMin;

        xBoundMin=((width / 2f - starSize) / planetAmount + sector.getPlanet(planetAmount - 1).getSize()) * orbits[planetAmount - 1]
                + starSize
                + 125
                - width/2f;
        xBoundMax =-xBoundMin;
    }

    public void stop() {
        sectorMapThread.stopThread();
    }

    private class SectorMapThread extends Thread {
        private boolean running = true;
        private float rotateAngle = 0;

        Paint sun=new Paint(){{
            setStyle(Style.FILL_AND_STROKE);
            setColor(0xFFFCE902);
        }};
        Paint stroke=new Paint(){{
            setStyle(Style.STROKE);
            setStrokeWidth(10);
            setColor(0xFF000000);
        }};
        Paint orbit = new Paint(){{
            setStyle(Style.STROKE);
            setColor(0x19FFFFFF);
            setStrokeWidth(20);
        }};

        public SectorMapThread() {
        }

        @Override
        public void run() {
            int shipWidth = ship.getIntrinsicWidth()/2;
            int shipHeight = ship.getIntrinsicHeight()/2;

            while (running) {
                try {
                    Random random = new Random(100);
                    if (holder.getSurface().isValid()) {
                        Canvas canvas = holder.lockCanvas();
                        if (canvas != null) {
                            canvas.drawColor(0xFF272744);
                            canvas.drawCircle(width / 2f + xMod, height / 2f + yMod, starSize, sun);
                            canvas.drawCircle(width / 2f + xMod, height / 2f + yMod, starSize, stroke);
                            int n = 1;
                            for (int i = 1; i <= planetAmount; i++) {
                                float radius = ((width / 2f - starSize) / planetAmount + sector.getPlanet(i - 1).getSize()) * (orbits[i - 1])
                                        + starSize
                                        + random.nextInt(50) - 25;
                                canvas.drawCircle(width / 2f + xMod, height / 2f + yMod, radius, orbit);
                                //TODO: Shadow с другой стороны от солнца
                                canvas.save();
                                sector.getPlanet(i-1).setX((float) (width/2-radius));
                                sector.getPlanet(i-1).setY((float) (height/2));
                                //canvas.rotate(sector.getPlanet(i - 1).getRotation() * n, width / 2f + xMod, height / 2f + yMod);
                                PlanetGenerator.GasPlanets.generate(canvas, sector.getPlanet(i - 1),
                                        width / 2f - radius + xMod,
                                        height / 2f + yMod,
                                        sector.getPlanet(i - 1).getSize());
                                canvas.restore();
                                n = -n;
                            }
                            canvas.save();
                            canvas.rotate(rotateAngle, shipX, shipY);
                            ship.setBounds((int) (shipX - shipWidth / 2 + xMod), (int) ( shipY - shipHeight / 2 + yMod),
                                    (int) (shipX + shipWidth / 2 + xMod), (int) (shipY + shipHeight / 2 + yMod));
                            ship.draw(canvas);
                            canvas.restore();
                        }
                        holder.unlockCanvasAndPost(canvas);

                        if (Math.abs(touchX-shipX+xMod)>5 || Math.abs(touchY-shipY)>5) {

                            float dx = touchX+xMod - shipX;
                            float dy = touchY+yMod - shipY;
                            double direction = Math.atan2(dy, dx);
                            shipSpeedX = (float) (shipSpeed * Math.cos(direction));
                            shipSpeedY = (float) (shipSpeed * Math.sin(direction));

                            shipX += shipSpeedX;
                            shipY += shipSpeedY;
                            rotateAngle = calculateRotateAngle(shipSpeedX, shipSpeedY);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        public void stopThread() {
            running = false;
        }

        private float calculateRotateAngle(float speedX, float speedY) {
            double angle = Math.atan2(speedY, speedX);
            return (float) Math.toDegrees(angle);
        }

    }

}

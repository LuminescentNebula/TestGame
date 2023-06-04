package com.example.testgame.data;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;

import androidx.annotation.NonNull;

import com.example.testgame.models.Planet;

import java.util.Random;

public class PlanetGenerator {
    private static final Paint layer = new Paint() {{
        setStyle(Style.FILL_AND_STROKE);
        setAntiAlias(true);
        setStrokeWidth(20);
        setStrokeCap(Cap.ROUND);
        setColor(Color.RED);
    }};
    private static final Paint  stroke = new Paint() {{
        setStyle(Style.STROKE);
        setColor(Color.BLACK);
        setStrokeWidth(10);
    }};
    private static final Paint details = new Paint() {{
        setStyle(Style.FILL);
        setColor(0x4D000000);
    }};


    //        int offBase = 40;
    //        int layers = 10;
    //        int waves = 7;
    //        int amplitude = 35;
    //        int step = 100;
    //        int planetSize = 170;
    //        int planetRotation = -65;

    public static void generateGasPlanet(@NonNull Canvas canvas, int seed, float x, float y) {
        generateGasPlanet(canvas, seed, x, y, 40f, 10,
                7, 35, 100, 170f,
                -65f);
    }

    public static void generateGasPlanet(@NonNull Canvas canvas, Planet planet) {
        //TODO: r
        generateGasPlanet(canvas, planet.getSeed(), planet.getX(), planet.getR(), 40f, 10,
                7, 35, 100, 170f,
                -65f);
    }

    public static void generateGasPlanet(@NonNull Canvas canvas, int seed, float x, float y, float offBase,
                                         int layers, int waves, int amplitude, int step, float planetSize,
                                         float planetRotation) {

        Random random = new Random(seed);
        canvas.drawColor(0xFF272744);


        canvas.translate(x, y);
        canvas.rotate(step * waves / 2f, layers * amplitude / 2f, -planetRotation);
        canvas.save();
        canvas.clipPath(makeDetailClip(step * waves / 2f, layers * amplitude / 2f, planetSize));
        int off = 0;
        for (int j = 0; j < layers; j++) {
            Path mPath = new Path();
            mPath.rLineTo(0, -amplitude * 1.5f);
            int n = -1;
            for (int i = 0; i < waves; i++) {
                float x2 = random.nextInt(step) + step;
                float offset = random.nextInt(amplitude) + amplitude / 2f;
                mPath.rQuadTo(x2 / 2, offset * n, x2, 0);
                n = -n;
            }
            mPath.rLineTo(0, amplitude * 1.5f);
            mPath.close();
            mPath.offset(0, off);
            int color =
                    (255 << 24) |
                            (random.nextInt(20) << 16) |
                            (random.nextInt(215) + 40 << 8) |
                            (random.nextInt(215) + 40);
            layer.setColor(color);
            off += offBase;
            canvas.drawPath(mPath, layer);
        }


        Path shadow = new Path();
        shadow.addCircle(0, 0, planetSize, Path.Direction.CCW);
        shadow.offset(step * waves / 2f, layers * amplitude / 2f);


        canvas.save();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutPath(makeDetailClip(step * waves / 2f - planetSize / 2f,
                    layers * amplitude / 2f,
                    planetSize));
        }
        canvas.drawPath(shadow, details);

        //TODO: Блик

//            canvas.restore();
//            canvas.clipPath(makeDetailClip(step * waves / 2f - planetSize * 1.5f, layers * amplitude / 2f, planetSize));
//            canvas.rotate(step * waves / 2f, layers * amplitude / 2f, planetRotation);
//            Paint paint = new Paint();
//            paint.setShader(new LinearGradient(
//                    step * waves / 2f - planetSize,
//                    layers * amplitude / 2f,
//                    step * waves / 2f + planetSize,
//                    layers * amplitude / 2f,
//                    Color.WHITE,
//                    Color.TRANSPARENT,
//                    Shader.TileMode.CLAMP));
//            canvas.drawPaint(paint);
        canvas.restore();
        canvas.drawCircle(step * waves / 2f, layers * amplitude / 2f, planetSize, stroke);
        canvas.save();
    }

    private static Path makeDetailClip(float centerX, float centerY, float radius) {
        Path path = new Path();
        path.addRoundRect(new RectF(centerX - radius, centerY - radius,
                        centerX + radius, centerY + radius),
                radius, radius, Path.Direction.CW);
        return path;
    }

}
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

    public static void generate(@NonNull Canvas canvas, Planet planet) {
        //TODO: r
        if (planet.getType()==Planet.GAS){
            GasPlanets.generate(canvas,planet);
        }
    }

    public static void generate(@NonNull Canvas canvas, Planet planet, float x, float y, float planetSize) {
        if (planet.getType()==Planet.GAS){
            GasPlanets.generate(canvas,planet,x,y,planetSize);
        }
    }


    public static class GasPlanets {
        private static final Paint layer = new Paint() {{
            setStyle(Style.FILL_AND_STROKE);
            setAntiAlias(true);
            setStrokeWidth(20);
            setStrokeCap(Cap.ROUND);
            setColor(Color.RED);
        }};
        private static final Paint stroke = new Paint() {{
            setStyle(Style.STROKE);
            setColor(Color.BLACK);
            setStrokeWidth(10);
        }};
        private static final Paint details = new Paint() {{
            setStyle(Style.FILL);
            setColor(0x4D000000);
        }};


        private static int base_layers = 10;
        private static int base_waves = 15;
        private static float base_rotation = -65f;
        private final static int step = 100;

        public static void generate(@NonNull Canvas canvas, Planet planet) {
            //TODO: r
            generate(canvas, planet.getSeed(), planet.getX(), planet.getR(), base_layers,
                    base_waves, 170f,
                    base_rotation);
        }

        public static void generate(@NonNull Canvas canvas, Planet planet, float x, float y, float planetSize) {
            generate(canvas, planet.getSeed(), x, y, base_layers,
                    base_waves, planetSize,
                    planet.getRotation());
        }

        public static void generate(@NonNull Canvas canvas, int seed, float x, float y,
                                    int layers, int waves, float planetSize,
                                    float planetRotation) {

            int amplitude = (int) (35 / (170 / planetSize));
            float offBase = planetSize / 4.25f;
            Random random = new Random(seed);

            //Переносим canvas на нажную точку и поворчиваем в соответвии с наклоном планеты
            canvas.save();
            canvas.translate(x - step * waves / 2f, y - layers * amplitude / 2f);
            canvas.rotate(0, 0 / 2f, -planetRotation);
            //Создаем круглую маску
            canvas.clipPath(makeDetailClip(step * waves / 2f, layers * amplitude / 2f, planetSize));

            //Рисуем волны
            int off = 0;
            for (int j = 0; j < layers; j++) {
                Path mPath = new Path();
                mPath.rLineTo(0, -amplitude * 1.5f);//Опускает края волны вниз, чтобы она была закрашена
                int n = -1;
                for (int i = 0; i < waves; i++) {
                    float x2 = (random.nextInt(step) + step) / (170 / planetSize); //Длина одной части волны
                    float offset = random.nextInt(amplitude) + amplitude / 2f; // высота подъема/опускания
                    mPath.rQuadTo(x2 / 2, offset * n, x2, 0);
                    n = -n;//Регулирует подъем/опускание
                }
                mPath.rLineTo(0, amplitude * 1.5f);
                mPath.close();
                mPath.offset(0, off);//Переходим ниже для следубщей волны
                int color = //Абсолютно случайный цвет
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
            canvas.drawPath(shadow, details);//Создаем новую маску и рисуем тень

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
            canvas.drawCircle(step * waves / 2f, layers * amplitude / 2f, planetSize, stroke); //Рисуем обводку
            canvas.restore();
        }

        private static Path makeDetailClip(float centerX, float centerY, float radius) {
            Path path = new Path();
            path.addRoundRect(new RectF(centerX - radius, centerY - radius,
                            centerX + radius, centerY + radius),
                    radius, radius, Path.Direction.CW);
            return path;
        }
    }
}
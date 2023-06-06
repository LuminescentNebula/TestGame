package com.example.testgame.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testgame.R;
import com.example.testgame.data.PlanetGenerator;
import com.example.testgame.interfaces.StarMapListener;
import com.example.testgame.models.Sector;

public class StarMapSelector extends LinearLayout {
    Sector sector;
    TextView name;
    SurfaceView planets;

    StarMapListener starMapListener;

    public StarMapSelector(Context context) {
        super(context);
        inflate(getContext(), R.layout.selector, (ViewGroup) getRootView());
        name = findViewById(R.id.sector_name);
        findViewById(R.id.start).setOnClickListener(v -> starMapListener.onSectorChanged(sector));
        planets = findViewById(R.id.planets);
    }


    public void setVisibility(boolean visible){
        if (!visible) {
            this.setVisibility(View.GONE);
        } else {
            this.setVisibility(View.VISIBLE);
        }
    }

    public void setStarMapListener(StarMapListener starMapListener) {
        this.starMapListener = starMapListener;
    }

    public void update(Sector sector, float xMod, float yMod) {
        name.setText(sector.getName());

        Handler handler = new Handler();
        handler.post(() -> {
            if (sector.getPlanetsSize() > 0) {
                SurfaceHolder holder = planets.getHolder();
                Canvas canvas = holder.lockCanvas();
                float width = planets.getWidth() / sector.getPlanetsSize();
                float offset = planets.getHeight()/2;
                if (canvas != null) {
                    canvas.drawColor(0xFF272744);
                    canvas.drawCircle(offset,offset,offset*0.9f,new Paint(){{setColor(Color.RED);}});
                    for (int i = 0; i < sector.getPlanetsSize(); i++) {
                        PlanetGenerator.GasPlanets.generate(canvas, sector.getPlanet(i),
                                offset + width * i,
                                offset, offset*0.9f);
                    }
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        });

        setY(sector.getY() + yMod);
        if (sector.getY()+yMod>155* Resources.getSystem().getDisplayMetrics().density) {
            setY(getY() - 150 * Resources.getSystem().getDisplayMetrics().density);
        }
        setX(sector.getX()+xMod);
        if (sector.getX()+xMod>250*Resources.getSystem().getDisplayMetrics().density) {
            setX(getX() - 240 * Resources.getSystem().getDisplayMetrics().density);
        }
    }
}

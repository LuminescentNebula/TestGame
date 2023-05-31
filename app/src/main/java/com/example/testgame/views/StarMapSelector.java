package com.example.testgame.views;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testgame.R;
import com.example.testgame.interfaces.StarMapListener;
import com.example.testgame.models.Sector;

public class StarMapSelector extends LinearLayout {
    Sector sector;
    TextView name;


    StarMapListener starMapListener;

    public StarMapSelector(Context context) {
        super(context);
        inflate(getContext(), R.layout.selector, (ViewGroup) getRootView());
        name = findViewById(R.id.sector_name);
        findViewById(R.id.start).setOnClickListener(v -> starMapListener.onSectorChanged(sector));
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

package com.example.testgame.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.testgame.models.Sector;

public class StarMapSelector extends LinearLayout {
    Sector sector;
    public StarMapSelector(Context context) {
        super(context);
    }

    public void setVisibility(boolean visible){
        if (!visible) {
            this.setVisibility(View.GONE);
        } else {
            this.setVisibility(View.VISIBLE);
        }
    }

    public void setSector(Sector sector) {
        this.sector = sector;
//        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
//        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        this.setLayoutParams(layoutParams);

    }


}

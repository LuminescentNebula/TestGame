package com.example.testgame.views;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testgame.R;
import com.example.testgame.interfaces.StarMapDrawerListener;
import com.example.testgame.interfaces.StarMapListener;
import com.example.testgame.models.Sector;

import java.util.ArrayList;

public class StarMapView extends Fragment implements StarMapDrawerListener {
    public StarMapDrawer surface;
    public StarMapSelector selector;
    ArrayList<Sector> map;
    StarMapListener starMapListener;
    LinearLayout.LayoutParams selectorParams= new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    );

    public StarMapView(ArrayList<Sector> map, StarMapListener starMapListener){
        this.map=map;
        this.starMapListener=starMapListener;
    }

    public void inflate(){
        surface = new StarMapDrawer(getContext(),map,this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.MATCH_PARENT);
        surface.setLayoutParams(layoutParams);
        selector=new StarMapSelector(getContext());

        selector.setVisibility(false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.star_map, container, false);
        inflate();
        view.addView(surface);
        view.addView(selector);
        selector.setStarMapListener(starMapListener);
        return view;
    }

    @Override
    public void onSectorTouch(int i,float xMod,float yMod) {
        if (i>=0) {
            selector.update(map.get(i),xMod,yMod);
            selector.setVisibility(true);
        } else {
            selector.setVisibility(false);
        }
    }
}

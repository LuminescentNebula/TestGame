package com.example.testgame.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testgame.R;
import com.example.testgame.interfaces.StarMapDrawerListener;
import com.example.testgame.models.Sector;

import java.util.ArrayList;

public class StarMapView extends Fragment implements StarMapDrawerListener {
    public StarMapDrawer surface;
    public StarMapSelector selector;
    ArrayList<Sector> map;

    public StarMapView(ArrayList<Sector> map){
        this.map=map;
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
        return view;
    }

    @Override
    public void onSectorTouch(int i) {

        selector.setVisibility(true);
    }
}

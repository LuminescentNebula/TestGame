package com.example.testgame.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testgame.R;
import com.example.testgame.interfaces.StarMapDrawerListener;
import com.example.testgame.interfaces.StarMapListener;
import com.example.testgame.models.Sector;

import java.util.ArrayList;

public class StarMapView extends Fragment implements StarMapDrawerListener {
    StarMapDrawer surface;
    StarMapSelector selector;
    ImageButton backButton;
    ArrayList<Sector> map;
    StarMapListener starMapListener;


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
        backButton = view.findViewById(R.id.back);
        backButton.bringToFront();
        selector.setStarMapListener(starMapListener);
        selector.setVisibility(true);
        selector.setVisibility(false);
        backButton.setOnClickListener(v -> onBackPressed());

        return view;
    }

    @Override
    public void onBackPressed() {
        getParentFragmentManager().setFragmentResult("StarMapBack",new Bundle());
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

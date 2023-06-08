package com.example.testgame.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testgame.R;
import com.example.testgame.interfaces.ShipListener;
import com.example.testgame.models.Sector;

public class SectorMapView extends Fragment implements ShipListener {

    private static final String TAG="SectorMapView";
    SectorMapDrawer surface;
    private Sector sector;

    public SectorMapView() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.star_map, container, false);
        view.findViewById(R.id.back).setVisibility(View.GONE);
        surface = new SectorMapDrawer(getContext(),sector);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        surface.setLayoutParams(layoutParams);
        view.addView(surface);
        return view;
    }

    public void stop(){
        surface.stop();
    }

    @Override
    public void setSector(Sector sector) {
        Log.d(TAG,sector.getName());
        this.sector=sector;
    }
}

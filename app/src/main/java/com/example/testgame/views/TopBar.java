package com.example.testgame.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.testgame.R;
import com.example.testgame.interfaces.InventoryContainerListener;

public class TopBar extends Fragment implements InventoryContainerListener {
    private TextView fuel;
    private TextView food;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.top_bar, container, false);
        fuel=view.findViewById(R.id.fuel);
        food=view.findViewById(R.id.food);

        return view;
    }

    @Override
    public void setFuel(int amount){
        fuel.setText("Fuel: "+amount);
    }
    @Override
    public void setFood(int amount){
        food.setText("Food: "+amount);
    }

    public void setVisibility(boolean visible){
        if (!visible) {
            getView().setVisibility(View.GONE);
        } else {
            getView().setVisibility(View.VISIBLE);
        }
    }
}

package com.example.testgame.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.testgame.R;
import com.example.testgame.interfaces.InventoryContainerListener;
import com.example.testgame.interfaces.ShowHideInterface;

public class TopBar extends Fragment implements InventoryContainerListener, ShowHideInterface {
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

    @Override
    public void hide() {
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .hide(this)
                .commit();
    }
    @Override
    public void show() {
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .show(this)
                .commit();
    }
}

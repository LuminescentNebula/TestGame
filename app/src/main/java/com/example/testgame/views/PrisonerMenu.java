package com.example.testgame.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.testgame.models.Prisoner;
import com.example.testgame.R;
import com.example.testgame.interfaces.PrisonerContainerListener;
import com.example.testgame.interfaces.PrisonerMenuListener;
import com.example.testgame.other.StatBar;

public class PrisonerMenu extends SideMenu implements PrisonerContainerListener {

    private TextView name;
    private StatBar health,hunger,stress;
    private PrisonerMenuListener prisonerMenuListener;
    private Button centerButton;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.prisoner_menu, container, false);
        name = view.findViewById(R.id.prisoner_name);
        centerButton= view.findViewById(R.id.button_center);

        view.findViewById(R.id.button_right).setOnClickListener(v -> prisonerMenuListener.changeDisplayedPrisoner(true));
        view.findViewById(R.id.button_left).setOnClickListener(v -> prisonerMenuListener.changeDisplayedPrisoner(false));
        centerButton.setOnClickListener(v -> prisonerMenuListener.feedPrisoner(10));

        health = view.findViewById(R.id.prisoner_health);
        hunger = view.findViewById(R.id.prisoner_hunger);
        hunger.rotate(StatBar.Right);
        stress = view.findViewById(R.id.prisoner_stress);
        stress.rotate(StatBar.Center);
        return view;
    }

    public void setPrisoner(Prisoner prisoner){
        name.setText(prisoner.getName());
        hunger.setAmount(prisoner.getHunger());
        health.setAmount(prisoner.getHealth());
    }

    @Override
    public void onPrisonerContainerUpdated(Prisoner prisoner) {
        hunger.setMaxAmount(prisoner.getMaxHunger());
        health.setMaxAmount(prisoner.getMaxHealth());
        setPrisoner(prisoner);
    }

    @Override
    public void setButtonActivated(boolean active) {
        centerButton.setClickable(false);
    }

    public void setPrisonerMenuListener(PrisonerMenuListener prisonerMenuListener) {
        this.prisonerMenuListener=prisonerMenuListener;
    }

    @Override
    public void hide() {
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .hide(this)
                .commit();
    }

    @Override
    public void change() {
        if (isHidden()){
            show();
        } else {
            hide();
        }
    }

    @Override
    public void show(){
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .show(this)
                .commit();
    }
}

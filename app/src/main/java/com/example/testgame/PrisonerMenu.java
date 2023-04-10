package com.example.testgame;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.testgame.interfaces.PrisonerContainerListener;
import com.example.testgame.interfaces.PrisonerMenuListener;

public class PrisonerMenu extends Fragment implements PrisonerContainerListener {

    private View view;
    private TextView first;
    private TextView second;
    private TextView third;

    private PrisonerMenuListener prisonerMenuListener;
    private Button leftButton;
    private Button rightButton;
    private Button centerButton;



    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.prisoner_menu, container, false);
        first = view.findViewById(R.id.first_text);
        second = view.findViewById(R.id.second_text);
        third = view.findViewById(R.id.third_text);
        leftButton=view.findViewById(R.id.button_left);
        rightButton=view.findViewById(R.id.button_right);
        centerButton=view.findViewById(R.id.button_center);

        //Todo: добавить изображение персонажа

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prisonerMenuListener.changeDisplayedPrisoner(true);
            }
        });
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prisonerMenuListener.changeDisplayedPrisoner(false);
            }
        });
        centerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prisonerMenuListener.feedPrisoner(10);
            }
        });

        return view;
    }

    public void setPrisoner(Prisoner prisoner){
        first.setText(prisoner.getName());
        second.setText("Hunger: "+prisoner.getHunger());
        third.setText("Health: "+prisoner.getHealth());
    }

    @Override
    public void onPrisonerContainerUpdated(Prisoner prisoner) {
        setPrisoner(prisoner);
    }

    @Override
    public void setButtonActivated(boolean active) {
        centerButton.setClickable(false);
    }

    public void setPrisonerMenuListener(PrisonerMenuListener prisonerMenuListener) {
        this.prisonerMenuListener=prisonerMenuListener;
    }
}

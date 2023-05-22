package com.example.testgame.views;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.testgame.interfaces.ShowHideInterface;
import com.example.testgame.models.Prisoner;
import com.example.testgame.R;
import com.example.testgame.interfaces.PrisonerContainerListener;
import com.example.testgame.interfaces.PrisonerMenuListener;

public class PrisonerMenu extends Fragment implements PrisonerContainerListener, ShowHideInterface {

    private TextView first;
    private TextView second;
    private TextView third;


    private PrisonerMenuListener prisonerMenuListener;
    private Button centerButton;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prisoner_menu, container, false);
        first = view.findViewById(R.id.first_text);
        second = view.findViewById(R.id.second_text);
        third = view.findViewById(R.id.third_text);
        centerButton= view.findViewById(R.id.button_center);

        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));
        setExitTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        //Todo: добавить изображение персонажа

        view.findViewById(R.id.button_right).setOnClickListener(v -> prisonerMenuListener.changeDisplayedPrisoner(true));
        view.findViewById(R.id.button_left).setOnClickListener(v -> prisonerMenuListener.changeDisplayedPrisoner(false));
        centerButton.setOnClickListener(v -> prisonerMenuListener.feedPrisoner(10));

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

    @Override
    public void hide() {
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .hide(this)
                .commit();
    }

    @Override
    public void show(){
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .show(this)
                .commit();
    }
}

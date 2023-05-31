package com.example.testgame.views;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testgame.interfaces.ShowHideInterface;
import com.example.testgame.models.Prisoner;
import com.example.testgame.R;
import com.example.testgame.interfaces.PrisonerContainerListener;
import com.example.testgame.interfaces.PrisonerMenuListener;

public class SideMenu extends Fragment implements ShowHideInterface {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));
        setExitTransition(transitionInflater.inflateTransition(R.transition.slide_right));
        return super.onCreateView(inflater, container, savedInstanceState);
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

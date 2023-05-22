package com.example.testgame.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.testgame.R;
import com.example.testgame.interfaces.ShowHideInterface;

public class BottomBar extends Fragment implements ShowHideInterface {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.bottom_bar, container, false);

        view.findViewById(R.id.button1).setOnClickListener(v -> {
            Log.i("BottomBar","First");
            getParentFragmentManager().setFragmentResult("StarMap",new Bundle());
        });
        view.findViewById(R.id.button2).setOnClickListener(v -> {
            if (getParentFragmentManager().findFragmentByTag("PrisonerMenu").isHidden()) {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .show(getParentFragmentManager().findFragmentByTag("PrisonerMenu"))
                        .commit();
            } else {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .hide(getParentFragmentManager().findFragmentByTag("PrisonerMenu"))
                        .commit();
            }
        });
        return view;
    }

    @Override
    public void hide() {
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .hide(this)
                .commit();
    }

    @Override
    public void  show(){
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .show(this)
                .commit();
    }
}

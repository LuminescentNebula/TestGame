package com.example.testgame.views;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.testgame.R;
import com.example.testgame.interfaces.ShowHideInterface;

public class BottomBar extends Fragment implements ShowHideInterface {

    private static String TAG = "BottomBar";
    private boolean scrolled=false;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.bottom_bar, container, false);

        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_left));
        setExitTransition(transitionInflater.inflateTransition(R.transition.slide_left));
        ImageButton[] buttons ={view.findViewById(R.id.button1),
                view.findViewById(R.id.button2),
                view.findViewById(R.id.button3),
                view.findViewById(R.id.button4),
                view.findViewById(R.id.button5),
                view.findViewById(R.id.button6)};

        buttons[5].setOnClickListener(v -> {
            Log.i("BottomBar","First");
            getParentFragmentManager().setFragmentResult("StarMap",new Bundle());
        });
        buttons[4].setOnClickListener(v -> {
            sideMenuUse("PrisonerMenu");
        });
        view.findViewById(R.id.hide).setOnClickListener(v -> {
            Log.d(TAG,"Hide clicked");
            int width=view.getWidth()-view.findViewById(R.id.hide).getWidth();
            if (!scrolled) {
                view.animate().translationX(-width).start();
            } else {
                view.animate().translationX(0).start();
            }
            scrolled=!scrolled;
        });
        return view;
    }

    private void sideMenuUse(String tag){
        if (getParentFragmentManager().findFragmentByTag(tag).isHidden()) {
            getParentFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .show(getParentFragmentManager().findFragmentByTag(tag))
                    .commit();
        } else {
            getParentFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .hide(getParentFragmentManager().findFragmentByTag(tag))
                    .commit();
        }
    }

    @Override
    public void hide() {
        Log.d("TAG", "hide: BottomBar");
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .hide(this)
                .commit();
        Log.d("TAG", String.valueOf(this.isHidden()));
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
    public void  show(){
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .show(this)
                .commit();
    }
}

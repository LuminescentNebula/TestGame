package com.example.testgame.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.testgame.R;

public class BottomBar extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.bottom_bar, container, false);

        view.findViewById(R.id.button1).setOnClickListener(v -> {
            Log.i("BottomBar","First");
            getParentFragmentManager().setFragmentResult("StarMap",new Bundle());
        });
        return view;
    }

    public void setVisibility(boolean visible){
        if (!visible) {
            getView().setVisibility(View.GONE);
        } else {
            getView().setVisibility(View.VISIBLE);
        }
    }
}

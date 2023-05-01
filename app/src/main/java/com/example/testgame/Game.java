package com.example.testgame;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.testgame.databinding.TopBarBinding;


public class Game extends AppCompatActivity{
    private JSONSaver jsonSaver;

    private final String TAG = "GAME";
    private Ship ship;
    private StarMap starMap;

    private FragmentManager fragmentManager;
    private PrisonerMenu prisonerMenu = new PrisonerMenu();
    private TopBar topBar = new TopBar();
    private BottomBar bottomBar = new BottomBar();
    private Fragment currentRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Turning off dark theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        // Hiding status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hiding navigation bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        super.onCreate(savedInstanceState);

        ship=new Ship();
        fragmentManager=getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.right_menu, prisonerMenu)
                    .commit();
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.top_bar, topBar)
                    .commit();
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.bottom_bar, bottomBar)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        //Фрагмент создается после onStart
        prisonerMenu.setPrisoner(ship.getPrisonersContainer().setDisplayedPrisoner(0));
        prisonerMenu.setPrisonerMenuListener(ship.getPrisonersContainer());
        ship.getPrisonersContainer().setPrisonerContainerListener(prisonerMenu);

        ship.getInventoryContainer().setInventoryContainerListener(topBar);
        ship.getInventoryContainer().updateTopBar();

        super.onResume();
    }
}
//    public void change(View view) {
//        ImageView planet = findViewById(R.id.planet);
//        ContextThemeWrapper[] ctw = new ContextThemeWrapper[] {
//                new ContextThemeWrapper(this, R.style.blue_planet_color_scheme),
//                new ContextThemeWrapper(this, R.style.green_planet_color_scheme),
//                new ContextThemeWrapper(this, R.style.yellow_planet_color_scheme)
//        };
//
//        Drawable[] layers= {getDrawable(R.drawable.planet_base),
//                ResourcesCompat.getDrawable(getResources(),
//                        R.drawable.planet_waves,
//                        ctw[new Random().nextInt(ctw.length)].getTheme()),
//                getDrawable(R.drawable.planet_clipper),
//                getDrawable(R.drawable.planet_shadow)
//        };
//        Drawable d =  new LayerDrawable(layers);
//        planet.setImageDrawable(d);
//    }
//}
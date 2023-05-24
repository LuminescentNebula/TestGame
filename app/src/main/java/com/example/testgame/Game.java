package com.example.testgame;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;

import com.example.testgame.data.JSONSaver;
import com.example.testgame.models.Ship;
import com.example.testgame.models.StarMapGenerator;
import com.example.testgame.views.BottomBar;
import com.example.testgame.views.PrisonerMenu;
import com.example.testgame.views.ShipView;
import com.example.testgame.views.StarMapView;
import com.example.testgame.views.TopBar;


public class Game extends AppCompatActivity{
    private JSONSaver jsonSaver;

    private final String TAG = "GAME";

    private StarMapView starMapView;

    private Ship ship;
    private FragmentManager fragmentManager;
    private PrisonerMenu prisonerMenu;
    private TopBar topBar;
    private BottomBar bottomBar;
    private ShipView shipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Turning off dark theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // Hiding status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Hiding navigation bar
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;
        flags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(flags);


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ship=new Ship();
        prisonerMenu = new PrisonerMenu();
        topBar = new TopBar();
        bottomBar = new BottomBar();
        shipView=new ShipView();

        starMapView=new StarMapView(StarMapGenerator.generate(1),ship);

        fragmentManager=getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.layout,shipView,"Ship")
                    .commit();
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.right_menu, prisonerMenu,"PrisonerMenu")
                    .commit();
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.top_bar, topBar,"TopBar")
                    .commit();
            fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.bottom_bar, bottomBar,"BottomBar")
                    .commit();
        }

        fragmentManager.addOnBackStackChangedListener(() -> {
            bottomBar.hide();
            topBar.hide();
            prisonerMenu.hide();
        });

        getSupportFragmentManager().setFragmentResultListener("StarMap", this, (requestKey, bundle) -> {
            fragmentManager.beginTransaction()
                    .addToBackStack("StarMap")
                    .setReorderingAllowed(true)
                    .replace(R.id.layout, starMapView)
                    .commit();
        });

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
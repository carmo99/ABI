package com.abi.homeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.abi.homeactivity.ui.home.FotoFragment;
import com.abi.homeactivity.ui.home.dummy.MensajeFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

public class MainActivity extends AppCompatActivity {

    MapsActivity mapafragment = new MapsActivity();
    FotoFragment fragment_foto = new FotoFragment();
    MensajeFragment fragment_mensaje = new MensajeFragment();
    AppBarLayout appBarLayout;
    BottomNavigationView bottomNavigationView;
    int veces_apretado = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        loadFragmentM(mapafragment);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        appBarLayout = findViewById(R.id.barlayout);
        bottomNavigationView = findViewById(R.id.nav_view);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */)
        {

            @Override
            public void handleOnBackPressed()
            {
                veces_apretado++;
                if(veces_apretado == 2)
                {
                    finish();
                }
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId() == R.id.fragment_foto)
            {
                loadFragmentM(fragment_foto);
                appBarLayout.setVisibility(View.GONE);
                bottomNavigationView.setVisibility(View.GONE);
                return true;
            }
            else if(item.getItemId() == R.id.fragment_mensaje)
            {
                loadFragmentM(fragment_mensaje);
                appBarLayout.setVisibility(View.GONE);
                bottomNavigationView.setVisibility(View.GONE);
                return true;
            }
            return false;
        }
    };

    public void loadFragmentM(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }


}
package com.abi.homeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;



import com.abi.homeactivity.ui.home.ContactoFragment;
import com.abi.homeactivity.ui.home.FotoFragment;
import com.abi.homeactivity.ui.home.dummy.MensajeFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    MapsActivity mapafragment = new MapsActivity();
    FotoFragment fragment_foto = new FotoFragment();
    MensajeFragment fragment_mensaje = new MensajeFragment();
    ContactoFragment fragment_contacto = new ContactoFragment();
    NavigationView nav_view_sidebar;

    AppBarLayout appBarLayout;
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
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
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        nav_view_sidebar = findViewById(R.id.nav_view_sidebar);

        nav_view_sidebar.setNavigationItemSelectedListener(item ->
        {
            switch (item.getItemId())
            {
                case R.id.nav_infoDia:
                    Intent intent1 = new Intent(MainActivity.this, InformacionDiaActivity.class);
                    startActivity(intent1);
                    this.finish();
                    return true;
                case R.id.nav_dispositivoABI:
                    Log.i("Opciones", "ABI");
                    return true;
                case R.id.nav_infoLegal:
                    Intent intent2 = new Intent(MainActivity.this, InformacionLegalActivity.class);
                    startActivity(intent2);
                    this.finish();
                    return true;
                case R.id.nav_infoConfiguracion:
                    Log.i("Opciones", "Configuracion");
                    return true;
            }
            return false;
        });

        toolbar();
        //menuHamburguesa.setOnClickListener( this );


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
            else if(item.getItemId() == R.id.fragment_contacto)
            {
                loadFragmentM(fragment_contacto);
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

    void toolbar(){
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled( true );
            ab.setTitle("");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId() ) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
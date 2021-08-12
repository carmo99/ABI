package com.abi.homeactivity.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.abi.homeactivity.informacion.InformacionDiaActivity;
import com.abi.homeactivity.informacion.InformacionLegalActivity;
import com.abi.homeactivity.R;
import com.abi.homeactivity.popup.PopUpLogOut;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.abi.homeactivity.retrofit.response.ResponseFoto;
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
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.widget.Toolbar;

import java.io.DataInputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static Activity fa;
    ImageView perfil;
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

    AuthABIService authABIService;
    AuthABIClient authABIClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fa = this;
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
        perfil = findViewById(R.id.imageViewProfilePrincipal);
        perfil.setOnClickListener(this);
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
                    Intent intent4 = new Intent(MainActivity.this, RegistraGadgetActivity.class);
                    startActivity(intent4);
                    this.finish();
                    return true;
                case R.id.nav_infoLegal:
                    Intent intent2 = new Intent(MainActivity.this, InformacionLegalActivity.class);
                    startActivity(intent2);
                    this.finish();
                    return true;
                case R.id.nav_LogOut:
                    Intent intent3 = new Intent(MainActivity.this, PopUpLogOut.class);
                    startActivity(intent3);
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

        retrofitInit();

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
                Call<ResponseFoto> call = authABIService.getFotoDia();
                call.enqueue(new Callback<ResponseFoto>() {
                    @Override
                    public void onResponse(Call<ResponseFoto> call, Response<ResponseFoto> response) {
                        if (response.isSuccessful()){
                            String fotoDia = response.body().getUrl();
                            Log.i("FotoDia", fotoDia);
                        }else{
                            Toast.makeText(MainActivity.this, "Algo fallo", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseFoto> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error en la conexion", Toast.LENGTH_SHORT).show();
                    }
                });
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

    private void retrofitInit() {
        authABIClient = AuthABIClient.getInstance();
        authABIService = authABIClient.getAuthABIService();
    }

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

    @Override
    public void onClick(View view) {
        Intent i = new Intent( MainActivity.this, DatosPerfil.class);
        startActivity( i );
        this.finish();
    }
}
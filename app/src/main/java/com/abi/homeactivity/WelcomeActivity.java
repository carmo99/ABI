package com.abi.homeactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends AppCompatActivity {

    private static final int DURACION_SPLASH = 4700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable()
        {
            public void run(){
                // Cuando pasen los 5 segundos, pasamos a la actividad principal de la aplicación

                Intent intent = new Intent(WelcomeActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);
    }
}
package com.abi.homeactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrivacidadActivity extends AppCompatActivity implements View.OnClickListener {

    Button b_aceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacidad);
        b_aceptar = findViewById(R.id.button_aviso);
        b_aceptar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.button_aviso)
        {
            Intent intent = new Intent(PrivacidadActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
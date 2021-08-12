package com.abi.homeactivity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.abi.homeactivity.R;

public class ActivityContrasenia extends AppCompatActivity implements View.OnClickListener {

    ImageButton atras;
    EditText contraseniaActual, contraseniaNueva;
    Button actualizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasenia);
        contraseniaActual = findViewById(R.id.editTextTextContraseniaActual);
        contraseniaNueva = findViewById(R.id.editTextTextNuevaContrasenia);
        actualizar = findViewById(R.id.buttonActualizaContrasenia);
        atras = findViewById( R.id.imageButtonCambioContraseniaToDatosPerfil);
        atras.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent( ActivityContrasenia.this, DatosPerfil.class);
        startActivity( i );
        this.finish();
    }
}
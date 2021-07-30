package com.abi.homeactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.SharedPreferencesManager;

public class FotoDiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_dia);

        String mensajeAyuda = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_MENSAJE);
        Log.i("Token7", mensajeAyuda);

    }
}
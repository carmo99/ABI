package com.abi.homeactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.retrofit.ABIClient;
import com.abi.homeactivity.retrofit.ABIService;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.abi.homeactivity.retrofit.request.RequestMensaje;
import com.abi.homeactivity.retrofit.response.ResponseMensaje;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MensajeActivity extends AppCompatActivity {

    Button b_mensajeAyuda;
    TextInputEditText ti_mensajeAyuda;

    AuthABIService authABIService;
    AuthABIClient authABIClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);
        String nombre = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_NOMBRE);
        Log.i("Token4", nombre);

        String mensajeAyuda = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_MENSAJE);
        Log.i("Token5", mensajeAyuda);

        ti_mensajeAyuda.setText("Hola");


        /*responseMensajeLiveData.observe((LifecycleOwner) getApplicationInfo(), new Observer<ResponseMensaje>() {
            @Override
            public void onChanged(ResponseMensaje responseMensaje) {
                ti_mensajeAyuda.setText(responseMensaje.getMensajeAyuda());
            }
        });*/

    }



}
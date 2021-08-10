package com.abi.homeactivity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.popup.PopUpError;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.abi.homeactivity.common.SharedPreferencesManager.getSomeStringValue;

public class WelcomeActivity extends AppCompatActivity {

    private static final int DURACION_SPLASH = 5000;

    AuthABIService authABIService;
    AuthABIClient authABIClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_TOKEN) == null)
        {
            SharedPreferencesManager
                    .setSomeStringValue(Constantes.PREF_TOKEN, "");
        }
        retrofitInit();
        verifica();
    }

    private void retrofitInit()
    {
        authABIClient = AuthABIClient.getInstance();
        authABIService = authABIClient.getAuthABIService();
    }

    private void verifica()
    {
        Call<ResponseLogIn> call = authABIService.verificaJWT();
        call.enqueue(new Callback<ResponseLogIn>() {
            @Override
            public void onResponse(Call<ResponseLogIn> call, Response<ResponseLogIn> response) {
                if(response.isSuccessful())
                {
                    SharedPreferencesManager
                            .setSomeStringValue(Constantes.PREF_TOKEN, response.body().getToken());
                    SharedPreferencesManager
                            .setSomeStringValue(Constantes.PREF_NOMBRE, response.body().getUsuario().getNombre());
                    SharedPreferencesManager
                            .setSomeStringValue(Constantes.PREF_MENSAJE, response.body().getUsuario().getMensajeAyuda());
                    SharedPreferencesManager
                            .setSomeStringValue(Constantes.PREF_ROL, response.body().getUsuario().getRol());
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(WelcomeActivity.this, LogInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ResponseLogIn> call, Throwable t)
            {
                Bundle parametros = new Bundle();
                parametros.putString("Mensaje", "Error en la conexión, intentalo nuevamente");
                Intent i = new Intent(getApplicationContext(), PopUpError.class);
                i.putExtras(parametros);
                startActivity(i);
            }
        });
    }
}
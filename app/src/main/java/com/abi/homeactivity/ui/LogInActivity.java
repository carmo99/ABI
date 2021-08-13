package com.abi.homeactivity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.abi.homeactivity.R;
import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.popup.PopUpCargando;
import com.abi.homeactivity.popup.PopUpCorrecto;
import com.abi.homeactivity.popup.PopUpError;
import com.abi.homeactivity.retrofit.ABIClient;
import com.abi.homeactivity.retrofit.ABIService;
import com.abi.homeactivity.retrofit.request.RequestLogin;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    Button b_login;
    EditText et_email, et_password;
    TextView tv_singup;

    ABIClient abiClient;
    ABIService abiService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        retrofitInit();
        referenciar();
        eventos();
    }

    private void retrofitInit()
    {
        abiClient = ABIClient.getInstance();
        abiService = abiClient.getABIService();
    }

    private void eventos()
    {
        b_login.setOnClickListener(this);
        tv_singup.setOnClickListener(this);
    }

    private void referenciar()
    {
        b_login = findViewById(R.id.buttonIniciarSesion);
        et_email = findViewById(R.id.editTexttCorreo);
        et_password = findViewById(R.id.editTextPassword);
        tv_singup = findViewById(R.id.textViewRegistrarse);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.buttonIniciarSesion)
        {
            gotologin();
        }
        else if (view.getId() == R.id.textViewRegistrarse)
        {
            gotosingup();
        }
    }

    private void gotosingup()
    {
        Intent intent = new Intent(LogInActivity.this, SingUpActivity.class);
        startActivity(intent);
    }

    private void gotologin()
    {
        String s_email = et_email.getText().toString();
        String s_password=et_password.getText().toString();
        Intent iC = new Intent(getApplicationContext(), PopUpCargando.class);
        startActivity(iC);

        if(s_email.isEmpty())
            et_email.setError("El correo es requerido");
        else if(s_password.isEmpty())
            et_password.setError("La contraseña es requerida");
        else
        {
            RequestLogin requestLogin = new RequestLogin(s_email, s_password);
            Call<ResponseLogIn> call = abiService.responselogin(requestLogin);
            call.enqueue(new Callback<ResponseLogIn>() {
                @Override
                public void onResponse(Call<ResponseLogIn> call, Response<ResponseLogIn> response)
                {
                    PopUpCargando.fa.finish();
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

                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        try {
                            String respuesta [] = response.errorBody().string().split("\"");
                            Bundle parametros = new Bundle();
                            int caracteres_totales = respuesta[3].length();
                            caracteres_totales = caracteres_totales/21;
                            float espacio_total = (float)(.3 + (caracteres_totales)*.05);
                            parametros.putString("Mensaje", respuesta[3]);
                            parametros.putFloat("Espacio", espacio_total);
                            Intent i = new Intent(getApplicationContext(), PopUpError.class);
                            i.putExtras(parametros);
                            startActivity(i);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseLogIn> call, Throwable t)
                {
                    PopUpCargando.fa.finish();
                    String mensaje = "Error en la conexión, intentalo nuevamente";
                    Bundle parametros = new Bundle();
                    int caracteres_totales = mensaje.length();
                    caracteres_totales = caracteres_totales/21;
                    float espacio_total = (float)(.3 + (caracteres_totales)*.05);
                    parametros.putFloat("Espacio", espacio_total);
                    parametros.putString("Mensaje", mensaje);
                    Intent i = new Intent(MyApp.getContext(), PopUpError.class);
                    i.putExtras(parametros);
                    startActivity(i);
                }
            });
        }
    }
}
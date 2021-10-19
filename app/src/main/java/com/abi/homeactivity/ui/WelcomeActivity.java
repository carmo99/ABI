package com.abi.homeactivity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

public class WelcomeActivity extends AppCompatActivity {

    private static final int DURACION_SPLASH = 5000;

    AuthABIService authABIService;
    AuthABIClient authABIClient;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CONTADOR, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CONTADOR_TIEMPO, "0");
        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ESTADO, "NORMAL");
        setContentView(R.layout.activity_welcome);
        if(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_TOKEN) == null)
        {
            SharedPreferencesManager
                    .setSomeStringValue(Constantes.PREF_TOKEN, "");
        }
        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CON_BLUETOOTH, null);
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
                            .setSomeStringValue(Constantes.PREF_ID, response.body().getUsuario().getUid());
                    SharedPreferencesManager
                            .setSomeStringValue(Constantes.PREF_TOKEN, response.body().getToken());
                    SharedPreferencesManager
                            .setSomeStringValue(Constantes.PREF_NOMBRE, response.body().getUsuario().getNombre());
                    SharedPreferencesManager
                            .setSomeStringValue(Constantes.PREF_MENSAJE, response.body().getUsuario().getMensajeAyuda());
                    SharedPreferencesManager
                            .setSomeStringValue(Constantes.PREF_ROL, response.body().getUsuario().getRol());
                    SharedPreferencesManager
                            .setSomeStringValue(Constantes.PREF_FOTO_PERFIL, response.body().getUsuario().getFotoPerfil());
                    SharedPreferencesManager
                            .setSomeStringValue(Constantes.PREF_FOTO_DIA, response.body().getUsuario().getFotoDia());

                    //DATOS CONTACTO DE EMERGENCIA 1.
                    if(response.body().getUsuario().getContactoEmergencia1() == null)
                    {
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.NOMBRE_CONT_1, "");
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.CORREO_CONT_1, "");
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.FOTO_CONT_1, "");
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.TELEFONO_CONT_1, "");
                    }
                    else
                    {
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.NOMBRE_CONT_1, response.body().getUsuario().getContactoEmergencia1().getNombre());
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.CORREO_CONT_1, response.body().getUsuario().getContactoEmergencia1().getCorreo());
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.FOTO_CONT_1, response.body().getUsuario().getContactoEmergencia1().getFotoPerfil());
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.TELEFONO_CONT_1, response.body().getUsuario().getContactoEmergencia1().getTelefono());
                    }

                    //DATOS CONTACTO DE EMERGENCIA 2.
                    if(response.body().getUsuario().getContactoEmergencia2() == null)
                    {
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.NOMBRE_CONT_2, "");
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.CORREO_CONT_2, "");
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.FOTO_CONT_2, "");
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.TELEFONO_CONT_2, "");
                    }
                    else
                    {
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.NOMBRE_CONT_2, response.body().getUsuario().getContactoEmergencia2().getNombre());
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.CORREO_CONT_2, response.body().getUsuario().getContactoEmergencia2().getCorreo());
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.FOTO_CONT_2, response.body().getUsuario().getContactoEmergencia2().getFotoPerfil());
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.TELEFONO_CONT_2, response.body().getUsuario().getContactoEmergencia2().getTelefono());
                    }

                    //DATOS CONTACTO DE EMERGENCIA 3.
                    if(response.body().getUsuario().getContactoEmergencia3() == null)
                    {
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.NOMBRE_CONT_3, "");
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.CORREO_CONT_3, "");
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.FOTO_CONT_3, "");
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.TELEFONO_CONT_3, "");
                    }
                    else
                    {
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.NOMBRE_CONT_3, response.body().getUsuario().getContactoEmergencia3().getNombre());
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.CORREO_CONT_3, response.body().getUsuario().getContactoEmergencia3().getCorreo());
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.FOTO_CONT_3, response.body().getUsuario().getContactoEmergencia3().getFotoPerfil());
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.TELEFONO_CONT_3, response.body().getUsuario().getContactoEmergencia3().getTelefono());
                    }

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
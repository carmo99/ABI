package com.abi.homeactivity.popup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;
import com.abi.homeactivity.ui.ContactoFragment;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopUpBorrarContacto extends AppCompatActivity implements View.OnClickListener {
    Button btn_borrar, btn_cancelar;
    AuthABIService authABIService;
    AuthABIClient authABIClient;
    String contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_borrar_contacto);
        Bundle parametros = this.getIntent().getExtras();
        contacto = parametros.getString("Contacto");
        Log.i("CONTACTO", contacto);

        retrofitInit();
        btn_borrar = findViewById(R.id.buttonAceptarBorrarContacto);
        btn_cancelar = findViewById(R.id.buttonCancelarBorrarContacto);
        btn_borrar.setOnClickListener(this);
        btn_cancelar.setOnClickListener(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9), (int)(height*.45));

        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void retrofitInit() {
        authABIClient = AuthABIClient.getInstance();
        authABIService = authABIClient.getAuthABIService();
    }

    private void BorrarContacto()
    {
        Intent iC = new Intent(MyApp.getContext(), PopUpCargando.class);
        startActivity(iC);
        Call<ResponseLogIn> call = authABIService.borrarContacto(contacto);
        call.enqueue(new Callback<ResponseLogIn>() {
            @Override
            public void onResponse(Call<ResponseLogIn> call, Response<ResponseLogIn> response) {
                PopUpCargando.fa.finish();
                if(response.isSuccessful())
                {
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
                    String mensaje = "¡Contacto borrado exitosamente!";
                    Bundle parametros = new Bundle();
                    int caracteres_totales = mensaje.length();
                    caracteres_totales = caracteres_totales/21;
                    float espacio_total = (float)(.3 + (caracteres_totales)*.05);
                    parametros.putFloat("Espacio", espacio_total);
                    parametros.putString("Mensaje", mensaje);
                    Intent i = new Intent(MyApp.getContext(), PopUpCorrecto.class);
                    i.putExtras(parametros);
                    startActivity(i);
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
                        finish();

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
                finish();
            }
        });
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.buttonAceptarBorrarContacto)
        {
            BorrarContacto();
        }
        else if (view.getId() == R.id.buttonCancelarBorrarContacto)
        {
            finish();
        }
    }
}
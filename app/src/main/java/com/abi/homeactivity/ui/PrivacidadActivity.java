package com.abi.homeactivity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.popup.PopUpCargando;
import com.abi.homeactivity.popup.PopUpError;
import com.abi.homeactivity.retrofit.ABIClient;
import com.abi.homeactivity.retrofit.ABIService;
import com.abi.homeactivity.retrofit.request.RequestCrearUsuario;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacidadActivity extends AppCompatActivity implements View.OnClickListener {

    Button b_aceptar;
    CheckBox checkBox;

    ABIClient abiClient;
    ABIService abiService;

    String s_name,s_email,s_password,s_phone;

    TextView aviso_privacidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle parametros = this.getIntent().getExtras();
        s_name = parametros.getString("Nombre");
        s_email = parametros.getString("Correo");
        s_password = parametros.getString("Contrasenia");
        s_phone = parametros.getString("Telefono");

        setContentView(R.layout.activity_privacidad);
        b_aceptar = findViewById(R.id.button_aviso);
        checkBox = findViewById(R.id.checkBoxLeido);
        aviso_privacidad = findViewById(R.id.textView_aviso);
        aviso_privacidad.setMovementMethod(new ScrollingMovementMethod());
        retrofitInit();
        b_aceptar.setOnClickListener(this);
    }

    private void retrofitInit()
    {
        abiClient = ABIClient.getInstance();
        abiService = abiClient.getABIService();
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.button_aviso)
        {
            if(checkBox.isChecked())
            {
                Intent iC = new Intent(getApplicationContext(), PopUpCargando.class);
                startActivity(iC);
                RequestCrearUsuario requestCrearUsuario = new RequestCrearUsuario(s_name,s_email,s_password,s_phone);
                Call<ResponseLogIn> call = abiService.responseSingUpSalvar(requestCrearUsuario);
                call.enqueue(new Callback<ResponseLogIn>() {
                    @Override
                    public void onResponse(Call<ResponseLogIn> call, Response<ResponseLogIn> response)
                    {
                        PopUpCargando.fa.finish();
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
                            SharedPreferencesManager
                                    .setSomeArray(Constantes.PREF_CONTACT_UBI, response.body().getUsuario().getUbicacionEmergencia());
                            SharedPreferencesManager
                                    .setSomeArray(Constantes.PREF_CONTACT_NOMBRE, response.body().getUsuario().getNombreEmergencia());
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
                            Intent intent = new Intent(PrivacidadActivity.this, MainActivity.class);
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
                                Intent i = new Intent(MyApp.getContext(), PopUpError.class);
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
            else
            {
                String mensaje = "Debe aceptar los términos y condiciones para continuar";
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
        }
    }
}
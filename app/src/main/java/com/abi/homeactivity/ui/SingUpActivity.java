package com.abi.homeactivity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SingUpActivity extends AppCompatActivity implements View.OnClickListener {
    Button b_singup;
    EditText et_name, et_email, et_password, et_phone;
    TextView tv_login;

    ABIClient abiClient;
    ABIService abiService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        referenciar();
        retrofitInit();
        eventos();
    }

    private void retrofitInit()
    {
        abiClient = ABIClient.getInstance();
        abiService = abiClient.getABIService();
    }

    private void eventos()
    {
        b_singup.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    private void referenciar()
    {
        b_singup = findViewById(R.id.buttonRegistrarse);
        et_name = findViewById(R.id.editTextNombreUsuario);
        et_email = findViewById(R.id.editTextCorreo);
        et_password = findViewById(R.id.editTextContrasena);
        et_phone = findViewById(R.id.editTextTelefono);
        tv_login = findViewById(R.id.textViewLogIn);
    }


    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.buttonRegistrarse)
            gotosingup();
        else if(view.getId() == R.id.textViewLogIn)
            gotologin();
    }

    private void gotologin()
    {
        Intent intent = new Intent(SingUpActivity.this, LogInActivity.class);
        startActivity(intent);
    }

    private void gotosingup()
    {
        String s_name = et_name.getText().toString();
        String s_email = et_email.getText().toString();
        String s_password = et_password.getText().toString();
        String s_phone = et_phone.getText().toString();
        if(s_name.isEmpty())
            et_name.setError("El nombre es requerido");
        else if(s_email.isEmpty())
            et_email.setError("El correo es requerido");
        else if(s_password.isEmpty())
            et_password.setError("La contraseña es requerida");
        else if(s_phone.isEmpty())
            et_phone.setError("El teléfono es requerido");
        else
        {
            Intent iC = new Intent(getApplicationContext(), PopUpCargando.class);
            startActivity(iC);
            RequestCrearUsuario requestCrearUsuario = new RequestCrearUsuario(s_name,s_email,s_password,s_phone);
            Call<ResponseLogIn> call = abiService.responseSingUp(requestCrearUsuario);
            call.enqueue(new Callback<ResponseLogIn>() {
                @Override
                public void onResponse(Call<ResponseLogIn> call, Response<ResponseLogIn> response) {
                    PopUpCargando.fa.finish();
                    if(response.isSuccessful())
                    {
                        try {
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

                            //DATOS CONTACTO DE EMERGENCIA 1.

                            SharedPreferencesManager
                                    .setSomeStringValue(Constantes.NOMBRE_CONT_1, response.body().getUsuario().getContactoEmergencia1().getNombre());
                            SharedPreferencesManager
                                    .setSomeStringValue(Constantes.CORREO_CONT_1, response.body().getUsuario().getContactoEmergencia1().getCorreo());
                            SharedPreferencesManager
                                    .setSomeStringValue(Constantes.FOTO_CONT_1, response.body().getUsuario().getContactoEmergencia1().getFotoPerfil());
                            SharedPreferencesManager
                                    .setSomeStringValue(Constantes.TELEFONO_CONT_1, response.body().getUsuario().getContactoEmergencia1().getTelefono());

                            //DATOS CONTACTO DE EMERGENCIA 2.

                            SharedPreferencesManager
                                    .setSomeStringValue(Constantes.NOMBRE_CONT_2, response.body().getUsuario().getContactoEmergencia2().getNombre());
                            SharedPreferencesManager
                                    .setSomeStringValue(Constantes.CORREO_CONT_2, response.body().getUsuario().getContactoEmergencia2().getCorreo());
                            SharedPreferencesManager
                                    .setSomeStringValue(Constantes.FOTO_CONT_2, response.body().getUsuario().getContactoEmergencia2().getFotoPerfil());
                            SharedPreferencesManager
                                    .setSomeStringValue(Constantes.TELEFONO_CONT_2, response.body().getUsuario().getContactoEmergencia2().getTelefono());

                            //DATOS CONTACTO DE EMERGENCIA 3.

                            SharedPreferencesManager
                                    .setSomeStringValue(Constantes.NOMBRE_CONT_3, response.body().getUsuario().getContactoEmergencia3().getNombre());
                            SharedPreferencesManager
                                    .setSomeStringValue(Constantes.CORREO_CONT_3, response.body().getUsuario().getContactoEmergencia3().getCorreo());
                            SharedPreferencesManager
                                    .setSomeStringValue(Constantes.FOTO_CONT_3, response.body().getUsuario().getContactoEmergencia3().getFotoPerfil());
                            SharedPreferencesManager
                                    .setSomeStringValue(Constantes.TELEFONO_CONT_3, response.body().getUsuario().getContactoEmergencia3().getTelefono());
                        }
                        catch (Exception e)
                        {
                            Log.i("CONSTANTES", "ERROR LOGIN");
                        }
                        Intent intent = new Intent(SingUpActivity.this, PrivacidadActivity.class);
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
                public void onFailure(Call<ResponseLogIn> call, Throwable t) {
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
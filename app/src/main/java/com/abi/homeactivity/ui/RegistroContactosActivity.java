package com.abi.homeactivity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.popup.PopUpCargando;
import com.abi.homeactivity.popup.PopUpCorrecto;
import com.abi.homeactivity.popup.PopUpError;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.abi.homeactivity.retrofit.request.RequestContacto;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;
import com.abi.homeactivity.retrofit.response.ResponseObtenerContactos;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroContactosActivity extends AppCompatActivity implements View.OnClickListener {

    Button anadir_contactos;
    ImageButton atras_contacto;
    EditText et_correo_RC, et_telefono_RC;

    AuthABIService authABIService;
    AuthABIClient authABIClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registro_contactos);
        anadir_contactos = findViewById(R.id.buttonRegistraGadget);
        et_correo_RC = findViewById(R.id.editTextTextEmailRC);
        et_telefono_RC = findViewById(R.id.editTextPhoneRC);
        atras_contacto = findViewById(R.id.imageButton_atras_registrar_contacto);
        atras_contacto.setOnClickListener(this);
        anadir_contactos.setOnClickListener(this);
        retrofitInit();
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */)
        {

            @Override
            public void handleOnBackPressed()
            {
                Intent intent = new Intent(RegistroContactosActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.buttonRegistraGadget)
        {
            registarContacto();
        }
        else if( view.getId() == R.id.imageButton_atras_registrar_contacto)
        {
            Intent intent = new Intent(RegistroContactosActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void retrofitInit() {
        authABIClient = AuthABIClient.getInstance();
        authABIService = authABIClient.getAuthABIService();
    }

    private void registarContacto()
    {
        String s_correo_RC = et_correo_RC.getText().toString();
        String s_telefono_RC = et_telefono_RC.getText().toString();
        if(s_correo_RC.isEmpty())
        {
            et_correo_RC.setError("El correo es requerido");
        }
        else if(s_telefono_RC.isEmpty()) {
            et_telefono_RC.setError("El correo es requerido");
        }
        else
        {
            Intent iC = new Intent(MyApp.getContext(), PopUpCargando.class);
            startActivity(iC);
            RequestContacto requestContacto = new RequestContacto(s_correo_RC, s_telefono_RC);
            Call<ResponseLogIn> call = authABIService.registrarContacto(requestContacto);
            call.enqueue(new Callback<ResponseLogIn>() {
                @Override
                public void onResponse(Call<ResponseLogIn> call, Response<ResponseLogIn> response)
                {
                    PopUpCargando.fa.finish();
                    if(response.isSuccessful())
                    {
                        Call <ResponseObtenerContactos> call1 = authABIService.obtenercontactos();
                        call1.enqueue(new Callback<ResponseObtenerContactos>() {
                            @Override
                            public void onResponse(Call<ResponseObtenerContactos> call1, Response<ResponseObtenerContactos> response1)
                            {
                                //DATOS CONTACTO DE EMERGENCIA 1.
                                if(response1.body().getContactoEmergencia1() == null)
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
                                            .setSomeStringValue(Constantes.NOMBRE_CONT_1, response1.body().getContactoEmergencia1().getNombre());
                                    SharedPreferencesManager
                                            .setSomeStringValue(Constantes.CORREO_CONT_1, response1.body().getContactoEmergencia1().getCorreo());
                                    SharedPreferencesManager
                                            .setSomeStringValue(Constantes.FOTO_CONT_1, response1.body().getContactoEmergencia1().getFotoPerfil());
                                    SharedPreferencesManager
                                            .setSomeStringValue(Constantes.TELEFONO_CONT_1, response1.body().getContactoEmergencia1().getTelefono());
                                }

                                //DATOS CONTACTO DE EMERGENCIA 2.
                                if(response1.body().getContactoEmergencia2() == null)
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
                                            .setSomeStringValue(Constantes.NOMBRE_CONT_2, response1.body().getContactoEmergencia2().getNombre());
                                    SharedPreferencesManager
                                            .setSomeStringValue(Constantes.CORREO_CONT_2, response1.body().getContactoEmergencia2().getCorreo());
                                    SharedPreferencesManager
                                            .setSomeStringValue(Constantes.FOTO_CONT_2, response1.body().getContactoEmergencia2().getFotoPerfil());
                                    SharedPreferencesManager
                                            .setSomeStringValue(Constantes.TELEFONO_CONT_2, response1.body().getContactoEmergencia2().getTelefono());
                                }

                                //DATOS CONTACTO DE EMERGENCIA 3.
                                if(response1.body().getContactoEmergencia3() == null)
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
                                            .setSomeStringValue(Constantes.NOMBRE_CONT_3, response1.body().getContactoEmergencia3().getNombre());
                                    SharedPreferencesManager
                                            .setSomeStringValue(Constantes.CORREO_CONT_3, response1.body().getContactoEmergencia3().getCorreo());
                                    SharedPreferencesManager
                                            .setSomeStringValue(Constantes.FOTO_CONT_3, response1.body().getContactoEmergencia3().getFotoPerfil());
                                    SharedPreferencesManager
                                            .setSomeStringValue(Constantes.TELEFONO_CONT_3, response1.body().getContactoEmergencia3().getTelefono());
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseObtenerContactos> call1, Throwable t1)
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
                        String mensaje = "¡Contacto registrado exitosamente!";
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

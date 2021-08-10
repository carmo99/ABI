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
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.popup.PopUpCargando;
import com.abi.homeactivity.popup.PopUpCorrecto;
import com.abi.homeactivity.popup.PopUpError;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.abi.homeactivity.retrofit.request.RequestContacto;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;

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
        anadir_contactos = findViewById(R.id.buttonAnadirContacto);
        et_correo_RC = findViewById(R.id.editTextTextEmailRC);
        et_telefono_RC = findViewById(R.id.editTextPhoneRC);
        atras_contacto = findViewById(R.id.imageButton_atras_nuevo_contacto);
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
        if(view.getId() == R.id.buttonAnadirContacto)
        {
            registarContacto();
        }
        else if( view.getId() == R.id.imageButton_atras_nuevo_contacto)
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
        Intent iC = new Intent(MyApp.getContext(), PopUpCargando.class);
        startActivity(iC);
        String s_correo_RC = et_correo_RC.getText().toString();
        String s_telefono_RC = et_telefono_RC.getText().toString();
        if(s_correo_RC.isEmpty())
            et_correo_RC.setError("El correo es requerido");
        else if(s_telefono_RC.isEmpty())
            et_telefono_RC.setError("El correo es requerido");
        else
        {
            RequestContacto requestContacto = new RequestContacto(s_correo_RC, s_telefono_RC);
            Call<ResponseLogIn> call = authABIService.registrarContacto(requestContacto);
            call.enqueue(new Callback<ResponseLogIn>() {
                @Override
                public void onResponse(Call<ResponseLogIn> call, Response<ResponseLogIn> response)
                {
                    PopUpCargando.fa.finish();
                    if(response.isSuccessful())
                    {
                        Bundle parametros = new Bundle();
                        parametros.putString("Mensaje", "Contacto registrado exitosamente");
                        Intent i = new Intent(MyApp.getContext(), PopUpCorrecto.class);
                        i.putExtras(parametros);
                        startActivity(i);
                    }
                    else
                    {
                        try {
                            String respuesta [] = response.errorBody().string().split("\"");
                            Bundle parametros = new Bundle();
                            parametros.putString("Mensaje", respuesta[3]);
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
                    Bundle parametros = new Bundle();
                    parametros.putString("Mensaje", "Error en la conexión, intentalo nuevamente");
                    Intent i = new Intent(MyApp.getContext(), PopUpError.class);
                    i.putExtras(parametros);
                    startActivity(i);
                }
            });
        }
    }
}

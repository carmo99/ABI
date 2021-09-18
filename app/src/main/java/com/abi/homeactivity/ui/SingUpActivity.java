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
            Call<ResponseLogIn> call = abiService.responseSingUpVerificar(requestCrearUsuario);
            call.enqueue(new Callback<ResponseLogIn>() {
                @Override
                public void onResponse(Call<ResponseLogIn> call, Response<ResponseLogIn> response) {
                    PopUpCargando.fa.finish();
                    if(response.isSuccessful())
                    {
                        Bundle parametros = new Bundle();
                        parametros.putString("Nombre", s_name);
                        parametros.putString("Correo", s_email);
                        parametros.putString("Contrasenia", s_password);
                        parametros.putString("Telefono", s_phone);
                        Intent intent = new Intent(SingUpActivity.this, PrivacidadActivity.class);
                        intent.putExtras(parametros);
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
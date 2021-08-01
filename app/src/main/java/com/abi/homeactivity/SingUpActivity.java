package com.abi.homeactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.retrofit.ABIClient;
import com.abi.homeactivity.retrofit.ABIService;
import com.abi.homeactivity.retrofit.request.RequestCrearUsuario;
import com.abi.homeactivity.retrofit.response.ResponseLogin;

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
            RequestCrearUsuario requestCrearUsuario = new RequestCrearUsuario(s_name,s_email,s_password,s_phone);
            Call<ResponseLogin> call = abiService.responseSingUp(requestCrearUsuario);
            call.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    if(response.isSuccessful())
                    {
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.PREF_TOKEN, response.body().getToken());
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.PREF_NOMBRE, response.body().getUsuario().getNombre());
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.PREF_MENSAJE, response.body().getUsuario().getMensajeAyuda());

                        Intent intent = new Intent(SingUpActivity.this, PrivacidadActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(SingUpActivity.this, "Algo falló", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    Toast.makeText(SingUpActivity.this, "Problemas de conexión", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


}
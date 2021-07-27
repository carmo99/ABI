package com.abi.homeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.retrofit.ABIClient;
import com.abi.homeactivity.retrofit.ABIService;
import com.abi.homeactivity.retrofit.request.RequestLogin;
import com.abi.homeactivity.retrofit.response.ResponseLogin;

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

        if(s_email.isEmpty())
            et_email.setError("El correo es requerido");
        else if(s_password.isEmpty())
            et_password.setError("La contraseña es requerida");
        else
        {
            RequestLogin requestLogin = new RequestLogin(s_email, s_password);
            Call<ResponseLogin> call = abiService.responselogin(requestLogin);
            call.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    if(response.isSuccessful())
                    {
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.PREF_TOKEN, response.body().getToken());
                        SharedPreferencesManager
                                .setSomeStringValue(Constantes.PREF_NOMBRE, response.body().getUsuario().getNombre());

                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(LogInActivity.this, "Algo falló", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t)
                {
                    Toast.makeText(LogInActivity.this, "Problemas de conexión", Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}
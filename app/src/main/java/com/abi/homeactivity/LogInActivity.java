package com.abi.homeactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    Button b_login;
    EditText et_email, et_password;
    TextView tv_singup;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        referenciar();
        eventos();
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
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
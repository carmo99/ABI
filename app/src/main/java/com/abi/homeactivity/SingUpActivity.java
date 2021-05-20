package com.abi.homeactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SingUpActivity extends AppCompatActivity implements View.OnClickListener {
    Button b_singup;
    EditText et_name, et_email, et_password, et_phone;
    TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        referenciar();
        eventos();
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
            Intent intent = new Intent(SingUpActivity.this, PrivacidadActivity.class);
            startActivity(intent);
            finish();
        }
    }


}
package com.abi.homeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroContactosActivity extends AppCompatActivity implements View.OnClickListener {

    Button anadir_contactos;
    EditText et_correo_RC, et_telefono_RC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registro_contactos);

        anadir_contactos = findViewById(R.id.buttonAnadirContacto);
        et_correo_RC = findViewById(R.id.editTextTextEmailRC);
        et_telefono_RC = findViewById(R.id.editTextPhoneRC);
        anadir_contactos.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.buttonAnadirContacto)
        {
            registarContacto();
        }
    }

    private void registarContacto()
    {
        String s_correo_RC = et_correo_RC.getText().toString();
        String s_telefono_RC = et_telefono_RC.getText().toString();

        if(s_correo_RC.isEmpty())
            et_correo_RC.setError("El correo es requerido");
        else if(s_telefono_RC.isEmpty())
            et_telefono_RC.setError("El correo es requerido");
        else
        {
            Intent intent = new Intent(RegistroContactosActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

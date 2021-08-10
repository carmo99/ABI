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

public class RegistroContactosActivity extends AppCompatActivity implements View.OnClickListener {

    Button anadir_contactos;
    ImageButton atras_contacto;
    EditText et_correo_RC, et_telefono_RC;

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

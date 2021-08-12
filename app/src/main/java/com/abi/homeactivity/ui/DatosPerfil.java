package com.abi.homeactivity.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.data.PerfilViewModel;
import com.abi.homeactivity.popup.PopUpCargando;
import com.abi.homeactivity.popup.PopUpCorrecto;
import com.abi.homeactivity.popup.PopUpError;
import com.abi.homeactivity.retrofit.ABIClient;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.abi.homeactivity.retrofit.request.RequestPerfil;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;
import com.abi.homeactivity.retrofit.response.ResponsetPerfil;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.gson.reflect.TypeToken.get;

public class DatosPerfil extends AppCompatActivity implements View.OnClickListener {

    Button cambiar_contrasenia, cambiar_foto, actualizar_datos;
    EditText nombre, correo, telefono;
    ImageButton atras;
    AuthABIService authABIService;
    AuthABIClient authABIClient;

    private PerfilViewModel perfilViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_perfil);

        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        cambiar_contrasenia = findViewById(R.id.buttonCambiarContrasenia);
        atras = findViewById(R.id.imageButtonDatosPerfilToMainActivity);
        cambiar_foto = findViewById(R.id.buttonCambiarFoto);
        nombre = findViewById(R.id.editTextTextNombreUsuario);
        correo = findViewById(R.id.editTextCorreoUsuario);
        telefono = findViewById(R.id.editTextTelefonoUsuario);
        actualizar_datos = findViewById(R.id.buttonActualizarDatos);

        retrofitInit();

        cambiar_contrasenia.setOnClickListener(this);
        atras.setOnClickListener(this);
        actualizar_datos.setOnClickListener(this);

        perfilViewModel.perfilUsuario.observe(this, new Observer<ResponsetPerfil>() {
            @Override
            public void onChanged(ResponsetPerfil responsetPerfil) {
                nombre.setText(responsetPerfil.getNombre());
                correo.setText(responsetPerfil.getCorreo());
                telefono.setText(responsetPerfil.getTelefono());
            }
        });

    }
    private void retrofitInit()
    {
        authABIClient = AuthABIClient.getInstance();
        authABIService = authABIClient.getAuthABIService();
    }

    @Override
    public void onClick(View view) {
        switch( view.getId() ){
            case R.id.buttonCambiarContrasenia:
                Intent intent1 = new Intent(DatosPerfil.this, ActivityContrasenia.class);
                startActivity( intent1 );
                this.finish();
                break;
            case R.id.imageButtonDatosPerfilToMainActivity:
                Intent intent2 = new Intent(DatosPerfil.this, MainActivity.class);
                startActivity( intent2 );
                this.finish();
                break;
            case R.id.buttonActualizarDatos:
                Intent iC = new Intent(getApplicationContext(), PopUpCargando.class);
                startActivity(iC);
                RequestPerfil requestPerfil = new RequestPerfil(nombre.getText().toString(),correo.getText().toString(), telefono.getText().toString());
                Call<ResponseLogIn> call = authABIService.actualizarPerfil( requestPerfil );
                call.enqueue(new Callback<ResponseLogIn>() {
                    @Override
                    public void onResponse(Call<ResponseLogIn> call, Response<ResponseLogIn> response) {
                        PopUpCargando.fa.finish();
                        if ( response.isSuccessful()){
                            Bundle parametros = new Bundle();
                            parametros.putString("Mensaje", "Datos actualizados exitosamente");
                            Intent i = new Intent(MyApp.getContext(), PopUpCorrecto.class);
                            i.putExtras(parametros);
                            startActivity(i);
                        }else{
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
                    public void onFailure(Call<ResponseLogIn> call, Throwable t) {
                        PopUpCargando.fa.finish();
                        Bundle parametros = new Bundle();
                        parametros.putString("Mensaje", "Error en la conexión, intentalo nuevamente");
                        Intent i = new Intent(MyApp.getContext(), PopUpError.class);
                        i.putExtras(parametros);
                        startActivity(i);
                    }
                });
                default:
                break;
        }

    }
}
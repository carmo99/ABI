package com.abi.homeactivity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.popup.PopUpCargando;
import com.abi.homeactivity.popup.PopUpCorrecto;
import com.abi.homeactivity.popup.PopUpError;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.abi.homeactivity.retrofit.request.RequestContrasenia;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityContrasenia extends AppCompatActivity implements View.OnClickListener {

    ImageButton atras;
    EditText contraseniaActual, contraseniaNueva;
    Button actualizar;
    AuthABIService authABIService;
    AuthABIClient authABIClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasenia);
        contraseniaActual = findViewById(R.id.editTextTextContraseniaActual);
        contraseniaNueva = findViewById(R.id.editTextTextNuevaContrasenia);
        actualizar = findViewById(R.id.buttonActualizaContrasenia);
        atras = findViewById( R.id.imageButtonCambioContraseniaToDatosPerfil);
        retrofiInit();
        atras.setOnClickListener(this);
        actualizar.setOnClickListener(this);
    }

    private void retrofiInit(){
            authABIClient = AuthABIClient.getInstance();
            authABIService = authABIClient.getAuthABIService();
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ){
            case R.id.imageButtonCambioContraseniaToDatosPerfil:
                Intent i = new Intent( ActivityContrasenia.this, DatosPerfil.class);
                startActivity( i );
                this.finish();
                break;
            case R.id.buttonActualizaContrasenia:
                Intent iC = new Intent(getApplicationContext(), PopUpCargando.class);
                startActivity(iC);
                String conActual = contraseniaActual.getText().toString();
                String conNueva = contraseniaNueva.getText().toString();
                RequestContrasenia requestContrasenia = new RequestContrasenia( conActual, conNueva );
                Call<ResponseLogIn> call = authABIService.actualizarContrasenia( requestContrasenia );
                call.enqueue(new Callback<ResponseLogIn>() {
                    @Override
                    public void onResponse(Call<ResponseLogIn> call, Response<ResponseLogIn> response) {
                        PopUpCargando.fa.finish();
                        if ( response.isSuccessful() ){
                            String mensaje = "¡Contraseña actualizada exitosamente!";
                            contraseniaActual.setText("");
                            contraseniaNueva.setText("");
                            Bundle parametros = new Bundle();
                            int caracteres_totales = mensaje.length();
                            caracteres_totales = caracteres_totales/21;
                            float espacio_total = (float)(.3 + (caracteres_totales)*.05);
                            parametros.putFloat("Espacio", espacio_total);
                            parametros.putString("Mensaje", mensaje);
                            Intent i = new Intent(MyApp.getContext(), PopUpCorrecto.class);
                            i.putExtras(parametros);
                            startActivity(i);
                        }else{
                            try {
                                String respuesta [] = new String[0];
                                respuesta = response.errorBody().string().split("\"");
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
package com.abi.homeactivity.ui;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.popup.PopUpCargando;
import com.abi.homeactivity.popup.PopUpCorrecto;
import com.abi.homeactivity.popup.PopUpError;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.abi.homeactivity.retrofit.request.RequestRegistraGadget;
import com.abi.homeactivity.retrofit.response.ResponseRegistraGadget;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistraGadgetActivity extends AppCompatActivity implements View.OnClickListener {

    Button registrar_gadget;
    ImageButton atras_gadget;
    EditText et_codigoDeGadget;

    AuthABIService authABIService;
    AuthABIClient authABIClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_gadget);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */)
        {

            @Override
            public void handleOnBackPressed()
            {
                Intent intent = new Intent(RegistraGadgetActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);

        referenciar();
        retrofitInit();
        eventos();

    }

    private void eventos() {

        registrar_gadget.setOnClickListener(this);
        atras_gadget.setOnClickListener(this);
    }

    private void referenciar(){
        registrar_gadget = (Button) findViewById(R.id.buttonRegistraGadget);
        atras_gadget = (ImageButton) findViewById(R.id.imageButton_atras_RegistraGadget);
        et_codigoDeGadget = (EditText) findViewById(R.id.editTextCodigoGadget);
    }

    private void retrofitInit() {
        authABIClient = AuthABIClient.getInstance();
        authABIService = authABIClient.getAuthABIService();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imageButton_atras_RegistraGadget) {
            Intent intent = new Intent(MyApp.getContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (view.getId() == R.id.buttonRegistraGadget) {
            registraGadget();
        }

    }

    private void registraGadget() {

        Intent iC = new Intent(MyApp.getContext(), PopUpCargando.class);
        startActivity(iC);

        String s_codigoGadget = et_codigoDeGadget.getText().toString();

        RequestRegistraGadget requestRegistraGadget = new RequestRegistraGadget(s_codigoGadget);
        Call<ResponseRegistraGadget> call = authABIService.registrarGadget(requestRegistraGadget);
        call.enqueue(new Callback<ResponseRegistraGadget>() {
            @Override
            public void onResponse(Call<ResponseRegistraGadget> call, Response<ResponseRegistraGadget> response) {
                PopUpCargando.fa.finish();
                if (response.isSuccessful())
                {
                    SharedPreferencesManager
                            .setSomeStringValue(Constantes.PREF_ROL, "PREMIUM_ROLE");
                    requestRegistraGadget.setGadget(s_codigoGadget);
                    String mensaje = "¡Tu gadget ha sido registrado!";
                    Bundle parametros = new Bundle();
                    int caracteres_totales = mensaje.length();
                    caracteres_totales = caracteres_totales/21;
                    float espacio_total = (float)(.3 + (caracteres_totales)*.05);
                    parametros.putFloat("Espacio", espacio_total);
                    parametros.putString("Mensaje", mensaje);
                    Intent i = new Intent(MyApp.getContext(), PopUpCorrecto.class);
                    i.putExtras(parametros);
                    startActivity(i);
                }
                else {
                    try {
                        String respuesta [] = response.errorBody().string().split("\"");
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
            public void onFailure(Call<ResponseRegistraGadget> call, Throwable t) {
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
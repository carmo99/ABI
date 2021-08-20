package com.abi.homeactivity.popup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.popup.PopUpCargando;
import com.abi.homeactivity.popup.PopUpCorrecto;
import com.abi.homeactivity.popup.PopUpError;
import com.abi.homeactivity.retrofit.ABIClient;
import com.abi.homeactivity.retrofit.ABIService;
import com.abi.homeactivity.retrofit.request.RequestOlvideContrasenia;
import com.abi.homeactivity.retrofit.response.ResponseOTP;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopUpCambioContraseniaOlvidada extends AppCompatActivity implements View.OnClickListener
{

    TextView tv_cerrar;
    EditText et_cambiaContraseniaOTP;
    Button btn_cambiaContraseniaOTP;

    String correoCambioContrasenia;

    ABIClient abiClient;
    ABIService abiService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_cambio_contrasenia_olvidada);

        tv_cerrar       = findViewById(R.id.textView_cambiaContraseniaOTP);
        et_cambiaContraseniaOTP = findViewById(R.id.editTextCambiaContraseniaOTP);
        btn_cambiaContraseniaOTP = findViewById(R.id.buttonCambiaContraseniaOTP);

        tv_cerrar.setOnClickListener(this);
        btn_cambiaContraseniaOTP.setOnClickListener(this);

        abiClient = ABIClient.getInstance();
        abiService = abiClient.getABIService();

        Bundle parametros = this.getIntent().getExtras();
        correoCambioContrasenia = parametros.getString("Correo");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9), (int)(height*.6));

        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.textView_cambiaContraseniaOTP)
        {
            finish();
        }
        else if (v.getId() == R.id.buttonCambiaContraseniaOTP)
        {
            cambiaContrasenia();
        }
    }

    private void cambiaContrasenia()
    {
        Intent iC = new Intent(MyApp.getContext(), PopUpCargando.class);
        startActivity(iC);

        String contraseniaNueva = et_cambiaContraseniaOTP.getText().toString();

        RequestOlvideContrasenia requestOlvideContrasenia = new RequestOlvideContrasenia(correoCambioContrasenia,contraseniaNueva);
        Call<ResponseOTP> call = abiService.responseCambiarContrOlvidada(requestOlvideContrasenia);
        call.enqueue(new Callback<ResponseOTP>() {
            @Override
            public void onResponse(Call<ResponseOTP> call, Response<ResponseOTP> response)
            {
                PopUpCargando.fa.finish();
                if (response.isSuccessful())
                {
                    requestOlvideContrasenia.setCorreo(correoCambioContrasenia);
                    requestOlvideContrasenia.setContrasenia(contraseniaNueva);

                    String mensaje = "¡Tu contraseña ha sido cambiada, ahora puedes iniciar sesión!";
                    Bundle parametros = new Bundle();
                    int caracteres_totales = mensaje.length();
                    caracteres_totales = caracteres_totales/21;
                    float espacio_total = (float)(.3 + (caracteres_totales)*.05);
                    parametros.putFloat("Espacio", espacio_total);
                    parametros.putString("Mensaje", mensaje);
                    Intent i = new Intent(MyApp.getContext(), PopUpCorrecto.class);
                    i.putExtras(parametros);
                    startActivity(i);
                    finish();

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
                        Intent i = new Intent(MyApp.getContext(), PopUpError.class);
                        i.putExtras(parametros);
                        startActivity(i);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseOTP> call, Throwable t)
            {
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
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
import com.abi.homeactivity.retrofit.ABIClient;
import com.abi.homeactivity.retrofit.ABIService;
import com.abi.homeactivity.retrofit.request.RequestGeneraOTP;
import com.abi.homeactivity.retrofit.response.ResponseOTP;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopUpGeneraOTP extends AppCompatActivity implements View.OnClickListener {

    TextView tv_cerrar;
    EditText et_correoGeneraOTP;
    Button btn_mandarCorreoOTP;

    ABIClient abiClient;
    ABIService abiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_genera_o_t_p);

        tv_cerrar           = findViewById(R.id.textView_cerrarOlvideContrasenia);
        et_correoGeneraOTP  = findViewById(R.id.editTextCorreoOTP);
        btn_mandarCorreoOTP = findViewById(R.id.buttonEnviaCorreoGeneraOTP);

        tv_cerrar.setOnClickListener(this);
        btn_mandarCorreoOTP.setOnClickListener(this);

        abiClient = ABIClient.getInstance();
        abiService = abiClient.getABIService();

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
        if(v.getId() == R.id.textView_cerrarOlvideContrasenia)
        {
            finish();
        }
        else if (v.getId() == R.id.buttonEnviaCorreoGeneraOTP)
        {
            generaOTP();
        }

    }

    private void generaOTP()
    {
        Intent iC = new Intent(MyApp.getContext(), PopUpCargando.class);
        startActivity(iC);

        String correoGeneraOTP = et_correoGeneraOTP.getText().toString();

        RequestGeneraOTP requestGeneraOTP = new RequestGeneraOTP(correoGeneraOTP);
        Call<ResponseOTP> call = abiService.responseGeneraOTP(requestGeneraOTP);
        call.enqueue(new Callback<ResponseOTP>() {
            @Override
            public void onResponse(Call<ResponseOTP> call, Response<ResponseOTP> response) {
                PopUpCargando.fa.finish();
                if (response.isSuccessful())
                {
                    requestGeneraOTP.setCorreo(correoGeneraOTP);

                    Bundle parametros = new Bundle();
                    parametros.putString("Correo", correoGeneraOTP);
                    Intent i = new Intent(MyApp.getContext(), PopUpVerificaOTP.class);
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
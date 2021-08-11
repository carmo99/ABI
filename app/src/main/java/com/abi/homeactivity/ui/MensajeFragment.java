package com.abi.homeactivity.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.Toast;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.popup.PopUpCargando;
import com.abi.homeactivity.popup.PopUpCorrecto;
import com.abi.homeactivity.popup.PopUpError;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.abi.homeactivity.retrofit.request.RequestMensaje;
import com.abi.homeactivity.retrofit.response.ResponseMensaje;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MensajeFragment extends Fragment implements View.OnClickListener {

    View vista;
    ImageButton atras_mensaje;

    Button b_mensajeAyuda;
    TextInputEditText ti_mensajeAyuda;

    AuthABIService authABIService;
    AuthABIClient authABIClient;

    String mensajeAyuda;


    public MensajeFragment() {
        // Required empty public constructor
    }

    public static MensajeFragment newInstance(String param1, String param2) {
        MensajeFragment fragment = new MensajeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */)
        {

            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        retrofitInit();

        mensajeAyuda = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_MENSAJE);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_mensaje, container, false);

        referenciar();
        eventos();

        //Seteamos el mensaje de ayuda en el TextInputEditText
        ti_mensajeAyuda.setText(mensajeAyuda);

        return vista;
    }

    private void eventos()
    {
        atras_mensaje.setOnClickListener(this);
        b_mensajeAyuda.setOnClickListener(this);
    }

    private void referenciar()
    {
        atras_mensaje = (ImageButton) vista.findViewById(R.id.imageButton_atras_mensaje);
        ti_mensajeAyuda = (TextInputEditText) vista.findViewById(R.id.textInputEditText);
        b_mensajeAyuda = (Button) vista.findViewById(R.id.buttonMensaje);

    }

    private void retrofitInit() {
        authABIClient = AuthABIClient.getInstance();
        authABIService = authABIClient.getAuthABIService();
    }


    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.imageButton_atras_mensaje) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        if (view.getId() == R.id.buttonMensaje) {
            actualizarMensaje();
        }
    }

    private void actualizarMensaje()
    {
        Intent iC = new Intent(MyApp.getContext(), PopUpCargando.class);
        startActivity(iC);

        String s_mensajeAyuda = ti_mensajeAyuda.getText().toString();

        RequestMensaje requestMensaje = new RequestMensaje(s_mensajeAyuda);
        Call<ResponseMensaje> call = authABIService.updateMensajeAyuda(requestMensaje);
        call.enqueue(new Callback<ResponseMensaje>()
        {
            @Override
            public void onResponse(Call<ResponseMensaje> call, Response<ResponseMensaje> response)
            {
                PopUpCargando.fa.finish();
                if (response.isSuccessful())
                {
                    requestMensaje.setMensajeAyuda(s_mensajeAyuda);
                    ti_mensajeAyuda.setText(s_mensajeAyuda);
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_MENSAJE, s_mensajeAyuda);

                    Bundle parametros = new Bundle();
                    parametros.putString("Mensaje", "¡Tu mensaje de ayuda ha sido actualizado!");
                    Intent i = new Intent(MyApp.getContext(), PopUpCorrecto.class);
                    i.putExtras(parametros);
                    startActivity(i);

                }
                else {
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
            public void onFailure(Call<ResponseMensaje> call, Throwable t)
            {
                Bundle parametros = new Bundle();
                parametros.putString("Mensaje", "Error en la conexión, intentalo nuevamente");
                Intent i = new Intent(MyApp.getContext(), PopUpError.class);
                i.putExtras(parametros);
                startActivity(i);
            }
        });
    }
}
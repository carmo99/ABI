package com.abi.homeactivity.ui.home.dummy;

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

import com.abi.homeactivity.LogInActivity;
import com.abi.homeactivity.MainActivity;
import com.abi.homeactivity.R;
import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.abi.homeactivity.retrofit.request.RequestMensaje;
import com.abi.homeactivity.retrofit.response.ResponseMensaje;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MensajeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MensajeFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MensajeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MensajeFragment newInstance(String param1, String param2) {
        MensajeFragment fragment = new MensajeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        Log.i("Token5", mensajeAyuda);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_mensaje, container, false);
        atras_mensaje = (ImageButton) vista.findViewById(R.id.imageButton_atras_mensaje);
        ti_mensajeAyuda = (TextInputEditText) vista.findViewById(R.id.textInputEditText);
        b_mensajeAyuda = (Button) vista.findViewById(R.id.buttonMensaje);
        atras_mensaje.setOnClickListener(this);
        b_mensajeAyuda.setOnClickListener(this);

        ti_mensajeAyuda.setText(mensajeAyuda);
        return vista;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imageButton_atras_mensaje) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        if (view.getId() == R.id.buttonMensaje) {
            actualizarMensaje();
        }
    }
    private void retrofitInit() {
        authABIClient = AuthABIClient.getInstance();
        authABIService = authABIClient.getAuthABIService();
    }

    private void actualizarMensaje()
    {
        String s_mensajeAyuda = ti_mensajeAyuda.getText().toString();
        if (s_mensajeAyuda.isEmpty()) {
            ti_mensajeAyuda.setError("El mensaje es requerido");
        }

        RequestMensaje requestMensaje = new RequestMensaje(s_mensajeAyuda);
        Call<ResponseMensaje> call = authABIService.updateMensajeAyuda(requestMensaje);
        call.enqueue(new Callback<ResponseMensaje>()
        {
            @Override
            public void onResponse(Call<ResponseMensaje> call, Response<ResponseMensaje> response) {
                if (response.isSuccessful())
                {
                    requestMensaje.setMensajeAyuda(s_mensajeAyuda);
                    ti_mensajeAyuda.setText(s_mensajeAyuda);
                    SharedPreferencesManager
                            .setSomeStringValue(Constantes.PREF_MENSAJE, s_mensajeAyuda);
                    Toast.makeText(MyApp.getContext(), "¡Tu mensaje de ayuda ha sido actualizado!", Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(MyApp.getContext(), "Algo ha ido mal", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseMensaje> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
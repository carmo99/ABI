package com.abi.homeactivity.data;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.retrofit.AuthABIClient;
import com.abi.homeactivity.retrofit.AuthABIService;
import com.abi.homeactivity.retrofit.response.ResponseLogIn;
import com.abi.homeactivity.retrofit.response.ResponsetPerfil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilRepository{
    AuthABIService authABIService;
    AuthABIClient authABIClient;

    MutableLiveData<ResponsetPerfil> perfilUsuario;
    MutableLiveData<ResponseLogIn> perfilUsuarioAc;

    PerfilRepository(){
        authABIClient = AuthABIClient.getInstance();
        authABIService = authABIClient.getAuthABIService();
        perfilUsuario = getPerfil();
    }

    public MutableLiveData<ResponsetPerfil> getPerfil() {
        if ( perfilUsuario == null){
            perfilUsuario = new MutableLiveData<>();
        }

        Call<ResponsetPerfil> call = authABIService.getPerfil();
        call.enqueue(new Callback<ResponsetPerfil>() {
            @Override
            public void onResponse(Call<ResponsetPerfil> call, Response<ResponsetPerfil> response) {
                if (response.isSuccessful()) {
                    perfilUsuario.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponsetPerfil> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });

        return perfilUsuario;

    }



}

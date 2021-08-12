package com.abi.homeactivity.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.abi.homeactivity.retrofit.response.ResponsetPerfil;

public class PerfilViewModel extends AndroidViewModel {
    public PerfilRepository perfilRepository;
    public LiveData<ResponsetPerfil> perfilUsuario;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        perfilRepository = new PerfilRepository();
        perfilUsuario = perfilRepository.getPerfil();
    }
}

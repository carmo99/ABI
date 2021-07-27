package com.abi.homeactivity.retrofit;

import com.abi.homeactivity.common.Constantes;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthABIClient
{
    private static AuthABIClient instance = null;
    private AuthABIService authABIService;
    private Retrofit retrofit;

    public AuthABIClient() {
    //Incluimos el token en la cabezera
        OkHttpClient.Builder okhttpclientBuilder = new OkHttpClient.Builder();
        okhttpclientBuilder.addInterceptor(new AuthInterceptor());
        OkHttpClient cliente = okhttpclientBuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_ABI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cliente)
                .build();

        authABIService = retrofit.create(AuthABIService.class);
    }

    public static AuthABIClient getInstance()
    {
        if(instance == null)
        {
            instance = new AuthABIClient();
        }
        return instance;
    }

    public AuthABIService getAuthABIService()
    {
        return authABIService;
    }
}

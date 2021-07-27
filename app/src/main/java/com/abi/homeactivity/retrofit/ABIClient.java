package com.abi.homeactivity.retrofit;

import com.abi.homeactivity.common.Constantes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ABIClient
{
    private static ABIClient instance = null;
    private ABIService abiService;
    private Retrofit retrofit;

    public ABIClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_ABI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        abiService = retrofit.create(ABIService.class);
    }

    public static ABIClient getInstance()
    {
        if(instance == null)
        {
            instance = new ABIClient();
        }
        return instance;
    }

    public ABIService getABIService()
    {
        return abiService;
    }
}

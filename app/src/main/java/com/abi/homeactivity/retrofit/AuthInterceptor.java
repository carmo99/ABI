package com.abi.homeactivity.retrofit;

import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.SharedPreferencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_TOKEN);
        Request request = chain.request().newBuilder().addHeader("x-token", token).build();
        return chain.proceed(request);
    }
}

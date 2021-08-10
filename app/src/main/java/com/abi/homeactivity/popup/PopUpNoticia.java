package com.abi.homeactivity.popup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.MyApp;
import com.abi.homeactivity.retrofit.ABIClient;
import com.abi.homeactivity.retrofit.ABIService;
import com.abi.homeactivity.retrofit.response.ResponseUnaNoticia;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopUpNoticia extends AppCompatActivity implements View.OnClickListener {

    ABIClient abiClient;
    ABIService abiService;
    String IdNoticia;
    TextView tvTitulo, tvContenido, tvTache;
    ImageView ivImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_noticia);
        Intent iC = new Intent(getApplicationContext(), PopUpCargando.class);
        startActivity(iC);
        referenciar();
        eventos();
        retrofitInit();
        Bundle parametros = this.getIntent().getExtras();
        IdNoticia = parametros.getString("IdNoticia");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9), (int)(height*.9));

        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        doPeticion();
    }

    private void retrofitInit() {
        abiClient = ABIClient.getInstance();
        abiService = abiClient.getABIService();
    }

    private void referenciar() {
        tvTitulo = findViewById(R.id.textView_Titulo_Noticia);
        tvContenido = findViewById(R.id.textView_Contenido_Noticia);
        tvTache = findViewById(R.id.textView_Tache_Noticia);
        ivImagen = findViewById(R.id.imageView_Foto_Noticia);
        tvContenido.setMovementMethod(new ScrollingMovementMethod());
    }

    private void eventos()
    {
        tvTache.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        finish();
    }

    private void doPeticion()
    {
        Call<ResponseUnaNoticia> call = abiService.responseNoticia(IdNoticia);
        call.enqueue(new Callback<ResponseUnaNoticia>() {
            @Override
            public void onResponse(Call<ResponseUnaNoticia> call, Response<ResponseUnaNoticia> response)
            {
                PopUpCargando.fa.finish();
                tvTitulo.setText(response.body().getTitulo());
                tvContenido.setText(response.body().getContenido());
                Glide.with(MyApp.getContext()).load(response.body().getFoto())
                        .centerCrop()
                        .into(ivImagen);
            }
            @Override
            public void onFailure(Call<ResponseUnaNoticia> call, Throwable t)
            {
                PopUpCargando.fa.finish();
                Bundle parametros = new Bundle();
                parametros.putString("Mensaje", "Error en la conexión, intentalo nuevamente");
                Intent i = new Intent(getApplicationContext(), PopUpError.class);
                i.putExtras(parametros);
                startActivity(i);
                finish();
            }
        });
    }
}
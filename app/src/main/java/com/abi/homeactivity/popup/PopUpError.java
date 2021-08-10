package com.abi.homeactivity.popup;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.abi.homeactivity.R;

public class PopUpError extends AppCompatActivity implements View.OnClickListener {
    TextView txtMensaje;
    TextView txtCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_error);

        Bundle parametros = this.getIntent().getExtras();
        String mensaje = parametros.getString("Mensaje");

        txtMensaje = findViewById(R.id.textView_texto_error);
        txtCerrar = findViewById(R.id.textView_cerrar);

        txtCerrar.setOnClickListener(this);

        txtMensaje.setText(mensaje);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.4));

        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClick(View view)
    {
        finish();

    }
}
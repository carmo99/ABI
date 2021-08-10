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

public class PopUpCorrecto extends AppCompatActivity implements View.OnClickListener {

    TextView txtMensaje_c;
    TextView txtCerrar_c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_correcto);

        Bundle parametros = this.getIntent().getExtras();
        String mensaje = parametros.getString("Mensaje");

        txtMensaje_c = findViewById(R.id.textView_texto_correcto);
        txtCerrar_c = findViewById(R.id.textView_cerrar_c);

        txtCerrar_c.setOnClickListener(this);

        txtMensaje_c.setText(mensaje);

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
    public void onClick(View view) {

    }
}
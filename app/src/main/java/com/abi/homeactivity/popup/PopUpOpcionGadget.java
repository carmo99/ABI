package com.abi.homeactivity.popup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.abi.homeactivity.R;
import com.abi.homeactivity.ui.MainActivity;
import com.abi.homeactivity.ui.RegistraGadgetActivity;
import com.abi.homeactivity.ui.VincularABIActivity;

public class PopUpOpcionGadget extends AppCompatActivity implements View.OnClickListener{

    Button btnRegistrar, btnVicular;
    TextView txtCerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_opcion_gadget);

        referenciar();
        eventos();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9), (int)(height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void referenciar() {
        btnRegistrar = findViewById(R.id.button_registrar_gadget);
        btnVicular = findViewById(R.id.button_vincular_gadget);
        txtCerrar = findViewById(R.id.textView_cerrar_pop_opciones_gadget);
    }

    private void eventos() {
        btnRegistrar.setOnClickListener(this);
        btnVicular.setOnClickListener(this);
        txtCerrar.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.textView_cerrar_pop_opciones_gadget)
        {
            finish();
        }
        else if ( view.getId() == R.id.button_registrar_gadget)
        {
            MainActivity.fa.finish();
            Intent ic = new Intent( PopUpOpcionGadget.this, RegistraGadgetActivity.class);
            startActivity( ic );
            finish();
        }
        else if ( view.getId() == R.id.button_vincular_gadget )
        {
            MainActivity.fa.finish();
            Intent ic = new Intent( PopUpOpcionGadget.this, VincularABIActivity.class);
            startActivity( ic );
            finish();
        }
    }
}
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
import android.widget.Button;
import android.widget.TextView;

import com.abi.homeactivity.R;
import com.abi.homeactivity.common.Constantes;
import com.abi.homeactivity.common.SharedPreferencesManager;
import com.abi.homeactivity.ui.LogInActivity;
import com.abi.homeactivity.ui.MainActivity;

public class PopUpLogOut extends AppCompatActivity implements View.OnClickListener {
    Button btncerrar;
    TextView txtCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_log_out);

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
        txtCerrar = findViewById(R.id.textView_cerrar_pop);
        btncerrar = findViewById(R.id.button_cerrar_sesion);
    }

    private void eventos()
    {
        txtCerrar.setOnClickListener(this);
        btncerrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.textView_cerrar_pop)
        {
            finish();
        }
        else if (view.getId() == R.id.button_cerrar_sesion)
        {
            SharedPreferencesManager
                    .setSomeStringValue(Constantes.PREF_TOKEN, "");
            MainActivity.fa.finish();
            finish();
            Intent intent = new Intent(PopUpLogOut.this, LogInActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
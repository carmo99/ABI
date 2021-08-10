package com.abi.homeactivity.informacion;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.abi.homeactivity.R;
import com.abi.homeactivity.ui.MainActivity;

public class InformacionLegalActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton atras_info_dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_legal);
        atras_info_dia = findViewById(R.id.imageButton_atras_info_legal);
        atras_info_dia.setOnClickListener(this);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */)
        {

            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(InformacionLegalActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(InformacionLegalActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
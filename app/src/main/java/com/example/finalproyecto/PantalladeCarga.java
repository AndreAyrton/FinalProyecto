package com.example.finalproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class PantalladeCarga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallade_carga);

        final int Duracion = 25000;  //Tiempo demora de la pantalla

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent intent = new Intent(PantalladeCarga.this, MainActivity.class;
                    startActivity(intent);
            }
        },Duracion);
    }
}
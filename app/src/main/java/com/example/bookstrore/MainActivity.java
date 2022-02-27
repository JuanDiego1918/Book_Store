package com.example.bookstrore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        handler.postDelayed(getRunnableStartApp(), (2 * 1000));
    }

    private Runnable getRunnableStartApp() {
        Runnable runnable = new Runnable() {
            public void run() {
                iniciarActividadInicial();
            }
        };
        return runnable;
    }

    private void iniciarActividadInicial() {
        Intent intent = new Intent(MainActivity.this, BibliotecaActivity.class);
        startActivity(intent);
        finish();
    }
}
package com.example.bookstrore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conection.Api;
import com.example.conection.Networking;
import com.example.dataObject.Libro;
import com.example.utils.ProgressView;
import com.example.utils.Util;

public class DetalleActivity extends AppCompatActivity {
    private static final String TAG = DetalleActivity.class.getName();

    private TextView txtNombre,txtDescripcion;
    private ImageView imgLibro;
    private RatingBar estrellas;
    private Button btnPdf;
    private Libro libro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        ProgressView.Show(this);
        iniciar();
    }

    private void iniciar() {
        Bundle bundle = getIntent().getExtras();
        String codigoLibro = bundle.getString("idLibro");

        txtNombre = findViewById(R.id.txtNombre);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        estrellas = findViewById(R.id.estrellas);
        imgLibro = findViewById(R.id.imgLibro);
        btnPdf = findViewById(R.id.btnPdf);

        buscarDetalle(codigoLibro);

    }

    private void buscarDetalle(String codigoLibro) {
        Networking.get(Api.DETALLE_LIBRO+codigoLibro, new Networking.ICallback() {
            @Override
            public void onFail(String error) {
                ProgressView.Dismiss();
                Toast.makeText(DetalleActivity.this, R.string.error_api, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String response) {
                ProgressView.Dismiss();
                libro = Util.fromJsonDetalle(response);
                cargarDetalle();
            }
        });
    }

    private void cargarDetalle() {

        txtNombre.setText(libro.getTitle());
        txtDescripcion.setText(libro.getSubtitle());
        estrellas.setRating(Float.parseFloat(libro.getRating()));
        Util.cargarImg(imgLibro,libro.getImage(),this);

        btnPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(libro.getURL()));
                    startActivity(intent);
                } catch (Exception e) {
                    Log.d(TAG, "Error en el proceso");
                }

            }
        });
    }
}
package com.example.bookstrore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.ListViewAdapterLibros;
import com.example.conection.Api;
import com.example.conection.Networking;
import com.example.dataObject.Biblioteca;
import com.example.utils.ProgressView;
import com.example.utils.Util;

public class BibliotecaActivity extends AppCompatActivity {

    private EditText editBusqueda;
    private RecyclerView recyclerLibro;
    private ListViewAdapterLibros adapterLibros;
    private String busquedaAnterior;
    private boolean nuevos = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        iniciar();
    }

    /**
     * Instancioamos todos los controles
     */
    @SuppressLint("WrongConstant")
    public void iniciar(){
        editBusqueda = findViewById(R.id.editBusqueda);
        recyclerLibro = findViewById(R.id.recyclerLibro);
        recyclerLibro.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));

        editBusqueda.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    busqueda();
                    handled = true;
                }

                return handled;
            }
        });

        editBusqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            synchronized (this) {
                                wait(1000);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String busqueda = editBusqueda.getText().toString().trim();
                                        if (busqueda.length() >= 3) {
                                            busqueda();
                                            nuevos = false;
                                        }else if(busqueda.length() == 0 && !nuevos){
                                            nuevos = true;
                                            obtenerLibroNuevos();
                                        }
                                    }
                                });
                            }
                        } catch (InterruptedException e) {
                            String line = e.getMessage();
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        obtenerLibroNuevos();
    }


    /**
     * Por defecto cargamos Libros nuevos.
     */
    private void obtenerLibroNuevos() {
        ProgressView.Show(this);

        Networking.get(Api.LISTA_LIBROS, new Networking.ICallback() {
            @Override
            public void onFail(String error) {
                ProgressView.Dismiss();
                Toast.makeText(BibliotecaActivity.this, R.string.error_api, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(String response) {
                listarLibros(response);
            }
        });
    }

    /**
     * Con la informacion obtenida en la url cargar los datos en el recyclerView
     * @param response
     */
    private void listarLibros(String response) {
        Biblioteca biblioteca = Util.fromJson(response);
        if(biblioteca!=null){
            adapterLibros = new ListViewAdapterLibros(this, biblioteca.getBooks());
            recyclerLibro.setAdapter(adapterLibros);
        }else{
            recyclerLibro.setAdapter(null);
        }
        ProgressView.Dismiss();
    }

    /**
     * Realizar la busqueda de productos
     */
    private void busqueda() {
        String buscar = editBusqueda.getText().toString();

        if(!buscar.isEmpty() && !buscar.equalsIgnoreCase(busquedaAnterior)){
            Toast.makeText(this, "buscando..", Toast.LENGTH_SHORT).show();
            busquedaAnterior = buscar;
            Networking.get(Api.BUSCAR_LIBROS+buscar, new Networking.ICallback() {
                @Override
                public void onFail(String error) {
                    ProgressView.Dismiss();
                    Toast.makeText(BibliotecaActivity.this, R.string.error_api, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(String response) {
                    Biblioteca biblioteca = Util.fromJson(response);
                    if(biblioteca.getBooks()!=null){
                        adapterLibros.actualizar(biblioteca.getBooks());
                    }
                }
            });
        }

    }
}
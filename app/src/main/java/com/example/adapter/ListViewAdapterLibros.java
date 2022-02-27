package com.example.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstrore.DetalleActivity;
import com.example.bookstrore.R;
import com.example.dataObject.Libro;
import com.example.utils.Util;

import java.util.List;

public class ListViewAdapterLibros extends RecyclerView.Adapter<ListViewAdapterLibros.MyHolderView> {

    private static final String TAG = ListViewAdapterLibros.class.getName();
    private AppCompatActivity context;
    private List<Libro> listaLibros;


    public ListViewAdapterLibros(AppCompatActivity context, List<Libro> listaLibros) {
        this.context = context;
        this.listaLibros = listaLibros;
    }

    public void actualizar(List<Libro> listaLibros){
        this.listaLibros = listaLibros;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int resource = R.layout.list_item_catalogo_list;
        View view = LayoutInflater.from(context).inflate(resource, parent, false);
        return new MyHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderView holder, int position) {
        Libro libro = listaLibros.get(position);

        holder.txtNombre.setText(libro.getTitle());
        holder.txtDescripcion.setText(libro.getSubtitle());

        context.runOnUiThread(new Runnable() {

            public void run() {
                Util.cargarImg(holder.imgLibro,libro.getImage(),context);
            }
        });

        holder.btnDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, DetalleActivity.class);
                intent.putExtra("idLibro", libro.getIsbn13());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaLibros.size();
    }

    public class MyHolderView extends RecyclerView.ViewHolder {
        TextView txtNombre;
        TextView txtDescripcion;
        Button btnDetalle;
        ImageView imgLibro;

        public MyHolderView(@NonNull View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            btnDetalle = itemView.findViewById(R.id.btnDetalle);
            imgLibro = itemView.findViewById(R.id.imgLibro);

        }
    }
}

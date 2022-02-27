package com.example.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.bookstrore.R;
import com.example.dataObject.Biblioteca;
import com.example.dataObject.Libro;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    private static final String TAG = Util.class.getName();

    public static String FechaActual(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static void cargarImg(final ImageView image, final String url, final Context context) {
        try {
            Glide.with(context)
                    .load(url)
                    .signature(new ObjectKey(url+FechaActual("yyyyMMdd")))
                    .error(R.drawable.libro_icon)
                    .placeholder(R.drawable.libro_icon)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(image);


        } catch (Exception e) {
            Log.i(TAG, "No existe imagen en ruta: " +url);
        }
    }

    public static Biblioteca fromJson(String response) {
        return new Gson().fromJson(response, Biblioteca.class);
    }

    public static Libro fromJsonDetalle(String response) {
        return new Gson().fromJson(response, Libro.class);
    }
}

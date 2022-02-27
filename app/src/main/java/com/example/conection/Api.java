package com.example.conection;

public class Api {

    private final static String URL_SYNC = "https://api.itbook.store/1.0/";

    public static String LISTA_LIBROS = URL_SYNC.concat("new");
    public static String BUSCAR_LIBROS = URL_SYNC.concat("search/");
    public static String DETALLE_LIBRO = URL_SYNC.concat("books/");
}

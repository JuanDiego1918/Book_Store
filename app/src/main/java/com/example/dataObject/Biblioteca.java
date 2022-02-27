package com.example.dataObject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Biblioteca {

    private String error;
    private String total;
    private List<Libro> books;

    @SerializedName("error")
    public String getError() { return error; }
    @SerializedName("error")
    public void setError(String value) { this.error = value; }

    @SerializedName("total")
    public String getTotal() { return total; }
    @SerializedName("total")
    public void setTotal(String value) { this.total = value; }

    @SerializedName("books")
    public List<Libro> getBooks() { return books; }
    @SerializedName("books")
    public void setBooks(List<Libro> value) { this.books = value; }
}

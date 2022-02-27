package com.example.dataObject;

import com.google.gson.annotations.SerializedName;

public class Libro {
    private String title;
    private String subtitle;
    private String isbn13;
    private String price;
    private String image;
    private String url;
    private String rating;

    @SerializedName("title")
    public String getTitle() { return title; }
    @SerializedName("title")
    public void setTitle(String value) { this.title = value; }

    @SerializedName("subtitle")
    public String getSubtitle() { return subtitle; }
    @SerializedName("subtitle")
    public void setSubtitle(String value) { this.subtitle = value; }

    @SerializedName("isbn13")
    public String getIsbn13() { return isbn13; }
    @SerializedName("isbn13")
    public void setIsbn13(String value) { this.isbn13 = value; }

    @SerializedName("price")
    public String getPrice() { return price; }
    @SerializedName("price")
    public void setPrice(String value) { this.price = value; }

    @SerializedName("image")
    public String getImage() { return image; }
    @SerializedName("image")
    public void setImage(String value) { this.image = value; }

    @SerializedName("url")
    public String getURL() { return url; }
    @SerializedName("url")
    public void setURL(String value) { this.url = value; }

    @SerializedName("rating")
    public String getRating() { return rating; }
    @SerializedName("rating")
    public void setRating(String value) { this.rating = value; }


}

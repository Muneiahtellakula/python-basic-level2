package com.example.lenovo.beauty;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName ="favourite_products" )
public class FavProducts {
    @PrimaryKey
            @NonNull
    String img;
    String udate;
    String brand;
    String name;
    String price;
    String rating;
    String description;

    public FavProducts(String img, String udate, String brand, String name, String price, String rating, String description) {
        this.img = img;
        this.udate = udate;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUdate() {
        return udate;
    }

    public void setUdate(String udate) {
        this.udate = udate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

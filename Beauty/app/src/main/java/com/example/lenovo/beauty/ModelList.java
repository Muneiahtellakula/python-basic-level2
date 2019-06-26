package com.example.lenovo.beauty;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelList implements Parcelable{
    String img;
    String udate;
    String brand;
    String name;
    String price;
    String rating;
    String description;

    public ModelList(String img, String udate, String brand, String name, String price, String rating, String description) {
        this.img = img;
        this.udate = udate;
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.description = description;
    }

    protected ModelList(Parcel in) {
        img = in.readString();
        udate = in.readString();
        brand = in.readString();
        name = in.readString();
        price = in.readString();
        rating = in.readString();
        description = in.readString();
    }

    public static final Creator<ModelList> CREATOR = new Creator<ModelList>() {
        @Override
        public ModelList createFromParcel(Parcel in) {
            return new ModelList(in);
        }

        @Override
        public ModelList[] newArray(int size) {
            return new ModelList[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(img);
        parcel.writeString(udate);
        parcel.writeString(brand);
        parcel.writeString(name);
        parcel.writeString(price);
        parcel.writeString(rating);
        parcel.writeString(description);
    }
}

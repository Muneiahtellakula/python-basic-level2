package com.example.lenovo.beauty;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MyDao {
    @Query("select * from favourite_products")
    LiveData<List<FavProducts>> getAllData();


    @Insert
    void insert (FavProducts favouriteProducts);
    @Delete
    void delete(FavProducts favouriteProducts);
}


package com.example.lenovo.beauty;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ViewModelClass extends AndroidViewModel {
    LiveData<List<FavProducts>> listLiveData;
    ProductsRepository productsRepository;
    public ViewModelClass(@NonNull Application application) {
        super(application);
        productsRepository=new ProductsRepository(application);
        listLiveData=productsRepository.getData();
    }
    public LiveData<List<FavProducts>> getListLiveData()
    {
        return listLiveData;
    }
    public  void insertList(FavProducts favProducts){
        productsRepository.insert(favProducts);
    }
    public void deletelist(FavProducts favProducts){
        productsRepository.delete(favProducts);
    }
}

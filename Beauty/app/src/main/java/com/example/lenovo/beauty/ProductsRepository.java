package com.example.lenovo.beauty;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ProductsRepository {
    MyDao myDao;
    LiveData<List<FavProducts>> productlist;
    public ProductsRepository(Application application) {
        MyDatabase database =  MyDatabase.getDatabase(application);
        myDao = database.myDao();
        productlist = myDao.getAllData();
    }
    LiveData<List<FavProducts>> getData(){
        return  productlist;
    }
    public void insert(FavProducts favouriteProducts) {new taskInsert(myDao).execute(favouriteProducts);
    }

    public void delete(FavProducts favouriteProducts)
    {
        new taskDelete(myDao).execute(favouriteProducts);
    }
    class taskInsert extends AsyncTask<FavProducts,Void,Void>{
        private MyDao dao;
        public taskInsert(MyDao productDAO){
            dao=productDAO;
        }
        @Override
        protected Void doInBackground(FavProducts... favProducts) {
            dao.insert(favProducts[0]);
            return null;
        }
    }
    private class taskDelete extends AsyncTask<FavProducts,Void,Void>{
        MyDao mdao;
        public taskDelete(MyDao productDAO){
            mdao=productDAO;
        }
        @Override
        protected Void doInBackground(FavProducts... favProducts) {
            mdao.delete(favProducts[0]);
            return null;
        }
    }
}

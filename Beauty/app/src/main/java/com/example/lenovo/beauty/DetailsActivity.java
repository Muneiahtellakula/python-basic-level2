package com.example.lenovo.beauty;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    ImageView proimg;
    LikeButton likeButton;
    String img, update, brand, name, prprice, prorating, desc;
    TextView proupdate, probrand, proname, proprice, rating, description;
    ViewModelClass viewModelClass;
    MyDatabase myDatabase;
    List<FavProducts> favouriteProducts;
    CollapsingToolbarLayout toolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        final Toolbar toolbar = findViewById(R.id.detailtool);

       /* toolbarLayout = findViewById(R.id.tool);
        setSupportActionBar(toolbar);*/
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        getSupportActionBar().setTitle("name");
        proimg = findViewById(R.id.primg);
        proupdate = findViewById(R.id.tv1);
        probrand = findViewById(R.id.tv2);
        proname = findViewById(R.id.tv3);
        proprice = findViewById(R.id.tv4);
        rating = findViewById(R.id.tv6);
        description = findViewById(R.id.tv5);
        likeButton = findViewById(R.id.heart_button);
       viewModelClass= ViewModelProviders.of(this).get(ViewModelClass.class);
        myDatabase= Room.databaseBuilder(this,MyDatabase.class,"Movie.db").allowMainThreadQueries().build();
        Bundle bundle = getIntent().getExtras();

        img = bundle.getString("image_link");
        update = bundle.getString("updated_at");
        brand = bundle.getString("brand");
        name = bundle.getString("name");
        prprice = bundle.getString("price");
        prorating = bundle.getString("rating");
        desc = bundle.getString("description");
        Picasso.with(this).load(img).into(proimg);
        proupdate.setText(update);

//        toolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
        probrand.setText(brand);
        proname.setText(name);
        proprice.setText(prprice);
        rating.setText(prorating);
        description.setText(desc);
        favouriteProducts();
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                FavProducts favouriteProducts = new FavProducts(img, update, brand, name,
                        prprice, prorating, desc);

                viewModelClass.insertList(favouriteProducts);


            }

            @Override
            public void unLiked(LikeButton likeButton) {

                FavProducts favouriteProducts = new FavProducts(img, update, brand, name,
                        prprice, prorating, desc);
                viewModelClass.deletelist(favouriteProducts);


            }
        });
        UpdateWidget();

    }
    public void UpdateWidget(){
        SharedPreferences sharedPreferences=getSharedPreferences("nandu",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("pimg",img);
        editor.putString("pname",name);
        editor.putString("pbrand",brand);
        editor.putString("price",prprice);
        editor.apply();
        Intent intent=new Intent(this,ProductWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids= AppWidgetManager.getInstance(this.getApplicationContext())
                .getAppWidgetIds(new ComponentName(this.getApplication(),ProductWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
    }
    private void favouriteProducts() {
        viewModelClass.getListLiveData().observe(this,
               new Observer<List<FavProducts>>() {
                    @Override
                    public void onChanged(@Nullable List<FavProducts> favouriteProducts) {
                        for (int i = 0; i < favouriteProducts.size(); i++) {
                            String ids = favouriteProducts.get(i).getImg();
                            if (ids.equalsIgnoreCase(img)) {
                                likeButton.setLiked(true);
                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    }


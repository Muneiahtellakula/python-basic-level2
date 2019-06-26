package com.example.lenovo.beauty;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CollapsingToolbarLayout layout;
    ConnectivityManager manager;
    String values;
  //  Toolbar tool;
    private AdView mAdView;
    ViewModelClass viewModelClass;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.tools);

        progressBar=findViewById(R.id.progbar);
        recyclerView = findViewById(R.id.rv);
        FirebaseApp.initializeApp(this);
        MobileAds.initialize(this, getString(R.string.mobileadd));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        viewModelClass = ViewModelProviders.of(this).get(ViewModelClass.class);

        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        sharedPreferences = getSharedPreferences("beauty", MODE_PRIVATE);


        check();
    }
    private void check() {
        if (manager.getNetworkInfo(manager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                manager.getNetworkInfo(manager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            values = sharedPreferences.getString("sp_key", "PRODUCTS");
            if (values.equalsIgnoreCase("PRODUCTS")) {

                getSupportLoaderManager().initLoader(2, null, this);
            } else if (values.equalsIgnoreCase("FAV")) {

                hasFavouriteProducts();
            }
        } else {
            noInternet();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorates:

                getSupportLoaderManager().destroyLoader(2);
                hasFavouriteProducts();
                editor = sharedPreferences.edit();
                editor.putString("sp_key", "FAV");
                editor.apply();
                break;
            case R.id.product:
                if (manager.getNetworkInfo(manager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        manager.getNetworkInfo(manager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    editor = sharedPreferences.edit();
                    editor.putString("sp_key", "PRODUCTS");
                    editor.apply();
                    getSupportLoaderManager().initLoader(2, null, this);
                } else {
                    noInternet();
                }

                break;
        }
        return super.onOptionsItemSelected(item);

    }
    private void noInternet() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.alert);
        builder.setTitle("Alert..!");
        builder.setCancelable(false);
        builder.setMessage("please check network connection....");
        builder.setNegativeButton("Got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Go to settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        }).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(2);
    }

    private void runAnimation(RecyclerView recyclerView, int type) {
        Context context = recyclerView.getContext();
        LayoutAnimationController controller = null;

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Nullable
            @Override
            public String loadInBackground() {
                String url = "http://makeup-api.herokuapp.com/api/v1/products.json?brand=revlon";
                try {
                    URL u = new URL(url);
                    HttpURLConnection httpsURLConnection = (HttpURLConnection) u.openConnection();
                    InputStream inputStream = httpsURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String ln = null;
                    while ((ln = bufferedReader.readLine()) != null) {
                        stringBuilder.append(ln);

                    }
                    return stringBuilder.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        List<ModelList> list = new ArrayList<>();
        try {

            JSONArray array = new JSONArray(data);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String img = jsonObject.getString("image_link");

                String update = jsonObject.getString("updated_at");
                String brand = jsonObject.getString("brand");
                String name = jsonObject.getString("name");
                String price = jsonObject.getString("price");
                String rating = jsonObject.getString("rating");
                String description = jsonObject.getString("description");
                ModelList modelList = new ModelList(img, update, brand, name, price, rating, description);
                list.add(modelList);

            }

            int ori = this.getResources().getConfiguration().orientation;
            recyclerView.setAdapter(new ProductListAdapter(this, list));
            progressBar.setVisibility(View.GONE);


            if (ori == Configuration.ORIENTATION_LANDSCAPE) {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
                runAnimation(recyclerView, 0);
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                runAnimation(recyclerView, 0);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
    private void hasFavouriteProducts() {
        progressBar.setVisibility(View.GONE);

        viewModelClass.getListLiveData().observe(this, new Observer<List<FavProducts>>() {
            @Override
            public void onChanged(@Nullable List<FavProducts> favproducts) {


                FavAdapter favProdAdapter = new
                        FavAdapter(MainActivity.this, favproducts);
                recyclerView.setAdapter(favProdAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

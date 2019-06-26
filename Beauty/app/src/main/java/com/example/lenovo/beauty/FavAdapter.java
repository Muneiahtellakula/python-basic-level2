package com.example.lenovo.beauty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder>{
    MainActivity activity;
    List<FavProducts> favouriteProducts;

    public FavAdapter(MainActivity activity, List<FavProducts> favouriteProducts) {
        this.activity = activity;
        this.favouriteProducts = favouriteProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(activity).inflate(R.layout.productlist,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(activity).load(favouriteProducts.get(position).getImg())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return favouriteProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.proimg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            Bundle bundle=new Bundle();
            Intent i=new Intent(activity,DetailsActivity.class);
            // bundle.putString("id",favouriteProducts.get(position).getId());
            bundle.putString("image_link",favouriteProducts.get(position).getImg());
            bundle.putString("updated_at",favouriteProducts.get(position).getUdate());
            bundle.putString("brand",favouriteProducts.get(position).getBrand());
            bundle.putString("name",favouriteProducts.get(position).getName());
            bundle.putString("price",favouriteProducts.get(position).getPrice());
            bundle.putString("rating",favouriteProducts.get(position).getRating());
            bundle.putString("description",favouriteProducts.get(position).getDescription());
            i.putExtras(bundle);
            activity.startActivity(i);

        }
    }
}

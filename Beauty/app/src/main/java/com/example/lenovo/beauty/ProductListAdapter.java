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

class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>{
    MainActivity mainActivity;
    List<ModelList> lists;
    public ProductListAdapter(MainActivity mainActivity, List<ModelList> list) {
        this.mainActivity=mainActivity;
        this.lists=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mainActivity).inflate(R.layout.productlist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(mainActivity).load(lists.get(position).getImg()).placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.proimg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int exactPosition=getAdapterPosition();

            Bundle bundle=new Bundle();
            Intent intent=new Intent(mainActivity,DetailsActivity.class);
            bundle.putString("image_link",lists.get(exactPosition).getImg());
            bundle.putString("updated_at",lists.get(exactPosition).getUdate());
            bundle.putString("brand",lists.get(exactPosition).getBrand());
            bundle.putString("name",lists.get(exactPosition).getName());
            bundle.putString("price",lists.get(exactPosition).getPrice());
            bundle.putString("rating",lists.get(exactPosition).getRating());
            bundle.putString("description",lists.get(exactPosition).getDescription());
            intent.putExtras(bundle);
            mainActivity.startActivity(intent);

        }
    }
}

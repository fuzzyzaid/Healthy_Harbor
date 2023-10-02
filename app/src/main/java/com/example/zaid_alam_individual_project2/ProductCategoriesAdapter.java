package com.example.zaid_alam_individual_project2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductCategoriesAdapter extends RecyclerView.Adapter<ProductCategoriesAdapter.MyViewHolder>{

    private List<ProductCategories> pCategories;

    public ProductCategoriesAdapter(List<ProductCategories> categories) {
        pCategories = categories;

    }

    @NonNull
    @Override
    public ProductCategoriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater, parent);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ProductCategories p = pCategories.get(position);


        Context actContext = holder.itemView.getContext();
        int resid = actContext.getResources().getIdentifier(p.getCategoryImage(), "drawable", actContext.getPackageName());

        holder.pCategoryName.setText(p.getCategoryName());
        holder.pCategoryImage.setImageResource(resid);

        // To open the category page
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context =v.getContext();

                Intent myIntent= new Intent(context, DisplayCategoryProducts.class);
                myIntent.putExtra("Category_Id",p.getCategoryId());
                context.startActivity(myIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pCategories.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pCategoryName;
        ImageView pCategoryImage;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.categories_list_layout, parent, false));
            pCategoryName = itemView.findViewById(R.id.cart_ItemName);
            pCategoryImage = itemView.findViewById(R.id.cart_ItemImage);
        }

    }


}


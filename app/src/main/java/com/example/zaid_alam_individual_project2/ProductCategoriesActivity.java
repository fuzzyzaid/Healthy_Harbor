package com.example.zaid_alam_individual_project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ProductCategoriesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ProductCategories> productCategories=new ArrayList<>();
    ProductCategoriesSingleton pSingleton;

    Toolbar myToolbar;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_categories);
        recyclerView=findViewById(R.id.rCategoriesView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        pSingleton = ProductCategoriesSingleton.getmProductCategoriesSingleton();

        pSingleton.fetchDataFromFirebase(new ProductCategoriesSingleton.DataFetchCallback() {
            @Override
            public void onDataFetched(ArrayList<ProductCategories> mProductCategoriesList) {
                // Initialize RecyclerView adapter with the fetched data
                productCategories =mProductCategoriesList;
                mAdapter = new ProductCategoriesAdapter(productCategories);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });

        if (recyclerView != null && mAdapter != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setAdapter(mAdapter);
        }


        myToolbar=findViewById(R.id.myToolbar);

        tabLayout= findViewById(R.id.tabLayout);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabValue=tab.getPosition();
                // Home Tab
                if(tabValue == 0)
                {
                    Log.i("info","From Home 2 ");
                  Intent myIntent=new Intent(ProductCategoriesActivity.this,ProductActivity.class);
                  startActivity(myIntent);
                  finish();
                }

                // Category Tab
                if(tabValue == 1)
                {
                   // Intent myIntent=new Intent(ProductCategoriesActivity.this,ProductCategoriesActivity.class);
                    //startActivity(myIntent);
                    Log.i("info","From Category 2");
                }

                //  Cart Tab
                if(tabValue == 2)
                {
                    ArrayList<AddToCartItems> items=CommonSingleton.getInstance().getCartItems();


                    if(items == null ){
                        Intent myIntent=new Intent(ProductCategoriesActivity.this,DisplayNoItemsActivity.class);
                        startActivity(myIntent);
                        finish();
                        Log.i("info","No items in cart");

                    }
                    else{
                        Intent myIntent=new Intent(ProductCategoriesActivity.this,DisplayCartItems.class);
                        startActivity(myIntent);
                        finish();
                        Log.i("info","From Cart 1");
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.i("info","Un-selected 2");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.i("info","Re-selected 2");
                Log.i("info","... "+tab.getPosition());

                int tabValue=tab.getPosition();
                if(tabValue == 0)
                {
                    Log.i("info","From Home 2 ");
                    Intent myIntent=new Intent(ProductCategoriesActivity.this,ProductActivity.class);
                    startActivity(myIntent);
                    finish();
                }

                if(tabValue == 2)
                {
                    ArrayList<AddToCartItems> items=CommonSingleton.getInstance().getCartItems();


                    if(items == null || items .isEmpty()){
                        Intent myIntent=new Intent(ProductCategoriesActivity.this,DisplayNoItemsActivity.class);
                        startActivity(myIntent);
                        finish();
                        Log.i("info","No items in cart");

                    }
                    else{
                        Intent myIntent=new Intent(ProductCategoriesActivity.this,DisplayCartItems.class);
                        startActivity(myIntent);
                        finish();
                        Log.i("info","From Cart 1");
                    }
                }


            }
        });


    }


}
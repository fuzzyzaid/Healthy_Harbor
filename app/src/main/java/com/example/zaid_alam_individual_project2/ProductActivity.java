package com.example.zaid_alam_individual_project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Products> productList=new ArrayList<>();
    ProductSingleton pSingleton;

    FirebaseDatabase database;
    DatabaseReference myRef;
    Toolbar myToolbar;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);



        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("Products");
        recyclerView=findViewById(R.id.rView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

       pSingleton = ProductSingleton.getProductSingleton();
       pSingleton.fetchDataFromFirebase(new ProductSingleton.DataFetchCallback() {
            @Override
            public void onDataFetched(ArrayList<Products> mProductList) {
                // Initialize RecyclerView adapter with the fetched data
                productList =mProductList;
                mAdapter = new ProductListAdapter(productList);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
       });

        if (recyclerView != null && mAdapter != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setAdapter(mAdapter);
        }

        myToolbar= findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        tabLayout= findViewById(R.id.tabLayout);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabValue=tab.getPosition();
                // Home Tab
                if(tabValue == 0)
                    {


                    }

                // Category Tab
                if(tabValue == 1)
                {
                    Intent myIntent=new Intent(ProductActivity.this,ProductCategoriesActivity.class);
                    startActivity(myIntent);
                    finish();

                }

              //  Cart Tab
                if(tabValue == 2)
                {
                    ArrayList<AddToCartItems> items=CommonSingleton.getInstance().getCartItems();


                    if(items == null || items .isEmpty()){
                        Intent myIntent=new Intent(ProductActivity.this,DisplayNoItemsActivity.class);
                        startActivity(myIntent);
                        finish();


                    }
                    else{
                        Intent myIntent=new Intent(ProductActivity.this,DisplayCartItems.class);
                        startActivity(myIntent);
                        finish();

                    }

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                int tabValue=tab.getPosition();
                if(tabValue == 1)
                {
                    Intent myIntent=new Intent(ProductActivity.this,ProductCategoriesActivity.class);
                    startActivity(myIntent);
                    finish();

                }

                if(tabValue == 2)
                {
                    ArrayList<AddToCartItems> items=CommonSingleton.getInstance().getCartItems();

                    if(items == null ){
                        Intent myIntent=new Intent(ProductActivity.this,DisplayNoItemsActivity.class);
                        startActivity(myIntent);
                        finish();
                    }
                    else{
                        Intent myIntent=new Intent(ProductActivity.this,DisplayCartItems.class);
                        startActivity(myIntent);
                        finish();
                    }
                }

            }
        });



    }






}
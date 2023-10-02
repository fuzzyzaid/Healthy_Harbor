package com.example.zaid_alam_individual_project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayCategoryProducts extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Products> productList=new ArrayList<>();
    ProductSingleton pSingleton;
    private final ArrayList<Products> mProductList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference myRef;
    Toolbar myToolbar;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_category_products);

        Intent getDetails=getIntent();
        int categoryId=getDetails.getIntExtra("Category_Id",1);

        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("Products");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int fetchedCategoryId=snapshot.child("categoryId").getValue(int.class);

                    if(fetchedCategoryId ==categoryId ) {

                        String image = snapshot.child("image").getValue(String.class);
                        Double price = snapshot.child("price").getValue(Double.class);
                        String name = snapshot.child("name").getValue(String.class);
                        String description = snapshot.child("description").getValue(String.class);
                        String category = snapshot.child("category").getValue(String.class);
                        int productId=snapshot.child("productId").getValue(int.class);
                        int categoryId=fetchedCategoryId;
                        Products product = new Products(name,image,description,category,price,categoryId,productId);
                        mProductList.add(product);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any error that occurs during data fetching
            }
        });

        recyclerView=findViewById(R.id.rCategoryProducts);
        mAdapter = new ProductListAdapter(mProductList);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(mAdapter);


    }
}
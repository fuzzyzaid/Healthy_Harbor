package com.example.zaid_alam_individual_project2;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductSingleton {
    private final ArrayList<Products> mProductList = new ArrayList<>();

    private static ProductSingleton mProductSingleton;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private boolean dataFetched = false;



    public static ProductSingleton getProductSingleton(){

        if (mProductSingleton == null){
            mProductSingleton = new ProductSingleton();
        }
        return mProductSingleton;

    }


    private ProductSingleton(){}

    public ArrayList<Products> getProductList() {
        return mProductList;
    }
    public void fetchDataFromFirebase(DataFetchCallback callback) {
        if (!dataFetched) {

            database=FirebaseDatabase.getInstance();
            myRef=database.getReference("Products");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        String image = snapshot.child("image").getValue(String.class);
                        Double price = snapshot.child("price").getValue(Double.class);
                        String name = snapshot.child("name").getValue(String.class);
                        String description = snapshot.child("description").getValue(String.class);
                        String category = snapshot.child("category").getValue(String.class);
                        int categoryId=snapshot.child("categoryId").getValue(int.class);
                        int productId=snapshot.child("productId").getValue(int.class);
                        Products product = new Products(name,image,description,category,price,categoryId,productId);
                        mProductList.add(product);
                    }
                    callback.onDataFetched(mProductList);
                    dataFetched = true;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle any error that occurs during data fetching
                }
            });

        } else {
            // Data already fetched, call the callback with the existing data
            callback.onDataFetched(mProductList);
        }
    }
    public interface DataFetchCallback {
        void onDataFetched(ArrayList<Products> mProductList);
    }


}

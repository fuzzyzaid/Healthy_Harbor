package com.example.zaid_alam_individual_project2;

import android.util.Log;

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


public class ProductCategoriesSingleton {

    private final ArrayList<ProductCategories> mProductCategoriesList = new ArrayList<>();
    private static ProductCategoriesSingleton mProductCategoriesSingleton;

    FirebaseDatabase database;
    DatabaseReference myRef;
    private boolean dataFetched = false;

    public static ProductCategoriesSingleton getmProductCategoriesSingleton(){

        if (mProductCategoriesSingleton == null){
            mProductCategoriesSingleton = new ProductCategoriesSingleton();
        }
        return mProductCategoriesSingleton;

    }

    private ProductCategoriesSingleton(){}

    public ArrayList<ProductCategories> getProductCategoriesListList() {
        return mProductCategoriesList;
    }
    public void fetchDataFromFirebase(ProductCategoriesSingleton.DataFetchCallback callback) {
        if (!dataFetched) {


            database=FirebaseDatabase.getInstance();
            myRef=database.getReference("Product_Categories");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String image = snapshot.child("categoryImage").getValue(String.class);
                        String name = snapshot.child("categoryName").getValue(String.class);
                        int id = snapshot.child("categoryId").getValue(int.class);

                        ProductCategories p=new ProductCategories(name,image,id);
                        mProductCategoriesList.add(p);
                    }
                    callback.onDataFetched(mProductCategoriesList);
                    dataFetched = true;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle any error that occurs during data fetching
                }
            });

        } else {
            // Data already fetched, call the callback with the existing data
            callback.onDataFetched(mProductCategoriesList);
        }
    }

    public interface DataFetchCallback {
        void onDataFetched(ArrayList<ProductCategories> mProductCategoriesList);
    }

}

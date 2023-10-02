package com.example.zaid_alam_individual_project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DisplayCartItems extends AppCompatActivity implements CartItemsAdapter.CartItemListener, CartItemsAdapter.ActivityFinishListener {
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<AddToCartItems> cartItems=new ArrayList<>();
    Toolbar myToolbar;
    TabLayout tabLayout;
    TextView totalPrice;

    Double totalCost=0.0;

    Button checkoutfinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cart_items);
        cartItems= CommonSingleton.getInstance().getCartItems();

        for(AddToCartItems items:cartItems){
            totalCost=totalCost+items.getProductQuantity()* items.getPrice();
        }


        recyclerView=findViewById(R.id.rCartItems);
        checkoutfinal=findViewById(R.id.checkoutfinal);
        String buttonText = String.format("Proceed to checkout ($%.2f)", totalCost);
        checkoutfinal.setText(buttonText);

        CommonSingleton.getInstance().setTotalPrice(totalCost);

        mAdapter = new CartItemsAdapter(cartItems,this,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        myToolbar= findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        tabLayout= findViewById(R.id.tabLayout);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);

        checkoutfinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent=new Intent(DisplayCartItems.this,CheckOutActivity.class);
                startActivity(myIntent);


            }
        });


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabValue=tab.getPosition();
                // Home Tab
                if(tabValue == 0)
                {

                        Intent myIntent=new Intent(DisplayCartItems.this,ProductActivity.class);
                        startActivity(myIntent);
                        finish();

                }

                // Category Tab
                if(tabValue == 1)
                {
                    Intent myIntent=new Intent(DisplayCartItems.this,ProductCategoriesActivity.class);
                    startActivity(myIntent);
                    finish();

                }

                //  Cart Tab
                if(tabValue == 2)
                {

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.i("info","Un-selected 1 ");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


                int tabValue=tab.getPosition();
                if(tabValue == 0)
                {
                    Intent myIntent=new Intent(DisplayCartItems.this,ProductActivity.class);
                    startActivity(myIntent);
                    finish();

                }

                if(tabValue == 1)
                {
                    Intent myIntent=new Intent(DisplayCartItems.this,ProductCategoriesActivity.class);
                    startActivity(myIntent);
                    finish();

                }

            }
        });
    }

    @Override
    public void onQuantityChanged(AddToCartItems position, Double newTotalCOst) {
        String buttonText = String.format("Proceed to checkout ($%.2f)", newTotalCOst);
        checkoutfinal.setText(buttonText);


    }

    @Override
    public void finishActivity() {
        finish();
    }
}
package com.example.zaid_alam_individual_project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class DisplayNoItemsActivity extends AppCompatActivity {
    Toolbar myToolbar;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_no_items);

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

                    Intent myIntent=new Intent(DisplayNoItemsActivity.this,ProductActivity.class);
                    startActivity(myIntent);
                    finish();

                }

                // Category Tab
                if(tabValue == 1)
                {
                    Intent myIntent=new Intent(DisplayNoItemsActivity.this,ProductCategoriesActivity.class);
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
                    Intent myIntent=new Intent(DisplayNoItemsActivity.this,ProductActivity.class);
                    startActivity(myIntent);
                    finish();

                }

                if(tabValue == 1)
                {
                    Intent myIntent=new Intent(DisplayNoItemsActivity.this,ProductCategoriesActivity.class);
                    startActivity(myIntent);
                    finish();

                }

            }
        });
    }
}
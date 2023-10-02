package com.example.zaid_alam_individual_project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseUser user=mAuth.getCurrentUser();
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button logout_btn;
    Button start_btn;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            CommonSingleton.getInstance().setUserUID(currentUser.getUid());
            CommonSingleton.getInstance().setUserEmail(currentUser.getEmail());
            Log.i("info"," User Name " + CommonSingleton.getInstance().getUserUID());
        }
        else{
            Intent myIntent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(myIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout_btn=findViewById(R.id.logout_btn);

        start_btn=findViewById(R.id.start_btn);






        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CommonSingleton.getInstance().getCartItems().isEmpty()){
                    database=FirebaseDatabase.getInstance();
                    myRef=database.getReference("Cart_Items");
                    UserCartItems userItem=new UserCartItems(CommonSingleton.getInstance().getCartItems(),CommonSingleton.getInstance().getUserUID());
                    myRef.setValue(userItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                }
                FirebaseAuth.getInstance().signOut();
                Intent myIntent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(myIntent);
            }
        });


        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database=FirebaseDatabase.getInstance();
                myRef=database.getReference("Cart_Items");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            ArrayList<AddToCartItems> addedItems = new ArrayList<>();
                            DataSnapshot addedItemsSnapshot = dataSnapshot.child("addedItems");
                            for (DataSnapshot itemSnapshot : addedItemsSnapshot.getChildren()) {
                                AddToCartItems item = itemSnapshot.getValue(AddToCartItems.class);
                                addedItems.add(item);
                            }
                            CommonSingleton.getInstance().setCartItems(addedItems);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any error that occurs during data fetching
                    }
                });

                Intent myIntent=new Intent(MainActivity.this,ProductActivity.class);
                startActivity(myIntent);
            }
        });

    }





}
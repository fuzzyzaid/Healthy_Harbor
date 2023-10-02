package com.example.zaid_alam_individual_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DsiplayProductActivity extends AppCompatActivity {

    ImageView productImage;
    Button addToCart;
    TextView productName,productPrice,productDescription,quantityValue;
    ImageView decreaseQuantity,increaseQuantity;
    Boolean itemALreadyPresent=false;
    public ArrayList<AddToCartItems> addedItems=new ArrayList<AddToCartItems>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsiplay_product);

        productImage=findViewById(R.id.productImage);
        productName=findViewById(R.id.productName);
        productPrice=findViewById(R.id.productPrice);
        productDescription=findViewById(R.id.productDescription);
        addToCart=findViewById(R.id.addToCart);
        decreaseQuantity=findViewById(R.id.decreaseQuantity);
        increaseQuantity=findViewById(R.id.increaseQuantity);
        quantityValue=findViewById(R.id.quantityValue);

        Intent getDetails=getIntent();
        String name=getDetails.getStringExtra("ProductName");
        String priceText = String.format("$%.2f ", getDetails.getDoubleExtra("ProductPrice",2.00));


        String image=getDetails.getStringExtra("ProductImage");
        String description=getDetails.getStringExtra("ProductDescription");

        productName.setText(name);
        productPrice.setText(priceText);
        productDescription.setText(description);

        int imgId=getResources().getIdentifier(image,"drawable",getPackageName());
        productImage.setImageResource(imgId);

        //Decrease Quantity
        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityAdded=Integer.parseInt(quantityValue.getText().toString());
                if (quantityAdded > 1) {
                    quantityAdded= quantityAdded - 1;
                    quantityValue.setText(Integer.toString(quantityAdded));
                }
            }
        });


        //Increase Quantity
        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityAdded=Integer.parseInt(quantityValue.getText().toString());
                if (quantityAdded < 20) {
                    quantityAdded= quantityAdded + 1;
                    quantityValue.setText(Integer.toString(quantityAdded));
                }
            }
        });

        // To add to cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pName=name;
                Double pPrice=getDetails.getDoubleExtra("ProductPrice",2.00);
                String pImage=image;
                int quantity=Integer.parseInt(quantityValue.getText().toString());
                CommonSingleton.getInstance().setProductName(pName);
                CommonSingleton.getInstance().setProductPrice(pPrice);
                AddToCartItems items=new AddToCartItems(pName,pImage,quantity,pPrice);
                Toast.makeText(DsiplayProductActivity.this, "Item added to the cart",
                        Toast.LENGTH_SHORT).show();

                if(CommonSingleton.getInstance().getCartItems() == null){
                    addedItems.add(items);

                    CommonSingleton.getInstance().setCartItems(addedItems);
                }
                else{
                    addedItems=CommonSingleton.getInstance().getCartItems();
                    for(AddToCartItems item:addedItems){
                        if(item.productName.equals(items.productName)){
                            item.setProductQuantity(item.getProductQuantity()+items.getProductQuantity());
                            itemALreadyPresent=true;
                            break;

                        }
                    }
                    if(itemALreadyPresent == false){
                        addedItems.add(items);
                        CommonSingleton.getInstance().setCartItems(addedItems);

                    }


                }



            }
        });

    }
}
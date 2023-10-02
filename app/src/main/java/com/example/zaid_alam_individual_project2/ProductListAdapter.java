package com.example.zaid_alam_individual_project2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder>{

    private ArrayList<Products> mProducts;
    public ArrayList<AddToCartItems> addedItems=new ArrayList<AddToCartItems>();
    FirebaseDatabase database;
    DatabaseReference myRef;
    Boolean itemALreadyPresent=false;



    public ProductListAdapter(ArrayList<Products> mProducts) {
        this.mProducts = mProducts;
    }

    @NonNull
    @Override
    public ProductListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.MyViewHolder holder, int position) {
        Products p = mProducts.get(position);


        Context actContext = holder.itemView.getContext();
        int resid = actContext.getResources().getIdentifier(p.getImage(), "drawable", actContext.getPackageName());

        holder.txtName.setText(p.getName());
        holder.imgProduct.setImageResource(resid);
        String priceText = String.format("$%.2f ", p.getPrice());
        holder.txtPrice.setText(priceText);
        holder.txtDescription.setText(p.getDescription());

        // To open Product Details Activity
        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context =v.getContext();

                Intent myIntent= new Intent(context, DsiplayProductActivity.class);
                myIntent.putExtra("ProductName",p.getName());
                myIntent.putExtra("ProductPrice",p.getPrice());
                myIntent.putExtra("ProductImage",p.getImage());
                myIntent.putExtra("ProductDescription",p.getDescription());
                context.startActivity(myIntent);
            }
        });


        //Decrease Quantity
        holder.decreaseQuantityHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int quantityAddedHome=Integer.parseInt(holder.quantityValueHome.getText().toString());
                if (quantityAddedHome > 1) {
                    quantityAddedHome= quantityAddedHome - 1;
                    holder.quantityValueHome.setText(Integer.toString(quantityAddedHome));
                }
            }
        });


        //Increase Quantity
        holder.increaseQuantityHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityAddedHome=Integer.parseInt(holder.quantityValueHome.getText().toString());
                if (quantityAddedHome < 20) {
                    quantityAddedHome= quantityAddedHome + 1;
                    holder.quantityValueHome.setText(Integer.toString(quantityAddedHome));
                }
            }
        });


        // To add to cart
        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pName=p.getName().toString();
                Double pPrice=p.getPrice();
                String pImage=p.getImage();
                int quantity=Integer.parseInt(holder.quantityValueHome.getText().toString());
                CommonSingleton.getInstance().setProductName(p.getName());
                CommonSingleton.getInstance().setProductPrice(p.getPrice());
                AddToCartItems items=new AddToCartItems(pName,pImage,quantity,pPrice);
                Toast.makeText(actContext, "Item Added to the cart", Toast.LENGTH_SHORT).show();

                if(CommonSingleton.getInstance().getCartItems() == null){
                    addedItems.add(items);
                    CommonSingleton.getInstance().setCartItems(addedItems);

                }
                else{
                    addedItems=CommonSingleton.getInstance().getCartItems();
                    for(AddToCartItems item:addedItems){
                        if(item.productName == items.productName){

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

    @Override
    public int getItemCount() {
        return mProducts.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName,txtDescription,txtPrice,quantityValueHome;
        ImageView imgProduct;
        Button addToCartBtn;

        ImageView decreaseQuantityHome,increaseQuantityHome;



        public MyViewHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.product_list_layout, parent, false));
            txtName = itemView.findViewById(R.id.txtName);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtDescription=itemView.findViewById(R.id.txtDescription);
            addToCartBtn=itemView.findViewById(R.id.addToCartBtn);
            decreaseQuantityHome=itemView.findViewById(R.id.decreaseQuantityHome);
            increaseQuantityHome=itemView.findViewById(R.id.increaseQuantityHome);
            quantityValueHome=itemView.findViewById(R.id.quantityValueHome);

        }


    }
}

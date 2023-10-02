package com.example.zaid_alam_individual_project2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;

public class CartItemsAdapter  extends RecyclerView.Adapter<CartItemsAdapter.MyViewHolder>{

    private ArrayList<AddToCartItems> cartItems;

    Double totalCost=0.0;

    public interface CartItemListener {
        void onQuantityChanged(AddToCartItems position, Double newTotalCOst);
    }
    public interface ActivityFinishListener {
        void finishActivity();
    }

    public CartItemListener listener;
    private ActivityFinishListener finishListener;

    public CartItemsAdapter(ArrayList<AddToCartItems> cartItems,CartItemListener listener,ActivityFinishListener finishListener) {
        this.cartItems = cartItems;
        this.listener = listener;
        this.finishListener = finishListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater, parent);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        AddToCartItems p = cartItems.get(position);
        int previousQuantity=p.getProductQuantity();
        totalCost=CommonSingleton.getInstance().getTotalPrice();


        Context actContext = holder.itemView.getContext();
        int resid = actContext.getResources().getIdentifier(p.getProductImage(), "drawable", actContext.getPackageName());

        holder.cart_ItemName.setText(p.getProductName());
        holder.cart_ItemImage.setImageResource(resid);
        String priceText = String.format("$%.2f ", p.getPrice());
        holder.cart_ItemPrice.setText(priceText);
        holder.quantityValueCart.setText(Integer.toString(p.getProductQuantity()));

        //Decrease Quantity
        holder.decreaseQuantityCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int quantityAddedHome=Integer.parseInt(holder.quantityValueCart.getText().toString());

                if (quantityAddedHome > 1) {
                    int newQuantity = quantityAddedHome - 1;
                    double costChange = (quantityAddedHome - newQuantity) * p.getPrice();

                    holder.quantityValueCart.setText(Integer.toString(newQuantity));
                    p.setProductQuantity(newQuantity);

                    totalCost -= costChange;

                    CommonSingleton.getInstance().setTotalPrice(totalCost);

                    if (listener != null) {
                        listener.onQuantityChanged(p, totalCost);
                    }
                }
            }
        });


        //Increase Quantity
        holder.increaseQuantityCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityAddedHome=Integer.parseInt(holder.quantityValueCart.getText().toString());

                if (quantityAddedHome < 20) {
                    int newQuantity = quantityAddedHome + 1;
                    double costChange = (newQuantity - quantityAddedHome) * p.getPrice();

                    holder.quantityValueCart.setText(Integer.toString(newQuantity));
                    p.setProductQuantity(newQuantity);

                    totalCost += costChange;

                    CommonSingleton.getInstance().setTotalPrice(totalCost);

                    if (listener != null) {
                        listener.onQuantityChanged(p, totalCost);
                    }
                }
            }
        });

        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<AddToCartItems> itemsAdded = CommonSingleton.getInstance().getCartItems();
                Iterator<AddToCartItems> iterator = itemsAdded.iterator();

                while (iterator.hasNext()) {
                    AddToCartItems currentItem = iterator.next();
                    if (currentItem.getProductName().equals(p.getProductName())) {
                        iterator.remove();
                        totalCost -= currentItem.getProductQuantity() * currentItem.getPrice();
                        CommonSingleton.getInstance().setTotalPrice(totalCost);

                        if (listener != null) {
                            listener.onQuantityChanged(p, totalCost);
                        }
                        notifyDataSetChanged();
                        break;
                    }
                }
                if(CommonSingleton.getInstance().getCartItems() == null || CommonSingleton.getInstance().getCartItems().isEmpty()){
                    Intent myIntent=new Intent(actContext,DisplayNoItemsActivity.class);
                    actContext.startActivity(myIntent);
                    finishListener.finishActivity();

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cart_ItemName,cart_ItemPrice,quantityValueCart;
        ImageView cart_ItemImage,decreaseQuantityCart,increaseQuantityCart;
        Button removeItem;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.carttems_list_layout, parent, false));
            cart_ItemName = itemView.findViewById(R.id.cart_ItemName);
            cart_ItemImage = itemView.findViewById(R.id.cart_ItemImage);
            cart_ItemPrice = itemView.findViewById(R.id.cart_ItemPrice);
            decreaseQuantityCart = itemView.findViewById(R.id.decreaseQuantityCart);
            increaseQuantityCart = itemView.findViewById(R.id.increaseQuantityCart);
            quantityValueCart = itemView.findViewById(R.id.quantityValueCart);
            removeItem = itemView.findViewById(R.id.removeItem);

        }

    }
}

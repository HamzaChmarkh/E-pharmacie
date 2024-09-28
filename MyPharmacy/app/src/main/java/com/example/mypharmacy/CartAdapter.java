package com.example.mypharmacy;

import static androidx.core.content.ContextCompat.startActivity;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mypharmacy.admin.CartActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.HashSet;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {


    private Context context;
    private static List<CartItem> cartItems;
    private static HashSet<String> uniqueTitles;
    public static double totalAllProducts;
    SharedPreferences sharedPreferences;

    // Constructor to initialize the list of cart items and context
    public CartAdapter(Context context, List<CartItem> cartItems) {

        this.context = context;
        this.cartItems = cartItems;
        this.uniqueTitles = new HashSet<>();
        filterUniqueItems(); // Appelle la méthode pour filtrer les éléments uniques
        sharedPreferences = context.getSharedPreferences("CartPreferences", Context.MODE_PRIVATE);
        // Load the total value from SharedPreferences
        totalAllProducts = sharedPreferences.getFloat("totalAllProducts", 0);
    }



    private void filterUniqueItems() {
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem currentItem = cartItems.get(i);
            if (!uniqueTitles.contains(currentItem.getDataTitle())) {
                uniqueTitles.add(currentItem.getDataTitle());
            } else {
                cartItems.remove(i);
                i--;
            }
        }
    }



    // ViewHolder for each item in the RecyclerView
    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView langTextView;
        //TextView descTextView;

        TextView dateTextView;

        //TextView keyTextView;
        //TextView usernameTextView;
        ImageView imageTextView;

        ImageButton deleteButton;

        ///////////////////////////////////////////

        ImageButton addButton; // Bouton d'ajout de quantité
        TextView quantityTextView; // Ajout du TextView pour la quantité
        ImageButton removeButton; // Bouton de suppression de quantité
        TextView totalPriceTextView;
        ImageButton button7;
        TextView totalPriceTextView1;
        TextView totalLabelTextView1;


        ///////////////////////////////////////////
        private Context context;

        private List<CartItem> cartItems;
        private HashSet<String> uniqueTitles;

        // Constructor to initialize the list of cart items and context

        public CartViewHolder(View itemView) {
            super(itemView);
            /*titleTextView = itemView.findViewById(R.id.titleTextView);
            langTextView = itemView.findViewById(R.id.langTextView);
            //descTextView = itemView.findViewById(R.id.descTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            //keyTextView = itemView.findViewById(R.id.keyTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            imageTextView = itemView.findViewById(R.id.imageTextView);

            deleteButton = itemView.findViewById(R.id.deleteButton);
            */



            titleTextView = itemView.findViewById(R.id.titleTextView);
            langTextView = itemView.findViewById(R.id.langTextView);
            //dateTextView = itemView.findViewById(R.id.dateTextView);
            //usernameTextView = itemView.findViewById(R.id.usernameTextView);
            imageTextView = itemView.findViewById(R.id.imageTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            ///////////////////////////////////////////////////////////////////////
            quantityTextView = itemView.findViewById(R.id.quantityTextView); // Initialisation de quantityTextView
            addButton = itemView.findViewById(R.id.addButton); // Initialisation du bouton d'ajout
            removeButton = itemView.findViewById(R.id.removeButton); // Initialisation du bouton de suppression
            ///////////////////////////////////////////////////////////////////////////
            totalPriceTextView=itemView.findViewById(R.id.totalPriceTextView);
            // textView10=itemView.findViewById(R.id.textView10);
            //button7=itemView.findViewById(R.id.button7);
            totalPriceTextView1=itemView.findViewById(R.id.totalPriceTextView1);
            totalLabelTextView1=itemView.findViewById(R.id.totalLabelTextView1);

        }

    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        /*CartItem currentItem = cartItems.get(position);
        holder.titleTextView.setText(currentItem.getDataTitle());
        holder.langTextView.setText(currentItem.getDataLang());
        //holder.descTextView.setText(currentItem.getDataDesc());
        holder.dateTextView.setText(currentItem.getDate());
        //holder.keyTextView.setText(currentItem.getKey());
        holder.usernameTextView.setText(currentItem.getUsername());
        int resourceId = holder.itemView.getContext().getResources().getIdentifier(currentItem.getDataImage(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imageTextView.setImageResource(resourceId);*/

        CartItem currentItem = cartItems.get(position);

        Glide.with(context).load(cartItems.get(position).getDataImage()).into(holder.imageTextView);
        holder.titleTextView.setText(cartItems.get(position).getDataTitle());
        holder.langTextView.setText(cartItems.get(position).getPrice());

        // holder.dateTextView.setText(cartItems.get(position).getDate());
        //holder.usernameTextView.setText(cartItems.get(position).getUsername());




        /////////////////////////////////////////////////////////////////////////
        holder.quantityTextView.setText(String.valueOf(currentItem.getQuantity())); // Affiche la quantité
        //holder.totalPriceTextView.setText(String.valueOf(currentItem.getTotalPrice())); // Affiche la quantité
        totalAllProducts = calculateTotalAllProducts();
        holder.totalPriceTextView.setText(String.valueOf(currentItem.getTotalPrice())); // Affiche la quantité
        //  holder.button4.setText(String.valueOf(totalAllProducts));
        if (position == cartItems.size() - 1) {
            // If it's the last item, show the total price
            holder.totalPriceTextView1.setVisibility(View.VISIBLE);
            holder.totalPriceTextView1.setText(String.valueOf(totalAllProducts));
        } else {
            // If it's not the last item, hide the total price
            holder.totalPriceTextView1.setVisibility(View.GONE);
        }

        if (position == cartItems.size() - 1) {
            // If it's the last item, show the total label
            holder.totalLabelTextView1.setVisibility(View.VISIBLE);
        } else {
            // If it's not the last item, hide the total label
            holder.totalLabelTextView1.setVisibility(View.GONE);
        }



/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to pass data to CartActivity
                Intent intent = new Intent(context, CartActivity.class);
                intent.putExtra("totalPrice", totalAllProducts);
                context.startActivity(intent);
            }
        });

         */

        holder.imageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem clickedItem = cartItems.get(position);
                totalAllProducts += clickedItem.getTotalPrice(); // Update total value

                // Save total value in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("totalAllProducts", (float) totalAllProducts);
                editor.apply();
                Intent intent = new Intent(context, CartActivity.class);
                intent.putExtra("totalPrice", totalAllProducts); // totalAllProducts est votre variable de prix total
                context.startActivity(intent);
            }
        });








        // saveTotalPriceInSharedPreferences(context, calculateTotalAllProducts());
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                int currentQuantity = currentItem.getQuantity();
                if (currentQuantity > 0) {
                    currentQuantity--;
                    currentItem.setQuantity(currentQuantity);
                    holder.quantityTextView.setText(String.valueOf(currentQuantity));
                    holder.totalPriceTextView.setText(String.valueOf(currentItem.getTotalPrice()));
                    notifyDataSetChanged();
                }
            }
        });


        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                int currentQuantity = currentItem.getQuantity();
                currentQuantity++;
                currentItem.setQuantity(currentQuantity);
                holder.quantityTextView.setText(String.valueOf(currentQuantity));
                holder.totalPriceTextView.setText(String.valueOf(currentItem.getTotalPrice()));
                totalAllProducts = calculateTotalAllProducts();
                notifyDataSetChanged();
            }
        });


        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                int currentQuantity = currentItem.getQuantity();
                if (currentQuantity > 0) {
                    currentQuantity--;
                    currentItem.setQuantity(currentQuantity);
                    holder.quantityTextView.setText(String.valueOf(currentQuantity));
                    holder.totalPriceTextView.setText(String.valueOf(currentItem.getTotalPrice()));
                    totalAllProducts = calculateTotalAllProducts();
                    notifyDataSetChanged();

                }
            }
        });



        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform deletion operation
                deleteItem(currentItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        // Return the size of the list of cart items
        return cartItems.size();
    }




    private void deleteItem(CartItem item) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("add to cart");

        reference.orderByChild("dataTitle").equalTo(item.getDataTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Remove the item from the database
                    snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Item Deleted From Cart", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Failed to delete item from cart", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled event if needed
                Log.e("CartItemDelete", "Database query cancelled: " + databaseError.getMessage());
            }
        });
    }
    public static double calculateTotalAllProducts() {
        double totalAllProducts=0;
        for (CartItem item : cartItems) {
            totalAllProducts += item.getTotalPrice();
        }
        return totalAllProducts;}



}
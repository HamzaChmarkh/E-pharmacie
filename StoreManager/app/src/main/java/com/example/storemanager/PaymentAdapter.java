package com.example.storemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private Context context;
    private List<Payment> payments;

    //private float totalPrice;


    public PaymentAdapter(Context context, List<Payment> payments) {
        this.context = context;
        this.payments = payments;
    }



   /* public PaymentAdapter(Context context, List<Payment> payments, float totalPrice) {
        this.context = context;
        this.payments = payments;
        this.totalPrice = totalPrice;
    }*/

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item_layout, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        Payment payment = payments.get(position);
        holder.username.setText(payments.get(position).getUsername());
        holder.textViewCardNumber.setText(payments.get(position).getCardNumber());
        holder.textViewAddress.setText( payments.get(position).getVille());
        holder.textViewPaymentType.setText( payments.get(position).getRue());
        holder.textViewDate.setText(payments.get(position).getDate());

        holder.total.setText(payments.get(position).getTotal());


        StringBuilder itemDetails = new StringBuilder();
        List<String> items = payment.getItems();
        for (String item : items) {
            itemDetails.append(item).append("\n");
        }
        holder.textViewItemDetails.setText(itemDetails.toString());
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCardNumber, textViewAddress, textViewPaymentType, textViewDate,total,username,textViewItemDetails;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.textViewUser);
            textViewCardNumber = itemView.findViewById(R.id.textViewCardNumber);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewPaymentType = itemView.findViewById(R.id.textViewRue);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            total = itemView.findViewById(R.id.total);
            textViewItemDetails = itemView.findViewById(R.id.textViewItemDetails);
        }
    }
}
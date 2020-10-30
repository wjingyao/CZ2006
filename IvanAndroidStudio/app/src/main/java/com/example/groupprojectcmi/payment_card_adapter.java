package com.example.groupprojectcmi;

import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class payment_card_adapter extends RecyclerView.Adapter<payment_card_adapter.payment_card_holder> {
    List<payment_item> mPaymentList;


    public class payment_card_holder extends RecyclerView.ViewHolder {
        TextView cardNo, expiryDate;
        public payment_card_holder(View itemView) {
            super(itemView);
            cardNo = itemView.findViewById(R.id.card_CardNo);
            expiryDate = itemView.findViewById(R.id.card_Expiry);
        }
    }


    public payment_card_adapter(List<payment_item> paymentList) {
        this.mPaymentList = paymentList;
    }

    @NonNull
    @Override
    public payment_card_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_card_item, parent, false);
        payment_card_holder vch = new payment_card_holder(v);
        return vch;
    }

    @Override
    public void onBindViewHolder(@NonNull payment_card_holder holder, int position) {
        payment_item currentItem = mPaymentList.get(position);

        holder.cardNo.setText(currentItem.getPaymentCardNo());
        holder.expiryDate.setText(currentItem.getExpiryDate());

    }

    @Override
    public int getItemCount() {
        return mPaymentList.size();
    }
}

package com.example.groupprojectcmi;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupprojectcmi.api.api;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class payment_card_adapter extends RecyclerView.Adapter<payment_card_adapter.payment_card_holder> {
    List<payment_item> mPaymentList;


    public class payment_card_holder extends RecyclerView.ViewHolder {
        TextView cardNo, expiryDate;
        Button deleteBtn;
        public payment_card_holder(View itemView) {
            super(itemView);
            cardNo = itemView.findViewById(R.id.card_CardNo);
            expiryDate = itemView.findViewById(R.id.card_Expiry);
            deleteBtn = itemView.findViewById(R.id.card_PaymentButton);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int paymentid = mPaymentList.get(getAdapterPosition()).getPaymentId();

                    MediaType JSON = MediaType.get("application/json; charset=utf-8");
                    JSONObject dataBody = new JSONObject();
                    OkHttpClient client = new OkHttpClient();

                    RequestBody body = RequestBody.create(dataBody.toString(),JSON);  //only req change
                    Log.d("OKHTTP3", "RequestBody Created.");
                    Request request = new Request.Builder()
                            .url(api.baseUrl + "paymentCards/"+paymentid)        //cthis is the url we can change based on different UI
                            .addHeader("Authorization", "Bearer " + api.token)
                            .delete()                                 //depends on where to get or post database, we use that word respectively
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()) {
                                String myResponse = response.body().string();
                                System.out.println(myResponse);
                                mPaymentList.remove(getAdapterPosition());
                                notifyItemRemoved(getAdapterPosition());
                            }
                            else
                            {
                                Log.d("Payment", "Fail To Delete");
                            }
                        }
                    });
                    Toast.makeText(v.getContext(), "Payment deleted", Toast.LENGTH_SHORT).show();

                }
            });
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

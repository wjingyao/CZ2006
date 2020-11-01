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

public class booking_card_adapter extends RecyclerView.Adapter<booking_card_adapter.booking_card_holder> {
    List<booking_item> mBookingList;

    public class booking_card_holder extends RecyclerView.ViewHolder {
        TextView active, vehicle, carPark, dateTime, address;
        Button deleteBtn;
        public booking_card_holder(View itemView){
            super(itemView);
            active = itemView.findViewById(R.id.card_BookingActive);
            vehicle = itemView.findViewById(R.id.card_BookingVehicle);
            carPark = itemView.findViewById(R.id.card_Carpark);
            dateTime = itemView.findViewById(R.id.card_BookingDateTime);
            address = itemView.findViewById(R.id.card_Address);
            deleteBtn = itemView.findViewById(R.id.btn_dBooking);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int bookingid = mBookingList.get(getAdapterPosition()).getBookingId();
                    Log.d("bookingid", String.valueOf(bookingid));
                    MediaType JSON = MediaType.get("application/json; charset=utf-8");
                    JSONObject dataBody = new JSONObject();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(dataBody.toString(),JSON);  //only req change
                    Log.d("OKHTTP3", "RequestBody Created.");
                    Request request = new Request.Builder()
                            .url(api.baseUrl + "bookings/"+bookingid)        //cthis is the url we can change based on different UI
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
                                mBookingList.remove(getAdapterPosition());
                                notifyItemRemoved(getAdapterPosition());
                            }
                            else
                            {
                                Log.d("Booking", "Fail To Delete");
                            }
                        }
                    });
                    Toast.makeText(v.getContext(), "Booking cancelled", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }


    public booking_card_adapter(List<booking_item> bookingList) { this.mBookingList = bookingList; }

    @NonNull
    @Override
    public booking_card_adapter.booking_card_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_card_item, parent, false);
        booking_card_adapter.booking_card_holder vch = new booking_card_adapter.booking_card_holder(v);
        return vch;
    }

    @Override
    public void onBindViewHolder(@NonNull booking_card_adapter.booking_card_holder holder, int position) {
        booking_item currentItem = mBookingList.get(position);

        holder.active.setText(currentItem.getBookingActive());
        holder.vehicle.setText(currentItem.getVehiclePlate());
        holder.carPark.setText(currentItem.getCarParkName());
        holder.dateTime.setText(currentItem.getBookingDateTime());
        holder.address.setText(currentItem.getAddress());
        if (currentItem.getBookingActive().equals("Completed"))
        {
          holder.deleteBtn.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mBookingList.size();
    }
}



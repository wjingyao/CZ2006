package com.example.groupprojectcmi;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class vehicle_card_adapter extends RecyclerView.Adapter<vehicle_card_adapter.vehicle_card_holder> {
    List<vehicle_item> mVehicleList;


    public class vehicle_card_holder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mVehiclePlate;
        TextView tVehicleId;
        Button deleteBtn;
        public vehicle_card_holder(View itemView) {
            super(itemView);
            tVehicleId = itemView.findViewById(R.id.card_VehicleId);
            mImageView = itemView.findViewById(R.id.card_VehicleLogo);
            mVehiclePlate = itemView.findViewById(R.id.card_VehiclePlate);
            deleteBtn = itemView.findViewById(R.id.card_VehicleButton);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("position", String.valueOf(getAdapterPosition()));
                    Log.d("id number", String.valueOf(mVehicleList.get(getAdapterPosition()).getVehicleId()));
                    int vehid = mVehicleList.get(getAdapterPosition()).getVehicleId();

                    MediaType JSON = MediaType.get("application/json; charset=utf-8");
                    JSONObject dataBody = new JSONObject();
                    OkHttpClient client = new OkHttpClient();

                    RequestBody body = RequestBody.create(dataBody.toString(),JSON);  //only req change
                    Log.d("OKHTTP3", "RequestBody Created.");
                    Request request = new Request.Builder()
                            .url(api.baseUrl + "vehicles/"+vehid)        //cthis is the url we can change based on different UI
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
                                mVehicleList.remove(getAdapterPosition());
                                notifyItemRemoved(getAdapterPosition());

                            }
                            else
                            {
                                Log.d("Delete", "Fail");
                            }
                        }
                    });
                    Toast.makeText(v.getContext(),"Vehicle deleted", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    public vehicle_card_adapter(List<vehicle_item> vehicleList) {
        this.mVehicleList = vehicleList;
    }

    @NonNull
    @Override
    public vehicle_card_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_card_item, parent, false);
        vehicle_card_holder vch = new vehicle_card_holder(v);

        return vch;
    }

    @Override
    public void onBindViewHolder(@NonNull vehicle_card_holder holder, int position) {
        vehicle_item currentItem = mVehicleList.get(position);

        holder.tVehicleId.setText(String.valueOf(currentItem.getVehicleId()));
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mVehiclePlate.setText(currentItem.getMvehiclePlateNo());

    }

    @Override
    public int getItemCount() {
        return mVehicleList.size();
    }
}

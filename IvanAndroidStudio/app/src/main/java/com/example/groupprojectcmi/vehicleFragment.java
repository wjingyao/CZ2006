package com.example.groupprojectcmi;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupprojectcmi.api.api;

import java.util.ArrayList;
import java.util.List;

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

public class vehicleFragment extends Fragment {

    RecyclerView mRecyclerView;
    List<vehicle_item> cardList;

    EditText aVehicle;
    RadioGroup aVehicleType;
    RadioButton aCar, aMotorbike, aLorry;
    Button aBtn;

    vehicle_card_adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vehicle, container, false);
        mRecyclerView = rootView.findViewById(R.id.vehicleRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        aVehicle = rootView.findViewById(R.id.addVehicle);
        aVehicleType = rootView.findViewById(R.id.radio_addVehicle);
        aCar = rootView.findViewById(R.id.radio_aCar);
        aMotorbike = rootView.findViewById(R.id.radio_aMotorbike);
        aLorry = rootView.findViewById(R.id.radio_aLorry);
        aBtn = rootView.findViewById(R.id.addVehicleBtn);

        initData();

        aBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plateNum = aVehicle.getText().toString().trim();

                int type_Id = aVehicleType.getCheckedRadioButtonId();
                String vehicle_Type = "Car";
                if (type_Id == aLorry.getId())
                {
                    vehicle_Type = "Lorry";
                }
                else if (type_Id == aMotorbike.getId())
                {
                    vehicle_Type = "Motorbike";
                }

                if (TextUtils.isEmpty(plateNum)) {
                    aVehicle.setError("A vehicle plate number is required");
                    return;
                }
                MediaType JSON = MediaType.get("application/json; charset=utf-8");  //dont change this part
                JSONObject dataBody = new JSONObject();                             //dont change this part
                OkHttpClient client = new OkHttpClient();

                try {
                    dataBody.put("plateNum",plateNum);
                    dataBody.put("typeOfVehicle", vehicle_Type);
                }
                catch (JSONException e) {
                    Log.d("OKHTTP3", "JSON Exception");
                    e.printStackTrace();
                }
                Log.d("OKHTTP3", dataBody.toString());

                RequestBody body = RequestBody.create(dataBody.toString(),JSON);  //only req change
                Log.d("OKHTTP3", "RequestBody Created.");
                Request request = new Request.Builder()
                        .url(api.baseUrl + "vehicles/create?userId=" + api.id)
                        .addHeader("Authorization", "Bearer " + api.token)
                        .post(body)
                        .build();
                Log.d("token",request.toString());
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        if (response.isSuccessful()) {
                            String myResponse = response.body().string();
                            System.out.println(myResponse);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Vehicle added", Toast.LENGTH_SHORT).show();
                                    initData();
                                }
                            });
                        }

                        else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Vehicle failed to add", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

            }
        });

        return rootView;

    }



    private void initData() {
        //Bind user's vehicle
        cardList = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(api.baseUrl + "vehicles/?userId=" + api.id)
                .addHeader("Authorization", "Bearer " + api.token)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    System.out.println(myResponse);
                    try {
                        JSONArray jArr = new JSONArray(myResponse);
                            for (int i = 0; i < jArr.length(); i++)
                            {
                                JSONObject obj = new JSONObject();
                                obj = jArr.optJSONObject(i);
                                Log.d("Jarr", obj.toString());
                                int id = obj.getInt("id");
                                Log.d("id", String.valueOf(id));
                                String plateNum = obj.getString("plateNum");
                                Log.d("platenum", plateNum);
                                String compareType = obj.getString("typeOfVehicle");
                                Log.d("typeofvehicle", compareType);
                                if (compareType.equals("Lorry"))
                                {
                                    cardList.add(new vehicle_item(id,R.drawable.ic_lorry_icon,plateNum));
                                }
                                else if (compareType.equals("Motorbike"))
                                {
                                    cardList.add(new vehicle_item(id,R.drawable.ic_motorcycle_icon,plateNum));
                                }
                                else
                                {
                                    cardList.add(new vehicle_item(id,R.drawable.ic_car_icon,plateNum));
                                }

                            }
                            if(cardList.size() !=0){
                                //if(mRecyclerView.getAdapter() == null){
                                    vehicle_card_adapter adapter = new vehicle_card_adapter(cardList);
                                    mRecyclerView.setAdapter(adapter);
                                //}else{
                                 //   vehicle_card_adapter adapter = new vehicle_card_adapter(cardList);
                                 //   mRecyclerView.setAdapter(adapter);
                                    //       adapter.notifyDataSetChanged();
                                }
                            }

                    }
                    catch (JSONException e) {}
                }
            }
        });


    }
}

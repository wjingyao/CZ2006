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

public class mybookingFragment extends Fragment {

    RecyclerView mRecyclerView;
    List<booking_item> cardList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mybooking, container, false);
        mRecyclerView = rootView.findViewById(R.id.bookingRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        booking_card_adapter adapter = new booking_card_adapter(initData());
        mRecyclerView.setAdapter(adapter);

        return rootView;
    }

    private List<booking_item> initData() {
        //Bind user's bookinglist
        cardList = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(api.baseUrl + "bookings/?username=" + api.user)
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
                            for (int i = jArr.length()-1; i >= 0 ; i--)
                            {
                                JSONObject obj = new JSONObject();
                                obj = jArr.optJSONObject(i);
                                int id = obj.getInt("id");
                                Log.d("id", String.valueOf(id));
                                String dateTime = obj.getString("bookingDateTime");
                                Log.d("Datetime", dateTime);
                                dateTime = dateTime.replace("T"," ");
                                String active = String.valueOf(obj.getBoolean("active"));
                                Log.d("active", String.valueOf(active));
                                String bkcarpark = obj.getJSONObject("carPark").getString("carParkName");
                                Log.d("carpark", bkcarpark);
                                String plateNum = obj.getJSONObject("vehicle").getString("plateNum");
                                Log.d("platenum", plateNum);
                                String address = obj.getJSONObject("carPark").getString("address");
                                if (active.equals("true")) {
                                   cardList.add(new booking_item(id,"Current",dateTime,plateNum,bkcarpark,"@"+address));
                                   System.out.println("current");
                                }
                                else
                                {
                                    cardList.add(new booking_item(id,"Completed",dateTime,plateNum,bkcarpark,"@"+address));
                                    System.out.println("completed");
                                }

                            }
                    }
                    catch (JSONException e)
                    {
                    }
                }
            }

        });
        return cardList;
    }

}

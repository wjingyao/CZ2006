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

public class paymentFragment extends Fragment {

    RecyclerView mRecyclerView;
    EditText txt_CCV, txt_Expiry, txt_CardNo;
    List<payment_item> cardList;
    Button aBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);

        mRecyclerView = rootView.findViewById(R.id.paymentRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        payment_card_adapter adapter = new payment_card_adapter(initData());
        mRecyclerView.setAdapter(adapter);

        txt_CCV = rootView.findViewById(R.id.add_CCV);
        txt_Expiry = rootView.findViewById(R.id.add_Expiry);
        txt_CardNo = rootView.findViewById(R.id.add_CardNo);
        aBtn = rootView.findViewById(R.id.addPaymentBtn);

        aBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ccv = txt_CCV.getText().toString().trim();
                String expiry = txt_Expiry.getText().toString().trim();
                String cardNo = txt_CardNo.getText().toString().trim();

                MediaType JSON = MediaType.get("application/json; charset=utf-8");  //dont change this part
                JSONObject dataBody = new JSONObject();                             //dont change this part
                OkHttpClient client = new OkHttpClient();

                try {
                    dataBody.put("cardNum", cardNo);
                    dataBody.put("ccv", ccv);
                    dataBody.put("expiry_date", expiry);
                }
                catch (JSONException e) {
                    Log.d("OKHTTP3", "JSON Exception");
                    e.printStackTrace();
                }
                Log.d("OKHTTP3", dataBody.toString());

                RequestBody body = RequestBody.create(dataBody.toString(),JSON);  //only req change
                Log.d("OKHTTP3", "RequestBody Created.");
                Request request = new Request.Builder()
                        .url(api.baseUrl + "paymentCards/create?userId=" + api.id)
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
                                    Toast.makeText(getActivity(), "Payment added", Toast.LENGTH_SHORT).show();
                                    update(initData());
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                        else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Payment failed to add", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                });
            }
        });

        return rootView;

    }

    public void update(List<payment_item> list){
        payment_card_adapter adapter = new payment_card_adapter(list);
        mRecyclerView.setAdapter(adapter);

    }

    private List<payment_item> initData() {
        //Bind user's paymentcard
        cardList = new ArrayList<>();
        cardList.add(new payment_item(3000, "TESTPAYMENTCARDNO", "10-10-23"));

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(api.baseUrl + "paymentCards/?userId=" + api.id)
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
                                int id = obj.getInt("id");
                                Log.d("id", String.valueOf(id));
                                String card_Num = obj.getString("cardNum");
                                Log.d("cardnum", card_Num);
                                String expiry_date = obj.getString("expiry_date");
                                Log.d("expiry", expiry_date);
                                cardList.add(new payment_item(id,card_Num,expiry_date));

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

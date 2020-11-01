package com.example.groupprojectcmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupprojectcmi.api.api;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BookActivity extends AppCompatActivity {

    RadioButton bcar, blorry, bmotor;
    TextView bkname, bkaddress, backbtn, btotal, bavail;
    Button bkBtn;
    Spinner vehicleSpinner, paymentSpinner;
    List<vehicle_item> vehicleList = new ArrayList<vehicle_item>();
    List<payment_item> paymentList = new ArrayList<payment_item>();
    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        bkname = findViewById(R.id.bkname);
        bkaddress = findViewById(R.id.bkaddress);
        btotal = findViewById(R.id.btotal);
        bavail = findViewById(R.id.bavail);
        bkBtn = findViewById(R.id.btn_Book);

        backbtn = findViewById(R.id.returnbtn);

        bcar = findViewById(R.id.rate_Car);
        blorry = findViewById(R.id.rate_Lorry);
        bmotor = findViewById(R.id.rate_Motorbike);

        vehicleSpinner = findViewById(R.id.spinner_Vehicle);
        paymentSpinner = findViewById(R.id.spinner_Payment);


        int id = getIntent().getIntExtra("id",0);
        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        String car = getIntent().getStringExtra("carRate");
        String lorry = getIntent().getStringExtra("lorryRate");
        String motor = getIntent().getStringExtra("motorRate");
        int total = getIntent().getIntExtra("total",0);
        int avail = getIntent().getIntExtra("avail",0);

        Log.d("lorry", lorry);

        bkname.setText("Carpark " + name);
        bkaddress.setText(address);
        bcar.setText(" $"+car+"0");
        blorry.setText(" $"+lorry+"0");
        bmotor.setText(" $"+motor+"0");
        btotal.setText("/"+String.valueOf(total));
        bavail.setText(String.valueOf(avail));

        if (avail == 0)
        {
            bkBtn.setVisibility(View.GONE);
        }

        if (api.size > 0)
        {
            bkBtn.setEnabled(false);
            bkBtn.setBackgroundColor(Color.parseColor("#E8E8E8"));
            bkBtn.setTextColor(Color.parseColor("#D3D3D3"));
        }

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

      //  this.vehicleList = vehicleData();
        this.vehicleList.add(new vehicle_item(-1, -1, "..."));
        this.paymentList.add(new payment_item(-1,"...","111"));


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
                                vehicleList.add(new vehicle_item(id,R.drawable.ic_lorry_icon,plateNum));
                            }
                            else if (compareType.equals("Motorbike"))
                            {
                                vehicleList.add(new vehicle_item(id,R.drawable.ic_motorcycle_icon,plateNum));
                            }
                            else
                            {
                                vehicleList.add(new vehicle_item(id,R.drawable.ic_car_icon,plateNum));
                            }

                        }

                    }
                    catch (JSONException e) {}
                }
            }
        });

        ArrayAdapter<vehicle_item> vehicleAdapter = new ArrayAdapter<vehicle_item>(BookActivity.this,android.R.layout.simple_list_item_1,vehicleList);
        vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleSpinner.setAdapter(vehicleAdapter);


        OkHttpClient client1 = new OkHttpClient();
        Request request1 = new Request.Builder()
                .url(api.baseUrl + "paymentCards/?userId=" + api.id)
                .addHeader("Authorization", "Bearer " + api.token)
                .get()
                .build();


        client1.newCall(request1).enqueue(new Callback() {
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
                            paymentList.add(new payment_item(id,card_Num,expiry_date));

                        }
                    }
                    catch (JSONException e)
                    {

                    }
                }
            }
        });
        ArrayAdapter<payment_item> paymentAdapter = new ArrayAdapter<payment_item>(BookActivity.this,android.R.layout.simple_list_item_1,paymentList);
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentSpinner.setAdapter(paymentAdapter);

        bkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vehicle_item vitem = (vehicle_item) vehicleSpinner.getSelectedItem();
                payment_item pitem = (payment_item) paymentSpinner.getSelectedItem();
                System.out.println(vitem.getVehicleId() +" "+ pitem.getPaymentId());

                int vehicle_id = vitem.getVehicleId();
                int payment_id = pitem.getPaymentId();


                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.00");
                String currentDateandTime = sdf.format(new Date());
                System.out.println(currentDateandTime);

                if (vitem.getVehicleId() == -1 || pitem.getPaymentId() == -1)
                {
                    Toast.makeText(BookActivity.this, "Please select a vehicle/payment", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    MediaType JSON = MediaType.get("application/json; charset=utf-8");  //do    nt change this part
                    JSONObject dataBody = new JSONObject();                             //dont change this part
                    OkHttpClient client = new OkHttpClient();

                    try {
                        dataBody.put("active",true);
                        dataBody.put("bookingDateTime",currentDateandTime);
                    }
                    catch (JSONException e) {
                        Log.d("OKHTTP3", "JSON Exception");
                        e.printStackTrace();
                    }

                    Log.d("OKHTTP3", dataBody.toString());

                    RequestBody body = RequestBody.create(dataBody.toString(),JSON);  //only req change
                    Log.d("OKHTTP3", "RequestBody Created.");
                    Request request = new Request.Builder()
                            .url(api.baseUrl + "bookings/create?userId="+api.id+"&vehicleId="+vehicle_id+"&carParkId=" + id)
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
                                BookActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(BookActivity.this, "Book Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent  = new Intent(BookActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    });
                }



            }
        });




    }


}
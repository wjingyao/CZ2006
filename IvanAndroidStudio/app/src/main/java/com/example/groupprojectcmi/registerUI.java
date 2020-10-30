package com.example.groupprojectcmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupprojectcmi.api.api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class registerUI extends AppCompatActivity {

    EditText rUser, rPassword, rComfirmPassword, rEmail, rPlateNumber, rFirstName, rLastName;
    Button rButton;
    RadioGroup rVehicleType;
    TextView rRegister;
    RadioButton rCar, rMotorbike, rLorry;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_u_i);

        //where to get the data from
        rUser = findViewById(R.id.register_Username); //used
        rPassword = findViewById(R.id.register_Password); //used
        rComfirmPassword = findViewById(R.id.register_ConfirmPassword); //used
        rPlateNumber = findViewById(R.id.register_VehiclePlate);
        rEmail = findViewById(R.id.register_Email); //used
        rButton = findViewById(R.id.register_Button); //used
        rFirstName = findViewById(R.id.register_Firstname); //used
        rLastName = findViewById(R.id.register_Lastname); //used
        rVehicleType = findViewById(R.id.radio_selectVehicle); //used
        rCar = rVehicleType.findViewById(R.id.radio_Car); //used
        rMotorbike = rVehicleType.findViewById(R.id.radio_Motorbike); //used
        rLorry = rVehicleType.findViewById(R.id.radio_Lorry); //used


        //when the user clicks the button "Register under "Register UI" this is what it will do.
        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //declaration of where i get the item from it
                String email = rEmail.getText().toString().trim();
                String password = rPassword.getText().toString().trim();
                String confirmPassword = rComfirmPassword.getText().toString().trim();
                String user = rUser.getText().toString().trim();
                String plateNum = rPlateNumber.getText().toString().trim();
                String firstName = rFirstName.getText().toString().trim();
                String lastName = rLastName.getText().toString().trim();

                //Vehicle type selection for radio group
                int type_Id = rVehicleType.getCheckedRadioButtonId();
                String vehicle_Type = "Car";
                if (type_Id == rLorry.getId())
                {
                    vehicle_Type = "Lorry";
                }
                else if (type_Id == rMotorbike.getId())
                {
                    vehicle_Type = "Motorbike";
                }



                //check if email is enter or not
                if (TextUtils.isEmpty(email)) {
                    rEmail.setError("Email is Required");
                    return;
                }
                //check if password is enter or not
                if (TextUtils.isEmpty(password)) {
                    rPassword.setError("Password is Required");
                    return;
                }
                //check if password is more than 6 characters
                if (password.length() < 6) {
                    rPassword.setError("Password Must be >= 6 characters");
                    return;
                }
                //check if password is not of equal length
                if (password.length() != confirmPassword.length()) {
                    rComfirmPassword.setError("Password not the same");
                    return;
                }
                //check if user is keyed in or not
                if (TextUtils.isEmpty(user)) {
                    rUser.setError("user is Required");
                    return;
                }

                if (TextUtils.isEmpty(plateNum)) {
                    rPlateNumber.setError("A default Vehicle Plate Number is required");
                    return;
                }

                if (TextUtils.isEmpty(firstName)) {
                    rFirstName.setError("Please enter your first name");
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    rLastName.setError("Please enter your last name");
                    return;
                }

                //from line 98 to 158 how ot is set up for the data connect to mysql using json coded
                MediaType JSON = MediaType.get("application/json; charset=utf-8");  //dont change this part
                JSONObject dataBody = new JSONObject();                             //dont change this part
                OkHttpClient client = new OkHttpClient();                           //dont change this part
                JSONObject vehicle = new JSONObject();

                try{                                                                //From line 103 to 108 is where we get the data from Register UI
                    dataBody.put("username", user);
                    dataBody.put("password", password);
                    dataBody.put("firstName", firstName);                   //we specify a fix value for line 106 and 107 for now first
                    dataBody.put("lastName", lastName);                    //as the UI has not updated, once change will follow.
                    dataBody.put("email", email);
                    vehicle.put("plateNum", plateNum);
                    vehicle.put("typeOfVehicle", vehicle_Type);
                    JSONArray arr = new JSONArray();
                    arr.put(vehicle);
                    dataBody.put("vehicles", arr);
                }
                catch (JSONException e) {
                    Log.d("OKHTTP3", "JSON Exception");
                    e.printStackTrace();
                }
                Log.d("OKHTTP3", dataBody.toString());



                RequestBody body = RequestBody.create(dataBody.toString(),JSON);  //only req change
                Log.d("OKHTTP3", "RequestBody Created.");
                Request request = new Request.Builder()
                        .url(api.baseUrl + "users/register")        //cthis is the url we can change based on different UI
                        .post(body)                                 //depends on where to get or post database, we use that word respectively
                        .build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        //if all the input response from the user is okay, it will do the following step
                        if (response.isSuccessful()) {
                            String myResponse = response.body().string();
                            System.out.println(myResponse);
                            registerUI.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(registerUI.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                                    //this startactivity step brings the user to the main activivty page aka home page
                                    Intent intent = new Intent(getBaseContext(), loginUI.class);
                                    startActivity(intent);
                                }
                            });
                            //if user input wrong
                        } else {
                            registerUI.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(registerUI.this, "sign up failed", Toast.LENGTH_SHORT).show();

                                }
                            });
                            //finish();
                        }
                    }

                });











            }
        });


    }


}






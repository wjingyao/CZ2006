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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class loginUI extends AppCompatActivity {

    EditText lUser, lPassword;
    TextView lNewUser;
    Button lButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_u_i);


        lUser =findViewById(R.id.login_User);
        lPassword = findViewById(R.id.login_Password);
        lNewUser = findViewById(R.id.login_NewUser);
        lButton = findViewById(R.id.login_Button);


        lButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //declaration of where i get the item from it
                String user = lUser.getText().toString().trim();
                String password = lPassword.getText().toString().trim();


                //check if email is enter or not
                if (TextUtils.isEmpty(user)) {
                    lUser.setError("Username is Required");
                    return;
                }

                //check if password is enter or not
                if (TextUtils.isEmpty(password)) {
                    lPassword.setError("Password is Required");
                    return;
                }

                //check if password is more than 6 characters
                if (password.length() < 6) {
                    lPassword.setError("Password Must be >= 6 characters");
                    return;
                }

                MediaType JSON = MediaType.get("application/json; charset=utf-8");  //dont change this part
                JSONObject dataBody = new JSONObject();                             //dont change this part
                OkHttpClient client = new OkHttpClient();                           //dont change this part

                try{                                                                //From line 103 to 108 is where we get the data from Register UI
                    dataBody.put("username", user);
                    dataBody.put("password", password);
                }
                catch (JSONException e) {
                    Log.d("OKHTTP3", "JSON Exception");
                    e.printStackTrace();
                }
                Log.d("OKHTTP3", dataBody.toString());

                RequestBody body = RequestBody.create(dataBody.toString(),JSON);  //only req change
                Log.d("OKHTTP3", "RequestBody Created.");
                Request request = new Request.Builder()
                        .url(api.baseUrl + "users/login")        //cthis is the url we can change based on different UI
                        .post(body)                                 //depends on where to get or post database, we use that word respectively
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
                                JSONObject Jobject = new JSONObject(myResponse);
                                api.token = Jobject.getString("token");
                                api.id = Jobject.getJSONObject("user").getInt("id");
                                Log.d("api id", String.valueOf(api.id));
                                Log.d("api token", api.token);
                                //Log.d("api id", api.id);


                                loginUI.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(loginUI.this, "login successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                        intent.putExtra("SESSION_ID", user);
                                        api.user = user;
                                        startActivity(intent);
                                    }
                                });
                            } catch (JSONException e) {
                                System.out.println("Error in token");
                            }
                        }
                        else {
                            loginUI.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(loginUI.this, "login failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });


            }
        });

        //when user click on -New User?, it will bring them to register page
        lNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),registerUI.class));
            }
        });

    }
}
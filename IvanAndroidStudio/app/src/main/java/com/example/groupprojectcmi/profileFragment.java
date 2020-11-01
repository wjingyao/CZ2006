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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.groupprojectcmi.api.api;

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

public class profileFragment extends Fragment {

    EditText pPassword, pCPassword, pEmail;
    Button pButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_profile,container,false);
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        pPassword = rootView.findViewById(R.id.profile_Password);
        pCPassword = rootView.findViewById(R.id.profile_cPassword);
        pButton = rootView.findViewById(R.id.profile_Update_Button);

//        MediaType JSON = MediaType.get("application/json; charset=utf-8");  //dont change this part
//        JSONObject dataBody = new JSONObject();                             //dont change this part
//        OkHttpClient client = new OkHttpClient();                           //dont change this part
//
//        Request request = new Request.Builder()
//                .url(api.baseUrl + "users/" + api.id)
//                .addHeader("Authorization", "Bearer" + api.token)
//                .get()
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//
//                if (response.isSuccessful()) {
//                    String myResponse = response.body().string();
//                    System.out.println(myResponse);
//                    try {
//                        JSONObject Jobject = new JSONObject(myResponse);
//                        pEmail.setText(Jobject.getString("email"));
//                    }
//                    catch (JSONException e){}
//                }
//            }
//        });


        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = pPassword.getText().toString().trim();
                String cpassword = pCPassword.getText().toString().trim();

                if (TextUtils.isEmpty(password)) {
                    pPassword.setError("Password is Required");
                    return;
                }

                if (TextUtils.isEmpty(cpassword)) {
                    pCPassword.setError("Confirm your password");
                    return;
                }

                if (password.length() != cpassword.length()) {
                    pCPassword.setError("Password not the same");
                    return;
                }


                //from line 98 to 158 how ot is set up for the data connect to mysql using json coded
                MediaType JSON = MediaType.get("application/json; charset=utf-8");  //dont change this part
                JSONObject dataBody = new JSONObject();                             //dont change this part
                OkHttpClient client = new OkHttpClient();                           //dont change this part
                JSONObject vehicle = new JSONObject();

                try {
                    dataBody.put("password", password);
                }
                catch (JSONException e) {
                    Log.d("OKHTTP3", "JSON Exception" );
                    e.printStackTrace();
                }
                Log.d("OKHTTP3", dataBody.toString());

                RequestBody body = RequestBody.create(dataBody.toString(), JSON);
                Log.d("OKHTTP3", "RequestBody created.");
                Request request = new Request.Builder()
                        .url(api.baseUrl + "users/" + api.id)
                        .put(body)
                        .addHeader("Authorization", "Bearer " + api.token)
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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Password changed", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), profileFragment.class);
                                    startActivity(intent);
                                }
                            });

                        }
                        else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Password failed to change", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                });
            }
        });


        return rootView;
    }





}

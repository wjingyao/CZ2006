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
    List<payment_item> cardList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);

        mRecyclerView = rootView.findViewById(R.id.paymentRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initData();

        mRecyclerView.setAdapter(new payment_card_adapter(initData()));

        return rootView;
    }

    private List<payment_item> initData() {
        //Bind user's paymentcard
        cardList = new ArrayList<>();
        cardList.add(new payment_item("TESTPAYMENTCARDNO", "10-10-23"));

        return cardList;
    }
}

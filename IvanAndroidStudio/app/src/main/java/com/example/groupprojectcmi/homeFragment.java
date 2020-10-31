package com.example.groupprojectcmi;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.groupprojectcmi.api.api;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class homeFragment extends Fragment implements OnMapReadyCallback{
    MapView mMapView;
    GoogleMap mGoogleMap;
    LocationRequest mLocationRequest;
    List<Marker> markerList;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.google_map);
        mMapView.onCreate(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mMapView.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        // Pan the camera to your home address (in this case, Google HQ).
        LatLng home = new LatLng(37.421982, -122.085109);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 12));

        

        //setMapLongClick(mMap); // Set a long click listener for the map;
        //setPoiClick(mMap); // Set a click listener for points of interest.
        enableMyLocation(mGoogleMap); // Enable location tracking.
    }
/*
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Log.d("call back" ,"success");
            Location location = locationResult.getLastLocation();

            MediaType JSON = MediaType.get("application/json; charset=utf-8");  //dont change this part
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(api.baseUrl + "carParks/?x=" + location.getLatitude() + "&y=" + location.getLongitude() + "&=2")
                    .addHeader("Authorization", "Bearer " + api.token)
                    .get()
                    .build();
            Log.d("urlzzz", api.baseUrl + "carParks/?x=" + location.getLatitude() + "&y=" + location.getLongitude() + "&=2");

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
                            JSONArray arr = new JSONArray(myResponse);
                            markerList = new ArrayList<>();
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jsonObject = arr.optJSONObject(i);
                                LatLng latLng = new LatLng(jsonObject.getDouble("x"), jsonObject.getDouble("y"));
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(latLng);
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                                markerList.add(mGoogleMap.addMarker(markerOptions));
                                //move map camera
                            }
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }
    };*/

    private void enableMyLocation(GoogleMap map) {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

}


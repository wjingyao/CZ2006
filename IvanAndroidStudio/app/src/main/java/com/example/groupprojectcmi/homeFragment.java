package com.example.groupprojectcmi;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

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

import static android.content.Context.LOCATION_SERVICE;

public class homeFragment extends Fragment implements OnMapReadyCallback {
    MapView mMapView;
    GoogleMap mGoogleMap;
    private FusedLocationProviderClient fusedLocationClient;
    List<JSONObject> carParkList;
    Location mLastLocation;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.google_map);
        mMapView.onCreate(savedInstanceState);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
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
        enableMyLocation(mGoogleMap);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            mLastLocation = location;
                            // Logic to handle location object
                            MediaType JSON = MediaType.get("application/json; charset=utf-8");  //dont change this part
                            OkHttpClient client = new OkHttpClient();

                            Request request = new Request.Builder()
                                    .url(api.baseUrl + "carParks/?x=" + mLastLocation.getLatitude() + "&y=" + mLastLocation.getLongitude() + "&distance=2")
                                    .addHeader("Authorization", "Bearer " + api.token)
                                    .get()
                                    .build();

                           client.newCall(request).enqueue(new Callback() {
                               @Override
                               public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                   if (response.isSuccessful()) {
                                       try {
                                           carParkList = new ArrayList<>();
                                           String myResponse = response.body().string();
                                           JSONArray arr = new JSONArray(myResponse);
                                           for (int i = 0; i < arr.length(); i++) {
                                               JSONObject jsonObject = arr.optJSONObject(i);
                                               carParkList.add(jsonObject);
                                           }
                                               getActivity().runOnUiThread(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                    for(JSONObject carPark : carParkList) {

                                                        try {
                                                            LatLng latLng = new LatLng(carPark.getDouble("x"), carPark.getDouble("y"));
                                                            MarkerOptions markerOptions = new MarkerOptions();
                                                            markerOptions.position(latLng);
                                                            Marker marker = mGoogleMap.addMarker(markerOptions);
                                                            marker.setTag(carPark.getInt("id"));
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                       LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                                       mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                                   }
                                               });

                                       } catch (JSONException e) {
                                           e.printStackTrace();
                                       }
                                   }
                               }
                               @Override
                               public void onFailure(@NotNull Call call, @NotNull IOException e) {

                               }
                           });
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude() , location.getLongitude()), 15));

                        }
                    }
                });



        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int position = (int)(marker.getTag());
                //Using position get Value from arraylist
                Log.d("id", String.valueOf(position));
                for(JSONObject object : carParkList){
                    try {
                        if(object.getInt("id") == position){
                            Log.d("Address",object.getString("address") );
                            Log.d("CarparkRate" , String.valueOf(object.getDouble("carRate")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

    }


    private void enableMyLocation(GoogleMap map) {
        if (ContextCompat.checkSelfPermission(getContext(),
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





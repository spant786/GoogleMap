package com.example.googlemapproject;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE=1;
    private GoogleMap mMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//       loadLocation();

//        CameraUpdate zoom;
//        zoom = CameraUpdateFactory.zoomTo(20);
//
//        // Add a marker in Sydney and move the camera
//        LatLng Softwarica = new LatLng(27.706416, 85.330044);
//        mMap.addMarker(new MarkerOptions().position(Softwarica).title("Hamro college sabai ko college"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(Softwarica));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
////        mMap.animateCamera(zoom);

        fetchLastLocation();
    }

    public void loadLocation() {
        List<Place> placeList = new ArrayList<>();
        placeList.add(new Place(27.704178, 85.332428, "Maitidevi"));
        placeList.add(new Place(27.706416, 85.330044, "Softwarica"));
        placeList.add(new Place(27.705285, 85.329145, "Dillibazar"));
        placeList.add(new Place(27.705951, 85.320012, "Baagbazaar"));
        placeList.add(new Place(27.703761, 85.331180, "Ghatteykhulo"));
        placeList.add(new Place(27.693608, 85.328925, "Anamnagar"));

        CameraUpdate center, zoom;

        for (Place place : placeList) {
            zoom = CameraUpdateFactory.zoomTo(15);
            center = CameraUpdateFactory.newLatLng(
                    new LatLng(place.getLat(), place.getLon()));
            mMap.addMarker(new MarkerOptions().
                    position(new LatLng(place.getLat(),
                    place.getLon())).title(place.getName()));
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
            mMap.getUiSettings().setZoomControlsEnabled(true);


        }


    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task =
                fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    currentLocation = location;
                    Toast.makeText(MapsActivity.this, currentLocation.getLatitude()
                            +","+ currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    Log.d("myloc:",currentLocation.getLatitude()
                            +","+ currentLocation.getLongitude());

                    LatLng current = new LatLng(currentLocation.getLatitude(),
                            currentLocation.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(current).title("I am Here!")
                            .snippet("marker info")
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                    CameraUpdate zoom;
                    zoom = CameraUpdateFactory.zoomTo(16);
                    mMap.animateCamera(zoom);
                    mMap.getUiSettings().setZoomControlsEnabled(true);


                }
                else {
                    Toast.makeText(MapsActivity.this, "not found",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLastLocation();
            }
            else {
                Toast.makeText(this, "No Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }


    }


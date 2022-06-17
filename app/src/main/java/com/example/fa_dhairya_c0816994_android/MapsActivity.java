package com.example.fa_dhairya_c0816994_android;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,
GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener{

    GoogleMap mMap;
    DatabaseHandler db;

    LatLng Place = new LatLng(53.544388, -113.490929);
    List<Marker> markerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        db = new DatabaseHandler(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);

        markerList = new ArrayList<>();
        List<Place> placeList = db.getAllPlaces();

        for(Place p: placeList){
            String myInfo = "ID: " + p.getId() + " Latitude: "+ p.getPlatitude() + "Longitude"
                    + p.getPlongitude() + " Title: " + p.getTitle();
            Log.d("myInfo", myInfo);

            markerList.add(mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(p.getPlatitude())
                            ,Double.parseDouble(p.getPlongitude()))).title(p.getTitle())
                .zIndex( p.getId() ).snippet("By ME")     ));
        }

        markerList.add(mMap.addMarker(new MarkerOptions()
                .position(Place).title("Edmonton")));

        for(Marker m : markerList){
            // Add a marker in Sydney and move the camera
            LatLng latLng = new LatLng(m.getPosition().latitude, m.getPosition().longitude);
            mMap.addMarker(new MarkerOptions().position(latLng) );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
        }


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
       // Toast.makeText(this,marker.getPosition().toString(),Toast.LENGTH_SHORT).show();
      //  return false;

        Intent intent = new Intent(MapsActivity.this, ShowActivity.class);
        intent.putExtra("latitude" , marker.getPosition().latitude);
        intent.putExtra("longitude" , marker.getPosition().longitude);
        intent.putExtra("title" , marker.getTitle());
        intent.putExtra("id" ,  marker.getZIndex()) ;
        startActivity(intent);
        return false;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
//        mMap.addMarker(new MarkerOptions().position(latLng).title("new Marker")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        Intent intent = new Intent(MapsActivity.this, AddActivity.class);
        intent.putExtra("latitude" , latLng.latitude);
        intent.putExtra("longitude" , latLng.longitude);

        startActivity(intent);
    }
}

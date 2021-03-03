package com.eclairios.CrossComps.Maps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.eclairios.CrossComps.Model.MapModel;
import com.eclairios.CrossComps.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ServiceProviderMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;

    private GoogleMap googleMap;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_maps);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.map_fragment,mapFragment).commit();
        mapFragment.getMapAsync(this);






    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
//        LatLng userLocation = new LatLng(33.6844, 73.0479);
//        map.addMarker(new MarkerOptions().position(userLocation).title("Islamabad"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(userLocation));


        latlngs.add(new LatLng(11.334343, 33.43434));
        latlngs.add(new LatLng(12.334343, 13.43434));
        latlngs.add(new LatLng(13.334343, 10.43434));
        latlngs.add(new LatLng(14.334343, 5.43434));
        latlngs.add(new LatLng(15.334343, 20.43434));
        latlngs.add(new LatLng(16.334343, 25.43434));


        for (LatLng point : latlngs) {
            options.position(point);
            options.title("Service 1");
            options.snippet("service");
            googleMap.addMarker(options);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(point));

        }


    }

}
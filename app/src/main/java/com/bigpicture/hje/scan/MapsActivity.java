package com.bigpicture.hje.scan;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions marker;
    private LatLng testLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        marker = new MarkerOptions();
        testLatLng = new LatLng(37.491, 127.020);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        marker.position(testLatLng).title("Select shop position");
        mMap.addMarker(marker);
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(testLatLng, 15) );

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            public void onMapClick(LatLng point) {
                String text = "latitude ="
                        + point.latitude + ", longitude ="
                        + point.longitude;
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
                        .show();
                mMap.clear();
                marker.position(point).title("Click marker and add shop info.");
                mMap.addMarker(marker);
            }
        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            public boolean onMarkerClick(Marker marker) {
                String text = "[마커 클릭 이벤트] latitude ="
                        + marker.getPosition().latitude + ", longitude ="
                        + marker.getPosition().longitude;
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
                        .show();
                return false;
            }
        });
    }
}

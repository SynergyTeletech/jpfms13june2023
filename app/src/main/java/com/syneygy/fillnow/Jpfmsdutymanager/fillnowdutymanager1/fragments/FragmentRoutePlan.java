package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.PreferenceManager;


public class FragmentRoutePlan extends Fragment implements GoogleMap.OnMarkerClickListener {
    private MapView mMapView;
    private GoogleMap googleMap;
    private String Latitude = "28";
    private Double lat = 0.0;
    private String Longitude = "72.4";
    private Double lon = 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_route_plan, container, false);
        Longitude = PreferenceManager.read("Longitude", "");

        Latitude = PreferenceManager.read("Latitude", "");


        mMapView = view.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);

//                // For dropping a marker at a point on the Map
//                LatLng sydney = new LatLng(-28.5825, 77.3141);
//                googleMap.addMarker(new MarkerOptions().position(sydney).title("Xs Infosol").snippet("Marker Description"));
//
//                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(10).build();
//                //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                LatLng noida = new LatLng(28.5825, 77.3141);
                mMap.addMarker(new MarkerOptions().position(noida).title("XS infosol"));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.5825, 77.3141), 200));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(noida));

                getUserLocation();
                getRoutedraw();
            }
        });
        return view;
    }

    private void getRoutedraw() {
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                lat = Double.valueOf(Latitude);
                lon = Double.valueOf(Longitude);
                String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lon + "&travelmode=driving";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });


    }

    /* method get current user location */
    private void getUserLocation() {
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
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
//    LatLng noida = new LatLng(28.5825, 77.3141);
//mMap.addMarker(new MarkerOptions().position(noida).title("XS infosol"));
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.5825,77.3141),200));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(noida));
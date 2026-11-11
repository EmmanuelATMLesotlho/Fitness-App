package com.example.fitlife.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitlife.R;
import com.example.fitlife.data.WorkoutRepository;
import com.example.fitlife.model.Workout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap gMap) {
        googleMap = gMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        for (Workout w : WorkoutRepository.getInstance().getWorkouts()) {
            if (w.getLatitude() != null && w.getLongitude() != null) {
                LatLng pos = new LatLng(w.getLatitude(), w.getLongitude());
                googleMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title(w.getName()));
            }
        }

        for (Workout w : WorkoutRepository.getInstance().getWorkouts()) {
            if (w.getLatitude() != null && w.getLongitude() != null) {
                LatLng pos = new LatLng(w.getLatitude(), w.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 13f));
                break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) mapView.onLowMemory();
    }
}

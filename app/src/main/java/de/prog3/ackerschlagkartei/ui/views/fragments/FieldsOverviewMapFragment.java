package de.prog3.ackerschlagkartei.ui.views.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.data.models.FieldModel;
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldsOverviewViewModel;
import de.prog3.ackerschlagkartei.ui.views.activities.FieldDetailsActivity;

public class FieldsOverviewMapFragment extends Fragment implements OnMapReadyCallback {
    private FieldsOverviewViewModel fieldViewModel;
    private NavController navController;

    private MapView mapView;
    private GoogleMap googleMap;

    private ProgressBar progressBar;

    private List<FieldModel> currentFieldModels;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fields_overview_map, container, false);

        this.progressBar = v.findViewById(R.id.progressBar2);
        this.progressBar.setVisibility(View.VISIBLE);

        this.mapView = v.findViewById(R.id.mv_fields_overview);
        this.mapView.setVisibility(View.INVISIBLE);

        this.mapView.onCreate(savedInstanceState);
        this.mapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.fieldViewModel = new ViewModelProvider(requireActivity()).get(FieldsOverviewViewModel.class);
        this.navController = Navigation.findNavController(view);

        this.fieldViewModel.getLiveFieldData().observe(getViewLifecycleOwner(), new Observer<List<FieldModel>>() {
            @Override
            public void onChanged(List<FieldModel> fieldModels) {
                currentFieldModels = fieldModels;
                createFieldPolygons();
            }
        });

        this.createFieldPolygons();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mapView.onStart();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }

        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.googleMap.setOnPolygonClickListener(onPolygonClick);

        this.createFieldPolygons();
    }

    private void createFieldPolygons() {
        if (googleMap == null || currentFieldModels == null || currentFieldModels.isEmpty()) {
            return;
        }

        googleMap.clear();
        LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();

        for (FieldModel field : currentFieldModels) {

            List<LatLng> latLngs = new ArrayList<>();

            for (GeoPoint point : field.getInfo().getPositions()) {
                if (!field.getInfo().getPositions().isEmpty()) {
                    LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
                    latLngs.add(latLng);
                    latLngBounds.include(latLng);
                }
            }

            Polygon polygon = googleMap.addPolygon(new PolygonOptions()
                    .addAll(latLngs)
                    .fillColor(R.color.field_polygon)
                    .clickable(true)
                    .strokeWidth(2));

            polygon.setTag(field);
        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 50));

        this.mapView.setVisibility(View.VISIBLE);
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    private final GoogleMap.OnPolygonClickListener onPolygonClick = new GoogleMap.OnPolygonClickListener() {
        @Override
        public void onPolygonClick(@NonNull Polygon polygon) {
            FieldModel fieldModel = (FieldModel) polygon.getTag();
            Intent i = new Intent(getContext(), FieldDetailsActivity.class);
            i.putExtra("fieldModelUid", fieldModel.getUid());
            startActivity(i);
        }
    };

    @Override
    public void onResume() {
        this.mapView.onResume();
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        this.mapView.onLowMemory();
    }
}
package de.prog3.ackerschlagkartei.ui.views.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldDetailsViewModel;
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldsMapViewModel;

public class FieldsMapFragment extends Fragment implements OnMapReadyCallback {
    private FieldsMapViewModel fieldsMapViewModel;
    private FieldDetailsViewModel fieldDetailsViewModel;
    private NavController navController;
    private MapView mapView;
    private GoogleMap googleMap;
    private List<FieldModel> currentFieldModels;

    public FieldsMapFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fields_map, container, false);

        this.mapView = view.findViewById(R.id.mv_fields_overview);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.fieldsMapViewModel = new ViewModelProvider(this).get(FieldsMapViewModel.class);
        this.fieldDetailsViewModel = new ViewModelProvider(this).get(FieldDetailsViewModel.class);
        this.navController = Navigation.findNavController(view);

        this.mapView.setVisibility(View.INVISIBLE);
        this.mapView.onCreate(savedInstanceState);
        this.mapView.getMapAsync(this);

        this.fieldsMapViewModel.getFieldListData().observe(getViewLifecycleOwner(), new Observer<List<FieldModel>>() {
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fields_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_add_field){
            navController.navigate(R.id.action_fieldMapFragment_to_fieldAddFragment);
        }

        if(id == R.id.menu_change_view){
            navController.navigate(R.id.action_fieldMapFragment_to_fieldListFragment);
        }

        if(id == R.id.menu_sign_out){
            navController.navigate(R.id.signInFragment);
            this.fieldsMapViewModel.logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);

        if(ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    }

    private final GoogleMap.OnPolygonClickListener onPolygonClick = new GoogleMap.OnPolygonClickListener() {
        @Override
        public void onPolygonClick(@NonNull Polygon polygon) {
            fieldDetailsViewModel.setSelectedFieldModel((FieldModel)polygon.getTag());
            navController.navigate(R.id.action_fieldsMapFragment_to_fieldDetailsFragment);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        this.mapView.onResume();
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
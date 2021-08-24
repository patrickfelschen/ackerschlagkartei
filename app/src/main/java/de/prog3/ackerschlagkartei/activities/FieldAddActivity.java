package de.prog3.ackerschlagkartei.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.models.FieldModel;
import de.prog3.ackerschlagkartei.viewmodels.FieldAddViewModel;

public class FieldAddActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FieldAddViewModel fieldAddViewModel;

    private Toolbar fieldAddToolbar;

    private MapView mapView;
    private GoogleMap googleMap;
    private EditText etDescription;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private final List<GeoPoint> fieldPositions = new ArrayList<>();
    private final List<LatLng> fieldLatLngs = new ArrayList<>();

    private Polygon polygon;
    private double area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_add);

        this.fieldAddViewModel = new ViewModelProvider(this).get(FieldAddViewModel.class);

        this.fieldAddToolbar = findViewById(R.id.field_add_toolbar);
        this.etDescription = findViewById(R.id.field_add_description);

        setSupportActionBar(fieldAddToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.mapView = findViewById(R.id.mv_field_add);
        this.mapView.onCreate(savedInstanceState);
        this.mapView.getMapAsync(this);
    }

    private final GoogleMap.OnMapClickListener onMapClick = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(@NonNull LatLng latLng) {
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Marker");
            googleMap.addMarker(markerOptions);
            fieldLatLngs.add(latLng);
            fieldPositions.add(new GeoPoint(latLng.latitude, latLng.longitude));
        }
    };

    private final GoogleMap.OnMapLongClickListener onMapLongClick = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(@NonNull LatLng latLng) {
            if (!fieldLatLngs.isEmpty()) {

                polygon = googleMap.addPolygon(new PolygonOptions()
                        .addAll(fieldLatLngs)
                        .fillColor(R.color.field_polygon)
                        .strokeWidth(2));
            }
        }
    };

    private void resetAll() {
        etDescription.getText().clear();
        googleMap.clear();
        if (polygon != null) {
            polygon.remove();
        }
        fieldPositions.clear();
        fieldLatLngs.clear();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_fiel_menu_confirm) {
            onAddConfirmClick();
            return true;
        }

        if (item.getItemId() == R.id.add_fiel_menu_reset) {
            resetAll();
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.field_add_menu, menu);
        return true;
    }

    private void onAddConfirmClick() {
        addFieldData();
    }

    private void addFieldData() {
        String fieldDescription = etDescription.getText().toString();

        if (TextUtils.isEmpty(fieldDescription)) {
            etDescription.setError("Description cannot be empty");
            etDescription.requestFocus();
            return;
        }

        if (fieldPositions.isEmpty()) {
            etDescription.setError("Positions cannot be empty");
            etDescription.requestFocus();
            return;
        }

        double area = SphericalUtil.computeArea(fieldLatLngs) / 10000;

        FieldModel newField = new FieldModel(fieldDescription, this.fieldPositions, area);

        this.fieldAddViewModel.createFieldModel(newField);
        finish();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }

        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        this.googleMap.setOnMapClickListener(onMapClick);
        this.googleMap.setOnMapLongClickListener(onMapLongClick);
    }

    @Override
    public void onResume() {
        this.mapView.onResume();
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mapView.onStart();
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
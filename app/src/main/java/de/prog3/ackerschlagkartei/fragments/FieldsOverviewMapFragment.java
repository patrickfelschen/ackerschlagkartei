package de.prog3.ackerschlagkartei.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.activities.FieldDetailsActivity;
import de.prog3.ackerschlagkartei.models.FieldModel;

public class FieldsOverviewMapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private ListenerRegistration fieldListener;

    LatLngBounds.Builder latLngBounds;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fields_overview_map, container, false);

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();

        this.mapView = v.findViewById(R.id.mv_fields_overview);
        this.mapView.onCreate(savedInstanceState);

        this.mapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }

        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.googleMap.setOnPolygonClickListener(onPolygonClick);

        this.loadFields();
    }

    private void loadFields() {
        String firebaseUserUid = firebaseAuth.getUid();
        final CollectionReference ref = firebaseFirestore.collection("Users").document(firebaseUserUid).collection("Fields");

        fieldListener = ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("Fielddata", "Listen failed!");
                    return;
                }

                List<FieldModel> fieldModels = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    fieldModels.add(doc.toObject(FieldModel.class));
                }
                Log.d("Fielddata", fieldModels.toString());

                createFieldPolygons(fieldModels);
            }
        });

    }

    private void createFieldPolygons(List<FieldModel> fieldModels) {

        googleMap.clear();
        latLngBounds = new LatLngBounds.Builder();

        for (FieldModel field : fieldModels) {

            List<LatLng> latLngs = new ArrayList<>();

            for (GeoPoint point : field.getInfo().getPositions()) {
                LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
                latLngs.add(latLng);
                latLngBounds.include(latLng);
            }

            Polygon polygon = googleMap.addPolygon(new PolygonOptions()
                    .addAll(latLngs)
                    .fillColor(R.color.field_polygon)
                    .clickable(true)
                    .strokeWidth(2));

            polygon.setTag(field);
        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 50));

    }

    private final GoogleMap.OnPolygonClickListener onPolygonClick = new GoogleMap.OnPolygonClickListener() {
        @Override
        public void onPolygonClick(@NonNull Polygon polygon) {
            Intent i = new Intent(getActivity(), FieldDetailsActivity.class);
            //TODO: put field id
            startActivity(i);
        }
    };

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
    public void onSaveInstanceState(Bundle outState) {
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
package de.prog3.ackerschlagkartei.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.models.FieldModel;
import de.prog3.ackerschlagkartei.viewmodels.FieldDetailsViewModel;

public class FieldInfoFragment extends Fragment implements OnMapReadyCallback {

    private FieldDetailsViewModel fieldDetailsViewModel;
    private MapView mapView;
    private GoogleMap googleMap;
    private Button deleteFieldButton;

    private EditText etFieldInfoDescription;
    private EditText etFieldInfoArea;
    private CheckBox cbWpa;
    private CheckBox cbRedArea;
    private CheckBox cbPsa;

    private Map<String, Object> infoChanges;

    private FieldModel currentFieldModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_field_info, container, false);

        this.infoChanges = new HashMap<>();

        this.mapView = v.findViewById(R.id.mv_field_info);
        this.mapView.onCreate(savedInstanceState);
        this.deleteFieldButton = v.findViewById(R.id.btn_delete_field);
        this.deleteFieldButton.setOnClickListener(deleteFieldButtonClick);

        this.etFieldInfoDescription = v.findViewById(R.id.et_description);
        this.etFieldInfoArea = v.findViewById(R.id.et_area);
        this.cbWpa = v.findViewById(R.id.cb_water_protection_area);
        this.cbRedArea = v.findViewById(R.id.cb_red_area);
        this.cbPsa = v.findViewById(R.id.cb_phosphate_sensitive_area);

        this.mapView.getMapAsync(this);

        return v;
    }

    private final View.OnClickListener deleteFieldButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    public void onStart() {
        super.onStart();

        this.fieldDetailsViewModel = new ViewModelProvider(requireActivity()).get(FieldDetailsViewModel.class);
        this.fieldDetailsViewModel.getFieldModelMutableLiveData().observe(getViewLifecycleOwner(), fieldData -> {
            currentFieldModel = fieldData;

            etFieldInfoDescription.setText(fieldData.getInfo().getDescription());
            etFieldInfoArea.setText(String.valueOf(fieldData.getInfo().getArea()));

            cbWpa.setChecked(fieldData.getInfo().isWaterProtectionArea());
            cbRedArea.setChecked(fieldData.getInfo().isRedArea());
            cbPsa.setChecked(fieldData.getInfo().isPhosphateSensitiveArea());

            infoChanges.put("area", fieldData.getInfo().getArea());
            infoChanges.put("description", fieldData.getInfo().getDescription());
            infoChanges.put("waterProtectionArea", fieldData.getInfo().isWaterProtectionArea());
            infoChanges.put("redArea", fieldData.getInfo().isRedArea());
            infoChanges.put("phosphateSensitiveArea", fieldData.getInfo().isPhosphateSensitiveArea());

            this.createFieldPolygon();
        });
    }

    private void createFieldPolygon() {
        if (googleMap == null || currentFieldModel == null) {
            return;
        }

        googleMap.clear();
        LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();

        List<LatLng> latLngs = new ArrayList<>();

        for (GeoPoint point : currentFieldModel.getInfo().getPositions()) {
            LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
            latLngs.add(latLng);
            latLngBounds.include(latLng);
        }

        Polygon polygon = googleMap.addPolygon(new PolygonOptions()
                .addAll(latLngs)
                .fillColor(R.color.field_polygon)
                .clickable(true)
                .strokeWidth(2));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 50));

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        this.createFieldPolygon();
    }

    @Override
    public void onResume() {
        this.mapView.onResume();
        super.onResume();
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
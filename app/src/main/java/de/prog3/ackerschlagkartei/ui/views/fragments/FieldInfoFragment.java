package de.prog3.ackerschlagkartei.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.data.models.FieldModel;
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldDetailsViewModel;

public class FieldInfoFragment extends Fragment implements OnMapReadyCallback {

    private FieldDetailsViewModel fieldDetailsViewModel;
    private MapView mapView;
    private GoogleMap googleMap;
    private Button deleteFieldButton;

    private EditText etFieldInfoDescription;
    private EditText etFieldInfoArea;
    private CheckBox cbWaterProtectionArea;
    private CheckBox cbRedArea;
    private CheckBox cbPhosphateSensitiveArea;

    private FieldModel currentFieldModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_field_info, container, false);

        this.mapView = v.findViewById(R.id.mv_field_info);
        this.mapView.onCreate(savedInstanceState);
        this.deleteFieldButton = v.findViewById(R.id.btn_delete_field);
        this.deleteFieldButton.setOnClickListener(deleteFieldButtonClick);

        this.etFieldInfoDescription = v.findViewById(R.id.et_description);
        this.etFieldInfoArea = v.findViewById(R.id.et_area);
        this.cbWaterProtectionArea = v.findViewById(R.id.cb_water_protection_area);
        this.cbRedArea = v.findViewById(R.id.cb_red_area);
        this.cbPhosphateSensitiveArea = v.findViewById(R.id.cb_phosphate_sensitive_area);

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
        this.fieldDetailsViewModel.getFieldModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FieldModel>() {
            @Override
            public void onChanged(FieldModel fieldModel) {
                currentFieldModel = fieldModel;
                etFieldInfoDescription.setText(fieldModel.getInfo().getDescription());
                etFieldInfoArea.setText(String.valueOf(fieldModel.getInfo().getArea()));
                cbWaterProtectionArea.setChecked(fieldModel.getInfo().isWaterProtectionArea());
                cbRedArea.setChecked(fieldModel.getInfo().isRedArea());
                cbPhosphateSensitiveArea.setChecked(fieldModel.getInfo().isPhosphateSensitiveArea());

                createFieldPolygon();
            }
        });

        this.etFieldInfoDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String changes = etFieldInfoDescription.getText().toString();
                    fieldDetailsViewModel.updateField("info.description", changes);
                    v.clearFocus();
                    etFieldInfoDescription.clearFocus();
                }
            }
        });

        this.etFieldInfoArea.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    double changes = Double.parseDouble(etFieldInfoArea.getText().toString());
                    fieldDetailsViewModel.updateField("info.area", changes);
                    v.clearFocus();
                    etFieldInfoArea.clearFocus();
                }
            }
        });

        this.cbWaterProtectionArea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fieldDetailsViewModel.updateField("info.waterProtectionArea", isChecked);
                cbWaterProtectionArea.clearFocus();
            }
        });

        this.cbRedArea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fieldDetailsViewModel.updateField("info.redArea", isChecked);
                cbRedArea.clearFocus();
            }
        });

        this.cbPhosphateSensitiveArea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fieldDetailsViewModel.updateField("info.phosphateSensitiveArea", isChecked);
                cbPhosphateSensitiveArea.clearFocus();
            }
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
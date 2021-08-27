package de.prog3.ackerschlagkartei.ui.views.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldInfoViewModel;
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldsMapViewModel;

public class FieldInfoFragment extends Fragment implements OnMapReadyCallback {
    private FieldsMapViewModel fieldsMapViewModel;
    private FieldInfoViewModel fieldInfoViewModel;

    private NavController navController;

    private MapView mapView;
    private GoogleMap googleMap;
    private Button deleteFieldButton;

    private EditText etFieldInfoDescription;
    private EditText etFieldInfoArea;
    private CheckBox cbWaterProtectionArea;
    private CheckBox cbRedArea;
    private CheckBox cbPhosphateSensitiveArea;

    private FieldModel selectedFieldModel;

    public FieldInfoFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_field_info, container, false);

        this.mapView = view.findViewById(R.id.mv_field_info);
        this.mapView.onCreate(savedInstanceState);
        this.deleteFieldButton = view.findViewById(R.id.btn_delete_field);
        this.etFieldInfoDescription = view.findViewById(R.id.et_description);
        this.etFieldInfoArea = view.findViewById(R.id.et_area);
        this.cbWaterProtectionArea = view.findViewById(R.id.cb_water_protection_area);
        this.cbRedArea = view.findViewById(R.id.cb_red_area);
        this.cbPhosphateSensitiveArea = view.findViewById(R.id.cb_phosphate_sensitive_area);

        //this.deleteFieldButton.setOnClickListener(deleteFieldButtonClick);

        this.mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.fieldsMapViewModel = new ViewModelProvider(requireActivity()).get(FieldsMapViewModel.class);
        this.fieldInfoViewModel = new ViewModelProvider(requireActivity()).get(FieldInfoViewModel.class);

        this.selectedFieldModel = this.fieldsMapViewModel.getSelectedFieldModel();

        this.deleteFieldButton.setOnClickListener(deleteFieldButtonClick);

        this.fieldInfoViewModel.getFieldModelMutableLiveData(this.selectedFieldModel).observe(getViewLifecycleOwner(), new Observer<FieldModel>() {
            @Override
            public void onChanged(FieldModel fieldModel) {
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
                    fieldInfoViewModel.updateField(selectedFieldModel,"info.description", changes);
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
                    fieldInfoViewModel.updateField(selectedFieldModel,"info.area", changes);
                    v.clearFocus();
                    etFieldInfoArea.clearFocus();
                }
            }
        });

        this.cbWaterProtectionArea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fieldInfoViewModel.updateField(selectedFieldModel,"info.waterProtectionArea", isChecked);
                cbWaterProtectionArea.clearFocus();
            }
        });

        this.cbRedArea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fieldInfoViewModel.updateField(selectedFieldModel,"info.redArea", isChecked);
                cbRedArea.clearFocus();
            }
        });

        this.cbPhosphateSensitiveArea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fieldInfoViewModel.updateField(selectedFieldModel,"info.phosphateSensitiveArea", isChecked);
                cbPhosphateSensitiveArea.clearFocus();
            }
        });

        this.navController = Navigation.findNavController(view);

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

        this.createFieldPolygon();
    }

    private void createFieldPolygon() {
        if (googleMap == null || this.selectedFieldModel == null) {
            return;
        }

        googleMap.clear();
        LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();

        List<LatLng> latLngs = new ArrayList<>();

        for (GeoPoint point : selectedFieldModel.getInfo().getPositions()) {
            LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
            latLngs.add(latLng);
            latLngBounds.include(latLng);
        }

        Polygon polygon = googleMap.addPolygon(new PolygonOptions()
                .addAll(latLngs)
                .fillColor(ContextCompat.getColor(requireActivity(), R.color.field_polygon))
                .clickable(true)
                .strokeWidth(2));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 50));

    }

    private final View.OnClickListener deleteFieldButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setTitle(getString(R.string.confirm_delete));
            builder.setPositiveButton(getString(R.string.confirm_delete_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    fieldInfoViewModel.deleteFieldModel(selectedFieldModel);
                    navController.navigate(R.id.action_fieldInfoFragment_to_mainActivity);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(getString(R.string.confirm_delete_no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
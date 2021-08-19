package de.prog3.ackerschlagkartei.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.viewmodels.FieldDetailsViewModel;

public class FieldInfoFragment extends Fragment implements OnMapReadyCallback {

    private FieldDetailsViewModel fieldDetailsViewModel;
    private MapView mapView;
    private GoogleMap googleMap;
    private Button deleteFieldButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_field_info, container, false);

        this.fieldDetailsViewModel = new ViewModelProvider(requireActivity()).get(FieldDetailsViewModel.class);
        this.fieldDetailsViewModel.getFieldModelMutableLiveData().observe(getViewLifecycleOwner(), fieldData -> {
            Toast.makeText(getContext(), fieldData + " " , Toast.LENGTH_SHORT).show();
            Log.d("FieldUid", fieldData.getUid());
        });

        this.mapView = v.findViewById(R.id.mv_field_info);
        this.mapView.onCreate(savedInstanceState);
        this.deleteFieldButton = v.findViewById(R.id.btn_delete_field);
        this.deleteFieldButton.setOnClickListener(deleteFieldButtonClick);

        this.mapView.getMapAsync(this);

        return v;
    }

    private final View.OnClickListener deleteFieldButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(52.52143, 7.31845)));
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
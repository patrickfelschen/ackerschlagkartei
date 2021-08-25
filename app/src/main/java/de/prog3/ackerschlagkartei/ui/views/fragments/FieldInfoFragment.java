package de.prog3.ackerschlagkartei.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldDetailsViewModel;

public class FieldInfoFragment extends Fragment implements OnMapReadyCallback {
    private FieldDetailsViewModel fieldDetailsViewModel;
    private NavController navController;

    private MapView mapView;
    private GoogleMap googleMap;
    private Button deleteFieldButton;

    private EditText etFieldInfoDescription;
    private EditText etFieldInfoArea;
    private CheckBox cbWaterProtectionArea;
    private CheckBox cbRedArea;
    private CheckBox cbPhosphateSensitiveArea;

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

        this.fieldDetailsViewModel = new ViewModelProvider(requireActivity()).get(FieldDetailsViewModel.class);
        this.navController = Navigation.findNavController(view);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }


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
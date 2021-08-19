package de.prog3.ackerschlagkartei.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.viewmodels.FieldDetailsViewModel;

public class FieldCultivationFragment extends Fragment {

    private FieldDetailsViewModel fieldDetailsViewModel;
    private AutoCompleteTextView ddPreviousCrop;
    private AutoCompleteTextView ddPrimaryCrop;
    private AutoCompleteTextView ddSecondaryCrop;
    private AutoCompleteTextView ddZwfGroup;
    private AutoCompleteTextView ddZwfCulture;

    public FieldCultivationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.fieldDetailsViewModel = new ViewModelProvider(requireActivity()).get(FieldDetailsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_field_cultivation, container, false);

        this.ddPreviousCrop = view.findViewById(R.id.dd_previous_crop);
        this.ddPrimaryCrop = view.findViewById(R.id.dd_primary_crop);
        this.ddSecondaryCrop = view.findViewById(R.id.dd_secondary_crop);
        this.ddZwfGroup = view.findViewById(R.id.dd_zwf_group);
        this.ddZwfCulture = view.findViewById(R.id.dd_zwf_culture);

        return view;
    }

}
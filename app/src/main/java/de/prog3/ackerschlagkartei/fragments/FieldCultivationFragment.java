package de.prog3.ackerschlagkartei.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.Map;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.viewmodels.FieldDetailsViewModel;

public class FieldCultivationFragment extends Fragment {

    private FieldDetailsViewModel fieldDetailsViewModel;
    private AutoCompleteTextView ddPreviousCrop;
    private AutoCompleteTextView ddPrimaryCrop;
    private AutoCompleteTextView ddSecondaryCrop;
    private AutoCompleteTextView ddZwfGroup;
    private AutoCompleteTextView ddNextCrop;

    private ArrayAdapter<CharSequence> cropAdapter;
    private ArrayAdapter<CharSequence> secondaryCropAdapter;
    private ArrayAdapter<CharSequence> zwfGroupAdapter;

    private Map<String, Object> cultivationChanges;

    public FieldCultivationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_field_cultivation, container, false);

        this.cultivationChanges = new HashMap<>();

        this.cropAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.previous_primary_next_crop_array,
                R.layout.support_simple_spinner_dropdown_item);

        this.secondaryCropAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.secondary_crop_array,
                R.layout.support_simple_spinner_dropdown_item);

        this.zwfGroupAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.zwf_group_array,
                R.layout.support_simple_spinner_dropdown_item);

        this.ddPreviousCrop = view.findViewById(R.id.dd_previous_crop);
        this.ddPrimaryCrop = view.findViewById(R.id.dd_primary_crop);
        this.ddSecondaryCrop = view.findViewById(R.id.dd_secondary_crop);
        this.ddNextCrop = view.findViewById(R.id.dd_next_crop);
        this.ddZwfGroup = view.findViewById(R.id.dd_zwf_group);

        this.ddPreviousCrop.setAdapter(this.cropAdapter);
        this.ddPrimaryCrop.setAdapter(this.cropAdapter);
        this.ddSecondaryCrop.setAdapter(this.secondaryCropAdapter);
        this.ddNextCrop.setAdapter(this.cropAdapter);
        this.ddZwfGroup.setAdapter(this.zwfGroupAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        this.fieldDetailsViewModel = new ViewModelProvider(requireActivity()).get(FieldDetailsViewModel.class);
        this.fieldDetailsViewModel.getFieldModelMutableLiveData().observe(getViewLifecycleOwner(), fieldData -> {
            ddPreviousCrop.setText(fieldData.getCultivation().getPreviousCrop(), false);
            ddPrimaryCrop.setText(fieldData.getCultivation().getPrimaryCrop(), false);
            ddSecondaryCrop.setText(fieldData.getCultivation().getSecondaryCrop(), false);
            ddZwfGroup.setText(fieldData.getCultivation().getZwfGroup(), false);
            ddNextCrop.setText(fieldData.getCultivation().getNextCrop(), false);

            cultivationChanges.put("previousCrop", fieldData.getCultivation().getPreviousCrop());
            cultivationChanges.put("primaryCrop", fieldData.getCultivation().getPrimaryCrop());
            cultivationChanges.put("secondaryCrop", fieldData.getCultivation().getSecondaryCrop());
            cultivationChanges.put("zwfGroup", fieldData.getCultivation().getZwfGroup());
            cultivationChanges.put("nextCrop", fieldData.getCultivation().getNextCrop());
        });

        this.ddPreviousCrop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String changes = cropAdapter.getItem(position).toString();

                cultivationChanges.put("previousCrop", changes);

                fieldDetailsViewModel.updateCultivation(cultivationChanges);

                view.clearFocus();
                ddPreviousCrop.clearFocus();
            }
        });

        this.ddPrimaryCrop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String changes = cropAdapter.getItem(position).toString();

                cultivationChanges.put("primaryCrop", changes);

                fieldDetailsViewModel.updateCultivation(cultivationChanges);
                view.clearFocus();
                ddPrimaryCrop.clearFocus();
            }
        });

        this.ddSecondaryCrop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String changes = secondaryCropAdapter.getItem(position).toString();

                cultivationChanges.put("secondaryCrop", changes);

                fieldDetailsViewModel.updateCultivation(cultivationChanges);
                view.clearFocus();
                ddSecondaryCrop.clearFocus();
            }
        });

        this.ddZwfGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String changes = zwfGroupAdapter.getItem(position).toString();

                cultivationChanges.put("zwfGroup", changes);

                fieldDetailsViewModel.updateCultivation(cultivationChanges);
                view.clearFocus();
                ddZwfGroup.clearFocus();
            }
        });

        this.ddNextCrop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String changes = cropAdapter.getItem(position).toString();

                cultivationChanges.put("nextCrop", changes);

                fieldDetailsViewModel.updateCultivation(cultivationChanges);
                view.clearFocus();
                ddNextCrop.clearFocus();
            }
        });


    }


}
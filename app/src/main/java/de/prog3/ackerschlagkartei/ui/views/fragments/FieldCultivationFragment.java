package de.prog3.ackerschlagkartei.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.data.models.FieldModel;
import de.prog3.ackerschlagkartei.data.models.WeatherModel;
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldDetailsViewModel;

public class FieldCultivationFragment extends Fragment {

    private FieldDetailsViewModel fieldDetailsViewModel;
    private AutoCompleteTextView ddPreviousCrop;
    private AutoCompleteTextView ddPrimaryCrop;
    private AutoCompleteTextView ddSecondaryCrop;
    private AutoCompleteTextView ddZwfGroup;
    private AutoCompleteTextView ddNextCrop;

    private TextView tvTemp;

    private ArrayAdapter<CharSequence> cropAdapter;
    private ArrayAdapter<CharSequence> secondaryCropAdapter;
    private ArrayAdapter<CharSequence> zwfGroupAdapter;

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
        this.tvTemp = view.findViewById(R.id.tv_temp);

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
        this.fieldDetailsViewModel.getFieldModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FieldModel>() {
            @Override
            public void onChanged(FieldModel fieldModel) {
                ddPreviousCrop.setText(fieldModel.getCultivation().getPreviousCrop(), false);
                ddPrimaryCrop.setText(fieldModel.getCultivation().getPrimaryCrop(), false);
                ddSecondaryCrop.setText(fieldModel.getCultivation().getSecondaryCrop(), false);
                ddZwfGroup.setText(fieldModel.getCultivation().getZwfGroup(), false);
                ddNextCrop.setText(fieldModel.getCultivation().getNextCrop(), false);

                fieldDetailsViewModel.loadWeather(fieldModel);
            }
        });

        this.fieldDetailsViewModel.getWeatherMutableLiveData().observe(getViewLifecycleOwner(), new Observer<WeatherModel>() {
            @Override
            public void onChanged(WeatherModel weatherModel) {
                tvTemp.setText(weatherModel.toString());
            }
        });

        this.ddPreviousCrop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String changes = cropAdapter.getItem(position).toString();

                fieldDetailsViewModel.updateField("cultivation.previousCrop", changes);

                view.clearFocus();
                ddPreviousCrop.clearFocus();
            }
        });

        this.ddPrimaryCrop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String changes = cropAdapter.getItem(position).toString();

                fieldDetailsViewModel.updateField("cultivation.primaryCrop", changes);
                view.clearFocus();
                ddPrimaryCrop.clearFocus();
            }
        });

        this.ddSecondaryCrop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String changes = secondaryCropAdapter.getItem(position).toString();

                fieldDetailsViewModel.updateField("cultivation.secondaryCrop", changes);
                view.clearFocus();
                ddSecondaryCrop.clearFocus();
            }
        });

        this.ddZwfGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String changes = zwfGroupAdapter.getItem(position).toString();

                fieldDetailsViewModel.updateField("cultivation.zwfGroup", changes);
                view.clearFocus();
                ddZwfGroup.clearFocus();
            }
        });

        this.ddNextCrop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String changes = cropAdapter.getItem(position).toString();

                fieldDetailsViewModel.updateField("cultivation.nextCrop", changes);
                view.clearFocus();
                ddNextCrop.clearFocus();
            }
        });


    }


}
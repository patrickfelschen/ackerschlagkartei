package de.prog3.ackerschlagkartei.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.viewmodels.FieldDetailsViewModel;


public class FieldImagesFragment extends Fragment {
    private FieldDetailsViewModel fieldDetailsViewModel;

    public FieldImagesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_field_images, container, false);

        this.fieldDetailsViewModel = new ViewModelProvider(requireActivity()).get(FieldDetailsViewModel.class);
        this.fieldDetailsViewModel.getFieldModelMutableLiveData().observe(getViewLifecycleOwner(), fieldData -> {
            Toast.makeText(getContext(), fieldData + " " , Toast.LENGTH_SHORT).show();
            Log.d("FieldUid", fieldData.getUid());
        });

        return view;
    }
}
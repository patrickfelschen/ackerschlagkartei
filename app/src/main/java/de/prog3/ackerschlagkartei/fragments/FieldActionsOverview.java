package de.prog3.ackerschlagkartei.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.viewmodels.FieldDetailsViewModel;

public class FieldActionsOverview extends Fragment {
    private FieldDetailsViewModel fieldDetailsViewModel;
    private TextView fieldAction;

    public FieldActionsOverview() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_field_actions_overview, container, false);

        this.fieldDetailsViewModel = new ViewModelProvider(requireActivity()).get(FieldDetailsViewModel.class);
        Toast.makeText(getContext(), fieldDetailsViewModel.getActionCategory().toString(), Toast.LENGTH_SHORT).show();

        this.fieldAction = view.findViewById(R.id.field_action);
        this.fieldAction.setText(fieldDetailsViewModel.getActionCategory().getValue());


        return view;
    }
}
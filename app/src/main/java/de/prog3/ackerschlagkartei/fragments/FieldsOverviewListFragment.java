package de.prog3.ackerschlagkartei.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.activities.FieldDetailsActivity;

public class FieldsOverviewListFragment extends Fragment {

    public FieldsOverviewListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fields_overview_list, container, false);

        Button btnOpenDetails = view.findViewById(R.id.btn_open_details);
        btnOpenDetails.setOnClickListener(btnOpenDetailsClick);

        return view;
    }

    private final View.OnClickListener btnOpenDetailsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(), FieldDetailsActivity.class);
            startActivity(i);
        }
    };

}
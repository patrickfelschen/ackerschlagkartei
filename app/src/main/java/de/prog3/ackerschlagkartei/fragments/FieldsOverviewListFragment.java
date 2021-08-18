package de.prog3.ackerschlagkartei.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.adapters.FieldsOverviewListAdapter;
import de.prog3.ackerschlagkartei.viewmodels.FieldsOverviewViewModel;

public class FieldsOverviewListFragment extends Fragment {
    private FieldsOverviewViewModel fieldViewModel;
    private FieldsOverviewListAdapter fieldListAdapter;
    private RecyclerView fieldListRecyclerView;

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

        this.fieldViewModel = new ViewModelProvider(this).get(FieldsOverviewViewModel.class);
        this.fieldListRecyclerView = view.findViewById(R.id.rv_field_overview_list);
        this.fieldListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.fieldListRecyclerView.setHasFixedSize(true);

        fieldViewModel.getLiveFieldData().observe(getViewLifecycleOwner(), fieldList->{
            fieldListAdapter = new FieldsOverviewListAdapter(fieldList);
            fieldListRecyclerView.setAdapter(fieldListAdapter);
            fieldListAdapter.notifyDataSetChanged();
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
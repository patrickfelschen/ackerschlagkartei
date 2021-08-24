package de.prog3.ackerschlagkartei.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.data.models.FieldModel;
import de.prog3.ackerschlagkartei.ui.adapters.FieldsOverviewListAdapter;
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldsOverviewViewModel;

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

        this.fieldViewModel = new ViewModelProvider(this).get(FieldsOverviewViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fields_overview_list, container, false);

        this.fieldListRecyclerView = view.findViewById(R.id.rv_field_overview_list);
        this.fieldListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.fieldListRecyclerView.setHasFixedSize(true);

        this.fieldListAdapter = new FieldsOverviewListAdapter();
        fieldListRecyclerView.setAdapter(fieldListAdapter);
        this.fieldViewModel.getLiveFieldData().observe(getActivity(), new Observer<List<FieldModel>>() {
            @Override
            public void onChanged(List<FieldModel> fieldModels) {
                fieldListAdapter.setFieldModelList(fieldModels);
                fieldListAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
package de.prog3.ackerschlagkartei.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.data.models.ActionModel;
import de.prog3.ackerschlagkartei.ui.adapters.FieldActionsAdapter;
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldDetailsViewModel;

public class FieldActionsOverview extends Fragment {
    private FieldDetailsViewModel fieldDetailsViewModel;
    private ListView fieldActionList;
    private List<ActionModel> fieldActions;
    private FieldActionsAdapter arrayAdapter;
    private String selectedCategory;

    public FieldActionsOverview() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_field_actions_overview, container, false);

        this.fieldActions = new ArrayList<>();
        this.fieldActionList = view.findViewById(R.id.actions_list);
        this.arrayAdapter = new FieldActionsAdapter(requireActivity(), fieldActions);

        this.fieldDetailsViewModel = new ViewModelProvider(requireActivity()).get(FieldDetailsViewModel.class);

        this.selectedCategory = fieldDetailsViewModel.getActionCategory().getValue();

        this.fieldDetailsViewModel.getActions().observe(requireActivity(), new Observer<List<ActionModel>>() {
            @Override
            public void onChanged(List<ActionModel> actionModels) {
                fieldActions.clear();
                if(actionModels != null) {
                    fieldActions.addAll(actionModels);
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

        if(selectedCategory.equals(getString(R.string.soil_cultivation))) {
            arrayAdapter.setIcon(R.drawable.ic_baseline_fence_24);
        }else if(selectedCategory.equals(getString(R.string.sowing))) {
            arrayAdapter.setIcon(R.drawable.ic_baseline_local_florist_24);
        }else if(selectedCategory.equals(getString(R.string.fertilization))) {
            arrayAdapter.setIcon(R.drawable.ic_baseline_scatter_plot_24);
        }else if(selectedCategory.equals(getString(R.string.plant_protection))) {
            arrayAdapter.setIcon(R.drawable.ic_baseline_bug_report_42);
        }else if(selectedCategory.equals(getString(R.string.harvest))) {
            arrayAdapter.setIcon(R.drawable.ic_baseline_filter_vintage_24);
        }

        fieldActionList.setAdapter(arrayAdapter);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.field_action_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.field_action_menu_add) {
            AddActionDialog popup = new AddActionDialog();
            popup.show(getActivity().getSupportFragmentManager(), "AddAction");
        }
        return super.onOptionsItemSelected(item);
    }
}
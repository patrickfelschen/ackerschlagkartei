package de.prog3.ackerschlagkartei.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.adapters.FieldActionsAdapter;
import de.prog3.ackerschlagkartei.viewmodels.FieldDetailsViewModel;

public class FieldActionsFragment extends Fragment {

    private FieldDetailsViewModel fieldDetailsViewModel;
    private ListView actionsListView;
    private List<String> actionList;
    private HashMap<String, List<String>> action;
    private FieldActionsAdapter adapter;
    private Integer[] listIcons;

    public FieldActionsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void initListData() {
        this.actionList.add(getString(R.string.soil_cultivation));
        this.actionList.add(getString(R.string.sowing));
        this.actionList.add(getString(R.string.fertilization));
        this.actionList.add(getString(R.string.plant_protection));
        this.actionList.add(getString(R.string.harvest));


        String[] array;

        List<String> soilCultivationActions = new ArrayList<>();
        array = getResources().getStringArray(R.array.soil_cultivation);
        soilCultivationActions.addAll(Arrays.asList(array));

        List<String> sowingActions = new ArrayList<>();
        array = getResources().getStringArray(R.array.sowing);
        sowingActions.addAll(Arrays.asList(array));

        List<String> fertilizationActions = new ArrayList<>();
        array = getResources().getStringArray(R.array.fertilization);
        fertilizationActions.addAll(Arrays.asList(array));

        List<String> plantProtectionActions = new ArrayList<>();
        array = getResources().getStringArray(R.array.plant_protection);
        plantProtectionActions.addAll(Arrays.asList(array));

        List<String> harvestActions = new ArrayList<>();
        array = getResources().getStringArray(R.array.harvest);
        harvestActions.addAll(Arrays.asList(array));

        action.put(actionList.get(0), soilCultivationActions);
        action.put(actionList.get(1), sowingActions);
        action.put(actionList.get(2), fertilizationActions);
        action.put(actionList.get(3), plantProtectionActions);
        action.put(actionList.get(4), harvestActions);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_field_actions, container, false);

        this.fieldDetailsViewModel = new ViewModelProvider(requireActivity()).get(FieldDetailsViewModel.class);
        this.fieldDetailsViewModel.getFieldModelMutableLiveData().observe(getViewLifecycleOwner(), fieldData -> {

        });

        this.actionsListView = view.findViewById(R.id.field_actions_listView);
        this.actionList = new ArrayList<>();
        this.action = new HashMap<>();
        this.listIcons = new Integer[] {
            R.drawable.ic_baseline_fence_24,
            R.drawable.ic_baseline_local_florist_24,
            R.drawable.ic_baseline_scatter_plot_24,
            R.drawable.ic_baseline_bug_report_24,
            R.drawable.ic_baseline_filter_vintage_24
        };


        adapter = new FieldActionsAdapter(getActivity(), this.actionList, this.listIcons);
        actionsListView.setAdapter(adapter);
        initListData();
        return view;
    }

    private final View.OnClickListener addActionButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CustomPopupWindow popup = new CustomPopupWindow();
            popup.show(getActivity().getSupportFragmentManager(), "test");
        }
    };

}
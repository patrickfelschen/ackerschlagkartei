package de.prog3.ackerschlagkartei.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.adapters.FieldActionsAdapter;

public class FieldActionsFragment extends Fragment {

    private ExpandableListView actionsListView;
    private List<String> actionList;
    HashMap<String, List<String>> action;
    FieldActionsAdapter adapter;
    private Button addActionButton;

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
        for(String item : array) {
            soilCultivationActions.add(item);
        }

        List<String> sowingActions = new ArrayList<>();
        array = getResources().getStringArray(R.array.sowing);
        for(String item : array) {
            sowingActions.add(item);
        }

        List<String> fertilizationActions = new ArrayList<>();
        array = getResources().getStringArray(R.array.fertilization);
        for(String item : array) {
            fertilizationActions.add(item);
        }

        List<String> plantProtectionActions = new ArrayList<>();
        array = getResources().getStringArray(R.array.plant_protection);
        for(String item : array) {
            plantProtectionActions.add(item);
        }

        List<String> harvestActions = new ArrayList<>();
        array = getResources().getStringArray(R.array.harvest);
        for(String item : array) {
            harvestActions.add(item);
        }

        action.put(actionList.get(0), soilCultivationActions);
        action.put(actionList.get(1), sowingActions);
        action.put(actionList.get(2), fertilizationActions);
        action.put(actionList.get(3), plantProtectionActions);
        action.put(actionList.get(4), harvestActions);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_field_actions, container, false);

        this.actionsListView = view.findViewById(R.id.field_actions_listView);
        this.actionList = new ArrayList<>();
        this.action = new HashMap<>();
        this.addActionButton = view.findViewById(R.id.add_action_button);

        addActionButton.setOnClickListener(addActionButtonClick);

        adapter = new FieldActionsAdapter(this.getContext(), actionList, action);
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
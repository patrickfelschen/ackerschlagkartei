package de.prog3.ackerschlagkartei.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.adapters.FieldActionsAdapter;
import de.prog3.ackerschlagkartei.viewmodels.FieldDetailsViewModel;

public class FieldActionsCategoriesFragment extends Fragment {

    private FieldDetailsViewModel fieldDetailsViewModel;
    private ListView actionsListView;
    private List<String> actionList;
    private HashMap<String, List<String>> action;
    private FieldActionsAdapter adapter;
    private Integer[] listIcons;
    private ArrayAdapter arrayAdapter;

    public FieldActionsCategoriesFragment() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_field_actions, container, false);

        this.fieldDetailsViewModel = new ViewModelProvider(requireActivity()).get(FieldDetailsViewModel.class);

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

        arrayAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, this.actionList);
        //adapter = new FieldActionsAdapter(getActivity(), this.actionList, this.listIcons);
        actionsListView.setAdapter(arrayAdapter);

        actionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fieldDetailsViewModel.setActionCategory(actionsListView.getItemAtPosition(position).toString());
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.fieldActionsOverview);
            }
        });

        initListData();
        return view;
    }

}
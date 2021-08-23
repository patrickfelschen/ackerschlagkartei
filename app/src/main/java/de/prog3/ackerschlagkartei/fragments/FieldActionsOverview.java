package de.prog3.ackerschlagkartei.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.models.ActionModel;
import de.prog3.ackerschlagkartei.viewmodels.FieldDetailsViewModel;

public class FieldActionsOverview extends Fragment {
    private FieldDetailsViewModel fieldDetailsViewModel;
    private ListView fieldActionList;
    private List<String> fieldActions;
    private  ArrayAdapter arrayAdapter;

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

        this.fieldActions = new ArrayList<>();
        this.fieldActionList = view.findViewById(R.id.actions_list);
        this.arrayAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, fieldActions);

        this.fieldDetailsViewModel = new ViewModelProvider(requireActivity()).get(FieldDetailsViewModel.class);
        this.fieldDetailsViewModel.getActions().observe(requireActivity(), new Observer<List<ActionModel>>() {
            @Override
            public void onChanged(List<ActionModel> actionModels) {
                if(actionModels != null) {
                    fieldActions.clear();
                    for(ActionModel actionModel : actionModels) {
                        fieldActions.add(actionModel.getDescription());
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

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
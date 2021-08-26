package de.prog3.ackerschlagkartei.ui.views.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.data.models.FieldModel;
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldActionsViewModel;
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldsMapViewModel;

public class AddActionDialog extends AppCompatDialogFragment {
    private FieldActionsViewModel fieldActionsViewModel;
    private FieldsMapViewModel fieldsMapViewModel;
    private AutoCompleteTextView action;
    private ArrayAdapter<CharSequence> actionCategoryAdapter;
    private ArrayAdapter<CharSequence> actionAdapter;
    private String selectedCategory;
    private FieldModel selectedFieldModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.popup_action_add, null);

        this.action = view.findViewById(R.id.new_action);

        this.fieldActionsViewModel = new ViewModelProvider(requireActivity()).get(FieldActionsViewModel.class);
        this.fieldsMapViewModel = new ViewModelProvider(requireActivity()).get(FieldsMapViewModel.class);
        this.selectedCategory = this.fieldActionsViewModel.getActionCategory();
        this.selectedFieldModel = this.fieldsMapViewModel.getSelectedFieldModel();


        this.actionCategoryAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.actions_array,
                R.layout.support_simple_spinner_dropdown_item);

        if(this.selectedCategory == getString(R.string.soil_cultivation)) {
            actionAdapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.soil_cultivation_array,
                    R.layout.support_simple_spinner_dropdown_item);
            action.setAdapter(actionAdapter);
        }else if(this.selectedCategory == getString(R.string.sowing)) {
            actionAdapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.sowing_array,
                    R.layout.support_simple_spinner_dropdown_item);
            action.setAdapter(actionAdapter);
        }else if(this.selectedCategory == getString(R.string.fertilization)) {
            actionAdapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.fertilization_array,
                    R.layout.support_simple_spinner_dropdown_item);
            action.setAdapter(actionAdapter);
        }else if(this.selectedCategory == getString(R.string.plant_protection)) {
            actionAdapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.plant_protection_array,
                    R.layout.support_simple_spinner_dropdown_item);
            action.setAdapter(actionAdapter);
        }
        else if(this.selectedCategory == getString(R.string.harvest)) {
            actionAdapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.harvest_array,
                    R.layout.support_simple_spinner_dropdown_item);
            action.setAdapter(actionAdapter);
        }

        builder.setView(view)
                .setTitle(getString(R.string.add_action_button))
                .setNegativeButton(R.string.cancel_action_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(getString(R.string.add_action_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fieldActionsViewModel.setAction(selectedFieldModel, action.getText().toString(), selectedCategory);
                    }
                });

        return builder.create();
    }

    public interface CustomPopupWindowListener {
        void apply();
    }
}

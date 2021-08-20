package de.prog3.ackerschlagkartei.fragments;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import de.prog3.ackerschlagkartei.R;

public class CustomPopupWindow extends AppCompatDialogFragment {
    private AutoCompleteTextView category;
    private AutoCompleteTextView action;
    private ArrayAdapter<CharSequence> actionCategoryAdapter;
    private ArrayAdapter<CharSequence> actionAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.popup_window, null);

        this.category = view.findViewById(R.id.action_category);
        this.action = view.findViewById(R.id.new_action);

        this.actionCategoryAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.actions_array,
                R.layout.support_simple_spinner_dropdown_item);
        this.category.setAdapter(actionCategoryAdapter);

        this.category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(actionCategoryAdapter.getItem(position).toString() == getString(R.string.soil_cultivation)) {
                    actionAdapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.soil_cultivation_array,
                            R.layout.support_simple_spinner_dropdown_item);
                    action.setAdapter(actionAdapter);
                }else if(actionCategoryAdapter.getItem(position).toString() == getString(R.string.sowing)) {
                    actionAdapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.sowing_array,
                            R.layout.support_simple_spinner_dropdown_item);
                    action.setAdapter(actionAdapter);
                }else if(actionCategoryAdapter.getItem(position).toString() == getString(R.string.fertilization)) {
                    actionAdapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.fertilization_array,
                            R.layout.support_simple_spinner_dropdown_item);
                    action.setAdapter(actionAdapter);
                }else if(actionCategoryAdapter.getItem(position).toString() == getString(R.string.plant_protection)) {
                    actionAdapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.plant_protection_array,
                            R.layout.support_simple_spinner_dropdown_item);
                    action.setAdapter(actionAdapter);
                }
                else if(actionCategoryAdapter.getItem(position).toString() == getString(R.string.harvest)) {
                    actionAdapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.harvest_array,
                            R.layout.support_simple_spinner_dropdown_item);
                    action.setAdapter(actionAdapter);
                }
            }
        });

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

            }
        });

        return builder.create();
    }

    public interface CustomPopupWindowListener {
        void apply();
    }
}


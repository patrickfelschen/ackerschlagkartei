package de.prog3.ackerschlagkartei.fragments;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.popup_window, null);

        this.category = view.findViewById(R.id.action_category);
        this.action = view.findViewById(R.id.new_action);

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


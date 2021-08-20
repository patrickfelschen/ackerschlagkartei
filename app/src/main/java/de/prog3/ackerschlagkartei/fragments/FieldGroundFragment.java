package de.prog3.ackerschlagkartei.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.models.FieldModel;
import de.prog3.ackerschlagkartei.viewmodels.FieldDetailsViewModel;
import de.prog3.ackerschlagkartei.viewmodels.FieldsOverviewViewModel;

public class FieldGroundFragment extends Fragment {
    private FieldDetailsViewModel fieldDetailsViewModel;
    private AutoCompleteTextView ddGround;
    private AutoCompleteTextView ddBkr;
    private EditText etHumus;
    private EditText etPhValue;
    private EditText etPhosphorus;
    private EditText etPotassium;
    private EditText etMagnesium;
    private EditText date;

    public FieldGroundFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_field_ground, container, false);

        this.ddGround = view.findViewById(R.id.dd_ground);
        this.ddBkr = view.findViewById(R.id.dd_bkr);
        this.etHumus = view.findViewById(R.id.et_humus);
        this.etPhValue = view.findViewById(R.id.et_ph_value);
        this.etPhosphorus = view.findViewById(R.id.et_phosphorus);
        this.etPotassium = view.findViewById(R.id.et_potassium);
        this.etMagnesium = view.findViewById(R.id.et_magnesium);
        this.date = view.findViewById(R.id.et_analysis_date);

        this.fieldDetailsViewModel = new ViewModelProvider(requireActivity()).get(FieldDetailsViewModel.class);
        this.fieldDetailsViewModel.getFieldModelMutableLiveData().observe(getViewLifecycleOwner(), fieldData -> {
            ddGround.setText(fieldData.getGround().getGroundType());
            ddBkr.setText(fieldData.getGround().getBkr());
            etHumus.setText(fieldData.getGround().getHumus());
            etPhValue.setText(String.valueOf(fieldData.getGround().getPhValue()));
            etPhosphorus.setText(String.valueOf(fieldData.getGround().getPhosphorus()));
            etPotassium.setText(String.valueOf(fieldData.getGround().getPotassium()));
            etMagnesium.setText(String.valueOf(fieldData.getGround().getMagnesium()));
            date.setText(fieldData.getGround().getDate());

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date.setText(year + "-" + month + "-" + dayOfMonth);
                }
            }, 2021, 01, 01);

            date.setOnTouchListener((v, event) -> {
                final int DRAWABLE_END = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (date.getRight() - date.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                        datePickerDialog.show();
                        return true;
                    }
                }
                return true;
            });
        });


        return view;
    }

}
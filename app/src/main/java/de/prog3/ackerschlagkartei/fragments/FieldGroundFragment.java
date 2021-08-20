package de.prog3.ackerschlagkartei.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.viewmodels.FieldDetailsViewModel;

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

    private ArrayAdapter<CharSequence> groundTypeAdapter;
    private ArrayAdapter<CharSequence> bkrAdapter;

    public FieldGroundFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_field_ground, container, false);

        this.groundTypeAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.ground_type_array,
                R.layout.support_simple_spinner_dropdown_item);

        this.bkrAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.bkr_array,
                R.layout.support_simple_spinner_dropdown_item);

        this.ddGround = view.findViewById(R.id.dd_ground);
        this.ddBkr = view.findViewById(R.id.dd_bkr);
        this.etHumus = view.findViewById(R.id.et_humus);
        this.etPhValue = view.findViewById(R.id.et_ph_value);
        this.etPhosphorus = view.findViewById(R.id.et_phosphorus);
        this.etPotassium = view.findViewById(R.id.et_potassium);
        this.etMagnesium = view.findViewById(R.id.et_magnesium);
        this.date = view.findViewById(R.id.et_analysis_date);

        this.ddGround.setAdapter(this.groundTypeAdapter);
        this.ddBkr.setAdapter(this.bkrAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        this.fieldDetailsViewModel = new ViewModelProvider(requireActivity()).get(FieldDetailsViewModel.class);
        this.fieldDetailsViewModel.getFieldModelMutableLiveData().observe(getViewLifecycleOwner(), fieldData -> {
            ddGround.setText(fieldData.getGround().getGroundType(), false);
            ddBkr.setText(fieldData.getGround().getBkr(), false);
            etHumus.setText(String.valueOf(fieldData.getGround().getHumus()));
            etPhValue.setText(String.valueOf(fieldData.getGround().getPhValue()));
            etPhosphorus.setText(String.valueOf(fieldData.getGround().getPhosphorus()));
            etPotassium.setText(String.valueOf(fieldData.getGround().getPotassium()));
            etMagnesium.setText(String.valueOf(fieldData.getGround().getMagnesium()));
            date.setText(fieldData.getGround().getDate());

            setDatePicker();

            this.ddGround.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String changes = groundTypeAdapter.getItem(position).toString();
                    fieldDetailsViewModel.updateField("ground.groundType", changes);
                    view.clearFocus();
                    ddGround.clearFocus();
                }
            });

            this.ddBkr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String changes = bkrAdapter.getItem(position).toString() ;
                    fieldDetailsViewModel.updateField("ground.bkr", changes);
                    view.clearFocus();
                    ddBkr.clearFocus();
                }
            });

            this.etHumus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        double changes = Double.parseDouble(etHumus.getText().toString());
                        fieldDetailsViewModel.updateField("ground.humus", changes);
                        v.clearFocus();
                        ddBkr.clearFocus();
                    }
                }
            });

            etPhValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        double changes = Double.parseDouble(etPhValue.getText().toString());
                        fieldDetailsViewModel.updateField("ground.phValue", changes);
                        v.clearFocus();
                        etPhValue.clearFocus();
                    }
                }
            });

            etPhosphorus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        double changes = Double.parseDouble(etPhosphorus.getText().toString());
                        fieldDetailsViewModel.updateField("ground.phosphorus", changes);
                        v.clearFocus();
                        etPhosphorus.clearFocus();
                    }
                }
            });

            etPotassium.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        double changes = Double.parseDouble(etPotassium.getText().toString());
                        fieldDetailsViewModel.updateField("ground.potassium", changes);
                        v.clearFocus();
                        etPotassium.clearFocus();
                    }
                }
            });

            etMagnesium.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        double changes = Double.parseDouble(etMagnesium.getText().toString());
                        fieldDetailsViewModel.updateField("ground.magnesium", changes);
                        v.clearFocus();
                        etMagnesium.clearFocus();
                    }
                }
            });

        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                date.setText(year + "-" + month + "-" + dayOfMonth);

                String changes = date.getText().toString();
                fieldDetailsViewModel.updateField("ground.date", changes);
                view.clearFocus();
                date.clearFocus();
            }
        }, 2021, 0, 1);

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
    }

    @Override
    public void onStop() {
        super.onStop();
        getView().clearFocus();
    }
}
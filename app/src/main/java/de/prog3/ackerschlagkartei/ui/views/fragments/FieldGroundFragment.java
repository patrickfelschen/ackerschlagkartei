package de.prog3.ackerschlagkartei.ui.views.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.data.models.FieldModel;
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldGroundViewModel;
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldsMapViewModel;

public class FieldGroundFragment extends Fragment {
    private FieldsMapViewModel fieldsMapViewModel;
    private FieldGroundViewModel fieldGroundViewModel;

    private NavController navController;

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

    private FieldModel selectedFieldModel;

    public FieldGroundFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.fieldsMapViewModel = new ViewModelProvider(requireActivity()).get(FieldsMapViewModel.class);
        this.fieldGroundViewModel = new ViewModelProvider(requireActivity()).get(FieldGroundViewModel.class);

        this.navController = Navigation.findNavController(view);

        this.selectedFieldModel = this.fieldsMapViewModel.getSelectedFieldModel();

        this.fieldGroundViewModel.getFieldModelMutableLiveData(this.selectedFieldModel).observe(getViewLifecycleOwner(), new Observer<FieldModel>() {
            @Override
            public void onChanged(FieldModel fieldModel) {
                ddGround.setText(fieldModel.getGround().getGroundType(), false);
                ddBkr.setText(fieldModel.getGround().getBkr(), false);
                etHumus.setText(String.valueOf(fieldModel.getGround().getHumus()));
                etPhValue.setText(String.valueOf(fieldModel.getGround().getPhValue()));
                etPhosphorus.setText(String.valueOf(fieldModel.getGround().getPhosphorus()));
                etPotassium.setText(String.valueOf(fieldModel.getGround().getPotassium()));
                etMagnesium.setText(String.valueOf(fieldModel.getGround().getMagnesium()));
                date.setText(fieldModel.getGround().getDate());
            }
        });

        this.setDatePicker();

        this.ddGround.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String changes = groundTypeAdapter.getItem(position).toString();
                fieldGroundViewModel.updateField(selectedFieldModel,"ground.groundType", changes);
                view.clearFocus();
                ddGround.clearFocus();
            }
        });

        this.ddBkr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String changes = bkrAdapter.getItem(position).toString();
                fieldGroundViewModel.updateField(selectedFieldModel,"ground.bkr", changes);
                view.clearFocus();
                ddBkr.clearFocus();
            }
        });

        this.etHumus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    double changes = Double.parseDouble(etHumus.getText().toString());
                    fieldGroundViewModel.updateField(selectedFieldModel,"ground.humus", changes);
                    v.clearFocus();
                    ddBkr.clearFocus();
                }
            }
        });

        etPhValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    double changes = Double.parseDouble(etPhValue.getText().toString());
                    fieldGroundViewModel.updateField(selectedFieldModel,"ground.phValue", changes);
                    v.clearFocus();
                    etPhValue.clearFocus();
                }
            }
        });

        etPhosphorus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    double changes = Double.parseDouble(etPhosphorus.getText().toString());
                    fieldGroundViewModel.updateField(selectedFieldModel,"ground.phosphorus", changes);
                    v.clearFocus();
                    etPhosphorus.clearFocus();
                }
            }
        });

        etPotassium.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    double changes = Double.parseDouble(etPotassium.getText().toString());
                    fieldGroundViewModel.updateField(selectedFieldModel,"ground.potassium", changes);
                    v.clearFocus();
                    etPotassium.clearFocus();
                }
            }
        });

        etMagnesium.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    double changes = Double.parseDouble(etMagnesium.getText().toString());
                    fieldGroundViewModel.updateField(selectedFieldModel,"ground.magnesium", changes);
                    v.clearFocus();
                    etMagnesium.clearFocus();
                }
            }
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
                fieldGroundViewModel.updateField(selectedFieldModel,"ground.date", changes);
                view.clearFocus();
                date.clearFocus();
            }
        }, 2021, 0, 1);

        date.setOnTouchListener((v, event) -> {
            final int DRAWABLE_END = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (date.getRight() - date.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                    datePickerDialog.show();
                    return true;
                }
            }
            return true;
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
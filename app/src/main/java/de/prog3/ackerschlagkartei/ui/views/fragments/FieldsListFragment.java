package de.prog3.ackerschlagkartei.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.data.models.FieldModel;
import de.prog3.ackerschlagkartei.ui.adapters.FieldsListAdapter;
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldsViewModel;

public class FieldsListFragment extends Fragment {
    private FieldsViewModel fieldsViewModel;
    private NavController navController;
    private RecyclerView fieldList;
    FieldsListAdapter fieldsListAdapter;

    public FieldsListFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fields_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.fieldList = view.findViewById(R.id.rv_field_list);
        this.fieldList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.fieldList.setHasFixedSize(true);

        this.navController = Navigation.findNavController(view);

        this.fieldsListAdapter = new FieldsListAdapter();
        this.fieldList.setAdapter(fieldsListAdapter);

        this.fieldsViewModel = new ViewModelProvider(this).get(FieldsViewModel.class);
        this.fieldsViewModel.getFieldListData().observe(requireActivity(), new Observer<List<FieldModel>>() {
            @Override
            public void onChanged(List<FieldModel> fieldModels) {
                fieldsListAdapter.setFieldModelList(fieldModels);
                fieldsListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fields_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_add_field){
            this.navController.navigate(R.id.action_fieldListFragment_to_fieldAddFragment);
            return true;
        }

        if(id == R.id.menu_change_view){
            this.navController.navigateUp();
            return true;
        }

        if(id == R.id.menu_sign_out){
            this.fieldsViewModel.logout();
            this.navController.navigate(R.id.signInFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
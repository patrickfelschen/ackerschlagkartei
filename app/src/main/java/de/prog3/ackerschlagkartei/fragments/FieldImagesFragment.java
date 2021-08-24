package de.prog3.ackerschlagkartei.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.adapters.FieldDocumentsRecyclerViewAdapter;
import de.prog3.ackerschlagkartei.viewmodels.FieldDetailsViewModel;


public class FieldImagesFragment extends Fragment implements FieldDocumentsRecyclerViewAdapter.ItemClickListener {
    private FieldDetailsViewModel fieldDetailsViewModel;
    private NavController navController;

    private RecyclerView rvFieldDocuments;
    private FieldDocumentsRecyclerViewAdapter fieldDocumentsRecyclerViewAdapter;


    public FieldImagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_field_images, container, false);
        setHasOptionsMenu(true);

        this.rvFieldDocuments = view.findViewById(R.id.rv_field_documents);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
    }

    @Override
    public void onStart() {
        super.onStart();

        this.fieldDetailsViewModel = new ViewModelProvider(requireActivity()).get(FieldDetailsViewModel.class);
        this.fieldDetailsViewModel.getFieldModelMutableLiveData().observe(getViewLifecycleOwner(), fieldData -> {

        });

        String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48"};

        int numberOfColumns = 6;
        rvFieldDocuments.setLayoutManager(new GridLayoutManager(requireContext(), numberOfColumns));
        fieldDocumentsRecyclerViewAdapter = new FieldDocumentsRecyclerViewAdapter(requireContext(), data);
        fieldDocumentsRecyclerViewAdapter.setClickListener(this);
        rvFieldDocuments.setAdapter(fieldDocumentsRecyclerViewAdapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(requireContext(), fieldDocumentsRecyclerViewAdapter.getItem(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.field_images_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btn_add_image) {
            openCamera();
        }

        if (item.getItemId() == R.id.btn_add_doc) {
            openFile();
        }
        return super.onOptionsItemSelected(item);
    }

    void performCamera() {
        this.navController.navigate(R.id.fieldImagesCameraFragment);
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    performCamera();
                }
            });

    void openCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            performCamera();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private static final int PICK_PDF_FILE = 2;

    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        startActivityForResult(intent, PICK_PDF_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == PICK_PDF_FILE && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                this.fieldDetailsViewModel.uploadFieldDocument(uri);
            }
        }
    }


}
package de.prog3.ackerschlagkartei.ui.views.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.data.interfaces.ItemClickListener;
import de.prog3.ackerschlagkartei.data.models.DocumentModel;
import de.prog3.ackerschlagkartei.data.models.FieldModel;
import de.prog3.ackerschlagkartei.ui.adapters.FieldDocumentsAdapter;
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldDocumentsViewModel;
import de.prog3.ackerschlagkartei.ui.viewmodels.FieldsMapViewModel;

public class FieldDocumentsFragment extends Fragment implements ItemClickListener {
    private FieldsMapViewModel fieldsMapViewModel;
    private FieldDocumentsViewModel fieldDocumentsViewModel;

    private NavController navController;

    private RecyclerView rvFieldDocuments;
    private FieldDocumentsAdapter fieldDocumentsAdapter;

    private FieldModel selectedFieldModel;

    public FieldDocumentsFragment(){}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_field_documents, container, false);
        this.rvFieldDocuments = view.findViewById(R.id.rv_field_documents);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.fieldsMapViewModel = new ViewModelProvider(requireActivity()).get(FieldsMapViewModel.class);
        this.fieldDocumentsViewModel = new ViewModelProvider(requireActivity()).get(FieldDocumentsViewModel.class);

        this.selectedFieldModel = this.fieldsMapViewModel.getSelectedFieldModel();
        this.navController = Navigation.findNavController(view);

        this.rvFieldDocuments.setLayoutManager(new GridLayoutManager(requireContext(), 4));

        this.fieldDocumentsViewModel.getDocumentsMutableLiveData(this.selectedFieldModel).observe(getViewLifecycleOwner(), new Observer<List<DocumentModel>>() {
            @Override
            public void onChanged(List<DocumentModel> documentModels) {
                fieldDocumentsAdapter = new FieldDocumentsAdapter(requireContext(), documentModels);
                fieldDocumentsAdapter.setClickListener(FieldDocumentsFragment.this);
                rvFieldDocuments.setAdapter(fieldDocumentsAdapter);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(requireContext(), fieldDocumentsAdapter.getItem(position).getUrl(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.documents_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btn_add_doc) {

        }

        if (id == R.id.btn_add_image) {

        }
        return super.onOptionsItemSelected(item);
    }


    ActivityResultLauncher<String> mGetContent =
            registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    fieldDocumentsViewModel.updateDocument(selectedFieldModel, uri);
                }
            });

    ActivityResultLauncher<Void> mTakePicture =
            registerForActivityResult(new ActivityResultContracts.TakePicturePreview(),
            new ActivityResultCallback<Bitmap>() {
        @Override
        public void onActivityResult(Bitmap result) {

        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
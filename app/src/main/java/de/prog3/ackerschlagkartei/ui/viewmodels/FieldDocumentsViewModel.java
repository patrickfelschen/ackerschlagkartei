package de.prog3.ackerschlagkartei.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import de.prog3.ackerschlagkartei.data.models.DocumentModel;
import de.prog3.ackerschlagkartei.data.models.FieldModel;
import de.prog3.ackerschlagkartei.data.repositories.FirestoreRepository;

public class FieldDocumentsViewModel extends AndroidViewModel {
    private final Application application;
    private FirestoreRepository firestoreRepository;

    public FieldDocumentsViewModel(@NonNull Application application) {
        super(application);

        this.application = application;
        this.firestoreRepository = new FirestoreRepository(application);
    }

    public MutableLiveData<FieldModel> getFieldModelMutableLiveData(@NonNull FieldModel selectedField) {
        return this.firestoreRepository.getFieldMutableLiveData(selectedField.getUid());
    }

    public MutableLiveData<List<DocumentModel>> getDocumentsMutableLiveData(@NonNull FieldModel selectedField){
        return this.firestoreRepository.getDocumentListGetData(selectedField.getUid());
    }

}

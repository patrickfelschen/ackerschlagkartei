package de.prog3.ackerschlagkartei.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import de.prog3.ackerschlagkartei.data.models.FieldModel;
import de.prog3.ackerschlagkartei.data.repositories.FirestoreRepository;

public class FieldAddViewModel extends AndroidViewModel {

    final FirestoreRepository firestoreRepository;

    public FieldAddViewModel(@NonNull Application application) {
        super(application);

        this.firestoreRepository = new FirestoreRepository(application);
    }

    public void createFieldModel(@NonNull FieldModel fieldModel) {
        firestoreRepository.createFieldModel(fieldModel);
    }

}

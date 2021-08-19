package de.prog3.ackerschlagkartei.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import de.prog3.ackerschlagkartei.models.FieldModel;
import de.prog3.ackerschlagkartei.repositories.FirestoreRepository;

public class FieldDetailsViewModel extends AndroidViewModel {

    private final FirestoreRepository firestoreRepository;
    private final MutableLiveData<FieldModel> fieldModelMutableLiveData;

    private final String fieldUid;

    public FieldDetailsViewModel(@NonNull Application application, @NonNull String fieldUid) {
        super(application);

        this.firestoreRepository = new FirestoreRepository(application);
        this.fieldModelMutableLiveData = new MutableLiveData<>();

        this.fieldUid = fieldUid;
    }

    public MutableLiveData<FieldModel> getFieldModelMutableLiveData() {
        return this.firestoreRepository.getFieldMutableLiveData(this.fieldUid);
    }

    public void updateField(FieldModel fieldModel) {
        updateField(fieldModel);
    }

    public void deleteField(FieldModel fieldModel) {
        deleteField(fieldModel);
    }
}

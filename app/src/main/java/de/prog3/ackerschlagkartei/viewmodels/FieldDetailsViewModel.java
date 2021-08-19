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

    private String fieldModelUid;

    public FieldDetailsViewModel(@NonNull Application application) {
        super(application);

        this.firestoreRepository = new FirestoreRepository(application);
        this.fieldModelMutableLiveData = new MutableLiveData<>();
    }

    public String getFieldModelUid() {
        return fieldModelUid;
    }

    public void setFieldModelUid(String fieldModelUid) {
        this.fieldModelUid = fieldModelUid;
    }

    public MutableLiveData<FieldModel> getFieldModelMutableLiveData() {
        return this.firestoreRepository.getFieldMutableLiveData(this.fieldModelUid);
    }

    public void updateField(@NonNull FieldModel fieldModel) {
        updateField(fieldModel);
    }

    public void deleteField(@NonNull FieldModel fieldModel) {
        deleteField(fieldModel);
    }
}

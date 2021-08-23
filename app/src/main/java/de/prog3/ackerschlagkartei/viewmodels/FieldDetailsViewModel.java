package de.prog3.ackerschlagkartei.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import de.prog3.ackerschlagkartei.models.FieldModel;
import de.prog3.ackerschlagkartei.repositories.FirestoreRepository;

public class FieldDetailsViewModel extends AndroidViewModel {

    private final FirestoreRepository firestoreRepository;
    private MutableLiveData<FieldModel> fieldModelMutableLiveData;
    private MutableLiveData<String> actionCategory;

    public FieldDetailsViewModel(@NonNull Application application) {
        super(application);

        this.firestoreRepository = new FirestoreRepository(application);
        this.actionCategory = new MutableLiveData<>();
    }

    public void setFieldModelMutableLiveData(@NonNull String fieldUid) {
        this.fieldModelMutableLiveData = this.firestoreRepository.getFieldMutableLiveData(fieldUid);
    }

    public MutableLiveData<FieldModel> getFieldModelMutableLiveData() {
        return this.fieldModelMutableLiveData;
    }

    public void updateField(String field, Object changes) {
        if (fieldModelMutableLiveData == null) {
            return;
        }
        this.firestoreRepository.updateFieldModel(fieldModelMutableLiveData.getValue(), field, changes);
    }

    public void deleteFieldModel(@NonNull FieldModel fieldModel) {
        this.firestoreRepository.deleteFieldModel(fieldModel);
    }

    public void setActionCategory(String actionCategory) {
        this.actionCategory.setValue(actionCategory);
    }

    public MutableLiveData<String> getActionCategory() {
        return this.actionCategory;
    }
}

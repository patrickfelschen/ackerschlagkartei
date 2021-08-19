package de.prog3.ackerschlagkartei.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.prog3.ackerschlagkartei.models.FieldModel;
import de.prog3.ackerschlagkartei.repositories.AuthRepository;
import de.prog3.ackerschlagkartei.repositories.FirestoreRepository;

public class FieldsOverviewViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<FirebaseUser> userMutableLiveData;

    private final MutableLiveData<List<FieldModel>> fieldMutableLiveData;
    private final FirestoreRepository fieldsOverviewListRepository;

    private FieldModel selectedField;

    public FieldsOverviewViewModel(Application application) {
        super(application);

        authRepository = new AuthRepository(application);
        userMutableLiveData = authRepository.getUserMutableLiveData();

        fieldsOverviewListRepository = new FirestoreRepository(application);
        fieldMutableLiveData = fieldsOverviewListRepository.getFieldListMutableLiveData();
    }

    public void logout() {
        authRepository.logout();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<List<FieldModel>> getLiveFieldData() {
        return this.fieldMutableLiveData;
    }

    public FieldModel getSelectedField() {
        return selectedField;
    }

    public void setSelectedField(FieldModel selectedField) {
        this.selectedField = selectedField;
    }
}

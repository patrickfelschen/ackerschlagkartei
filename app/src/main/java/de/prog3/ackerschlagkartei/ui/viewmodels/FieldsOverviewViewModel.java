package de.prog3.ackerschlagkartei.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.prog3.ackerschlagkartei.data.models.FieldModel;
import de.prog3.ackerschlagkartei.data.repositories.AuthRepository;
import de.prog3.ackerschlagkartei.data.repositories.FirestoreRepository;

public class FieldsOverviewViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<FirebaseUser> userMutableLiveData;

    private final MutableLiveData<List<FieldModel>> fieldMutableLiveData;
    private final FirestoreRepository firestoreRepository;

    public FieldsOverviewViewModel(Application application) {
        super(application);

        this.authRepository = new AuthRepository(application);
        this.userMutableLiveData = authRepository.getUserMutableLiveData();

        this.firestoreRepository = new FirestoreRepository(application);
        this.fieldMutableLiveData = firestoreRepository.getFieldListMutableLiveData();
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

}

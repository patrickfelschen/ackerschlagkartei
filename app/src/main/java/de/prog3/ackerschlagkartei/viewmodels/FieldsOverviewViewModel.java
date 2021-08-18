package de.prog3.ackerschlagkartei.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.prog3.ackerschlagkartei.models.FieldModel;
import de.prog3.ackerschlagkartei.repositories.AuthRepository;
import de.prog3.ackerschlagkartei.repositories.FirestoreRepository;

public class FieldsOverviewViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<FirebaseUser> userMutableLiveData;
    private final MutableLiveData<Boolean> loggedOutMutableLiveData;
    private final MutableLiveData<List<FieldModel>> fieldMutableLiveData;
    private final FirebaseFirestore firebaseFirestore;
    private final FirestoreRepository fieldsOverviewListRepository;

    public FieldsOverviewViewModel(Application application) {
        super(application);

        authRepository = new AuthRepository(application);
        userMutableLiveData = authRepository.getUserMutableLiveData();
        loggedOutMutableLiveData = authRepository.getLoggedOutMutableLiveData();
        fieldsOverviewListRepository = new FirestoreRepository();
        firebaseFirestore = FirebaseFirestore.getInstance();
        fieldMutableLiveData = fieldsOverviewListRepository.getFieldListMutableLiveData();
    }

    public void logout() {
        authRepository.logout();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() {
        return loggedOutMutableLiveData;
    }

    public MutableLiveData<List<FieldModel>> getLiveFieldData() {
        return this.fieldMutableLiveData;
    }
}

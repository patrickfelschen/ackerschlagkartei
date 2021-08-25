package de.prog3.ackerschlagkartei.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.prog3.ackerschlagkartei.data.models.FieldModel;
import de.prog3.ackerschlagkartei.data.repositories.AuthRepository;
import de.prog3.ackerschlagkartei.data.repositories.FirestoreRepository;

public class FieldsMapViewModel extends AndroidViewModel {
    private final Application application;

    private final AuthRepository authRepository;
    private final FirestoreRepository firestoreRepository;

    public FieldsMapViewModel(@NonNull Application application) {
        super(application);
        this.application = application;

        this.authRepository = new AuthRepository(application);
        this.firestoreRepository = new FirestoreRepository(application);
    }

    public void logout() {
        this.authRepository.logout();
    }

    public MutableLiveData<List<FieldModel>> getFieldListData() {
        return this.firestoreRepository.getFieldListGetData();
    }

}

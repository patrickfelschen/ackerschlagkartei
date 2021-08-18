package de.prog3.ackerschlagkartei.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.prog3.ackerschlagkartei.models.FieldModel;
import de.prog3.ackerschlagkartei.repositories.FieldsOverviewListRepository;

public class FieldsOverviewListViewModel extends ViewModel {
    MutableLiveData<List<FieldModel>> fieldMutableLiveData;
    FirebaseFirestore firebaseFirestore;
    FieldsOverviewListRepository fieldRepository;

    public FieldsOverviewListViewModel() {
       this.fieldRepository = new FieldsOverviewListRepository();
       this.fieldMutableLiveData = fieldRepository.getFieldListMutableLiveData();
       this.firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<List<FieldModel>> getLiveFieldData() {
       return this.fieldMutableLiveData;
    }
}

package de.prog3.ackerschlagkartei.repositories;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.prog3.ackerschlagkartei.models.FieldModel;

public class FirestoreRepository {
    private MutableLiveData<List<FieldModel>> fieldListMutableLiveData;
    private FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth firebaseAuth;

    public FirestoreRepository() {
        this.fieldListMutableLiveData = new MutableLiveData<>();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public MutableLiveData<List<FieldModel>> getFieldListMutableLiveData() {
        firebaseFirestore.collection("Users").document(firebaseAuth.getUid()).collection("Fields")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<FieldModel> fieldList = new ArrayList<>();
                        for(QueryDocumentSnapshot doc : value) {
                            if(doc != null) {
                                fieldList.add(doc.toObject(FieldModel.class));
                            }
                        }
                        fieldListMutableLiveData.postValue(fieldList);
                    }
                });
        return fieldListMutableLiveData;
    }
}

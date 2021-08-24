package de.prog3.ackerschlagkartei.repositories;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.prog3.ackerschlagkartei.models.ActionModel;
import de.prog3.ackerschlagkartei.models.FieldModel;

public class FirestoreRepository {
    private final Application application;
    private final FirebaseFirestore firebaseFirestore;
    private final FirebaseAuth firebaseAuth;

    private final MutableLiveData<List<FieldModel>> fieldListMutableLiveData;
    private final MutableLiveData<FieldModel> fieldMutableLiveData;

    private final MutableLiveData<List<ActionModel>> actionListMutableLiveData;
    private final MutableLiveData<ActionModel> actionMutableLiveData;

    private final CollectionReference fieldCollection;

    public FirestoreRepository(@NonNull Application application) {
        this.application = application;
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();

        this.fieldListMutableLiveData = new MutableLiveData<>();
        this.fieldMutableLiveData = new MutableLiveData<>();

        this.actionListMutableLiveData = new MutableLiveData<>();
        this.actionMutableLiveData = new MutableLiveData<>();

        this.fieldCollection = this.firebaseFirestore
                .collection("Users")
                .document(firebaseAuth.getUid())
                .collection("Fields");
    }

    // FIELDMODEL

    public MutableLiveData<List<FieldModel>> getFieldListMutableLiveData() {
        this.fieldCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(application, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value != null) {
                    List<FieldModel> fieldList = new ArrayList<>();

                    for (QueryDocumentSnapshot doc : value) {
                        if (doc != null) {
                            fieldList.add(doc.toObject(FieldModel.class));
                        }
                    }

                    fieldListMutableLiveData.postValue(fieldList);

                }
            }
        });

        return fieldListMutableLiveData;
    }

    public MutableLiveData<FieldModel> getFieldMutableLiveData(@NonNull String uid) {
        this.fieldCollection.document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(application, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value != null && value.exists()) {
                    FieldModel fieldModel = value.toObject(FieldModel.class);
                    fieldMutableLiveData.postValue(fieldModel);
                }

            }
        });

        return fieldMutableLiveData;
    }

    public void createFieldModel(@NonNull FieldModel fieldModel) {
        this.fieldCollection.add(fieldModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateFieldModel(@NonNull FieldModel fieldModel) {
        this.fieldCollection.document(fieldModel.getUid()).set(fieldModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateFieldModel(@NonNull FieldModel fieldModel, String field, Object changes) {
        this.fieldCollection
                .document(fieldModel.getUid())
                .update(field, changes)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteFieldModel(@NonNull FieldModel fieldModel) {
        this.fieldCollection.document(fieldModel.getUid()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ACTIONMODEL

    public MutableLiveData<List<ActionModel>> getActionListMutableLiveData(@NonNull String fieldUid, String category) {
        this.fieldCollection
                .document(fieldUid)
                .collection("Actions")
                .whereEqualTo("type", category)
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Log.w("GetAction", error.getLocalizedMessage());
                            Toast.makeText(application, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (value != null) {
                            List<ActionModel> actionList = new ArrayList<>();

                            for (QueryDocumentSnapshot doc : value) {
                                if (doc != null) {
                                    actionList.add(doc.toObject(ActionModel.class));
                                }
                            }

                            actionListMutableLiveData.postValue(actionList);

                        }
                    }
                });

        return actionListMutableLiveData;
    }

    public MutableLiveData<ActionModel> getActionMutableLiveData(@NonNull String fieldUid, @NonNull String actionUid) {
        this.fieldCollection
                .document(fieldUid)
                .collection("Actions")
                .document(actionUid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Toast.makeText(application, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (value != null && value.exists()) {
                            ActionModel actionModel = value.toObject(ActionModel.class);
                            actionMutableLiveData.postValue(actionModel);
                        }
                    }
                });
        return actionMutableLiveData;
    }

    public void createActionModel(@NonNull String uid, ActionModel actionModel) {
        this.fieldCollection
                .document(uid)
                .collection("Actions")
                .add(actionModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

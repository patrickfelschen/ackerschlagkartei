package de.prog3.ackerschlagkartei.repositories;

import android.app.Application;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

// Users/{userId}/Fields/{fieldId}/Images/{imageId}

public class StorageRepository {
    private final Application application;

    private final FirebaseStorage firebaseStorage;
    private final FirebaseAuth firebaseAuth;

    public StorageRepository(Application application) {
        this.application = application;
        this.firebaseStorage = FirebaseStorage.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public void uploadFieldDocument(String fieldId, Uri contentUri) {
        StorageReference storageReference = firebaseStorage
                .getReference()
                .child("Users")
                .child(firebaseAuth.getUid())
                .child("Fields")
                .child(fieldId)
                .child("Documents")
                .child(UUID.randomUUID().toString());

        storageReference.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

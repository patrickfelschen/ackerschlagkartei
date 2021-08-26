package de.prog3.ackerschlagkartei.data.repositories;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import de.prog3.ackerschlagkartei.data.models.DocumentModel;
import de.prog3.ackerschlagkartei.utils.Status;

// Users/{userId}/Fields/{fieldId}/Documents/{documentId}

public class StorageRepository {
    private final Application application;
    private final FirebaseStorage firebaseStorage;
    private final FirebaseAuth firebaseAuth;

    private final MutableLiveData<Status> uploadDocumentStatus;
    private final MutableLiveData<File> downloadDocumentFile;

    public StorageRepository(Application application) {
        this.application = application;
        this.firebaseStorage = FirebaseStorage.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();

        this.uploadDocumentStatus = new MutableLiveData<>(Status.INITIAL);
        this.downloadDocumentFile = new MutableLiveData<>();
    }

    public void uploadFieldDocument(String fieldId, Uri contentUri) {
        this.uploadDocumentStatus.postValue(Status.LOADING);
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
                uploadDocumentStatus.postValue(Status.SUCCESS);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(application, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                uploadDocumentStatus.postValue(Status.ERROR);
            }
        });
    }

    public void uploadBytes(String fieldId, byte[] data){
        StorageReference storageReference = firebaseStorage
                .getReference()
                .child("Users")
                .child(firebaseAuth.getUid())
                .child("Fields")
                .child(fieldId)
                .child("Documents")
                .child(UUID.randomUUID().toString());

        storageReference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void downloadFieldDocument(DocumentModel documentModel){
        File localFile;

        try {
            localFile = File.createTempFile("doc_", null);
        }catch (IOException e){
            return;
        }

        this.firebaseStorage.getReference()
                .child(documentModel.getUriFullsize())
                .getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                downloadDocumentFile.postValue(localFile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public MutableLiveData<File> getDownloadDocumentFile() {
        return downloadDocumentFile;
    }
}

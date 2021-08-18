package de.prog3.ackerschlagkartei.repositories;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {
    private final Application application;
    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<FirebaseUser> userMutableLiveData;

    public AuthRepository(Application application) {
        this.application = application;

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userMutableLiveData = new MutableLiveData<>();

        if (firebaseAuth.getCurrentUser() != null) {
            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
        }
    }

    public void register(String email, String password) {
        this.firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login(String email, String password) {
        this.firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logout() {
        firebaseAuth.signOut();
    }

    public void resetPassword(String email) {
        firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

}

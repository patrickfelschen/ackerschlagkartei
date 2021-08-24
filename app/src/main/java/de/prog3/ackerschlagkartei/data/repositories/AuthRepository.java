package de.prog3.ackerschlagkartei.data.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.prog3.ackerschlagkartei.utils.Status;

public class AuthRepository {
    private final Application application;
    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<FirebaseUser> userMutableLiveData;

    private final MutableLiveData<Status> registerStatus;
    private final MutableLiveData<Status> loginStatus;
    private final MutableLiveData<Status> logoutStatus;
    private final MutableLiveData<Status> resetPasswordStatus;

    public AuthRepository(Application application) {
        this.application = application;

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userMutableLiveData = new MutableLiveData<>();

        this.registerStatus = new MutableLiveData<>(Status.INITIAL);
        this.loginStatus = new MutableLiveData<>(Status.INITIAL);
        this.logoutStatus = new MutableLiveData<>(Status.INITIAL);
        this.resetPasswordStatus = new MutableLiveData<>(Status.INITIAL);

        if (firebaseAuth.getCurrentUser() != null) {
            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
        }
    }

    public void register(String email, String password) {
        this.registerStatus.postValue(Status.LOADING);
        this.firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if (authResult.getUser() != null) {
                    authResult.getUser().sendEmailVerification();
                }
                registerStatus.postValue(Status.SUCCESS);
                userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                registerStatus.postValue(Status.ERROR);
                //Toast.makeText(application, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login(String email, String password) {
        this.loginStatus.postValue(Status.LOADING);
        this.firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                loginStatus.postValue(Status.SUCCESS);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loginStatus.postValue(Status.ERROR);
                //Toast.makeText(application, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logout() {
        this.logoutStatus.postValue(Status.LOADING);
        this.firebaseAuth.signOut();
        this.logoutStatus.postValue(Status.SUCCESS);
    }

    public void resetPassword(String email) {
        this.resetPasswordStatus.postValue(Status.LOADING);
        this.firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                resetPasswordStatus.postValue(Status.SUCCESS);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                resetPasswordStatus.postValue(Status.ERROR);
                //Toast.makeText(application, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Status> getRegisterStatus() {
        return registerStatus;
    }

    public MutableLiveData<Status> getLoginStatus() {
        return loginStatus;
    }

    public MutableLiveData<Status> getLogoutStatus() {
        return logoutStatus;
    }

    public MutableLiveData<Status> getResetPasswordStatus() {
        return resetPasswordStatus;
    }
}

package de.prog3.ackerschlagkartei.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.viewmodels.AuthViewModel;

public class SignInActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;

    private Button btnSignIn;
    private Button btnOpenSignUp;
    private Button btnOpenPasswordReset;
    private EditText etSignInEmail;
    private EditText etSignInPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        this.setContentView(R.layout.activity_sign_in);

        this.authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        this.authViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    startActivity(new Intent(SignInActivity.this, FieldsOverviewActivity.class));
                }
            }
        });

        this.btnSignIn = findViewById(R.id.btn_sign_in);
        this.btnOpenSignUp = findViewById(R.id.btn_open_sign_up);
        this.btnOpenPasswordReset = findViewById(R.id.btn_open_password_reset);
        this.etSignInEmail = findViewById(R.id.et_sign_in_email);
        this.etSignInPassword = findViewById(R.id.et_sign_in_password);

        this.btnSignIn.setOnClickListener(btnSignInClick);
        this.btnOpenSignUp.setOnClickListener(btnOpenSignUpClick);
        this.btnOpenPasswordReset.setOnClickListener(btnOpenPasswordResetClick);
    }

    private final View.OnClickListener btnSignInClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signInFirebaseUser();
        }
    };

    private final View.OnClickListener btnOpenSignUpClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        }
    };

    private final View.OnClickListener btnOpenPasswordResetClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
        }
    };

    private void signInFirebaseUser() {
        String email = etSignInEmail.getText().toString().trim();
        String password = etSignInPassword.getText().toString().trim();

        if (TextUtils.isEmpty((email))) {
            etSignInEmail.setError("Email cannot be empty");
            etSignInEmail.requestFocus();
        } else if (TextUtils.getTrimmedLength(password) < 8) {
            etSignInPassword.setError("Password too short - must be at least 8 characters");
            etSignInPassword.requestFocus();
        } else {
            authViewModel.login(email, password);
        }
    }

}
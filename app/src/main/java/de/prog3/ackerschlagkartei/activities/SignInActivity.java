package de.prog3.ackerschlagkartei.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import de.prog3.ackerschlagkartei.R;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

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

        this.firebaseAuth = FirebaseAuth.getInstance();

        this.setContentView(R.layout.activity_sign_in);

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
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(signInOnCompleteListener());
        }
    }

    private OnCompleteListener<AuthResult> signInOnCompleteListener() {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignInActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this, FieldsOverviewActivity.class));
                } else {
                    Toast.makeText(SignInActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

}
package de.prog3.ackerschlagkartei.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import static android.content.ContentValues.TAG;

public class SignUpActivity extends AppCompatActivity {

    private final View.OnClickListener returnToSignInClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finishActivity();
        }
    };
    private FirebaseAuth firebaseAuth;
    private EditText etSignUpEmail;
    private EditText etSignUpPassword;
    private EditText etSignUpPasswordConfirm;
    private final View.OnClickListener signUpButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createFirebaseUser();
        }
    };
    private Button btnSignUp;
    private Button btnReturnToSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_sign_up);

        this.firebaseAuth = FirebaseAuth.getInstance();

        this.etSignUpEmail = findViewById(R.id.et_sign_up_email);
        this.etSignUpPassword = findViewById(R.id.et_sign_up_password);
        this.etSignUpPasswordConfirm = findViewById(R.id.et_sign_up_password_confirm);
        this.btnSignUp = findViewById(R.id.btn_sign_up);
        this.btnReturnToSignIn = findViewById(R.id.btn_return_to_sign_in);

        this.btnSignUp.setOnClickListener(signUpButtonClick);
        this.btnReturnToSignIn.setOnClickListener(returnToSignInClick);

    }

    private void createFirebaseUser() {
        String email = etSignUpEmail.getText().toString().trim();
        String password = etSignUpPassword.getText().toString().trim();
        String passwordConfirm = etSignUpPasswordConfirm.getText().toString().trim();

        if (TextUtils.isEmpty((email))) {
            etSignUpEmail.setError("Email cannot be empty");
            etSignUpEmail.requestFocus();
        } else if (TextUtils.getTrimmedLength(password) < 8) {
            etSignUpPassword.setError("Password too short - must be at least 8 characters");
            etSignUpPassword.requestFocus();
        } else if (!TextUtils.equals(password, passwordConfirm)) {
            etSignUpPasswordConfirm.setError("Password doesn't match");
            etSignUpPasswordConfirm.requestFocus();
        } else {
            Log.d(TAG, "Email: " + email + "Password: " + password);
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(signUpOnCompleteListener());
        }

    }

    private OnCompleteListener<AuthResult> signUpOnCompleteListener() {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                } else {
                    Toast.makeText(SignUpActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void finishActivity() {
        this.finish();
    }

}
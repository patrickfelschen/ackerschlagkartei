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

public class SignUpActivity extends AppCompatActivity {

    private EditText etSignUpEmail;
    private EditText etSignUpPassword;
    private EditText etSignUpPasswordConfirm;
    private Button btnSignUp;
    private Button btnReturnToSignIn;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_sign_up);

        this.authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        this.authViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    startActivity(new Intent(SignUpActivity.this, FieldsOverviewActivity.class));
                }
            }
        });

        this.etSignUpEmail = findViewById(R.id.et_sign_up_email);
        this.etSignUpPassword = findViewById(R.id.et_sign_up_password);
        this.etSignUpPasswordConfirm = findViewById(R.id.et_sign_up_password_confirm);
        this.btnSignUp = findViewById(R.id.btn_sign_up);
        this.btnReturnToSignIn = findViewById(R.id.btn_return_to_sign_in);

        this.btnSignUp.setOnClickListener(signUpButtonClick);
        this.btnReturnToSignIn.setOnClickListener(returnToSignInClick);
    }

    private final View.OnClickListener returnToSignInClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finishActivity();
        }
    };

    private final View.OnClickListener signUpButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createFirebaseUser();
        }
    };

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
            authViewModel.register(email, password);
        }

    }

    private void finishActivity() {
        this.finish();
    }

}
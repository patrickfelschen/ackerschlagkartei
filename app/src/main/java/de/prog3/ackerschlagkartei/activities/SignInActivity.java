package de.prog3.ackerschlagkartei.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import de.prog3.ackerschlagkartei.R;

import static android.content.ContentValues.TAG;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private Button signInButton;
    private Button openSignUpButton;
    private Button openPasswordResetButton;
    private EditText emailEditText;
    private EditText passwordEditText;

    private void instantiateUI() {
        this.signInButton = findViewById(R.id.btnSignIn);
        this.openSignUpButton = findViewById(R.id.btnOpenSignUp);
        this.openPasswordResetButton = findViewById(R.id.btnOpenPasswordReset);
        this.emailEditText = findViewById(R.id.etEmail);
        this.passwordEditText = findViewById(R.id.etPassword);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        this.firebaseAuth = FirebaseAuth.getInstance();

        this.setContentView(R.layout.activity_sign_in);
        this.instantiateUI();
    }

    public void signInButtonClick(View v) {
        Log.d(TAG, "signInButtonClick");
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Intent intent = new Intent(this, FieldsOverviewActivity.class);
        this.startActivity(intent);
    }

    public void openSignUpButtonClick(View v) {
        Intent intent = new Intent(this, SignUpActivity.class);
        this.startActivity(intent);
    }

    public void openPasswordResetClick(View v) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        this.startActivity(intent);
    }

}
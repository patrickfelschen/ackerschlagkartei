package de.prog3.ackerschlagkartei.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import de.prog3.ackerschlagkartei.R;

public class SignInActivity extends AppCompatActivity {

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
        this.setContentView(R.layout.activity_sign_in);
        this.instantiateUI();
    }

    public void signInButtonClick(View v) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Intent intent = new Intent(this, FieldsOverviewActivity.class);
        intent.putExtra("Key", "Value"); //optional um Argumente ins naechste Activity zu uebergeben
        this.startActivity(intent);
    }

    public void openSignUpButtonClick(View v) {

    }

    public void openPasswordResetClick(View v) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        this.startActivity(intent);
    }

}
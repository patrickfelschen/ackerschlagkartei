package de.prog3.ackerschlagkartei.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.viewmodels.AuthViewModel;

public class ForgotPasswordActivity extends AppCompatActivity {
    private AuthViewModel authViewModel;

    private EditText emailEditText;
    private Button forgotPasswordButton;
    private Button returnToSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_forgot_password);

        this.authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        this.emailEditText = findViewById(R.id.et_forgot_password_email);
        this.forgotPasswordButton = findViewById(R.id.btn_forgot_password);
        this.returnToSignInButton = findViewById(R.id.btn_return_to_sign_in);

        this.forgotPasswordButton.setOnClickListener(forgotPasswordButtonClick());
        this.returnToSignInButton.setOnClickListener(returnToSignInButtonClick());
    }

    private View.OnClickListener forgotPasswordButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                authViewModel.resetPassword(email);
            }
        };
    }

    private View.OnClickListener returnToSignInButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
    }

}
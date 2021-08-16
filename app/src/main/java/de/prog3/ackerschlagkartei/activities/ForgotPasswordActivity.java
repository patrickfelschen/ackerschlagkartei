package de.prog3.ackerschlagkartei.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import de.prog3.ackerschlagkartei.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button forgotPasswordButton;
    private Button returnToSignInButton;

    private void instantiateUI() {
        this.emailEditText = findViewById(R.id.etForgotPasswordMail);
        this.forgotPasswordButton = findViewById(R.id.btnForgotPassword);
        this.returnToSignInButton = findViewById(R.id.btn_return_to_sign_in);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_forgot_password);
        instantiateUI();
    }

    public void forgotPasswordClick(View v) {

    }

    public void returnToSignInClick(View v) {
        this.finish();
    }
}
package de.prog3.ackerschlagkartei.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.prog3.ackerschlagkartei.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText signUpMailEditText;
    private EditText signUpPasswordEditText;
    private EditText signUpPasswordConfirmEditText;
    private Button signUpButton;
    private Button returnToSignInButton;

    private void instantiateUI() {
        this.signUpMailEditText = (EditText) findViewById(R.id.etSignUpMail);
        this.signUpPasswordEditText = (EditText) findViewById(R.id.etSignUpPassword);
        this.signUpPasswordConfirmEditText = (EditText) findViewById(R.id.etSignUpPasswordConfirm);
        this.signUpButton = (Button) findViewById(R.id.btnSignUp);
        this.returnToSignInButton = (Button) findViewById(R.id.btnReturnToSignIn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_sign_up);
        this.instantiateUI();
    }

    private void signUpButtonClick(View v) {

    }

    public void returnToSignInClick(View v) {
        this.finish();
    }
}
package de.prog3.ackerschlagkartei.ui.views.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseUser;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.ui.viewmodels.AuthViewModel;

public class SignInFragment extends Fragment {
    private AuthViewModel authViewModel;
    private NavController navController;

    private Button btnSignIn;
    private Button btnOpenSignUp;
    private Button btnOpenPasswordReset;
    private EditText etSignInEmail;
    private EditText etSignInPassword;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        this.btnSignIn = view.findViewById(R.id.btn_sign_in);
        this.btnOpenSignUp = view.findViewById(R.id.btn_open_sign_up);
        this.btnOpenPasswordReset = view.findViewById(R.id.btn_open_password_reset);
        this.etSignInEmail = view.findViewById(R.id.et_sign_in_email);
        this.etSignInPassword = view.findViewById(R.id.et_sign_in_password);

        this.btnSignIn.setOnClickListener(btnSignInClick);
        this.btnOpenSignUp.setOnClickListener(btnOpenSignUpClick);
        this.btnOpenPasswordReset.setOnClickListener(btnOpenPasswordResetClick);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.navController = Navigation.findNavController(requireActivity(), R.id.auth_nav_host_fragment);
        this.authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        this.authViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    navController.navigate(R.id.action_signInFragment_to_fieldsOverviewActivity);
                }
            }
        });
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
            navController.navigate(R.id.action_signInFragment_to_signUpFragment);
        }
    };

    private final View.OnClickListener btnOpenPasswordResetClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            navController.navigate(R.id.action_signInFragment_to_forgotPasswordFragment);
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
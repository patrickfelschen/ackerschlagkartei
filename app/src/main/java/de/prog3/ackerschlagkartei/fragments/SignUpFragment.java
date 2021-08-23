package de.prog3.ackerschlagkartei.fragments;

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
import de.prog3.ackerschlagkartei.viewmodels.AuthViewModel;

public class SignUpFragment extends Fragment {
    private AuthViewModel authViewModel;
    private NavController navController;

    private EditText etSignUpEmail;
    private EditText etSignUpPassword;
    private EditText etSignUpPasswordConfirm;
    private Button btnSignUp;
    private Button btnReturnToSignIn;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        this.authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        this.authViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    navController.navigate(R.id.fieldsOverviewActivity);
                }
            }
        });

        this.etSignUpEmail = view.findViewById(R.id.et_sign_up_email);
        this.etSignUpPassword = view.findViewById(R.id.et_sign_up_password);
        this.etSignUpPasswordConfirm = view.findViewById(R.id.et_sign_up_password_confirm);
        this.btnSignUp = view.findViewById(R.id.btn_sign_up);
        this.btnReturnToSignIn = view.findViewById(R.id.btn_return_to_sign_in);

        this.btnSignUp.setOnClickListener(signUpButtonClick);
        this.btnReturnToSignIn.setOnClickListener(returnToSignInClick);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.navController = Navigation.findNavController(requireActivity(), R.id.auth_nav_host_fragment);
    }

    private final View.OnClickListener returnToSignInClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            navController.navigate(R.id.action_signUpFragment_to_signInFragment);
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
}
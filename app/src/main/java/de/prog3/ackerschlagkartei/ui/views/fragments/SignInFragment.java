package de.prog3.ackerschlagkartei.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private Button btnOpenSignUp;
    private Button btnOpenResetPassword;
    private Button btnSignIn;

    private EditText etSignInEmail;
    private EditText etSignInPassword;

    public SignInFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        this.btnOpenSignUp = view.findViewById(R.id.btn_open_sign_up);
        this.btnOpenResetPassword = view.findViewById(R.id.btn_open_password_reset);
        this.btnSignIn = view.findViewById(R.id.btn_sign_in);

        this.etSignInEmail = view.findViewById(R.id.et_sign_in_email);
        this.etSignInPassword = view.findViewById(R.id.et_sign_in_password);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        this.navController = Navigation.findNavController(view);

        this.authViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    navController.navigate(R.id.action_signInFragment_to_fieldMapFragment);
                }
            }
        });

        this.btnOpenSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });

        this.btnOpenResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signInFragment_to_resetPasswordFragment);
            }
        });

        this.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etSignInEmail.getText().toString().trim().isEmpty()){
                    return;
                }

                if(etSignInPassword.getText().toString().trim().isEmpty()){
                    return;
                }

                authViewModel.login(etSignInEmail.getText().toString().trim(), etSignInPassword.getText().toString().trim());

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
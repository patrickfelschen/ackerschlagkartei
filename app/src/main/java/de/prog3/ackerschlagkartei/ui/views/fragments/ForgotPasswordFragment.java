package de.prog3.ackerschlagkartei.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.ui.viewmodels.AuthViewModel;

public class ForgotPasswordFragment extends Fragment {
    private AuthViewModel authViewModel;
    private NavController navController;

    private EditText emailEditText;
    private Button forgotPasswordButton;
    private Button returnToSignInButton;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        this.authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        this.emailEditText = view.findViewById(R.id.et_forgot_password_email);
        this.forgotPasswordButton = view.findViewById(R.id.btn_forgot_password);
        this.returnToSignInButton = view.findViewById(R.id.btn_return_to_sign_in);

        this.forgotPasswordButton.setOnClickListener(forgotPasswordButtonClick());
        this.returnToSignInButton.setOnClickListener(returnToSignInButtonClick());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.navController = Navigation.findNavController(requireActivity(), R.id.auth_nav_host_fragment);
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
                navController.navigate(R.id.action_forgotPasswordFragment_to_signInFragment);
            }
        };
    }
}
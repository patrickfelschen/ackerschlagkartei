package de.prog3.ackerschlagkartei.ui.views.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.ui.viewmodels.AuthViewModel;

public class AuthActivity extends AppCompatActivity {
    private AuthViewModel authViewModel;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_auth);

        this.navController = Navigation.findNavController(this, R.id.auth_nav_host_fragment);
        this.authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }
}
package de.prog3.ackerschlagkartei.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.viewmodels.FieldDetailsViewModel;

public class FieldDetailsActivity extends AppCompatActivity {

    private FieldDetailsViewModel fieldDetailsViewModel;

    private Toolbar fieldDetailsToolbar;

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    private String fieldUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_details);

        this.fieldDetailsViewModel = new ViewModelProvider(this).get(FieldDetailsViewModel.class);

        this.fieldUid = getIntent().getStringExtra("fieldModelUid");
        this.fieldDetailsViewModel.setFieldModelMutableLiveData(fieldUid);

        this.fieldDetailsToolbar = findViewById(R.id.field_details_toolbar);
        this.bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        this.navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        setSupportActionBar(fieldDetailsToolbar);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}
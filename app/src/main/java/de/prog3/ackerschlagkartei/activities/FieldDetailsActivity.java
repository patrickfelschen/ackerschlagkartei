package de.prog3.ackerschlagkartei.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.viewmodels.FieldsOverviewViewModel;

public class FieldDetailsActivity extends AppCompatActivity {

    private FieldsOverviewViewModel fieldViewModel;

    private Toolbar fieldDetailsToolbar;

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    private String fieldUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_details);

        this.fieldViewModel = new ViewModelProvider(this).get(FieldsOverviewViewModel.class);

        this.fieldDetailsToolbar = findViewById(R.id.field_details_toolbar);
        this.bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        this.navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        setSupportActionBar(fieldDetailsToolbar);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        this.fieldUid = getIntent().getStringExtra("fieldModelUid");
        Toast.makeText(this,fieldUid,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.field_details_menu_confirm) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.field_details_main_menu, menu);
        return true;
    }
}
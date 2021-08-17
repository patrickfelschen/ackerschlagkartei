package de.prog3.ackerschlagkartei.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.prog3.ackerschlagkartei.R;

public class FieldDetailsActivity extends AppCompatActivity {

    private Toolbar fieldDetailsToolbar;

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_details);

        this.fieldDetailsToolbar = findViewById(R.id.field_details_toolbar);
        this.bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        this.navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        setSupportActionBar(fieldDetailsToolbar);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

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
package de.prog3.ackerschlagkartei.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.viewmodels.FieldsOverviewViewModel;

enum ViewMode {LIST, MAP}

public class FieldsOverviewActivity extends AppCompatActivity {

    private FieldsOverviewViewModel fieldsOverviewViewModel;

    private NavController navController;

    private Toolbar mToolbar;
    private ViewMode viewMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_overview);

        this.fieldsOverviewViewModel = new ViewModelProvider(this).get(FieldsOverviewViewModel.class);

        this.navController = Navigation.findNavController(this, R.id.nav_host_field_overview);

        this.mToolbar = findViewById(R.id.fields_overview_toolbar);
        this.setSupportActionBar(mToolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.fields_overview_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_bar_add_field) {
            this.showAddField();
            return true;
        } else if (item.getItemId() == R.id.app_bar_change_view) {
            this.switchViewMode();
            return true;
        } else if (item.getItemId() == R.id.app_bar_search) {
            return true;
        } else if (item.getItemId() == R.id.app_bar_sign_out) {
            this.logout();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void logout() {
        this.fieldsOverviewViewModel.logout();
        startActivity(new Intent(this, SignInActivity.class));
    }

    private void switchViewMode() {
        if (this.viewMode == ViewMode.LIST) {
            setMapView();
        } else {
            setListView();
        }
    }

    private void setMapView() {
        this.navController.navigate(R.id.fieldsOverviewMapFragment);
        this.viewMode = ViewMode.MAP;
    }

    private void setListView() {
        this.navController.navigate(R.id.fieldsOverviewListFragment);
        this.viewMode = ViewMode.LIST;
    }

    private void showAddField() {
        Intent i = new Intent(this, FieldAddActivity.class);
        startActivity(i);
    }

}
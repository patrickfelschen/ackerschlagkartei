package de.prog3.ackerschlagkartei.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.fragments.FieldsOverviewListFragment;
import de.prog3.ackerschlagkartei.fragments.FieldsOverviewMapFragment;

enum ViewMode {LIST, MAP}

public class FieldsOverviewActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewMode viewMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_overview);

        this.mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            this.setMapView();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.fields_overview_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_add_field:
                this.showAddField();
                return true;
            case R.id.app_bar_change_view:
                this.switchViewMode();
                return true;
            case R.id.app_bar_search:
                return true;
            case R.id.app_bar_sign_out:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void switchViewMode() {
        if (this.viewMode == ViewMode.LIST) {
            setMapView();
        } else {
            setListView();
        }
    }

    private void setMapView() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerView, FieldsOverviewMapFragment.class, null)
                .commit();
        this.viewMode = ViewMode.MAP;
    }

    private void setListView() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerView, FieldsOverviewListFragment.class, null)
                .commit();
        this.viewMode = ViewMode.LIST;
    }

    private void showAddField() {
        Intent i = new Intent(this, FieldAddActivity.class);
        startActivity(i);
    }

}
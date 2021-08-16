package de.prog3.ackerschlagkartei.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.fragments.AddFieldChooseMappointFragment;
import de.prog3.ackerschlagkartei.fragments.AddFieldConfirmFragment;
import de.prog3.ackerschlagkartei.fragments.FieldsOverviewMapFragment;

enum AddFieldMode {MAP_POINTS, CONFIRM}

public class AddFieldActivity extends AppCompatActivity {

    private Toolbar addFieldToolbar;
    private AddFieldMode viewMode;
    private TextView instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_field);

        this.addFieldToolbar = findViewById(R.id.add_field_toolbar);
        this.instructions = findViewById(R.id.add_field_instructions);
        setSupportActionBar(addFieldToolbar);

        if(savedInstanceState == null) {
            this.setMapPointsView();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_field_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_fiel_menu_next) {
            this.switchViewMode();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchViewMode() {
        if(this.viewMode == AddFieldMode.MAP_POINTS) {
            setConfirmView();
        }
        else {
            //setMapPointsView();
        }
    }

    private void setConfirmView() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerView, AddFieldConfirmFragment.class, null)
                .commit();
        this.instructions.setText(getString(R.string.add_field_confirm_instruction));
        this.viewMode = AddFieldMode.CONFIRM;
    }

    private void setMapPointsView() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerView, AddFieldChooseMappointFragment.class, null)
                .commit();
        this.instructions.setText(getString(R.string.add_field_map_point_instruction));
        this.viewMode = AddFieldMode.MAP_POINTS;
    }
}
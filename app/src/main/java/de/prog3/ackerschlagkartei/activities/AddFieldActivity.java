package de.prog3.ackerschlagkartei.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import de.prog3.ackerschlagkartei.R;


public class AddFieldActivity extends AppCompatActivity {

    private Toolbar addFieldToolbar;
    private TextView instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_field);

        this.addFieldToolbar = findViewById(R.id.add_field_toolbar);
        this.instructions = findViewById(R.id.add_field_instructions);
        setSupportActionBar(addFieldToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_field_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_fiel_menu_confirm) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
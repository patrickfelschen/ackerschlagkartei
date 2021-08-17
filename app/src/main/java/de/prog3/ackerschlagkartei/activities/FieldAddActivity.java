package de.prog3.ackerschlagkartei.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.models.FieldModel;

import static android.content.ContentValues.TAG;


public class FieldAddActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Toolbar addFieldToolbar;
    private TextView instructions;

    private MapView mapView;
    private GoogleMap googleMap;
    private EditText etDescription;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_add);

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();

        this.addFieldToolbar = findViewById(R.id.add_field_toolbar);
        this.instructions = findViewById(R.id.add_field_instructions);
        this.etDescription = findViewById(R.id.field_add_description);
        setSupportActionBar(addFieldToolbar);

        this.mapView = findViewById(R.id.mv_field_add);
        this.mapView.onCreate(savedInstanceState);

        this.mapView.getMapAsync(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_fiel_menu_confirm) {
            onAddConfirmClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.field_add_menu, menu);
        return true;
    }

    private void onAddConfirmClick() {
        addFieldData();

        this.finish();
    }

    private void addFieldData() {
        String firebaseUserUid = firebaseAuth.getUid();
        String fieldDescription = etDescription.getText().toString();

        //TODO: get positions from maps
        List<GeoPoint> fieldPositions = new ArrayList<>();
        fieldPositions.add(new GeoPoint(10, 11));

        FieldModel newField = new FieldModel(fieldDescription, fieldPositions);

        CollectionReference ref = this.firebaseFirestore
                .collection("Users")
                .document(firebaseUserUid)
                .collection("Fields");

        ref.add(newField).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(52.52143, 7.31845)));
    }

    @Override
    public void onResume() {
        this.mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        this.mapView.onLowMemory();
    }
}
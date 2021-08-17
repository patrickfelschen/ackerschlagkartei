package de.prog3.ackerschlagkartei.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.fragments.FieldsOverviewListFragment;
import de.prog3.ackerschlagkartei.fragments.FieldsOverviewMapFragment;
import de.prog3.ackerschlagkartei.models.FieldModel;

import static android.content.ContentValues.TAG;

enum ViewMode {LIST, MAP}

public class FieldsOverviewActivity extends AppCompatActivity {
    private ArrayList<FieldModel> fieldList;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    private Toolbar mToolbar;
    private ViewMode viewMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_overview);

        this.fieldList = new ArrayList<>();

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();

        this.mToolbar = findViewById(R.id.fields_overview_toolbar);
        setSupportActionBar(mToolbar);

        this.getFieldsFromFirestore();

        if (savedInstanceState == null) {
            this.setMapView();
        }

    }

    private void getFieldsFromFirestore() {
        String firebaseUserUid = firebaseAuth.getUid();
        db.collection("Users").document(firebaseUserUid).collection("Fields")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            FieldModel fieldModel = document.toObject(FieldModel.class);
                            //Log.d("Firestore Field", fieldModel.toString());
                            fieldList.add(fieldModel);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.d("Firestore Field", e.getMessage());
                    }
                });
    }

    public List<FieldModel> getFieldList() {
        return fieldList;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
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
        if(item.getItemId() == R.id.app_bar_add_field) {
            this.showAddField();
            return true;
        }else if(item.getItemId() == R.id.app_bar_change_view) {
            this.switchViewMode();
            return true;
        }else if(item.getItemId() == R.id.app_bar_search) {
            return true;
        }else if(item.getItemId() == R.id.app_bar_sign_out) {
            firebaseAuth.signOut();
            startActivity(new Intent(this, SignInActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);

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
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", this.fieldList);
        FieldsOverviewListFragment fieldsOverviewListFragment = new FieldsOverviewListFragment();
        fieldsOverviewListFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fieldsOverviewListFragment)
                .commit();

        /*
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerView, FieldsOverviewListFragment.class, null)
                .commit();

         */
        this.viewMode = ViewMode.LIST;
    }

    private void showAddField() {
        Intent i = new Intent(this, FieldAddActivity.class);
        startActivity(i);
    }

}
package de.prog3.ackerschlagkartei.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.activities.FieldDetailsActivity;
import de.prog3.ackerschlagkartei.adapters.FieldListAdapter;
import de.prog3.ackerschlagkartei.models.FieldModel;

public class FieldsOverviewListFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private ListenerRegistration realtimeListener;
    private List<FieldModel> fieldModelList;
    private RecyclerView fieldListRecyclerView;
    private FieldListAdapter fieldListAdapter;

    public FieldsOverviewListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fields_overview_list, container, false);


        this.fieldListRecyclerView = view.findViewById(R.id.rv_field_overview_list);
        this.fieldModelList = new ArrayList<>();
        this.fieldListAdapter = new FieldListAdapter(fieldModelList);
        this.fieldListRecyclerView.setHasFixedSize(true);
        this.fieldListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.fieldListRecyclerView.setAdapter(fieldListAdapter);

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();

        getFieldsFromFirestore();

        return view;
    }

    private void getFieldsFromFirestore() {
        String uid = firebaseAuth.getUid();
        final CollectionReference ref = db.collection("Users").document(uid).collection("Fields");
        realtimeListener = ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                fieldModelList.clear();
                if(error != null) {
                    Log.w("Field data'", "Listen failed!");
                    return;
                }

                for(QueryDocumentSnapshot doc : value) {
                        FieldModel fieldModel = doc.toObject(FieldModel.class);
                        fieldModelList.add(fieldModel);

                        fieldListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private final View.OnClickListener btnOpenDetailsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(), FieldDetailsActivity.class);
            startActivity(i);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        realtimeListener.remove();
    }
}
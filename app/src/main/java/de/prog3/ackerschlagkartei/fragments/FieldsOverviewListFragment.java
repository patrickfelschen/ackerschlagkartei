package de.prog3.ackerschlagkartei.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.activities.FieldDetailsActivity;
import de.prog3.ackerschlagkartei.activities.FieldsOverviewActivity;
import de.prog3.ackerschlagkartei.models.FieldModel;

import static android.content.ContentValues.TAG;

public class FieldsOverviewListFragment extends Fragment {
    private ArrayList<FieldModel> fieldList;
    private ArrayList<String> fieldListNames;
    private ListView fieldListListView;

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

        Button btnOpenDetails = view.findViewById(R.id.btn_open_details);
        btnOpenDetails.setOnClickListener(btnOpenDetailsClick);

        this.fieldListListView = view.findViewById(R.id.lv_field_overview_list);
        this.fieldListNames = new ArrayList<>();

        Bundle bundle = getArguments();
        fieldList = bundle.getParcelableArrayList("list");
        for(FieldModel fieldModel : fieldList) {
            fieldListNames.add(fieldModel.getInfo().getDescription());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, this.fieldListNames);
        fieldListListView.setAdapter(arrayAdapter);

        return view;
    }

    private final View.OnClickListener btnOpenDetailsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(), FieldDetailsActivity.class);
            startActivity(i);
        }
    };

}
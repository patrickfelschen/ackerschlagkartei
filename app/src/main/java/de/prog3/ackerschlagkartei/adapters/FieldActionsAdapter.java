package de.prog3.ackerschlagkartei.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.activities.FieldDetailsActivity;
import de.prog3.ackerschlagkartei.fragments.FieldActionsOverview;
import de.prog3.ackerschlagkartei.models.FieldModel;
import de.prog3.ackerschlagkartei.viewmodels.FieldDetailsViewModel;

public class FieldActionsAdapter extends ArrayAdapter {
    private List objects;
    private Integer[] images;
    private Activity context;

    public FieldActionsAdapter(@NonNull Activity context, List objects, Integer[] images) {
        super(context, R.layout.list_item, objects);
        this.context = context;
        this.objects = objects;
        this.images = images;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView == null) {
            row = inflater.inflate(R.layout.list_item, null, true);
        }

        TextView labelListItem = row.findViewById(R.id.list_child);
        ImageView iconListItem = row.findViewById(R.id.list_item_icon);

        labelListItem.setText(objects.get(position).toString());
        if(images != null) {
            iconListItem.setImageResource(images[position]);
        }

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NavController navController = Navigation.findNavController(context, R.id.nav_host_fragment);
                //navController.navigate(R.id.fieldActionsOverview);
            }
        });

        return row;
    }
}

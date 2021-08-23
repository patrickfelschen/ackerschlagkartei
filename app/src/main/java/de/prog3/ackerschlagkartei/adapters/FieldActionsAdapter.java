package de.prog3.ackerschlagkartei.adapters;

import android.app.Activity;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.activities.FieldDetailsActivity;
import de.prog3.ackerschlagkartei.models.FieldModel;

public class FieldActionsAdapter extends ArrayAdapter {
    List objects;
    Integer[] images;
    Activity context;

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

        return row;
    }
}

package de.prog3.ackerschlagkartei.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import de.prog3.ackerschlagkartei.R;

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
            row = inflater.inflate(R.layout.fieldlist_item, null, true);
        }

        TextView labelListItem = row.findViewById(R.id.tv_list_item);
        TextView dateListItem = row.findViewById(R.id.textView2);
        ImageView iconListItem = row.findViewById(R.id.imageView);

        labelListItem.setText(objects.get(position).toString());
        dateListItem.setText("");
        if(images != null) {
            iconListItem.setImageResource(images[position]);
        }

        return row;
    }
}

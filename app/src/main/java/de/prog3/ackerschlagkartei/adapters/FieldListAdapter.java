package de.prog3.ackerschlagkartei.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.activities.FieldDetailsActivity;
import de.prog3.ackerschlagkartei.models.FieldModel;

public class FieldListAdapter extends RecyclerView.Adapter<FieldListAdapter.ViewHolder> {
    private final List<FieldModel> fieldModelList;

    public FieldListAdapter(List<FieldModel> fieldModelList) {
        this.fieldModelList = fieldModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fieldlist_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mFieldName.setText(fieldModelList.get(position).getInfo().getDescription());
        holder.mFieldName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.itemView.getContext(), FieldDetailsActivity.class);
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fieldModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public TextView mFieldName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            mFieldName = mView.findViewById(R.id.tv_list_item);
        }
    }
}

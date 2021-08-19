package de.prog3.ackerschlagkartei.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.activities.FieldDetailsActivity;
import de.prog3.ackerschlagkartei.models.FieldModel;

public class FieldsOverviewListAdapter extends RecyclerView.Adapter<FieldsOverviewListAdapter.ViewHolder> {
    private List<FieldModel> fieldModelList;

    public FieldsOverviewListAdapter() {
       fieldModelList = new ArrayList<>();
    }

    public void setFieldModelList(List<FieldModel> fieldModelList) {
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
        holder.itemView.setTag(fieldModelList.get(position));
        holder.mFieldName.setText(fieldModelList.get(position).getInfo().getDescription());

    }

    @Override
    public int getItemCount() {
        return this.fieldModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mFieldName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mFieldName = itemView.findViewById(R.id.tv_list_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FieldModel fieldModel = (FieldModel) itemView.getTag();
                    Intent i = new Intent(v.getContext(), FieldDetailsActivity.class);
                    i.putExtra("fieldModelUid", fieldModel.getUid());
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}

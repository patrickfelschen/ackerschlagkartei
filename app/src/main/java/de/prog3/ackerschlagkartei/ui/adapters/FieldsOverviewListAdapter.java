package de.prog3.ackerschlagkartei.ui.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.prog3.ackerschlagkartei.R;
import de.prog3.ackerschlagkartei.data.models.FieldModel;
import de.prog3.ackerschlagkartei.ui.views.activities.FieldDetailsActivity;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_field_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FieldModel fieldModel = fieldModelList.get(position);
        holder.itemView.setTag(fieldModel);
        holder.fieldName.setText(fieldModel.getInfo().getDescription());
        holder.fieldArea.setText(String.format("%.2f ha", fieldModel.getInfo().getArea()));
    }

    @Override
    public int getItemCount() {
        return this.fieldModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView fieldName;
        public TextView fieldArea;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fieldName = itemView.findViewById(R.id.tv_list_name);
            fieldArea = itemView.findViewById(R.id.tv_list_area);

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

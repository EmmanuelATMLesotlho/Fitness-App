package com.example.fitlife.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitlife.R;
import com.example.fitlife.model.Workout;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    public interface WorkoutItemListener {
        void onEditClicked(Workout workout);
        void onDeleteClicked(Workout workout);
        void onCompletedChanged(Workout workout, boolean completed);
        void onSmsClicked(Workout workout);
    }

    private final List<Workout> workouts;
    private final WorkoutItemListener listener;

    public WorkoutAdapter(List<Workout> workouts, WorkoutItemListener listener) {
        this.workouts = workouts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_workout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Workout w = workouts.get(position);
        holder.tvTitle.setText(w.getName());
        String details = w.getEquipment() + " • " + w.getSets() + "x" + w.getReps();
        holder.tvDetails.setText(details);
        holder.cbCompleted.setChecked(w.isCompleted());

        holder.cbCompleted.setOnCheckedChangeListener((buttonView, isChecked) ->
                listener.onCompletedChanged(w, isChecked));

        holder.btnEdit.setOnClickListener(v -> listener.onEditClicked(w));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClicked(w));
        holder.btnSms.setOnClickListener(v -> listener.onSmsClicked(w));
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDetails;
        CheckBox cbCompleted;
        ImageButton btnSms, btnEdit, btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvWorkoutTitle);
            tvDetails = itemView.findViewById(R.id.tvWorkoutDetails);
            cbCompleted = itemView.findViewById(R.id.cbCompleted);
            btnSms = itemView.findViewById(R.id.btnSms);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

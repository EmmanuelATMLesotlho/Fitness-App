package com.example.fitlife.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitlife.R;
import com.example.fitlife.data.WorkoutRepository;
import com.example.fitlife.model.Workout;
import com.example.fitlife.ui.adapter.WorkoutAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WorkoutListFragment extends Fragment implements WorkoutAdapter.WorkoutItemListener {

    public interface OnWorkoutInteractionListener {
        void onCreateNewWorkout();
        void onEditWorkout(int workoutId);
    }

    private OnWorkoutInteractionListener listener;
    private WorkoutAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnWorkoutInteractionListener) {
            listener = (OnWorkoutInteractionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_list, container, false);

        RecyclerView rv = view.findViewById(R.id.rvWorkouts);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WorkoutAdapter(WorkoutRepository.getInstance().getWorkouts(), this);
        rv.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fabAddWorkout);
        fab.setOnClickListener(v -> {
            if (listener != null) listener.onCreateNewWorkout();
        });

        return view;
    }

    @Override
    public void onEditClicked(Workout workout) {
        if (listener != null) listener.onEditWorkout(workout.getId());
    }

    @Override
    public void onDeleteClicked(Workout workout) {
        WorkoutRepository.getInstance().deleteWorkout(workout);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCompletedChanged(Workout workout, boolean completed) {
        workout.setCompleted(completed);
    }

    @Override
    public void onSmsClicked(Workout workout) {
        String body = "Workout: " + workout.getName() +
                "\nEquipment: " + workout.getEquipment() +
                "\nSets x Reps: " + workout.getSets() + "x" + workout.getReps() +
                "\nInstructions: " + workout.getInstructions();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:"));
        intent.putExtra("sms_body", body);
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void refresh() {
        if (adapter != null) adapter.notifyDataSetChanged();
    }
}

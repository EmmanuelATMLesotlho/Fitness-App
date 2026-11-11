package com.example.fitlife.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitlife.R;

public class HomeFragment extends Fragment {

    public interface HomeNavigationListener {
        void openCreateWorkout();
        void openWorkoutList();
    }

    private HomeNavigationListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeNavigationListener) {
            listener = (HomeNavigationListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnCreate = view.findViewById(R.id.btnGoToCreate);
        Button btnWorkouts = view.findViewById(R.id.btnGoToWorkouts);

        btnCreate.setOnClickListener(v -> {
            if (listener != null) listener.openCreateWorkout();
        });
        btnWorkouts.setOnClickListener(v -> {
            if (listener != null) listener.openWorkoutList();
        });

        return view;
    }
}

package com.example.fitlife.data;

import com.example.fitlife.model.Workout;

import java.util.ArrayList;
import java.util.List;

public class WorkoutRepository {

    private static WorkoutRepository instance;
    private final List<Workout> workouts = new ArrayList<>();
    private int nextId = 1;

    private WorkoutRepository() {}

    public static WorkoutRepository getInstance() {
        if (instance == null) {
            instance = new WorkoutRepository();
        }
        return instance;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public Workout getWorkoutById(int id) {
        for (Workout w : workouts) {
            if (w.getId() == id) return w;
        }
        return null;
    }

    public Workout addWorkout(Workout w) {
        Workout withId = new Workout(nextId++, w.getName(), w.getEquipment(),
                w.getSets(), w.getReps(), w.getInstructions(),
                w.getImageUri(), w.getLatitude(), w.getLongitude());
        workouts.add(withId);
        return withId;
    }

    public void deleteWorkout(Workout w) {
        workouts.remove(w);
    }
}

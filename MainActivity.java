package com.example.fitlife;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fitlife.ui.CreateWorkoutFragment;
import com.example.fitlife.ui.HomeFragment;
import com.example.fitlife.ui.MapFragment;
import com.example.fitlife.ui.WorkoutListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
        implements WorkoutListFragment.OnWorkoutInteractionListener,
        HomeFragment.HomeNavigationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(this::onNavItemSelected);

        loadFragment(new HomeFragment());
    }

    private boolean onNavItemSelected(@NonNull MenuItem item) {
        Fragment selected = null;
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            selected = new HomeFragment();
        } else if (id == R.id.nav_workouts) {
            selected = new WorkoutListFragment();
        } else if (id == R.id.nav_map) {
            selected = new MapFragment();
        }
        if (selected != null) {
            loadFragment(selected);
            return true;
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragmentContainer, fragment)
                .commit();
    }

    @Override
    public void onCreateNewWorkout() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFragmentContainer, new CreateWorkoutFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onEditWorkout(int workoutId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFragmentContainer, CreateWorkoutFragment.newInstance(workoutId))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openCreateWorkout() {
        onCreateNewWorkout();
    }

    @Override
    public void openWorkoutList() {
        loadFragment(new WorkoutListFragment());
    }
}

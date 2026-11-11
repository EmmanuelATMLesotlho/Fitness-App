package com.example.fitlife.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitlife.R;
import com.example.fitlife.data.WorkoutRepository;
import com.example.fitlife.model.Workout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class CreateWorkoutFragment extends Fragment {

    private static final String ARG_WORKOUT_ID = "workout_id";

    public static CreateWorkoutFragment newInstance(int workoutId) {
        CreateWorkoutFragment f = new CreateWorkoutFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_WORKOUT_ID, workoutId);
        f.setArguments(b);
        return f;
    }

    private EditText etName, etEquipment, etSets, etReps, etInstructions;
    private TextView tvImageInfo, tvLocationInfo;
    private Button btnSave, btnPickImage, btnUseCurrentLocation;

    private Uri selectedImageUri;
    private Double latitude, longitude;
    private Workout editingWorkout;

    private FusedLocationProviderClient locationClient;

    private final ActivityResultLauncher<String> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            selectedImageUri = uri;
                            tvImageInfo.setText("Image selected");
                        }
                    });

    public CreateWorkoutFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_workout, container, false);

        etName = view.findViewById(R.id.etWorkoutName);
        etEquipment = view.findViewById(R.id.etEquipment);
        etSets = view.findViewById(R.id.etSets);
        etReps = view.findViewById(R.id.etReps);
        etInstructions = view.findViewById(R.id.etInstructions);

        tvImageInfo = view.findViewById(R.id.tvImageInfo);
        tvLocationInfo = view.findViewById(R.id.tvLocationInfo);

        btnSave = view.findViewById(R.id.btnSaveWorkout);
        btnPickImage = view.findViewById(R.id.btnPickImage);
        btnUseCurrentLocation = view.findViewById(R.id.btnUseCurrentLocation);

        locationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        int editingId = getArguments() != null ? getArguments().getInt(ARG_WORKOUT_ID, -1) : -1;
        if (editingId != -1) {
            editingWorkout = WorkoutRepository.getInstance().getWorkoutById(editingId);
            if (editingWorkout != null) populateFields(editingWorkout);
        }

        btnPickImage.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
        btnUseCurrentLocation.setOnClickListener(v -> getCurrentLocation());
        btnSave.setOnClickListener(v -> saveWorkout());

        return view;
    }

    private void populateFields(Workout w) {
        etName.setText(w.getName());
        etEquipment.setText(w.getEquipment());
        etSets.setText(String.valueOf(w.getSets()));
        etReps.setText(String.valueOf(w.getReps()));
        etInstructions.setText(w.getInstructions());
        selectedImageUri = w.getImageUri() != null ? Uri.parse(w.getImageUri()) : null;
        latitude = w.getLatitude();
        longitude = w.getLongitude();
        if (latitude != null && longitude != null) {
            tvLocationInfo.setText("Tagged at: " + latitude + ", " + longitude);
        }
    }

    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }

        locationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) setLocation(location);
            else tvLocationInfo.setText("Could not get location");
        });
    }

    private void setLocation(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        tvLocationInfo.setText("Tagged at: " + latitude + ", " + longitude);
    }

    private void saveWorkout() {
        String name = etName.getText().toString().trim();
        String equipment = etEquipment.getText().toString().trim();
        String setsStr = etSets.getText().toString().trim();
        String repsStr = etReps.getText().toString().trim();
        String instructions = etInstructions.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(getContext(), "Enter a name", Toast.LENGTH_SHORT).show();
            return;
        }

        int sets = setsStr.isEmpty() ? 0 : Integer.parseInt(setsStr);
        int reps = repsStr.isEmpty() ? 0 : Integer.parseInt(repsStr);

        String image = selectedImageUri != null ? selectedImageUri.toString() : null;

        Workout w = new Workout(0, name, equipment, sets, reps,
                instructions, image, latitude, longitude);

        if (editingWorkout != null) {
            editingWorkout.updateFrom(w);
            Toast.makeText(getContext(), "Workout updated", Toast.LENGTH_SHORT).show();
        } else {
            WorkoutRepository.getInstance().addWorkout(w);
            Toast.makeText(getContext(), "Workout created", Toast.LENGTH_SHORT).show();
        }

        requireActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
    }
}

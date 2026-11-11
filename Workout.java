package com.example.fitlife.model;

import java.io.Serializable;

public class Workout implements Serializable {
    private int id;
    private String name;
    private String equipment;
    private int sets;
    private int reps;
    private String instructions;
    private boolean completed;
    private String imageUri;
    private Double latitude;
    private Double longitude;

    public Workout(int id, String name, String equipment, int sets, int reps,
                   String instructions, String imageUri,
                   Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.equipment = equipment;
        this.sets = sets;
        this.reps = reps;
        this.instructions = instructions;
        this.imageUri = imageUri;
        this.latitude = latitude;
        this.longitude = longitude;
        this.completed = false;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEquipment() { return equipment; }
    public int getSets() { return sets; }
    public int getReps() { return reps; }
    public String getInstructions() { return instructions; }
    public String getImageUri() { return imageUri; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public void updateFrom(Workout other) {
        this.name = other.name;
        this.equipment = other.equipment;
        this.sets = other.sets;
        this.reps = other.reps;
        this.instructions = other.instructions;
        this.imageUri = other.imageUri;
        this.latitude = other.latitude;
        this.longitude = other.longitude;
    }
}

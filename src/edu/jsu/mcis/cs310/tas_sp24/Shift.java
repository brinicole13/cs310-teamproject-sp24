package edu.jsu.mcis.cs310.tas_sp24;

public class Shift {
    
    private final int id;
    private final String description;
    private final int lunchDuration;
    private final int shiftDuration;

    public Shift(int id, String description, int lunchDuration, int shiftDuration) {
        this.id = id;
        this.description = description;
        this.lunchDuration = lunchDuration;
        this.shiftDuration = shiftDuration;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getLunchDuration() {
        return lunchDuration;
    }

    public int getShiftDuration() {
        return shiftDuration;
    }

    @Override
    public String toString() {
        return description;
    }
}

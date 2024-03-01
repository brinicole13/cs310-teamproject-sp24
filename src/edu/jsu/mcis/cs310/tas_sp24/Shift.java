package edu.jsu.mcis.cs310.tas_sp24;

import java.sql.Time;

public class Shift {

    private final int id;
    private final String description;
    private final Time shiftStart;
    private final Time shiftStop;
    private final int roundInterval;
    private final int gracePeriod;
    private final int dockPenalty;
    private final Time lunchStart;
    private final Time lunchStop;
    private final int lunchThreshold;

    public Shift(int id, String description, Time shiftStart, Time shiftStop, int roundInterval, int gracePeriod,
                 int dockPenalty, Time lunchStart, Time lunchStop, int lunchThreshold) {
        this.id = id;
        this.description = description;
        this.shiftStart = shiftStart;
        this.shiftStop = shiftStop;
        this.roundInterval = roundInterval;
        this.gracePeriod = gracePeriod;
        this.dockPenalty = dockPenalty;
        this.lunchStart = lunchStart;
        this.lunchStop = lunchStop;
        this.lunchThreshold = lunchThreshold;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Time getShiftStart() {
        return shiftStart;
    }

    public Time getShiftStop() {
        return shiftStop;
    }

    public int getRoundInterval() {
        return roundInterval;
    }

    public int getGracePeriod() {
        return gracePeriod;
    }

    public int getDockPenalty() {
        return dockPenalty;
    }

    public Time getLunchStart() {
        return lunchStart;
    }

    public Time getLunchStop() {
        return lunchStop;
    }

    public int getLunchThreshold() {
        return lunchThreshold;
    }

    @Override
    public String toString() {
        return "Shift " + id + ": " + shiftStart.toString().substring(0, 5) + " - " + shiftStop.toString().substring(0, 5) + " (" + (shiftStop.getTime() - shiftStart.getTime()) / 60000 + " minutes); Lunch: " + lunchStart.toString().substring(0, 5) + " - " + lunchStop.toString().substring(0, 5) + " (" + (lunchStop.getTime() - lunchStart.getTime()) / 60000 + " minutes)";
    }
}

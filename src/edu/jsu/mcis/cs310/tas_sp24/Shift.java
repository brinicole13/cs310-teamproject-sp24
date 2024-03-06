package edu.jsu.mcis.cs310.tas_sp24;

import java.util.*;
import java.time.*;

public class Shift {
    private final String description;
    private final LocalTime startTime;
    private final LocalTime stopTime;
    private final LocalTime lunchStart;
    private final LocalTime lunchStop;
    private final int id;
    private final int roundInterval;
    private final int gracePeriod;
    private final int dockPenalty;
    private final int lunchThreshold;
    private final Duration lunchDuration;
    private final Duration shiftDuration;

    public Shift(HashMap map) {
        this.id = Integer.parseInt((String)map.get("id"));
        this.description = map.get("description").toString();
        this.startTime = LocalTime.parse((String)map.get("startTime"));
        this.stopTime = LocalTime.parse((String)map.get("stopTime"));
        this.lunchStart = LocalTime.parse((String)map.get("lunchStart"));
        this.lunchStop = LocalTime.parse((String)map.get("lunchStop"));
        this.roundInterval = Integer.parseInt((String)map.get("roundInterval"));
        this.gracePeriod = Integer.parseInt((String)map.get("gracePeriod"));
        this.dockPenalty = Integer.parseInt((String)map.get("dockPenalty"));
        this.lunchThreshold = Integer.parseInt((String)map.get("lunchThreshold"));
        this.lunchDuration = Duration.between(lunchStart, lunchStop);
        this.shiftDuration = Duration.between(startTime, stopTime);
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getStopTime() {
        return stopTime;
    }

    public LocalTime getLunchStart() {
        return lunchStart;
    }

    public LocalTime getLunchStop() {
        return lunchStop;
    }

    public Duration getLunchDuration() {
        return lunchDuration;
    }

    public Duration getShiftDuration() {
        return shiftDuration;
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

    public int getLunchThreshold() {
        return lunchThreshold;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append(description).append(": ");
        s.append(startTime).append(" - ");
        s.append(stopTime).append(" (");
        s.append(shiftDuration.toMinutes()).append(" minutes); Lunch: ");
        s.append(lunchStart).append(" - ");
        s.append(lunchStop).append(" (");
        s.append(lunchDuration.toMinutes()).append(" minutes)");

        return s.toString();

    } 
    
}
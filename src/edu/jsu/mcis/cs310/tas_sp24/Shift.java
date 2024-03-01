/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp24;

import java.util.Date;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Divash
 */
public class Shift {
    // Shift class represents a work shift
    private int id;
    private String description;
    private Date startTime;
    private Date endTime;
    private int lunchDuration;
    private int shiftDuration;

    // Constructor
    public Shift(int id, String description, Date startTime, Date endTime, int lunchDuration, int shiftDuration) {
        this.id = id;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lunchDuration = lunchDuration;
        this.shiftDuration = shiftDuration;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getLunchDuration() {
        return lunchDuration;
    }

    public void setLunchDuration(int lunchDuration) {
        this.lunchDuration = lunchDuration;
    }

    public int getShiftDuration() {
        return shiftDuration;
    }

    public void setShiftDuration(int shiftDuration) {
        this.shiftDuration = shiftDuration;
    }
}

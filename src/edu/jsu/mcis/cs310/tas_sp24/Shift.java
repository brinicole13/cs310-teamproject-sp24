package edu.jsu.mcis.cs310.tas_sp24;

import java.util.*;
import java.time.*;

public class Shift {
    private final String description;
    private final int id;
    private HashMap<Integer, DailySchedule> schedule;
    private final DailySchedule defaultschedule;

    public Shift(HashMap map) {
        this.id = Integer.parseInt((String)map.get("id"));
        this.description = map.get("description").toString();
        this.defaultschedule = (DailySchedule)map.get("defaultschedule");
        this.schedule = (HashMap<Integer, DailySchedule>)map.get("schedule");
    }
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
     public DailySchedule getDefaultSchedule() {
        return defaultschedule;
    }
     
     public  DailySchedule getDailySchedule (DayOfWeek dayofweek){
         return schedule.getOrDefault(dayofweek, defaultschedule);
    }
     public HashMap<Integer, DailySchedule> getSchedule(){
         return schedule;
     }
     
    
    public int getRoundInterval(){
        return defaultschedule.getRoundinterval();
    }

    public int getGracePeriod(){
        return defaultschedule.getGraceperiod();
    }

    public int getDockPenalty() {
        return defaultschedule.getDockpenalty();
    }

    public int getLunchThreshold() {
        return defaultschedule.getLunchthreshold();
    }
    
    
    public LocalTime getStartTime() 
    {return defaultschedule.getStarttime();
    }

    public LocalTime getShiftStop() 
    {return defaultschedule.getShiftstop();
    }

    public LocalTime getLunchStart() {
        return defaultschedule.getLunchstart();
    }

    public LocalTime getLunchStop() 
    {return defaultschedule.getLunchstop();
    }
    

    public int getLunchDuration() {
        
        int lunchstopminutes = (defaultschedule.getLunchstop().getHour() * 60) + defaultschedule.getLunchstop().getMinute();
        int lunchstartminutes = (defaultschedule.getLunchstart().getHour() * 60) + defaultschedule.getLunchstart().getMinute();
        int lunchDuration = lunchstopminutes - lunchstartminutes;
        
        return lunchDuration;
    }

    public int getShiftDuration() {
        
        int shiftstopminutes = (defaultschedule.getShiftstop().getHour() * 60) + defaultschedule.getShiftstop().getMinute();
        int shiftstartminutes = (defaultschedule.getShiftstart().getHour() * 60) + defaultschedule.getShiftstart().getMinute();
        int shiftDuration = shiftstopminutes - shiftstartminutes;
        
        return shiftDuration;
    }



   

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append(getDescription()).append(':').append(' ');
        s.append(getShiftStart()).append(' ').append('-');
        s.append(' ').append(getShiftStop()).append(' ');
        s.append('(').append(getShiftDuration()).append(' ').append("minutes").append(')').append(';');
        s.append(' ').append("Lunch:").append(' ').append(defaultschedule.getLunchstart()).append(' ').append('-').append(' ');
        s.append(defaultschedule.getLunchstop()).append(' ').append('(').append(getLunchDuration()).append(' ').append("minutes").append(')');
        return s.toString();
    }    
}
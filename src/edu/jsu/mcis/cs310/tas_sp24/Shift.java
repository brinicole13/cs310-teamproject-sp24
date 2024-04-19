package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;
import java.util.HashMap;
import java.time.DayOfWeek;

/**
 * <p> class shift </p>
 */
public class Shift {
    private int id;
    private String description;
    private HashMap<Integer, DailySchedule> schedule;
    private LocalDate beginDate, endDate;
    private final DailySchedule defaultschedule;
    
    public Shift(HashMap<String, Object> hashmap) {
        this.id = Integer.parseInt((String)hashmap.get("id"));
        this.description = (String)hashmap.get("description");
        this.defaultschedule = (DailySchedule)hashmap.get("defaultschedule");
        this.schedule = (HashMap<Integer, DailySchedule>)hashmap.get("schedule");
       
    }
/**
 * 
 * @return 
 */
    public DailySchedule getDefaultschedule() {
        return defaultschedule;
    }

   
    public DailySchedule getDailySchedule (DayOfWeek dayofweek) {
        return schedule.getOrDefault(dayofweek, defaultschedule);
    }

    public String getDescription() {
        return description;
    }

    public HashMap<Integer, DailySchedule> getSchedule() {
        return schedule;
    }
    
    
    
    public int getId() {return id;}
    
    public int getRoundinterval() {
        return defaultschedule.getRoundinterval();
    }

    public int getGraceperiod() {
        return defaultschedule.getGraceperiod();
    }

    public int getDockpenalty() {
        return defaultschedule.getDockpenalty();
    }

    public int getLunchThreshold() {
        return defaultschedule.getLunchthreshold();
    }
    
    public String getDesciption() {
        return description;
    }
    
    public LocalTime getShiftstart() {
        return defaultschedule.getShiftstart();
    }

    public LocalTime getShiftstop() {
        return defaultschedule.getShiftstop();
    }

    public LocalTime getLunchstart() {
        return defaultschedule.getLunchstart();
    }

    public LocalTime getLunchstop() {
        return defaultschedule.getLunchstop();
    }
    
    public LocalDate getBeginDate() {
        return beginDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
/**
 * 
 * @return 
 */
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
/**
 * <p> Public to String </p>
 * Append
 */
    public String toString(){
     
        StringBuilder s = new StringBuilder();

        s.append(getDesciption()).append(':').append(' ');
        s.append(getShiftstart()).append(' ').append('-');
        s.append(' ').append(getShiftstop()).append(' ');
        s.append('(').append(getShiftDuration()).append(' ').append("minutes").append(')').append(';');
        s.append(' ').append("Lunch:").append(' ').append(defaultschedule.getLunchstart()).append(' ').append('-').append(' ');
        s.append(defaultschedule.getLunchstop()).append(' ').append('(').append(getLunchDuration()).append(' ').append("minutes").append(')');

        return s.toString();
    }
}
package edu.jsu.mcis.cs310.tas_sp24;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.*;

// Punch class represents a time punch
public class Punch {
    private Integer id; // Null for new punches
    private Integer terminalId;
    private Badge badge;
    private LocalDateTime originalTimestamp;
    private LocalDateTime adjustedTimestamp; // Null
    private EventType punchType;
    private PunchAdjustmentType adjustmentType;

    // Constructor for new punches
    public Punch(int terminalId, Badge badge, EventType punchType) {
        this.terminalId = terminalId;
        this.badge = badge;
        this.punchType = punchType;
        this.originalTimestamp = LocalDateTime.now(); // Initialize to current time
        originalTimestamp = originalTimestamp.withNano(0);
    }

    // Constructor for existing punches
    public Punch(int id, int terminalId, Badge badge, LocalDateTime originalTimestamp, EventType punchType) {
        this.id = id;
        this.terminalId = terminalId;
        this.badge = badge;
        this.originalTimestamp = originalTimestamp.withNano(0);
        this.punchType = punchType;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }
    
    public void adjust (Shift s){
         //Set starting variables
        adjustedTimestamp = originalTimestamp;
        int gracePeriod = s.getGracePeriod();
        int roundInterval = s.getRoundInterval();
        int dockPenalty = s.getDockPenalty();
           
        var lunchstart = adjustedTimestamp.with(s.getLunchStart());
        var lunchstop = adjustedTimestamp.with(s.getLunchStop());
        var shiftstart = adjustedTimestamp.with(s.getStartTime()); //Doing it this way prevents LocalTime conversion issues later on
        var shiftstop = adjustedTimestamp.with(s.getStopTime()); // Was returning chronounit error? until this method was used
        adjustedTimestamp = originalTimestamp;
           
        //If the punch is on the weekend
        if(adjustedTimestamp.getDayOfWeek()== DayOfWeek.SUNDAY || adjustedTimestamp.getDayOfWeek() == DayOfWeek.SATURDAY){
            //Adjust type interval round
            adjustmentType = adjustmentType.INTERVAL_ROUND;// sets type of adjustment for read out 
            //Round about way of rounding mins and secs for if comparison
            int intInSec = roundInterval * 60;
            int num1 = 60 - roundInterval;
            int num2 = roundInterval / 2;
            int roundMin = num1 + num2;
            double num4 = Math.abs(num2 - Math.round(num2));
            double num5 = num4 * 60;
            int roundSecs = (int)num5;
            int punchMin = adjustedTimestamp.getMinute();
             
             //Make sure to round to closest hour
            if(adjustedTimestamp.isEqual(adjustedTimestamp.withMinute(roundMin).withSecond(roundSecs))||adjustedTimestamp.isAfter(adjustedTimestamp.withMinute(roundMin).withSecond(roundSecs))){
               adjustedTimestamp = adjustedTimestamp.plusHours(1).withMinute(0).withSecond(0);
             }
             //Make sure to round down if within certain time
            else if(punchMin % roundInterval < (roundInterval / 2)){
                adjustedTimestamp = adjustedTimestamp.withMinute(adjustedTimestamp.getMinute() / roundInterval * roundInterval);
                adjustedTimestamp = adjustedTimestamp.withSecond(adjustedTimestamp.getSecond() / intInSec * intInSec);
             }
             //Round up in hour
            else{ 
                punchMin = (Math.round(punchMin/roundInterval) * roundInterval)+ roundInterval;
                adjustedTimestamp = adjustedTimestamp.withMinute(punchMin);
                adjustedTimestamp = adjustedTimestamp.withSecond(adjustedTimestamp.getSecond() / intInSec * intInSec);
                 
             }
           }
           
           //If the punch is on the week day
           else{
               //If clocking in
               if(punchType == punchType.CLOCK_IN){
                   //Round Interval shift start
                   if(adjustedTimestamp.isEqual(shiftstart.minusMinutes(roundInterval))|| adjustedTimestamp.isBefore(shiftstart) && adjustedTimestamp.isAfter(shiftstart.minusMinutes(roundInterval))){
                       adjustedTimestamp = shiftstart;
                       adjustmentType = adjustmentType.SHIFT_START; // sets type of adjustment for read out 
                   }
                   //adjust if within Grace Period 
                   else if(adjustedTimestamp.isAfter(adjustedTimestamp.with(shiftstart)) && adjustedTimestamp.isBefore(adjustedTimestamp.with(shiftstart).plusMinutes(gracePeriod)) || adjustedTimestamp.isEqual(shiftstart.plusMinutes(gracePeriod))){
                       adjustedTimestamp = shiftstart;
                       adjustmentType = adjustmentType.SHIFT_START;// sets type of adjustment for read out 
                   }
                   //adjust with dock penalty within shift start
                   else if(adjustedTimestamp.isAfter(shiftstart) && adjustedTimestamp.isBefore(shiftstart.plusMinutes(dockPenalty)) || adjustedTimestamp.isEqual(shiftstart.plusMinutes(dockPenalty))){
                       adjustedTimestamp = shiftstart.plusMinutes(dockPenalty);
                       adjustmentType = adjustmentType.SHIFT_DOCK;// sets type of adjustment for read out 
                   }
                   //Round Interval for lunch stop // Ending break is same as clocking back in
                   else if(adjustedTimestamp.isBefore(lunchstop) && adjustedTimestamp.isAfter(lunchstop.minusMinutes(roundInterval)) || adjustedTimestamp.isEqual(lunchstop.minusMinutes(roundInterval))){
                       adjustedTimestamp = lunchstop;
                       adjustmentType = adjustmentType.LUNCH_STOP;// sets type of adjustment for read out 
                   }
                   //adjust if within lunchstop grace period
                   else if(adjustedTimestamp.isAfter(lunchstop) && adjustedTimestamp.isBefore(lunchstop.plusMinutes(gracePeriod)) || adjustedTimestamp.isEqual(lunchstop.plusMinutes(gracePeriod))){
                       adjustedTimestamp = lunchstop;
                       adjustmentType = adjustmentType.LUNCH_STOP;// sets type of adjustment for read out 
                   }
                   //Dock 
                   else if(adjustedTimestamp.isAfter(adjustedTimestamp.with(lunchstop)) && adjustedTimestamp.isBefore(adjustedTimestamp.with(lunchstop).plusMinutes(dockPenalty)) || adjustedTimestamp.isEqual(lunchstop.plusMinutes(dockPenalty))){
                       adjustedTimestamp = lunchstop.plusMinutes(dockPenalty);
                       adjustmentType = adjustmentType.SHIFT_DOCK;// sets type of adjustment for read out 
                   }
              
                   else if(adjustedTimestamp.getMinute() % roundInterval == 0 || adjustedTimestamp.getMinute() == 0){
                       adjustedTimestamp = adjustedTimestamp.withSecond(0).withNano(0);
                       adjustmentType = adjustmentType.NONE;// sets type of adjustment for read out 
                   }
                   //Interval Round
                   else{
                       adjustmentType = adjustmentType.INTERVAL_ROUND;// sets type of adjustment for read out 
                       //Similar process as to earlier
                       int intInSec = roundInterval * 60;
                       int num1 = 60 - roundInterval;
                       int num2 = roundInterval / 2;
                       int roundMin = num1 + num2;
                       double num4 = Math.abs(num2 - Math.round(num2));
                       double num5 = num4 * 60;
                       int roundSecs = (int)num5;
                       int punchMin = adjustedTimestamp.getMinute();
                       
                       //Round to next hour
                       if(adjustedTimestamp.isEqual(adjustedTimestamp.withMinute(roundMin).withSecond(roundSecs)) || adjustedTimestamp.isAfter(adjustedTimestamp.withMinute(roundMin).withSecond(roundSecs))){
                           adjustedTimestamp = adjustedTimestamp.plusHours(1).withMinute(0).withSecond(0);
                       }
                       //Round up within hour
                       else if(punchMin % roundInterval < (roundInterval / 2)){
                           adjustedTimestamp = adjustedTimestamp.withMinute(adjustedTimestamp.getMinute() / roundInterval * roundInterval);
                           adjustedTimestamp = adjustedTimestamp.withSecond(adjustedTimestamp.getSecond() / intInSec * intInSec);
                       }
                       else {
                           punchMin = (Math.round(punchMin/roundInterval) * roundInterval) + roundInterval;
                           adjustedTimestamp = adjustedTimestamp.withMinute(punchMin);
                           adjustedTimestamp = adjustedTimestamp.withSecond(adjustedTimestamp.getSecond() / intInSec * intInSec);
                       }
                   }
                   
                   
               }
               //If clocking out
               else if(punchType == punchType.CLOCK_OUT){
                   //Same processes as the clocking in except adjusting based on clocking out times
                   if(adjustedTimestamp.isAfter(shiftstop) && adjustedTimestamp.isBefore(shiftstop.plusMinutes(roundInterval)) || adjustedTimestamp.isEqual(lunchstop.plusMinutes(roundInterval))){
                       adjustedTimestamp = shiftstop;
                       adjustmentType = adjustmentType.SHIFT_STOP;// sets type of adjustment for read out 
                   }
                   //Grace Period adjustment based on stop clock out
                   else if(adjustedTimestamp.isBefore(shiftstop) && adjustedTimestamp.isAfter(shiftstop.minusMinutes(gracePeriod)) || adjustedTimestamp.isEqual(shiftstop.minusMinutes(gracePeriod))){
                       adjustedTimestamp = shiftstop;
                       adjustmentType = adjustmentType.SHIFT_STOP;// sets type of adjustment for read out 
                   }
                   //Dock Pen
                   else if(adjustedTimestamp.isBefore(shiftstop) && adjustedTimestamp.isAfter(shiftstop.minusMinutes(dockPenalty)) || adjustedTimestamp.isEqual(shiftstop.minusMinutes(dockPenalty))){
                       adjustedTimestamp = shiftstop.minusMinutes(dockPenalty);
                       adjustmentType = adjustmentType.SHIFT_DOCK;// sets type of adjustment for read out 
                   }
                   //Lunch Start (starting break is same as clocking out)
                   else if(adjustedTimestamp.isAfter(lunchstart) && adjustedTimestamp.isBefore(lunchstart.plusMinutes(roundInterval)) || adjustedTimestamp.isEqual(lunchstart.plusMinutes(roundInterval))){
                    adjustedTimestamp = lunchstart;
                    adjustmentType = adjustmentType.LUNCH_START;// sets type of adjustment for read out 
                  } 
                else if(adjustedTimestamp.isBefore(lunchstart) && adjustedTimestamp.isAfter(lunchstart.minusMinutes(gracePeriod)) || adjustedTimestamp.isEqual(lunchstart.minusMinutes(gracePeriod))) { 
                    adjustedTimestamp = lunchstart;
                    adjustmentType = adjustmentType.LUNCH_START;// sets type of adjustment for read out 
                  }
                else if(adjustedTimestamp.isBefore(lunchstart) && adjustedTimestamp.isAfter(lunchstart.minusMinutes(dockPenalty)) || adjustedTimestamp.isEqual(lunchstart.minusMinutes(dockPenalty))) { 
                    adjustedTimestamp = lunchstart.minusMinutes(dockPenalty);
                    adjustmentType = adjustmentType.LUNCH_START;// sets type of adjustment for read out 
                  }
                    else if(adjustedTimestamp.getMinute() % 15 == 0 || 
                    adjustedTimestamp.getMinute() == 0) {
                    adjustedTimestamp = adjustedTimestamp.withSecond(0).withNano(0);
                    adjustmentType = adjustmentType.NONE; // sets type of adjustment for read out        
                }
                //Interval Round
                else {
                    adjustmentType = adjustmentType.INTERVAL_ROUND;// sets type of adjustment for read out 
                    //Similar process again
                    int intInSec = roundInterval * 60;  
                    int num1 = 60 - roundInterval;
                    int num2 = roundInterval / 2;
                    int roundMin = num1 + num2;
                    double num4 = Math.abs(num2 - Math.round(num2));
                    double num5 = num4 * 60;
                    int roundSecs = (int)num5;
                    int punchMin = adjustedTimestamp.getMinute();
                    //If round up to next hour
                    if(adjustedTimestamp.isEqual(adjustedTimestamp.withMinute(roundMin).withSecond(roundSecs)) || adjustedTimestamp.isAfter(adjustedTimestamp.withMinute(roundMin).withSecond(roundSecs))) {
                        adjustedTimestamp = adjustedTimestamp.plusHours(1).withMinute(0).withSecond(0);
                    }
                    //If round down within hour
                    else if(punchMin % roundInterval < (roundInterval / 2)) {
                        adjustedTimestamp = adjustedTimestamp.withMinute(adjustedTimestamp.getMinute() / roundInterval * roundInterval);
                        adjustedTimestamp = adjustedTimestamp.withSecond(adjustedTimestamp.getSecond() / intInSec * intInSec); 
                    }
                    //If round up within hour
                    else {
                        punchMin = (Math.round(punchMin/roundInterval) * roundInterval) + roundInterval;
                        adjustedTimestamp = adjustedTimestamp.withMinute(punchMin);
                        adjustedTimestamp = adjustedTimestamp.withSecond(adjustedTimestamp.getSecond() / intInSec * intInSec);
                    }
                
               }
               }
           }
    }

    public LocalDateTime getOriginalTimestamp(){
        return originalTimestamp;
    }

    public void setOriginalTimestamp(LocalDateTime originalTimestamp) {
        this.originalTimestamp = originalTimestamp;
    }

    public LocalDateTime getAdjustedTimestamp() {
        return adjustedTimestamp;
    }

    public void setAdjustedTimestamp(LocalDateTime adjustedTimestamp) {
        this.adjustedTimestamp = adjustedTimestamp;
    }

    public EventType getPunchType() {
        return punchType;
    }

    public void setPunchType(EventType punchType) {
        this.punchType = punchType;
    }

    public PunchAdjustmentType getadjustmentType() {
        return adjustmentType;
    }

    public void setadjustmentType(PunchAdjustmentType adjustmentType) {
        this.adjustmentType = adjustmentType;
    }
    
   public String printOriginal() {
        StringBuilder s = new StringBuilder();
        
        DateTimeFormatter newformat = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        s.append("#");
        s.append(badge.getId()).append(" ");
        s.append(punchType).append(": ");
        s.append(originalTimestamp.format(newformat));

        return s.toString().toUpperCase();
    }

   

    public String printAdjusted() {
        StringBuilder s = new StringBuilder();
        
        DateTimeFormatter newformat = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

        // #28DC3FB8 CLOCK IN: FRI 09/07/2018 07:00:00 (Shift Start)
        s.append("#");
        s.append(badge.getId()).append(" ");
        s.append(punchType).append(": ");
        s.append(adjustedTimestamp.format(newformat));

        s = new StringBuilder(s.toString().toUpperCase());
        s.append(" ").append("(").append(adjustmentType).append(")");

        return s.toString();
    }
    
    
    // Override toString() to print the original timestamp
    @Override
    public String toString() {
        return "Original Timestamp: " + printOriginal();
    }
 

}


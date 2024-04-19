package edu.jsu.mcis.cs310.tas_sp24;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * <p> class for Daily Schedule </p>
 */
public class DailySchedule{
    
    private final LocalTime shiftstart, shiftstop, lunchstart, lunchstop;
    private final int roundinterval, graceperiod, dockpenalty, lunchthreshold, shiftduration, lunchduration;
    
    
    public DailySchedule(HashMap< String, String> hashmap){
        
        this.shiftstart = LocalTime.parse(hashmap.get("shiftstart"));
        this.shiftstop = LocalTime.parse(hashmap.get("shiftstop"));
        this.roundinterval = Integer.parseInt(hashmap.get("roundinterval"));
        this.graceperiod = Integer.parseInt(hashmap.get("graceperiod"));
        this.dockpenalty = Integer.parseInt(hashmap.get("dockpenalty"));
        this.lunchstart = LocalTime.parse(hashmap.get("lunchstart"));
        this.lunchstop = LocalTime.parse(hashmap.get("lunchstop"));
        this.lunchthreshold = Integer.parseInt(hashmap.get("lunchthreshold"));
        this.shiftduration = Integer.parseInt(hashmap.get("shiftduration"));
        this.lunchduration = Integer.parseInt(hashmap.get("lunchduration"));
    }
    
/**
 * <p> Getters and Setters </p>
 * @return 
 **/
    public LocalTime getShiftstart(){
        return shiftstart;
    }
     public LocalTime getShiftstop() {
        return shiftstop;
    }

    public LocalTime getLunchstart() {
        return lunchstart;
    }

    public LocalTime getLunchstop() {
        return lunchstop;
    }

    public int getRoundinterval() {
        return roundinterval;
    }

    public int getGraceperiod() {
        return graceperiod;
    }

    public int getDockpenalty() {
        return dockpenalty;
    }

    public int getLunchthreshold() {
        return lunchthreshold;
    }
    public int getShiftduration(){
        return shiftduration;
    }
    public int getLunchduration(){
        return lunchduration;
    }

}
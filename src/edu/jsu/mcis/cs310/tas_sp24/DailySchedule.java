package edu.jsu.mcis.cs310.tas_sp24;
import java.time.LocalTime;
import java.util.HashMap;

public class DailySchedule{
    
    private final LocalTime shiftstart, shiftstop, lunchstart, lunchstop;
    private final int roundinterval, graceperiod, dockpenalty, lunchthreshold;
    
    
    public DailySchedule(HashMap< String, String> map){
        
        this.shiftstart = LocalTime.parse(map.get("shiftstart"));
        this.shiftstop = LocalTime.parse(map.get("shiftstop"));
        this.roundinterval = Integer.parseInt(map.get("roundinterval"));
        this.graceperiod = Integer.parseInt(map.get("graceperiod"));
        this.dockpenalty = Integer.parseInt(map.get("dockpenalty"));
        this.lunchstart = LocalTime.parse(map.get("lunchstart"));
        this.lunchstop = LocalTime.parse(map.get("lunchstop"));
        this.lunchthreshold = Integer.parseInt(map.get("lunchthreshold"));

    }
    
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

}
package edu.jsu.mcis.cs310.tas_sp24;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.HashMap;



/**
 * 
 * <p> public class for Schedule Override</p>
 **/
public class ScheduleOverride {
    private int id;
    private LocalDateTime start, end;
    private DayOfWeek day;
    private DailySchedule dailyschedule;
    private Badge badge;
    
    
    public ScheduleOverride(HashMap<String, Object> map) {
        this.id = Integer.parseInt((String) (map.get("id")));
        this.start = LocalDateTime.parse((CharSequence) (map.get("start")));
        this.end = LocalDateTime.parse((CharSequence) (map.get("end")));
        this.badge = (Badge)map.get("badge");
        this.day = (DayOfWeek)map.get("day");
        this.dailyschedule = (DailySchedule)map.get("dailyschedule");
        
    }
/**
 * 
 * @return 
 */
    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public DailySchedule getDailyschedule() {
        return dailyschedule;
    }

    public Badge getBadge() {
        return badge;
    }
    
    
}
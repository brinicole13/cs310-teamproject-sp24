package edu.jsu.mcis.cs310.tas_sp24;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    }

    // Constructor for existing punches
    public Punch(int id, int terminalId, Badge badge, LocalDateTime originalTimestamp, EventType punchType) {
        this.id = id;
        this.terminalId = terminalId;
        this.badge = badge;
        this.originalTimestamp = originalTimestamp;
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

    public LocalDateTime getOriginalTimestamp() {
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
        s.append("(").append(adjustmentType).append(")");

        return s.toString();
    }
    
    // Override toString() to print the original timestamp
    @Override
    public String toString() {
        return "Original Timestamp: " + printOriginal();
    }
 

}


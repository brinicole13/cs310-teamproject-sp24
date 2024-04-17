package edu.jsu.mcis.cs310.tas_sp24;

/**
 * <p> enum: set of named values </p>
 * CLOCK OUT, CLOCK IN, TIME OUT
 */
public enum EventType {

    CLOCK_OUT("CLOCK OUT"),
    CLOCK_IN("CLOCK IN"),
    TIME_OUT("TIME OUT");

    private final String description;

/**
 * 
 * @param d 
 */
    private EventType(String d) {
        description = d;
    }
    
/**
 * 
 * @return 
 */
    @Override
    public String toString() {
        return description;
    }

}

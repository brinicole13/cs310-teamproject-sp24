package edu.jsu.mcis.cs310.tas_sp24;

/**
 * <p> enum: set of named values </p>
 * Part-Time = Temporary
 * Full-Time = Full-Time
 */
public enum EmployeeType {

    PART_TIME("Temporary / Part-Time"),
    FULL_TIME("Full-Time");
    private final String description;

/**
 * 
 * @param d 
 */
    private EmployeeType(String d) {
        description = d;
    }

/**
 * <p> return description </p>
 * @return 
 */
    @Override
    public String toString() {
        return description;
    }
    
}

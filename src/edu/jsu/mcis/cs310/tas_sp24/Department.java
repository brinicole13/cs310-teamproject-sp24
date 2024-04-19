package edu.jsu.mcis.cs310.tas_sp24;

/**
 * <p> Private final int terminal id and numeric id <p>
 * Private final string description
 **/
public class Department {
    private final int terminalid;
    private final String description;
    private final int numericid;
    
    public Department(int numericid, String description, int terminalid){
       this.terminalid = terminalid;
       this.description = description;
       this.numericid = numericid;
    }
    
    public int getTerminalid(){
        return terminalid;
    }
    
    public String getDescription(){
        return description;
    }
    
    public int getNumericid(){
        return numericid;
    }
    
/**
 * <p> Append numericid and terminalid </p>
 **/
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("#").append(numericid).append(" (").append(description).append("), ");
        sb.append("Terminal ID: ").append(terminalid);
        
        return sb.toString();
    }
}

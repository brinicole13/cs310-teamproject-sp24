package edu.jsu.mcis.cs310.tas_sp24;

public class Department {
    private final int terminalid;
    private final String description;
    private final int numericid;
    
    public Department(int terminalid, String description, int numericid){
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
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("#").append(numericid).append(" (").append(description).append("), ");
        sb.append("Terminal Id: ").append(terminalid);
        
        return sb.toString();
    }
}

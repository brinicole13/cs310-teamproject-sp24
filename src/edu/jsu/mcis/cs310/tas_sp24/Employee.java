
package edu.jsu.mcis.cs310.tas_sp24;
import java.time.LocalDateTime;

public class Employee {
    //Create the needed variables
    private final String firstname;
    private final String middlename;
    private final String lastname;
    private final Integer id;
    private final LocalDateTime active;
    private final EmployeeType employeeType;
    private final Badge badge; 
    private final Shift shift; //Need to implement shift class to work
    private final Department department; 
    
    // Set variables to active versions
    public Employee(int id, String firstName, String middleName, String lastName, LocalDateTime active, Badge badge, Department department, Shift shift, EmployeeType employeeType){
        this.firstname = firstName;
        this.middlename = middleName;
        this.lastname = lastName;
        this.id = id;
        this.active = active;
        this.employeeType = employeeType;
        this.badge = badge;
        this.shift = shift;
        this.department = department;
    }
    //Get functions
    public String getFirstName(){
        return firstname;
    }
    public String getMiddleName(){
        return middlename;
    }   
    public String getLastName(){
        return lastname;
    }
    public Integer getId(){
        return id;
    }
    public LocalDateTime getActive(){
        return active;     
    }
    public EmployeeType getEmployeeType(){
        return employeeType;
    }
    public Badge getBadge(){
        return badge;
    }
    public Shift getShift(){ // waiting on shift implementation
        return shift;
    }
    public Department getDepartment(){ 
        return department;
    }
    
  @Override
    public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("ID #").append(id).append(": ").append(lastname).append(", ").append(firstname).append(" ").append(middlename)
            .append(" (#").append(badge.getId()).append("), Type: ").append(employeeType).append(", Department: ")
            .append(department.getDescription()).append(", Active: ").append(active);
    return s.toString();
    }

}

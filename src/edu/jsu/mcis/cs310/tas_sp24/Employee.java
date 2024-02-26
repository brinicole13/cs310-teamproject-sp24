
package edu.jsu.mcis.cs310.tas_sp24;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author djbla
 */
public class Employee {
    //Create the needed variables
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final Integer id;
    private final LocalDateTime current;
    private final EmployeeType employeeType;
    private final Badge badge; 
    private final Shift shift; //Need to implement shift class to work
    private final Department department; //Need to implement Department class
    
    public Employee(int id, String firstName, String middleName, String lastName, LocalDateTime current, Badge badge, Department department, Shift shift, EmployeeType employeeType){
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.id = id;
        this.current = current;
        this.employeeType = employeeType;
        this.badge = badge;
        this.shift = shift;
        this.department = department;
    }
    
    public String getFirstName(){
        return firstName;
    }
    public String getMiddleName(){
        return middleName;
    }   
    public String getLastName(){
        return lastName;
    }
    public Integer getId(){
        return id;
    }
    public LocalDateTime getCurrent(){
        return current;     
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
    public Department getDepartment(){ // waiting on department implementation
        return department;
    }
        
}

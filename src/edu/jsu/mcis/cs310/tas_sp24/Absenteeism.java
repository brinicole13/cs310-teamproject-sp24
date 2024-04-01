package edu.jsu.mcis.cs310.tas_sp24;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;


public class Absenteeism {
    //class fields
    private Employee employee;
    private final LocalDate startDate;
    private final BigDecimal percentage;
    
    //Constructor
    public Absenteeism(Employee employee, LocalDate startDate, BigDecimal percentage) {
        this.employee = employee;
        this.startDate = startDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        this.percentage = percentage;
    }
    
    //Getters and Setters
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public BigDecimal getPercentage() {
        return percentage;
    }
    
    //toString Method
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        // "#F1EE0555 (Pay Period Starting 08-05-2018): -20.00%"
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        
        s.append("#")
                .append(employee.getBadge().getId())
                .append(' ')
                .append("(Pay Period Starting ")
                .append(startDate.format(format))
                .append("): ")
                .append(percentage)
                .append("%");

        return s.toString();        
    }

    public LocalDate getPayperiod() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
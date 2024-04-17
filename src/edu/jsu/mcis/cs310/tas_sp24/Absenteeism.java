package edu.jsu.mcis.cs310.tas_sp24;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * <p>Employee, LocalDate, BigDecimal are the class fields being declared.</p>
 */
public class Absenteeism {
    //class fields
    private final Employee employee;
    private final LocalDate payperiod;
    private final BigDecimal percentage;
    
/**
 * 
 * @param employee
 * @param payperiod
 * @param absenteeism 
 */
    //Constructor
    public Absenteeism(Employee employee, LocalDate payperiod, BigDecimal absenteeism) {
        this.employee = employee;
        this.payperiod = payperiod.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        this.percentage = absenteeism.setScale(2, RoundingMode.HALF_UP);
    }
 
/**
 * <p> employee, pay period, percentage </p>
 * @return 
 */
    
    //Getters and Setters
    public Employee getEmployee() {
        return employee;
    }
    
    public LocalDate getPayPeriod() {
        return payperiod;
    }
    
    public BigDecimal getPercentage() {
        return percentage;
    }
    
/**
 * <p> Appending employee information </p>
 * @return 
 */
    
    //toString Method
    @Override
    public String toString() {
        // "#F1EE0555 (Pay Period Starting 08-05-2018): -20.00%"
        StringBuilder s = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        s.append("#").append(employee.getBadge().getId());
        s.append(" (Pay Period Starting ").append(payperiod.format(formatter)).append("): ");
        s.append(percentage).append("%");
        
        return s.toString();
                
    }
}
package edu.jsu.mcis.cs310.tas_sp24;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class Abseenteeism {
    
    private final Employee employee;
    private final LocalDate payperiod;
    private final BigDecimal percentage;
    
    public Abseenteeism(Employee employee, LocalDate payperiod, BigDecimal abseenteeism){
        this.employee = employee;
        this.payperiod = payperiod.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        this.percentage = abseenteeism.setScale(2, RoundingMode.HALF_UP);
    }
    
    public Employee getEmployee(){
        return employee;
    }
    
    public LocalDate getPayperiod(){
        return payperiod;
    }
    
    public BigDecimal getPercentage(){
        return percentage;
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("#").append(employee.getBadge().getId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        s.append(" (Pay Period Starting ").append(payperiod.format(formatter)).append("): ");
        s.append(percentage).append("%");
        return s.toString();
    }
}


package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.*;
import static edu.jsu.mcis.cs310.tas_sp24.EventType.CLOCK_IN;
import static edu.jsu.mcis.cs310.tas_sp24.EventType.CLOCK_OUT;
import static edu.jsu.mcis.cs310.tas_sp24.EventType.TIME_OUT;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * Utility class for DAOs. This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 *
 */
public final class DAOUtility {

    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist) {
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

        for (Punch punch : dailypunchlist) {
            HashMap<String, String> punchData = new HashMap<>();

            punchData.put("id", String.valueOf(punch.getId()));
            punchData.put("badgeid", String.valueOf(punch.getBadge().getId()));
            punchData.put("terminalid", String.valueOf(punch.getTerminalId()));
            punchData.put("punchtype", String.valueOf(punch.getPunchType()));
            punchData.put("adjustmenttype", String.valueOf(punch.getadjustmentType()));
            punchData.put("originaltimestamp", punch.getOriginalTimestamp().format(format).toUpperCase());
            punchData.put("adjustedtimestamp", punch.getAdjustedTimestamp().format(format).toUpperCase());

            jsonData.add(punchData);

        }
        
        

        String json = JSONValue.toJSONString(jsonData);
        return json;

    }

    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift s) {
        long totalMinutes = 0;
        long shiftDuration;

        LocalDateTime shiftStart = null;
        LocalDateTime shiftStop = null;

        Boolean isRecorded;
        Boolean isTimeout = false;

        for (Punch p : dailypunchlist) {
            isRecorded = false;

            PunchAdjustmentType type = (PunchAdjustmentType) p.getadjustmentType();

            if (type == PunchAdjustmentType.LUNCH_START || type == PunchAdjustmentType.LUNCH_STOP) {
                continue;
            }

            if (null != type) {
                switch (type) {
                    case SHIFT_START:
                        shiftStart = (LocalDateTime) p.getAdjustedTimestamp();
                        isRecorded = true;
                        break;
                    case SHIFT_STOP:
                        shiftStop = (LocalDateTime) p.getAdjustedTimestamp();
                        isRecorded = true;
                        break;
                    default:
                        break;
                }
            }

            // is the punch is docked or in interval or no adjustment was made
            if (!isRecorded) {
                switch (p.getPunchType()) {
                    case CLOCK_IN:
                        shiftStart = (LocalDateTime) p.getAdjustedTimestamp();
                        break;
                    case CLOCK_OUT:
                        shiftStop = (LocalDateTime) p.getAdjustedTimestamp();
                        break;
                    case TIME_OUT:
                        isTimeout = true;
                        break;
                }
            } else {
                continue;
            }

            if (isTimeout) {
                return (int) totalMinutes;
            }
        }

        if (shiftStop == null) {
            LocalTime sStop = s.getStopTime();
            LocalDateTime ot = (LocalDateTime) dailypunchlist.get(0).getAdjustedTimestamp();
            
            shiftStop = ot.withHour(sStop.getHour()).withMinute(sStop.getMinute()).withSecond(0).withNano(0);
        }
        
        shiftDuration = ChronoUnit.MINUTES.between(shiftStart, shiftStop);
//        shiftDuration = Duration.between(shiftStart, shiftStop).toMinutes();

        if (shiftDuration > s.getLunchThreshold()) {
            totalMinutes = shiftDuration - s.getLunchDuration().toMinutes();
        } else {
            totalMinutes = shiftDuration;
        }

        return (int) totalMinutes;
    }

    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s) {

        ArrayList<Punch> dailyPunches = new ArrayList();
        
        Integer ongoingDay = punchlist.get(0).getAdjustedTimestamp().getDayOfMonth();
        int currentDay;

        long totalMinutesWorked = 0;
        long totalWorkExpected = 0;
        long shiftDuration = s.getShiftDuration().toMinutes();

        for (Punch p: punchlist) {
            currentDay = p.getAdjustedTimestamp().getDayOfMonth();

            if (currentDay != ongoingDay) {
                // current punch is from a new day
                for (Punch a : dailyPunches) {
                    System.out.println(a.getAdjustedTimestamp().getDayOfMonth());
                }
                
                totalMinutesWorked += calculateTotalMinutes(dailyPunches, s);
                
                if (shiftDuration > s.getLunchThreshold()) {
                    totalWorkExpected += (shiftDuration - s.getLunchDuration().toMinutes());
                } else {
                    totalWorkExpected += shiftDuration;
                }
                
                dailyPunches.clear();
                dailyPunches.add(p);

                ongoingDay = currentDay;
            }

            dailyPunches.add(p);
        }
        
        System.out.println(totalMinutesWorked + " here");
        System.out.println(totalWorkExpected + " oer");
        



        return 100.0 - totalMinutesWorked / totalWorkExpected * 100;
    }       


}
package edu.jsu.mcis.cs310.tas_sp24.dao;

import com.github.cliftonlabs.json_simple.Jsoner;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.EmployeeType;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import edu.jsu.mcis.cs310.tas_sp24.Shift;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ReportDAO {

    // Badge and Employee Queries
    private static final String QUERY_EMPLOYEE = "SELECT e.*, et.description AS employeetype, s.description AS shift, b.id AS badgeid, b.description AS name, d.description AS department FROM employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id JOIN badge b ON e.badgeid = b.id JOIN department d ON e.departmentid = d.id ORDER BY e.lastname, e.firstname";
    private static final String QUERY_EMPLOYEE_DEPARTMENT = "SELECT e.*, et.description AS employeetype, s.description AS shift, b.id AS badgeid, b.description AS name, d.description AS department FROM employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id JOIN badge b ON e.badgeid = b.id JOIN department d ON e.departmentid = d.id WHERE e.departmentid = ? ORDER BY e.lastname, e.firstname";
    private static final String QUERY_EMPLOYEE2 = "SELECT e.*, et.description AS employeetype, s.description AS shift, b.id AS badgeid, b.description AS name, d.description AS department FROM employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id JOIN badge b ON e.badgeid = b.id JOIN department d ON e.departmentid = d.id ORDER BY d.description, e.firstname, e.lastname, e.middlename";
    private static final String QUERY_EMPLOYEE_DEPARTMENT2 = "SELECT e.*, et.description AS employeetype, s.description AS shift, b.id AS badgeid, b.description AS name, d.description AS department FROM employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id JOIN badge b ON e.badgeid = b.id JOIN department d ON e.departmentid = d.id WHERE e.departmentid = ? ORDER BY d.description, e.firstname, e.lastname, e.middlename";
    // WhosInWhosOut Queries
    private static final String QUERY_IN_OUT = "SELECT e.firstname, e.lastname, e.badgeid, et.description AS employeetype, s.description AS shift, MIN(CASE WHEN p.eventtypeid = 1 THEN p.timestamp END) AS arrived, CASE WHEN MIN(CASE WHEN p.eventtypeid = 1 THEN p.timestamp END) <= ? THEN 'In' ELSE 'Out' END AS status FROM employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id LEFT JOIN event p ON e.badgeid = p.badgeid AND DATE(p.timestamp) = ? GROUP BY et.description, e.lastname, e.firstname, e.badgeid, s.description ORDER BY status, employeetype, e.lastname, e.firstname";
    private static final String QUERY_IN_OUT_DEPARTMENT = "SELECT e.firstname, e.lastname, e.badgeid, et.description AS employeetype, s.description AS shift, MIN(CASE WHEN p.eventtypeid = 1 THEN p.timestamp END) AS arrived, CASE WHEN MIN(CASE WHEN p.eventtypeid = 1 THEN p.timestamp END) <= ? THEN 'In' ELSE 'Out' END AS status FROM employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id LEFT JOIN event p ON e.badgeid = p.badgeid AND DATE(p.timestamp) = ? WHERE e.departmentid = ? GROUP BY et.description, e.lastname, e.firstname, e.badgeid, s.description ORDER BY status, employeetype, e.lastname, e.firstname";
    //Absenteeism Queries
    private static final String QUERY_ABSENTEEISM = "SELECT b.description as name, d.description as department, e.badgeid, a.* from employee e JOIN absenteeism a ON e.id = a.employeeid JOIN badge b ON e.badgeid = b.id JOIN department d ON e.departmentid = d.id WHERE employeeid = ? ORDER BY a.payperiod LIMIT 12";

    // HoursSummary Queries
    private static final String QUERY_HOURS_DEFAULT = "SELECT et.description AS employeetype, s.description AS shift, e.firstname, e.lastname, e.middlename, e.badgeid, d.description AS department from employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id JOIN department d ON e.departmentid = d.id ORDER BY e.lastname, e.firstname, e.middlename";
    private static final String QUERY_HOURS_DEPARTMENT = "SELECT et.description AS employeetype, s.description AS shift, e.firstname, e.lastname, e.middlename, e.badgeid, d.description AS department from employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id JOIN department d ON e.departmentid = d.id WHERE e.departmentid = ? ORDER BY e.lastname, e.firstname, e.middlename";
    private static final String QUERY_HOURS_TYPE = "SELECT et.description AS employeetype, s.description AS shift, e.firstname, e.lastname, e.middlename, e.badgeid, d.description AS department from employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id JOIN department d ON e.departmentid = d.id WHERE e.employeetypeid = ? ORDER BY e.lastname, e.firstname, e.middlename";
    private static final String QUERY_HOURS_BOTH = "SELECT et.description AS employeetype, s.description AS shift, e.firstname, e.lastname, e.middlename, e.badgeid, d.description AS department from employee e JOIN employeetype et ON e.employeetypeid = et.id JOIN shift s ON e.shiftid = s.id JOIN department d ON e.departmentid = d.id WHERE e.employeetypeid = ? AND e.departmentid = ? ORDER BY e.lastname, e.firstname, e.middlename";

    private final DAOFactory daoFactory;

    ReportDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public String getBadgeSummary(Integer departmentId) {
        ArrayList<HashMap<String, String>> employees = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                if (departmentId == null) {
                    ps = conn.prepareStatement(QUERY_EMPLOYEE);
                } else {
                    ps = conn.prepareStatement(QUERY_EMPLOYEE_DEPARTMENT);
                    ps.setInt(1, departmentId);
                }

                rs = ps.executeQuery();

                while (rs.next()) {
                    HashMap<String, String> badgeData = new HashMap<>();

                    String badgeid = rs.getString("badgeid");
                    String employeetype = rs.getString("employeetype");
                    String department = rs.getString("department");
                    String name = rs.getString("name");

                    badgeData.put("badgeid", badgeid);
                    badgeData.put("name", name);

                    badgeData.put("department", department);

                    badgeData.put("type", employeetype);

                    employees.add(badgeData);

                }

            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }

        return Jsoner.serialize(employees);

    }

    public String getWhosInWhosOut(LocalDateTime ts, Integer departmentId) {

        ArrayList<HashMap<String, String>> employees = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                if (departmentId != null) {
                    ps = conn.prepareStatement(QUERY_IN_OUT_DEPARTMENT);
                    ps.setTimestamp(1, Timestamp.valueOf(ts));
                    ps.setDate(2, Date.valueOf(ts.toLocalDate()));
                    ps.setInt(3, departmentId);
                } else {
                    ps = conn.prepareStatement(QUERY_IN_OUT);
                    ps.setTimestamp(1, Timestamp.valueOf(ts));
                    ps.setDate(2, Date.valueOf(ts.toLocalDate()));
                }

                rs = ps.executeQuery();

                while (rs.next()) {
                    HashMap<String, String> inOutData = new HashMap<>();

                    String badgeid = rs.getString("badgeid");
                    String firstname = rs.getString("firstname");
                    String lastname = rs.getString("lastname");
                    String shift = rs.getString("shift");
                    String status = rs.getString("status");

                    Timestamp arrived = rs.getTimestamp("arrived");
                    if (arrived != null && status.equals("In")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
                        inOutData.put("arrived", arrived.toLocalDateTime().format(formatter).toUpperCase());
                    }

                    inOutData.put("employeetype", rs.getString("employeetype"));

                    inOutData.put("firstname", firstname);
                    inOutData.put("badgeid", badgeid);
                    inOutData.put("shift", shift);
                    inOutData.put("lastname", lastname);
                    inOutData.put("status", status);

                    employees.add(inOutData);
                }

            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }

        return Jsoner.serialize(employees);

    }

    public String getHoursSummary(LocalDate date, Integer departmentId, EmployeeType employeeType) {
        BadgeDAO badgeDao = daoFactory.getBadgeDAO();
        PunchDAO punchDao = daoFactory.getPunchDAO();
        ShiftDAO shiftDao = daoFactory.getShiftDAO();

        ArrayList<HashMap<String, String>> hoursSummary = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                if (departmentId != null) {
                    ps = conn.prepareStatement(QUERY_HOURS_DEPARTMENT);
                    ps.setInt(1, departmentId);
                } else if (employeeType != null) {
                    ps = conn.prepareStatement(QUERY_HOURS_TYPE);
                    ps.setInt(1, employeeType.ordinal());
                } else if (departmentId != null && employeeType != null) {
                    ps = conn.prepareStatement(QUERY_HOURS_BOTH);
                    ps.setInt(1, employeeType.ordinal());
                    ps.setInt(2, departmentId);
                } else {
                    ps = conn.prepareStatement(QUERY_HOURS_DEFAULT);
                }

                rs = ps.executeQuery();

                while (rs.next()) {
                    HashMap<String, String> hourData = new HashMap<>();

                    Badge badge = badgeDao.find(rs.getString("badgeid"));
                    Shift shift = shiftDao.find(badge);

                    LocalDate begin = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
                    LocalDate end = begin.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

                    ArrayList<Punch> punches = punchDao.list(badge, begin, end);

                    for (Punch p : punches) {
                        p.adjust(shift);
                    }

                    String employeetype = rs.getString("employeetype");
                    String middlename = rs.getString("middlename");
                    String lastname = rs.getString("lastname");
                    String department = rs.getString("department");

                    DecimalFormat df = new DecimalFormat("0.00");

                    int totalMinutesWorked = DAOUtility.calculateTotalMinutes(punches, shift);
                    int scheduledMinutes = shift.getscheduledMinutes();

                    int regularMinutes = Math.min(totalMinutesWorked, scheduledMinutes);
                    int overtimeMinutes = Math.max(0, totalMinutesWorked - scheduledMinutes);

                    double regularHours = regularMinutes / 60.0;
                    double overtimeHours = overtimeMinutes / 60.0;

                    String regularF = df.format(regularHours);
                    String overtimeF = df.format(overtimeHours);

                    if (regularHours > 0) {
                        hourData.put("employeetype", employeetype);
                        hourData.put("shift", shift.getDescription());
                        hourData.put("name", badge.getDescription());
                        hourData.put("middlename", middlename);
                        hourData.put("overtime", overtimeF);
                        hourData.put("department", department);
                        hourData.put("regular", regularF);
                        hourData.put("lastname", lastname);

                        hoursSummary.add(hourData);
                    }

                }

            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }

        return Jsoner.serialize(hoursSummary);

    }

    public String getAbsenteeismHistory(Integer employeeId) {
        HashMap<String, Object> employees = new HashMap<>();
        ArrayList<HashMap<String, Object>> absHistory = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        String name = "";
        String department = "";

        DecimalFormat df = new DecimalFormat("0.00");

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_ABSENTEEISM);
                ps.setInt(1, employeeId);

                rs = ps.executeQuery();

                double totalPercentage = 0.0;
                int numPeriods = 0;

                while (rs.next()) {
                    String badgeid = rs.getString("badgeid");
                    String payperiod = rs.getString("payperiod");
                    double percentage = rs.getDouble("percentage");
                    name = rs.getString("name");
                    department = rs.getString("department");

                    HashMap<String, Object> absData = new HashMap<>();

                    absData.put("payperiod", payperiod);
                    absData.put("percentage", df.format(percentage));

                    totalPercentage += percentage;
                    numPeriods++;
                    BigDecimal lifetime = BigDecimal.valueOf(totalPercentage / numPeriods).setScale(2, RoundingMode.HALF_DOWN);
                    absData.put("lifetime", lifetime);

                    absHistory.add(absData);

                    employees.put("badgeid", badgeid);
                }

                Collections.reverse(absHistory);

                employees.put("absenteeismhistory", absHistory);
                employees.put("name", name);
                employees.put("department", department);

            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }

        return Jsoner.serialize(employees);

    }

    public String getEmployeeSummary(Integer departmentId) {
        ArrayList<HashMap<String, Object>> employees = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                if (departmentId == null) {
                    ps = conn.prepareStatement(QUERY_EMPLOYEE2);
                } else {
                    ps = conn.prepareStatement(QUERY_EMPLOYEE_DEPARTMENT2);
                    ps.setInt(1, departmentId);
                }

                rs = ps.executeQuery();

                while (rs.next()) {
                    HashMap<String, Object> employeeData = new HashMap<>();

                    String badgeid = rs.getString("badgeid");
                    String employeetype = rs.getString("employeetype");
                    String department = rs.getString("department");
                    String firstname = rs.getString("firstname");
                    String shift = rs.getString("shift");
                    LocalDate active = rs.getDate("active").toLocalDate();
                    String middlename = rs.getString("middlename");
                    String lastname = rs.getString("lastname");

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

                    employeeData.put("firstname", firstname);
                    employeeData.put("employeetype", employeetype);
                    employeeData.put("badgeid", badgeid);
                    employeeData.put("shift", shift);
                    employeeData.put("middlename", middlename);
                    employeeData.put("active", active.format(formatter));
                    employeeData.put("department", department);
                    employeeData.put("lastname", lastname);

                    employees.add(employeeData);
                }

            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }

        }

        return Jsoner.serialize(employees);

    }

}
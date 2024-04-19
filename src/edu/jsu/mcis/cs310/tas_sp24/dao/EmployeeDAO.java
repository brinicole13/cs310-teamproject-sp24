package edu.jsu.mcis.cs310.tas_sp24.dao;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import edu.jsu.mcis.cs310.tas_sp24.EmployeeType;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Shift;
import edu.jsu.mcis.cs310.tas_sp24.Department;
import java.time.LocalDateTime;

public class EmployeeDAO {
    
    private static final String QUERY_FIND_ID = "SELECT * FROM employee WHERE id = ?";
    private static final String QUERY_FIND_BADGE = "SELECT * FROM employee WHERE badgeid = ?";

    private final DAOFactory daoFactory;

/**
 * 
 * @param daoFactory 
 */
    EmployeeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    public Employee find(int id) {

        Employee employee = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND_ID);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        BadgeDAO badgeDAO = new BadgeDAO(daoFactory);
                        ShiftDAO shiftDAO = new ShiftDAO(daoFactory);
                        DepartmentDAO departmentDAO = new DepartmentDAO(daoFactory);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        String firstname = rs.getString("firstname");
                        String middlename = rs.getString("middlename");
                        String lastname = rs.getString("lastname");
                        LocalDateTime active = LocalDateTime.parse(rs.getString("active"), formatter);
                        Badge badge = badgeDAO.find(rs.getString("badgeid"));
                        Department department = departmentDAO.find(rs.getInt("departmentid"));
                        Shift shift = shiftDAO.find(badge);
                        EmployeeType employeeType = EmployeeType.values()[rs.getInt("employeetypeid")];

                        employee = new Employee(id, firstname, middlename, lastname, active, badge, department, shift, employeeType);
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
                    rs.close();
                } catch (SQLException e) {
                    throw  new DAOException(e.getMessage());
                }
            }
        }
        return employee;
    }
/**
 * 
 * @param badge
 * @return 
 **/
    public Employee find(Badge badge) {
        Employee employee = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND_BADGE);
                ps.setString(1, badge.getId());

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        ShiftDAO shiftDAO = new ShiftDAO(daoFactory);
                        DepartmentDAO departmentDAO = new DepartmentDAO(daoFactory);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        int id = rs.getInt("id");
                        String firstname = rs.getString("firstname");
                        String middlename = rs.getString("middlename");
                        String lastname = rs.getString("lastname");
                        LocalDateTime active = LocalDateTime.parse(rs.getString("active"), formatter);

                        Department department = departmentDAO.find(rs.getInt("departmentid"));
                        Shift shift = shiftDAO.find(rs.getInt("shiftid"));
                        EmployeeType employeeType = EmployeeType.values()[rs.getInt("employeetypeid")];


                        employee = new Employee(id, firstname, middlename, lastname, active, badge, department, shift, employeeType);
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
                    rs.close();
                } catch (SQLException e) {
                    throw  new DAOException(e.getMessage());
                }
            }
        }
        return employee;
    }
}
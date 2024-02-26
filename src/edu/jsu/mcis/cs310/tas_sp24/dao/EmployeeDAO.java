package edu.jsu.mcis.cs310.tas_sp24.dao;

import java.sql.*;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import edu.jsu.mcis.cs310.tas_sp24.EmployeeType;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Shift;
import edu.jsu.mcis.cs310.tas_sp24.Department;
import java.time.LocalDateTime;

/**
 *
 * @author djbla
 */
public class EmployeeDAO {
    
    private static final String FIND_ID = "SELECT * FROM employee WHERE id = ?";
    private static final String FIND_BADGE = "SELECT * FROM employee WHERE badgeid = ?";
    private final DAOFactory daoFactory;
    
    public EmployeeDAO(DAOFactory daoFactory){
        this.daoFactory = daoFactory;
    }
    
    //use the id passed to find employee
    public Employee find(int id){
        //Reset pass through
        Employee employee = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            
            //Verify connection
            if(conn.isValid(0)){
                ps = conn.prepareStatment(FIND_ID);
                ps.setInt(1, id);
                
                boolean hasReults = ps.execute();
                
                if(hasReults){
                    rs = ps.getReultSet();
                    
                    while(rs.next()){
                        BadgeDAO badgeDAO = new BadgeDAO(daoFactory);
                        ShiftDAO shiftDAO = new ShiftDAO(daoFactory); // Need to implement shift still
                        DepartmentDAO departmentDAO = new DepartmentDAO(daoFactory); // Need to implement department still
                        //Still need to implement a formatting option for date and time
                        
                        String firstName = rs.getString("firstname");
                        String middleName = rs.getString("middlename");
                        String lastName = rs.getString("lastname");
                        Badge badge = badgeDAO.find(rs.getString("badgeid"));
                        Department department = departmentDAO.find(rs.getInt("departmentid")); //Still need to implement department
                        Shift shift = shiftDAO.find(badge); // Still need to implement shift
                        EmployeeType employeeType = EmployeeType.values()[rs.getInt("employeetypeid")];

                        employee = new Employee(id, firstName, middleName, lastName, badge, department, shift, employeeType);
                        
                    }
                }
              
            }
        } catch (SQLException e){
            throw new DAOException(e.getMessage());
        }finally {
            if (rs != null){
                try { rs.close();
            }catch (SQLException e){
                throw new DAOException(e.getMessage());
            }
        }
        if (ps != null){
            try {
                rs.close();
            } catch(SQLException e){
                throw new DAOException(e.getMessage());
            }
        }
    }
        return employee;
    
}

//Find employee based off their badge ID
 public Employee find(Badge badge) {
    Employee employee = null;

    PreparedStatement ps = null;
    ResultSet rs = null;

      try {

        Connection conn = daoFactory.getConnection();

        if (conn.isValid(0)) {

            ps = conn.prepareStatement(FIND_BADGE);
            ps.setString(1, badge.getId());

            boolean hasResults = ps.execute();

            if (hasResults) {

                rs = ps.getResultSet();

                while (rs.next()) {
                    ShiftDAO shiftDAO = new ShiftDAO(daoFactory); // Need to implement Shift
                    DepartmentDAO departmentDAO = new DepartmentDAO(daoFactory); // Need to implement Department
                    // Meed to implement some kind of date and time format

                    int id = rs.getInt("id");
                    String firstName = rs.getString("firstname");
                    String middleName = rs.getString("middlename");
                    String lastName = rs.getString("lastname");
                

                    Department department = departmentDAO.find(rs.getInt("departmentid"));
                    Shift shift = shiftDAO.find(rs.getInt("shiftid"));
                    EmployeeType employeeType = EmployeeType.values()[rs.getInt("employeetypeid")];


                    employee = new Employee(id, firstName, middleName, lastName, badge, department, shift, employeeType);
                   
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


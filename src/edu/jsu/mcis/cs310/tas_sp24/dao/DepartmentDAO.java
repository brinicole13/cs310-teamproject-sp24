package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Department;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDAO {
    private static final  String QUERY_FIND = "SELECT * FROM department WHERE id = ?";
    private final DAOFactory daoFactory;
    
    DepartmentDAO(DAOFactory daoFactory){
        this.daoFactory = daoFactory;
    }
    
    public Department find(int numericid){
        Department department = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)){
                ps = conn.prepareStatement(QUERY_FIND);
                
                ps.setInt(1,numericid);
                
                rs = ps.executeQuery();
                
                if (rs.next()) {
                    int terminalid = rs.getInt("terminaid");
                    String description = rs.getString("description");
                    
                    department = new Department(terminalid, description, numericid);
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
        return department;
    }
}

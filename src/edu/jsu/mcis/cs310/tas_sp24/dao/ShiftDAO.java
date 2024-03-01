package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Shift;
import java.sql.*;
import edu.jsu.mcis.cs310.tas_sp24.Badge;

public class ShiftDAO {

    private static final String QUERY_FIND_BY_ID = "SELECT * FROM shift WHERE id = ?";
    private static final String QUERY_FIND_BY_EMPLOYEE_ID = "SELECT * FROM shift WHERE id = ?";
    
    private final DAOFactory daoFactory;

    public ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Shift find(int id) {
        Shift shift = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            ps = conn.prepareStatement(QUERY_FIND_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                shift = extractShiftFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        
        return shift;
    }
    
    public Shift find(Badge badge) {
        Shift shift = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            ps = conn.prepareStatement(QUERY_FIND_BY_EMPLOYEE_ID);
            ps.setInt(1, employeeId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                shift = extractShiftFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            closeResources(rs, ps);
        }
        
        return shift;
    }

    private Shift extractShiftFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String description = rs.getString("description");
        int lunchDuration = rs.getInt("lunchduration");
        int shiftDuration = rs.getInt("shiftduration");
        
        return new Shift(id, description, lunchDuration, shiftDuration);
    }

    private void closeResources(ResultSet rs, PreparedStatement ps) {
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
}

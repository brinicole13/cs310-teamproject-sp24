package edu.jsu.mcis.cs310.tas_sp24.dao;


import edu.jsu.mcis.cs310.tas_sp24.Badge;
import java.sql.*;

public class BadgeDAO {

    private static final String QUERY_FIND = "SELECT * FROM badge WHERE id = ?";
    private static final String QUERY_CREATE = "INSERT INTO badge (id, description) VALUES (?, ?)";
    private static final String QUERY_DELETE = "DELETE FROM badge WHERE id = ?";
    private final DAOFactory daoFactory;

    BadgeDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;

    }
/**
 * 
 * @param id
 * @return 
 **/
    public Badge find(String id) {

        Badge badge = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setString(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {

                        String description = rs.getString("description");
                        badge = new Badge(id, description);

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

        return badge;

    }
    
     // Method to create a new badge in the database
    public boolean create(Badge badge) {
        PreparedStatement ps = null;
        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_CREATE);
                ps.setString(1, badge.getId());
                ps.setString(2, badge.getDescription());
                int rowsAffected = ps.executeUpdate();
                return rowsAffected == 1; 
                
            }

        }catch (SQLException e) {
            e.printStackTrace();
           
        }
        return false;//insertion failed
        
    }
    
    // Method to delete a badge from the database
    public boolean delete(String badgeId) {
        PreparedStatement ps = null;
        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_DELETE);
                ps.setString(1, badgeId);
                int rowsAffected = ps.executeUpdate();
                return rowsAffected == 1;
            }
        
        
    }catch (SQLException e) {
            e.printStackTrace();
            
        }
        return false;//deletion failed
        
    }
    
}
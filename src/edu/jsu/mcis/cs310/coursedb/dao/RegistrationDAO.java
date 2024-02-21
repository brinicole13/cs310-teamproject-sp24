//BRI THOMAS
package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public boolean create(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            //CONNECTING SQL
            Connection conn = daoFactory.getConnection();
            String sql = "INSERT INTO REGISTRATION (STUDENTID, TERMID, CRN) VALUES (?,?,?)";
            if (conn.isValid(0)) {
                
                //PS = PREPARESTATEMENT 1. STUDENTID, 2. TERMID, 3. CRN
                ps = conn.prepareStatement(sql);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                int Infochanged = ps.executeUpdate();
                result = Infochanged > 0;
            
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            //CONNECTING SQL
            Connection conn = daoFactory.getConnection();
            String sql = "DELETE FROM REGISTRATION WHERE STUDENTID = ? AND TERMID = ? AND CRN = ?"; 
            
            if (conn.isValid(0)) {
                
                //PS = PREPARESTATEMENT 1. STUDENTID, 2. TERMID, 3. CRN
                ps = conn.prepareStatement(sql);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3,crn);
                int Infochanged = ps.executeUpdate();
                result = Infochanged > 0;
                
                
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            // CONNECTING SQL
            Connection conn = daoFactory.getConnection();
            String sql = "DELETE FROM REGISTRATION WHERE STUDENTID = ? AND TERMID = ?";
            if (conn.isValid(0)) {
                
                //PS = PREPARESTATEMENT 1. STUDENTID, 2. TERMID
                ps = conn.prepareStatement(sql);
                ps.setInt(1, studentid);
                ps.setInt(2,termid);
                int Infochanged = ps.executeUpdate();
                result = Infochanged > 0;
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public String list(int studentid, int termid) {
        
        String result = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            //CONNECTING SQL       
            Connection conn = daoFactory.getConnection();
            String sql = "SELECT * FROM REGISTRATION WHERE STUDENTID = ? AND TERMID = ? ORDER BY CRN";
            if (conn.isValid(0)) {
                
                //PS = PREPARESTATEMENT 1. STUDENTID 2. TERMID
                ps = conn.prepareStatement(sql);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                // EXECUTEQUERY: 
                rs = ps.executeQuery();
                result = DAOUtility.getResultSetAsJson(rs);
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
}

package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Shift;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.DailySchedule;
import edu.jsu.mcis.cs310.tas_sp24.ScheduleOverride;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class ShiftDAO {

    private static final String QUERY_FIND = "SELECT * FROM shift WHERE id = ?";
    
    private static final String QUERY_FIND_BY_BADGE = "SELECT s.id, s.description, s.dailyscheduleid\n" + "from badge b\n" + "join employee e on b.id = e.badgeid\n" + "join shift s on s.id = e.shiftid\n" + "where b.id = ?";
   
    private final DAOFactory daoFactory;

    private HashMap<String, Object> hashmap = new HashMap<>();
    private HashMap<String, String> hashmap2 = new HashMap<>();

    public ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
/**
 * 
 * @param id
 * @return 
 */
    public Shift find(int id){
        Shift shift = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)){
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults){
                    rs = ps.getResultSet();

                    while (rs.next()){
                       DailyScheduleDAO dailyscheduleDAO = new DailyScheduleDAO(daoFactory);
                       hashmap.put("id", rs.getString("id"));
                       hashmap.put("description", rs.getString("description"));
                       hashmap.put("defaultschedule", dailyscheduleDAO.find(rs.getInt("dailyscheduleid")));
                    
                    }
                     shift = new Shift(hashmap);
                     
                    
                }
               
            }
        }
        catch (SQLException e) {

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
        return shift;
    }
/**
 * 
 * @param badge
 * @return 
 */
     public Shift find(Badge badge){
        Shift shift = null;
        DailyScheduleDAO defaultschedule = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)){
                ps = conn.prepareStatement(QUERY_FIND_BY_BADGE);
                ps.setString(1, badge.getId());

                boolean hasresults = ps.execute();

                if (hasresults){
                    rs = ps.getResultSet();

                    while (rs.next()){
                        DailyScheduleDAO dailyscheduleDAO = new DailyScheduleDAO(daoFactory);
                        hashmap.put("id", rs.getString("id"));
                        hashmap.put("description", rs.getString("description"));
                        hashmap.put("defaultschedule", dailyscheduleDAO.find(rs.getInt("dailyscheduleid")));
                    }
                    shift = new Shift(hashmap);
                }
                
            }
        }
        catch (SQLException e) {

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
        return shift;
    }
/**
 * 
 * @param badge
 * @param localdate
 * @return 
 */
      public Shift find(Badge badge, LocalDate localdate){
        Shift shift = null;
        DailyScheduleDAO defaultschedule = null;
        ScheduleOverrideDAO scheduleoverrideDAO = null;
        int day;
        ScheduleOverride scheduleoverride;
        HashMap<Integer, DailySchedule> schedule2;
        AbsenteeismDAO absenteeismDAO;
        DailySchedule schedule;
        
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)){
                ps = conn.prepareStatement(QUERY_FIND_BY_BADGE);
                ps.setString(1, badge.getId());

                boolean hasresults = ps.execute();

                if (hasresults){
                    rs = ps.getResultSet();

                    while (rs.next()){
                        DailyScheduleDAO dailyscheduleDAO = new DailyScheduleDAO(daoFactory);
                        hashmap.put("id", rs.getString("id"));
                        hashmap.put("description", rs.getString("description"));
                        hashmap.put("defaultschedule", dailyscheduleDAO.find(rs.getInt("dailyscheduleid")));
                        
                       
                    }
                    
                    shift = new Shift(hashmap);
                   
                }
                
            }
        }
        catch (SQLException e) {

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
        return shift;
    }
     
}
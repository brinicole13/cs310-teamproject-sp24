package edu.jsu.mcis.cs310.tas_sp24.dao;
import java.util.HashMap;
import edu.jsu.mcis.cs310.tas_sp24.*;
import java.sql.*;
import java.time.LocalDate;


public class ScheduleOverrideDAO {
    private static final String QUERY_FIND_DAILYSCHEDULE = "SELECT * FROM scheduleovveride WHERE start = ?";
     
    private final DAOFactory daoFactory;
    
    private HashMap<String, Object> hashmap = new HashMap<>();
    
    public ScheduleOverrideDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
/**
 * 
 * @param localdate
 * @return 
 **/
    public ScheduleOverride find(LocalDate localdate){
        ScheduleOverride scheduleoverride = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)){
                ps = conn.prepareStatement(QUERY_FIND_DAILYSCHEDULE);
                ps.setObject(2, localdate);

                boolean hasresults = ps.execute();

                if (hasresults){
                    rs = ps.getResultSet();

                    while (rs.next()){
                        DailyScheduleDAO dailyscheduleDAO = new DailyScheduleDAO(daoFactory);
                        BadgeDAO badgeDAO = new BadgeDAO(daoFactory);
                        hashmap.put("id", rs.getString("id"));
                        hashmap.put("start", rs.getString("start"));
                        hashmap.put("end", rs.getString("end"));
                        hashmap.put("badge", badgeDAO.find(rs.getString("badgeid")));
                        hashmap.put("day", rs.getString("day"));
                        hashmap.put("dailyschedule", dailyscheduleDAO.find(rs.getInt("dailyscheduleid")));
                    }
                    scheduleoverride = new ScheduleOverride(hashmap);
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
        return scheduleoverride;
    }
        

}
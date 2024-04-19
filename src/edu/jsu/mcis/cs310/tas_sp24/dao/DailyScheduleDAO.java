package edu.jsu.mcis.cs310.tas_sp24.dao;
import java.util.HashMap;
import edu.jsu.mcis.cs310.tas_sp24.*;
import java.sql.*;

public class DailyScheduleDAO {
    private static final String QUERY_FIND_DAILYSCHEDULE = "SELECT * FROM dailyschedule WHERE id = ?";
   
    private final DAOFactory daoFactory;
    
    private HashMap<String, String> hashmap2 = new HashMap<>();
    
    public DailyScheduleDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
/**
 * 
 * @param id
 * @return 
 */
     public DailySchedule find(int id){
        DailySchedule dailyschedule = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)){
                ps = conn.prepareStatement(QUERY_FIND_DAILYSCHEDULE);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults){
                    rs = ps.getResultSet();

                    while (rs.next()){
                        hashmap2.put("shiftstart", rs.getString("shiftstart"));
                        hashmap2.put("shiftstop", rs.getString("shiftstop"));
                        hashmap2.put("roundinterval", rs.getString("roundinterval"));
                        hashmap2.put("graceperiod", rs.getString("graceperiod"));
                        hashmap2.put("dockpenalty", rs.getString("dockpenalty"));
                        hashmap2.put("lunchstart", rs.getString("lunchstart"));
                        hashmap2.put("lunchstop", rs.getString("lunchstop"));
                        hashmap2.put("lunchthreshold", rs.getString("lunchthreshold"));
                    }
                    dailyschedule = new DailySchedule(hashmap2);
                    
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
        return dailyschedule;
    }
        

}
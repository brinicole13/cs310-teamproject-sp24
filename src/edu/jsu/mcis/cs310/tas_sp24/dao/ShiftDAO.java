package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Shift;
import java.sql.*;
import edu.jsu.mcis.cs310.tas_sp24.Badge;

public class ShiftDAO {

    private static final String QUERY_FIND_BY_ID = "SELECT * FROM shift WHERE id = ?";
    private static final String QUERY_FIND_BY_BADGE_ID = "SELECT * FROM shift WHERE id IN (SELECT shiftid FROM employee WHERE badgeid = ?)";

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
            ps = conn.prepareStatement(QUERY_FIND_BY_BADGE_ID);
            ps.setString(1, badge.getId());
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
        Time shiftStart = rs.getTime("shiftstart");
        Time shiftStop = rs.getTime("shiftstop");
        int roundInterval = rs.getInt("roundinterval");
        int gracePeriod = rs.getInt("graceperiod");
        int dockPenalty = rs.getInt("dockpenalty");
        Time lunchStart = rs.getTime("lunchstart");
        Time lunchStop = rs.getTime("lunchstop");
        int lunchThreshold = rs.getInt("lunchthreshold");

        return new Shift(id, description, shiftStart, shiftStop, roundInterval, gracePeriod, dockPenalty, lunchStart, lunchStop, lunchThreshold);
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

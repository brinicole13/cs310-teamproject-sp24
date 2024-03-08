package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import edu.jsu.mcis.cs310.tas_sp24.Punch;
import java.util.ArrayList;

public class PunchDAO{

    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    private static final String QUERY_CREATE = "INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?, ?, ?, ?)";
    private static final String QUERY_LIST = "SELECT * FROM event WHERE badgeid = ? ORDER BY timestamp";
    private static final String QUERY_LIST_E = "SELECT * FROM event WHERE badgeid = ? AND timestamp > ? LIMIT 1";

    private final DAOFactory daoFactory;

    // constructor
    PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Punch find(int id) {

        Punch punch = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setString(1, Integer.toString(id));

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {

                        int terminalid;
                        String badgeid;
                        EventType punchtype;
                        LocalDateTime originaltimestamp;

                        // get terminal id  
                        terminalid = rs.getInt("terminalid");

                        // getting badge
                        badgeid = rs.getString("badgeid");
                        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
                        Badge b = badgeDAO.find(badgeid);

                        // get punch type 
                        punchtype = EventType.values()[rs.getInt("eventtypeid")];

                        // get timestamp
                        originaltimestamp = rs.getTimestamp("timestamp").toLocalDateTime();

                        punch = new Punch(id, terminalid, b, originaltimestamp, punchtype);

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

        return punch;

    }
    public ArrayList list(Badge badge, LocalDate date) {
        ArrayList<Punch> list = new ArrayList();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_LIST);
                ps.setString(1, badge.getId());

                boolean hasresults = ps.execute();
                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {

                        Timestamp punchdate = rs.getTimestamp(4);
                        LocalDateTime local = punchdate.toLocalDateTime();
                        LocalDate ld = local.toLocalDate();

                        if (ld.equals(date)) {
                            int id = rs.getInt(1);
                            list.add(find(id));
                        }

                    }

                }

            }

            if (((list.get(list.size() - 1)).getPunchType() == EventType.CLOCK_IN)) {
                LocalDateTime newdate = list.get(list.size() - 1).getOriginalTimestamp();
                Timestamp newts = Timestamp.valueOf(newdate);

                ps = conn.prepareStatement(QUERY_LIST_E);
                ps.setString(1, badge.getId());
                ps.setString(2, newts.toString());

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        int id = rs.getInt(1);
                        list.add(find(id));
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

        return list;

    }
    
    public ArrayList list(Badge badge, LocalDate lowerDate, LocalDate upperDate) {
        ArrayList<Punch> list = new ArrayList();
        
        LocalDate date = lowerDate;
        
        while (date.isBefore(upperDate) || date.equals(upperDate)) {
            ArrayList<Punch> entries = new ArrayList();
            
            try {
                entries = list(badge, date);
            } catch (IndexOutOfBoundsException e) {}
            
            if (!entries.isEmpty() && !list.isEmpty()) {
                if (list.get(list.size() - 1).toString().equals(entries.get(0).toString())) {
                    list.remove(list.size() - 1);
                }
            }
            
            list.addAll(entries);
            
            date = date.plusDays(1);
        }
        
        return list;
    }
    public int create(Punch newPunch) {

        int punchId = 0;

        PreparedStatement ps = null;
        ResultSet rs = null;

        EmployeeDAO empDao = daoFactory.getEmployeeDAO();
        Employee emp = empDao.find(newPunch.getBadge());
        int empTerminalId = emp.getDepartment().getTerminalid();

        if (empTerminalId == newPunch.getTerminalId()) {

            try {

                Connection conn = daoFactory.getConnection();

                if (conn.isValid(0)) {

                    ps = conn.prepareStatement(QUERY_CREATE, PreparedStatement.RETURN_GENERATED_KEYS);

                    ps.setInt(1, newPunch.getTerminalId());
                    ps.setString(2, newPunch.getBadge().getId());
                    ps.setString(3, newPunch.getOriginalTimestamp().toString());
                    ps.setInt(4, newPunch.getPunchType().ordinal());

                    int rowAffected = ps.executeUpdate();

                    if (rowAffected == 1) {

                        rs = ps.getGeneratedKeys();

                        if (rs.next()) {
                            punchId = rs.getInt(1);
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

        }

        return punchId;

    }  

}
   


    

    



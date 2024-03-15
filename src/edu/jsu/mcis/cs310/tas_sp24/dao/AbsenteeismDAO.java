package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.*;
import java.sql.*;
import java.time.LocalDate;

public class AbsenteeismDAO {

    private static final String QUERY_FIND = "SELECT * FROM absenteeism WHERE employeeid = ?";
    private static final String QUERY_CREATE = "INSERT INTO absenteeism (employeeid, payperiod, percentage) VALUES (?, ?, ?)";
    private static final String QUERY_UPDATE = "UPDATE absenteeism SET percentage = ? WHERE employeeid = ?";
    private final DAOFactory daoFactory = null;

    AbsenteeismDAO(DAOFactory aThis) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    public Absenteeism find(Employee employeeid, LocalDate payperiod) {

        Absenteeism absent = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setString(1, Integer.toString(employeeid.getId()));

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {

                        double percentage = rs.getDouble("percentage");

                        absent = new Absenteeism(employeeid, payperiod, percentage);
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

        return absent;
    }

    public int create(Absenteeism newAbsenteeism) {

        int absentId = 0;

        PreparedStatement ps = null;
        ResultSet rs = null;

        Absenteeism absent2 = null;
        absent2 = this.find(newAbsenteeism.getEmployee(), newAbsenteeism.getStartDate());

        
        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                if (absent2 == null) {
                    ps = conn.prepareStatement(QUERY_CREATE, PreparedStatement.RETURN_GENERATED_KEYS);

                    ps.setInt(1, newAbsenteeism.getEmployee().getId());
                    ps.setString(2, newAbsenteeism.getStartDate().toString());
                    ps.setDouble(3, newAbsenteeism.getPercent());

                    int rowAffected = ps.executeUpdate();

                    if (rowAffected == 1) {

                        rs = ps.getGeneratedKeys();

                        if (rs.next()) {
                            absentId = rs.getInt(1);
                        }
                    }
                } 
                else {
                    ps = conn.prepareStatement(QUERY_UPDATE);

                    ps.setDouble(1, newAbsenteeism.getPercent());
                    ps.setInt(2, newAbsenteeism.getEmployee().getId());

                    int rowAffected = ps.executeUpdate();

                    if (rowAffected == 1) {

//                        rs = ps.getGeneratedKeys();
//
//                        if (rs.next()) {
//                            absentId = rs.getInt(1);
//                        }
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
        
        return absentId;
    }
}
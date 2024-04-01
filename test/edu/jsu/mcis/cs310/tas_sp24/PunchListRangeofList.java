package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import java.time.*;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class PunchListRangeofList {
     private DAOFactory daoFactory;
    
    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");
        
    }
    
    @Test
    public void testFindPunchListRange1() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts1 = LocalDate.of(2018, Month.SEPTEMBER, 27);
        LocalDate ts2 = LocalDate.of(2018, Month.SEPTEMBER, 29);

        Badge b = badgeDAO.find("021890C0");

        /* Retrieve Punch List #1 (created by DAO) */
        
        ArrayList<Punch> p1 = punchDAO.list(b, ts1, ts2);

        /* Export Punch List #1 Contents to StringBuilder */
        
        for (Punch p : p1) {
            s1.append(p.printOriginal());
            s1.append("\n");
        }

        /* Create Punch List #2 (created manually) */
        
        ArrayList<Punch> p2 = new ArrayList<>();

        /* Add Punches */
        p2.add(punchDAO.find(6072));
        p2.add(punchDAO.find(6162));
        p2.add(punchDAO.find(6218));
        p2.add(punchDAO.find(6305));
        p2.add(punchDAO.find(6352));

        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }
    
    
    
}
    


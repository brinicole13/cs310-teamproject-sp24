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
        p2.add(punchDAO.find(6392));
        p2.add(punchDAO.find(6402));
        p2.add(punchDAO.find(6432));
        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }
    
    @Test
    public void testFindPunchListRange2() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts1 = LocalDate.of(2018, Month.SEPTEMBER, 26);
        LocalDate ts2 = LocalDate.of(2018, Month.SEPTEMBER, 28);

        Badge b = badgeDAO.find("CBDE17A7");

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
        p2.add(punchDAO.find(5957));
        p2.add(punchDAO.find(6031));
        p2.add(punchDAO.find(6108));
        p2.add(punchDAO.find(6185));
        p2.add(punchDAO.find(6263));
        p2.add(punchDAO.find(6334));
        
        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }
      @Test
    public void testFindPunchListRange3() {

        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        /* Create StringBuilders for Test Output */
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        /* Create Timestamp and Badge Objects for Punch List */
        
        LocalDate ts1 = LocalDate.of(2018, Month.SEPTEMBER, 5);
        LocalDate ts2 = LocalDate.of(2018, Month.SEPTEMBER, 12);

        Badge b = badgeDAO.find("95497F63");

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
        p2.add(punchDAO.find(3463));
        p2.add(punchDAO.find(3482));
        p2.add(punchDAO.find(3548));
        p2.add(punchDAO.find(3586));
        p2.add(punchDAO.find(3659));
        p2.add(punchDAO.find(3694));
        p2.add(punchDAO.find(3765));
        p2.add(punchDAO.find(3830));
        p2.add(punchDAO.find(3916));
        p2.add(punchDAO.find(3965));
        p2.add(punchDAO.find(4057));
        p2.add(punchDAO.find(4126));
        p2.add(punchDAO.find(4234));
        p2.add(punchDAO.find(4279));

        /* Export Punch List #2 Contents to StringBuilder */
        
        for (Punch p : p2) {
            s2.append(p.printOriginal());
            s2.append("\n");
        }

        /* Compare Output Strings */
        
        assertEquals(s2.toString(), s1.toString());

    }
    
    
}
    


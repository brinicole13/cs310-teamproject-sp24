//BRI THOMAS
package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;
import java.util.ArrayList;

public class DAOUtility {
    
    public static final int TERMID_FA24 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray records = new JsonArray();
        
        try {
        
            if (rs != null) {
                //RSMD = RESULTSETMETADATA
                ResultSetMetaData rsmd = rs.getMetaData();
                
                while (rs.next()) {
                
                    JsonObject InfoData = new JsonObject();
                    
                    //FOR LOOP
                    for (int i = 1; i <= rsmd.getColumnCount(); i++){
                        
                        String columnN = rsmd.getColumnName(i);
                        Object columnV = rs.getObject(i).toString();
                        
                        InfoData.put(columnN, columnV);
                    }
                //ADD RECORDS
                records.add(InfoData);
 
            }
            
        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return Jsoner.serialize(records);
        
    }
    
}


package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ClassSchedule {
    
    private final String CSV_FILENAME = "jsu_sp24_v1.csv";
    private final String JSON_FILENAME = "jsu_sp24_v1.json";
    
    private final String CRN_COL_HEADER = "crn";
    private final String SUBJECT_COL_HEADER = "subject";
    private final String NUM_COL_HEADER = "num";
    private final String DESCRIPTION_COL_HEADER = "description";
    private final String SECTION_COL_HEADER = "section";
    private final String TYPE_COL_HEADER = "type";
    private final String CREDITS_COL_HEADER = "credits";
    private final String START_COL_HEADER = "start";
    private final String END_COL_HEADER = "end";
    private final String DAYS_COL_HEADER = "days";
    private final String WHERE_COL_HEADER = "where";
    private final String SCHEDULE_COL_HEADER = "schedule";
    private final String INSTRUCTOR_COL_HEADER = "instructor";
    private final String SUBJECTID_COL_HEADER = "subjectid";
    
    public String convertCsvToJsonString(List<String[]> csv) {
        //Create Json Objects and Array
        JsonArray section = new JsonArray();
        JsonObject CsvToJson = new JsonObject();
        JsonObject scheduletype = new JsonObject();
        JsonObject subject = new JsonObject();
        JsonObject course = new JsonObject();
        String jsonString = "";
        
        //Set up Iterator
        Iterator<String[]> iterator = csv.iterator();
        
        //Set up array for header row and get headerRow
        String[] headerRow = iterator.next();
        
        //Create headerRow hashmap
        HashMap<String, Integer> headers = new HashMap<>();
        
        //Create for loop and enumerate the headers to be used as constants
        for (int i = 0; i < headerRow.length; ++i) {
            headers.put(headerRow[i], i);
        }
        
        //Begin Processing the Csv FIle contents
        while (iterator.hasNext()) {
            
            //Get next row of CSV
            String[] row = iterator.next();
            
            String numColHeader = row[headers.get(NUM_COL_HEADER)];
            String subjectColHeader = row[headers.get(SUBJECT_COL_HEADER)];
            String typeColHeader = row[headers.get(TYPE_COL_HEADER)];
            String scheduleColHeader = row[headers.get(SCHEDULE_COL_HEADER)];
            String crnColHeader = row[headers.get(CRN_COL_HEADER)]; //needs to be int 
            String sectionColHeader = row[headers.get(SECTION_COL_HEADER)];
            String startColHeader = row[headers.get(START_COL_HEADER)];
            String endColHeader = row[headers.get(END_COL_HEADER)];
            String daysColHeader = row[headers.get(DAYS_COL_HEADER)];
            String whereColHeader = row[headers.get(WHERE_COL_HEADER)];
            String[] instructors = row[headers.get(INSTRUCTOR_COL_HEADER)].split(",");
            String[] numArray = numColHeader.split(" ");
            String subjectId = numArray[0];
            String numHeader = numArray[1];
            
             //Add the subjectId and subjectColHeader to the subject data
            subject.put(subjectId, subjectColHeader);
         
            //Add typeColHeader and scheduleColHeader to the schedule type data
            scheduletype.put(typeColHeader, scheduleColHeader);
            
            // Create courseData jsonObject
            JsonObject courseData = new JsonObject();
            
            //Add the numHeader, credit amount, decription, and subject Id into the course data
            courseData.put(NUM_COL_HEADER, numHeader);
            courseData.put(CREDITS_COL_HEADER, Integer.valueOf(row[headers.get(CREDITS_COL_HEADER)]));
            courseData.put(DESCRIPTION_COL_HEADER, row[headers.get(DESCRIPTION_COL_HEADER)]);
            courseData.put(SUBJECTID_COL_HEADER, subjectId);
            
            
            //Add to course using the new course data
            course.put(numColHeader, courseData);
   
            //Create sectionData JsonObject
            JsonObject sectionData = new JsonObject();
            
            //Add the header datas to the section data object
            sectionData.put(CRN_COL_HEADER, Integer.valueOf(row[headers.get(CRN_COL_HEADER)]));
            sectionData.put(START_COL_HEADER, startColHeader);
            sectionData.put(END_COL_HEADER, endColHeader);
            sectionData.put(SUBJECTID_COL_HEADER, subjectId);
            sectionData.put(NUM_COL_HEADER, numHeader);
            sectionData.put(SECTION_COL_HEADER, sectionColHeader);
            sectionData.put(TYPE_COL_HEADER, typeColHeader);
            sectionData.put(DAYS_COL_HEADER, daysColHeader);
            sectionData.put(WHERE_COL_HEADER, whereColHeader);
            
            //Create an instructor list
            List<String> instructorsList = new ArrayList<>();
           
            //Get rid of whitespace in out instructor list to format
           for(String instructor : instructors){
               instructorsList.add(instructor.trim());
           }
           //Add instructors into our section data
           sectionData.put(INSTRUCTOR_COL_HEADER, instructorsList);
           section.add(sectionData);
           
           //Add data into out CsvToJson Object
            CsvToJson.put("subject", subject);
            CsvToJson.put("scheduletype", scheduletype);
            CsvToJson.put("course", course);
            CsvToJson.put("section", section);
     }
       //Serialize the final jsonString and return it
       jsonString = Jsoner.serialize(CsvToJson);
       return jsonString;  
    } 

     public String convertJsonToCsvString(JsonObject jsonData) {

    //Create a copy of input data
    JsonObject dataInput = new JsonObject(jsonData);

    //taking out our objects and array from the main object so we can manipulate it: 
    JsonObject scheduleTypes = (JsonObject) dataInput.get("scheduletype");
    JsonObject subjects = (JsonObject) dataInput.get("subject");
    JsonObject courses = (JsonObject) dataInput.get("course");
    JsonArray sections = (JsonArray) dataInput.get("section");

    //Create a list to store csv
    List<String[]> csvStorage = new ArrayList<>();

    //Set the csv header 
    String[] csvHeader = {CRN_COL_HEADER, SUBJECT_COL_HEADER, NUM_COL_HEADER, DESCRIPTION_COL_HEADER, 
            SECTION_COL_HEADER, TYPE_COL_HEADER, CREDITS_COL_HEADER, START_COL_HEADER, END_COL_HEADER, 
            DAYS_COL_HEADER, WHERE_COL_HEADER, SCHEDULE_COL_HEADER, INSTRUCTOR_COL_HEADER};
    
    //Add header to storage
    csvStorage.add(csvHeader);

    //Iterate through the section
    for (int i = 0; i < sections.size(); i++) {
        //Recieve the current section 
        JsonObject currentSection = (JsonObject) sections.get(i);

        //Take the instructor names based on the current section and create a string *Make sure to format them correctly include commas etc..
        JsonArray instructorArray = (JsonArray) currentSection.get(INSTRUCTOR_COL_HEADER);
        String[] instructorNames = instructorArray.toArray(new String[0]); 
        String instructors = String.join(", ", instructorNames);
        
        //Create a hashmap that can be converted into a single csv line ** Make sure to format correctly include spaces, commas etc...
        HashMap hashData = (HashMap) courses.get((currentSection.get(SUBJECTID_COL_HEADER) + " " + currentSection.get(NUM_COL_HEADER)));
        String[] csvLines = {currentSection.get(CRN_COL_HEADER).toString(), subjects.get(currentSection.get(SUBJECTID_COL_HEADER)).toString(),
        (currentSection.get(SUBJECTID_COL_HEADER) + " " + currentSection.get(NUM_COL_HEADER)), hashData.get(DESCRIPTION_COL_HEADER).toString(),
        currentSection.get(SECTION_COL_HEADER).toString(),currentSection.get(TYPE_COL_HEADER).toString(),hashData.get(CREDITS_COL_HEADER).toString(),
        currentSection.get(START_COL_HEADER).toString(),currentSection.get(END_COL_HEADER).toString(),currentSection.get(DAYS_COL_HEADER).toString(),
        currentSection.get(WHERE_COL_HEADER).toString(),scheduleTypes.get(currentSection.get(TYPE_COL_HEADER).toString()).toString(),instructors};
        
        //add the CSV line to the list
        csvStorage.add(csvLines);
    }
    
    //Create string and csv writers 
    StringWriter stringWriter = new StringWriter();
    CSVWriter csvWriter = new CSVWriter(stringWriter, '\t', '"', '\\', "\n"); // Here to format based on the below objects provided by Mr. Snellen
    
    //write the data in our csv storage to out csv writer
    csvWriter.writeAll(csvStorage);

    //Return the data from csv to string
    return stringWriter.toString();
}
    
    public JsonObject getJson() {

        JsonObject json = getJson(getInputFileData(JSON_FILENAME));
        return json;

    }

    public JsonObject getJson(String input) {

        JsonObject json = null;

        try {
            json = (JsonObject) Jsoner.deserialize(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;

    }

    public List<String[]> getCsv() {

        List<String[]> csv = getCsv(getInputFileData(CSV_FILENAME));
        return csv;

    }

    public List<String[]> getCsv(String input) {

        List<String[]> csv = null;

        try {

            CSVReader reader = new CSVReaderBuilder(new StringReader(input)).withCSVParser(new CSVParserBuilder().withSeparator('\t').build()).build();
            csv = reader.readAll();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return csv;

    }

    public String getCsvString(List<String[]> csv) {

        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer, '\t', '"', '\\', "\n");

        csvWriter.writeAll(csv);

        return writer.toString();

    }

    private String getInputFileData(String filename) {

        StringBuilder buffer = new StringBuilder();
        String line;

        ClassLoader loader = ClassLoader.getSystemClassLoader();

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("resources" + File.separator + filename)));

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append('\n');
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();

    }

}
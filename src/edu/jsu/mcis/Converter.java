package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings, and which values should be encoded as integers!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
    
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including example code.
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            String[] colHeaders = iterator.next();
            // INSERT YOUR CODE HERE
            
            JSONArray changeRecord = new JSONArray();
            LinkedHashMap<String, JSONArray> jsonData = new LinkedHashMap<>();
            
            String[] record;
            String[] rowHeaders = new String[full.size() - 1];
            int[][] data = new int[full.size() - 1][colHeaders.length - 1];
            int counter = 0;
            int counter2= 0;
            
            while (iterator.hasNext()){
                record= iterator.next();
                for (int i = 0; i <colHeaders.length; i++){
                    if(i ==0){
                        rowHeaders[counter] = record[i];
                        counter++;
                    }
                    else{
                        data[counter2][i - 1] = Integer.parseInt(record[i]);
                    }
                }
                counter2++;
            }
            
            counter = 0;
            for(String i : rowHeaders){
                changeRecord.add(counter, i);
                counter++;
            }
            jsonData.put("rowHeaders", changeRecord);
            
            changeRecord = new JSONArray();
            JSONArray changeRecord2 = new JSONArray();
            
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    changeRecord2.add(j, data[i][j]);
                }
                changeRecord.add(i, changeRecord2);
                changeRecord2 = new JSONArray();
            }
            
            jsonData.put("data", changeRecord);
            
            changeRecord = new JSONArray();
            counter = 0;
            
            for (String i : colHeaders) {
                changeRecord.add(counter, i);
                counter++;
            }
            
            jsonData.put("colHeaders", changeRecord);
            
            results = JSONValue.toJSONString(jsonData);
        }
                
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            String[] sections = jsonString.split("],\"");
            String[] rowHeaders = sections[0].split(":\\[")[1].replaceAll("\"", "").split(",");
                        
            String[] colHeaders = sections[2].split(":")[1].replaceAll("\"", "").replace("[", "").replace("]", "").replace("}", "").split(",");
                        
            String[] beforeData = sections[1].split(":\\[")[1].split("],\\[");
            String[][] data = new String[beforeData.length][colHeaders.length];
                        
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < colHeaders.length; j++) {
                    if (j == 0) {
                        data[i][j] = rowHeaders[i];
                    } 
                    else {
                        data[i][j] = beforeData[i].split(",")[j - 1].replace("[", "").replace("]", "");
                    }
                }
            }
                        
            csvWriter.writeNext(colHeaders);
            for (String[] i : data) {
                csvWriter.writeNext(i);
            }
                        
            results = writer.toString();
            
        }
               
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }

}
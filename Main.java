import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Main {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        String inputCsvFile = "cells.csv"; // Path to your input CSV file
        String outputCsvFile = "cells_sanitized.csv"; // Path to your output CSV file
        Cell obj = new Cell();
        try {
            FileReader fileReader = new FileReader(inputCsvFile);
            CSVParser parser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            Writer writer = Files.newBufferedWriter(Paths.get(outputCsvFile));

            // Writing header to output CSV
            writer.write(String.join(",", parser.getHeaderNames()) + "\n");

            for (CSVRecord record : parser) {
                obj.setOem (record.get("oem").isEmpty() ? "null" : record.get("oem"));
                obj.setModel (record.get("model").isEmpty() ? "null" : record.get("model"));
                obj.setLaunchAnnounced (UtilityMethods.extractYear(record.get("launch_announced")));
                obj.setLaunchStatus (UtilityMethods.cleanLaunchStatus(record.get("launch_status")));
                obj.setBodyDimensions(record.get("body_dimensions").equals("-") ? "null" : record.get("body_dimensions").replace(","," "));
                obj.setBodyWeight (UtilityMethods.extractWeight(record.get("body_weight")));
                obj.setBodySim (UtilityMethods.cleanBodySim(record.get("body_sim").replace(","," ")));
                obj.setDisplayType (record.get("display_type").isEmpty() ? "null" : record.get("display_type").replace(","," "));
                obj.setDisplaySize (record.get("display_size").isEmpty() ? "null" :record.get("display_size").replace(","," "));
                obj.setDisplayResolution(record.get("display_resolution").isEmpty() ? "null" : record.get("display_resolution").replace(","," "));
                obj.setFeaturesSensors (record.get("features_sensors").isEmpty() ? "null" : record.get("features_sensors").replace(","," "));
                obj.setPlatformOs (UtilityMethods.cleanPlatformOs(record.get("platform_os").replace(","," ")));

                // Constructing cleaned record
                String cleanedRecord = String.join(",", new String[]{obj.getOem(), obj.getModel(), obj.getLaunchAnnounced(), obj.getLaunchStatus(),
                        obj.getBodyDimensions(),obj.getBodyWeight(), obj.getBodySim(), obj.getDisplayType(), obj.getDisplaySize(), obj.getDisplayResolution(),obj.getFeaturesSensors(), obj.getPlatformOs()//
                    });
                writer.write(cleanedRecord + "\n");
            }

            parser.close();
            writer.flush();
            writer.close();

            try {
                // Path to the CSV file
                String filePath = "cells_sanitized.csv";
                
                // Command to open CSV in Excel
                String[] commands = {"cmd", "/c", "start", "excel", filePath};
                
                // Execute the command
                Runtime.getRuntime().exec(commands);
            } catch (IOException e) {
                e.printStackTrace();
            }
        
 
        } catch (IOException e) {
            e.printStackTrace();
        }

    
    }

   
}


 

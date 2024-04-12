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


@SuppressWarnings("unused")
public class Main {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        String inputCsvFile = "cells.csv"; // Path to your input CSV file
        String outputCsvFile = "cells_sanitized.csv"; // Path to your output CSV file
        try {
            FileReader fileReader = new FileReader(inputCsvFile);
            CSVParser parser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            Writer writer = Files.newBufferedWriter(Paths.get(outputCsvFile));

            // Writing header to output CSV
            writer.write(String.join(",", parser.getHeaderNames()) + "\n");

            for (CSVRecord record : parser) {
                Cell cell = new Cell();
                cell.setProperty("oem",record.get("oem").isEmpty() ? "null" : record.get("oem"));
                cell.setProperty("model",record.get("model").isEmpty() ? "null" : record.get("model"));
                cell.setProperty("launch_announced",UtilityMethods.extractYear(record.get("launch_announced")));
                cell.setProperty("launch_status",UtilityMethods.cleanLaunchStatus(record.get("launch_status")));
                cell.setProperty("body_dimension",record.get("body_dimensions").equals("-") ? "null" : record.get("body_dimensions").replace(","," "));
                cell.setProperty("body_weight",UtilityMethods.extractWeight(record.get("body_weight")));
                cell.setProperty("body_sim",UtilityMethods.cleanBodySim(record.get("body_sim").replace(","," ")));
                cell.setProperty("display_type",record.get("display_type").isEmpty() ? "null" : record.get("display_type").replace(","," "));
                cell.setProperty("display_size",record.get("display_size").isEmpty() ? "null" :record.get("display_size").replace(","," "));
                cell.setProperty("display_resolution",record.get("display_resolution").isEmpty() ? "null" : record.get("display_resolution").replace(","," "));
                cell.setProperty("features_sensors",record.get("features_sensors").isEmpty() ? "null" : record.get("features_sensors").replace(","," "));
                cell.setProperty("platform_os",UtilityMethods.cleanPlatformOs(record.get("platform_os").replace(","," ")));
                // Constructing cleaned record
                List<String> values = parser.getHeaderNames().stream()
                                           .map(header -> cell.getProperty(header))
                                           .collect(Collectors.toList());
                String cleanedRecord = String.join(",", values);
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


 

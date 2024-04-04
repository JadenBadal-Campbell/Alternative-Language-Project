import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;


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
                String oem = record.get("oem").isEmpty() ? "null" : record.get("oem");
                String model = record.get("model").isEmpty() ? "null" : record.get("model");
                String launchAnnounced = UtilityMethods.extractYear(record.get("launch_announced"));
                String launchStatus = UtilityMethods.cleanLaunchStatus(record.get("launch_status"));
                String bodyDimensions = record.get("body_dimensions").equals("-") ? "null" : record.get("body_dimensions").replace(","," ");
                String bodyWeight = UtilityMethods.extractWeight(record.get("body_weight"));
                String bodySim = UtilityMethods.cleanBodySim(record.get("body_sim").replace(","," "));
                String displayType = record.get("display_type").isEmpty() ? "null" : record.get("display_type").replace(","," ");
                String displaySize =  record.get("display_size").isEmpty() ? "null" :record.get("display_size").replace(","," ");
                String displayResolution = record.get("display_resolution").isEmpty() ? "null" : record.get("display_resolution").replace(","," ");
                String featuresSensors = record.get("features_sensors").isEmpty() ? "null" : record.get("features_sensors").replace(","," ");
                String platformOs = UtilityMethods.cleanPlatformOs(record.get("platform_os").replace(","," "));

                // Constructing cleaned record
                String cleanedRecord = String.join(",", new String[]{oem, model, launchAnnounced, launchStatus,
                        bodyDimensions,bodyWeight, bodySim, displayType, displaySize, displayResolution,featuresSensors, platformOs//
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


 

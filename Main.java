import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                String launchAnnounced = extractYear(record.get("launch_announced"));
                String launchStatus = cleanLaunchStatus(record.get("launch_status"));
                String bodyDimensions = record.get("body_dimensions").equals("-") ? "null" : record.get("body_dimensions").replace(","," ");
                String bodyWeight = extractWeight(record.get("body_weight"));
                String bodySim = cleanBodySim(record.get("body_sim").replace(","," "));
                String displayType = record.get("display_type").isEmpty() ? "null" : record.get("display_type").replace(","," ");
                String displaySize =  record.get("display_size").isEmpty() ? "null" :record.get("display_size").replace(","," ");
                String displayResolution = record.get("display_resolution").isEmpty() ? "null" : record.get("display_resolution").replace(","," ");
                String featuresSensors = record.get("features_sensors").isEmpty() ? "null" : record.get("features_sensors").replace(","," ");
                String platformOs = cleanPlatformOs(record.get("platform_os").replace(","," "));

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

    private static String extractYear(String launchAnnounced) {
        Pattern pattern = Pattern.compile("\\b(19|20)\\d{2}\\b");
        Matcher matcher = pattern.matcher(launchAnnounced);
        return matcher.find() ? matcher.group(0) : "null";
    }
    
    private static String extractWeight(String bodyWeight) {
        Pattern pattern = Pattern.compile("(\\d+) g");
        Matcher matcher = pattern.matcher(bodyWeight);
        return matcher.find() ? matcher.group(1) : "null";
    }

    private static String extractDisplaySize(String displaySize) {
        Pattern pattern = Pattern.compile("(\\d+\\.?\\d*) inches");
        Matcher matcher = pattern.matcher(displaySize);
        return matcher.find() ? matcher.group(1) : "null";
    }

    private static String cleanLaunchStatus(String launchStatus) {
        if (launchStatus.contains("Discontinued") || launchStatus.contains("Cancelled")) {
            return launchStatus;
        } else {
            return extractYear(launchStatus);
        }
    }

    
    private static String cleanBodySim(String bodySim) {
        return bodySim.equals("No") || bodySim.equals("Yes") ? "null" : bodySim;
    }

    
    private static String cleanPlatformOs(String platformOs) {
        if (platformOs == null || platformOs.isEmpty()) {
            return "null";
        }
        int commaIndex = platformOs.indexOf(',');
        return commaIndex != -1 ? platformOs.substring(0, commaIndex) : platformOs;
    }
}


 

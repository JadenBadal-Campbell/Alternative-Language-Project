import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String inputCsvFile = "cells.csv"; // Path to your input CSV file
        //String outputCsvFile = "cells_sanitized.csv"; // Path to your output CSV file
        List<Cell> cells = readCellsFromCSV(inputCsvFile);

        // Queries
        System.out.println("Company with the highest average weight: " + companyWithHighestAverageWeight(cells));
        System.out.println("Phones announced and released in different years:");
        phonesAnnouncedAndReleasedDifferentYears(cells).forEach(System.out::println);
        System.out.println("Number of phones with only one feature sensor: " + countPhonesWithSingleFeatureSensor(cells));
        System.out.println("Year with the most phones launched after 1999: " + yearWithMostPhonesLaunched(cells));

        // Prepare to write results to a text file
        File file = new File("results.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Company with the highest average weight: " + companyWithHighestAverageWeight(cells) + "\n");
            writer.write("Phones announced and released in different years:\n");
            for (String detail : phonesAnnouncedAndReleasedDifferentYears(cells)) {
                writer.write(detail + "\n");
            }
            writer.write("Number of phones with only one feature sensor: " + countPhonesWithSingleFeatureSensor(cells) + "\n");
            writer.write("Year with the most phones launched after 1999: " + yearWithMostPhonesLaunched(cells) + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    

    public static List<Cell> readCellsFromCSV(String filePath) {
        List<Cell> cells = new ArrayList<>();
        try (FileReader fileReader = new FileReader(filePath);
             @SuppressWarnings("deprecation")
            CSVParser parser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : parser) {
                Cell cell = new Cell();
                cell.setProperty("oem", UtilityMethods.cleanString(record.get("oem").isEmpty()?"null":record.get("oem")));
                cell.setProperty("model", UtilityMethods.cleanString(record.get("model").isEmpty() ? "null" : record.get("model")));
                cell.setProperty("launch_announced", UtilityMethods.extractYear(record.get("launch_announced")));
                cell.setProperty("launch_status", UtilityMethods.cleanLaunchStatus(record.get("launch_status")));

                String bodyDims = record.get("body_dimensions").trim();
                if (bodyDims.isEmpty() || bodyDims.equals("-")){
                    cell.setProperty("body_dimensions", "null");
                } else {
                    bodyDims = bodyDims.replace(",", ";");  // Replace commas if any to avoid CSV format issues
                    cell.setProperty("body_dimensions", bodyDims);
                }

                //cell.setProperty("body_dimensions", UtilityMethods.cleanString(record.get("body_dimensions")));

                cell.setProperty("body_weight", UtilityMethods.extractWeight(record.get("body_weight")));
                cell.setProperty("body_sim", UtilityMethods.cleanBodySim(record.get("body_sim")));
                cell.setProperty("display_type", UtilityMethods.cleanString(record.get("display_type")));
                cell.setProperty("display_size", UtilityMethods.extractDisplaySize(record.get("display_size")));
                cell.setProperty("display_resolution", UtilityMethods.cleanString(record.get("display_resolution")));
                cell.setProperty("features_sensors", UtilityMethods.cleanString(record.get("features_sensors")));
                cell.setProperty("platform_os", UtilityMethods.cleanPlatformOS(record.get("platform_os")));
                cells.add(cell);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cells;
    }

    // Additional methods (implementation from previous explanations)
    public static String companyWithHighestAverageWeight(List<Cell> cells) {
        return cells.stream()
            .collect(Collectors.groupingBy(cell -> cell.getProperty("oem"),
                   Collectors.averagingDouble(cell -> Double.parseDouble(cell.getProperty("body_weight").equals("null") ? "0" : cell.getProperty("body_weight")))))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("No data");
    }

    public static List<String> phonesAnnouncedAndReleasedDifferentYears(List<Cell> cells) {
        return cells.stream()
            .filter(cell -> !cell.getProperty("launch_announced").equals(cell.getProperty("launch_status")))
            .filter(cell -> !cell.getProperty("launch_announced").equals("null") && !cell.getProperty("launch_status").equals("null"))
            .map(cell -> cell.getProperty("oem") + " " + cell.getProperty("model") + " - Announced: "
                  + cell.getProperty("launch_announced") + ", Released: " + cell.getProperty("launch_status"))
            .collect(Collectors.toList());
    }

    public static long countPhonesWithSingleFeatureSensor(List<Cell> cells) {
        return cells.stream()
            .filter(cell -> cell.getProperty("features_sensors").split(",").length == 1 && !cell.getProperty("features_sensors").equals("null"))
            .count();
    }

    public static String yearWithMostPhonesLaunched(List<Cell> cells) {
        return cells.stream()
            .map(cell -> cell.getProperty("launch_announced"))
            .filter(year -> !year.equals("null") && Integer.parseInt(year) > 1999)
            .collect(Collectors.groupingBy(year -> year, Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("No data");
    }
}

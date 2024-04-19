import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Cell {
    private Map<String, String> properties;

    public Cell() {
        properties = new HashMap<>();
    }

    public String getProperty(String key) {
        if (!properties.containsKey(key)) {
            throw new IllegalArgumentException("Property " + key + " does not exist.");
        }
        return properties.get(key);
    }

    public void setProperty(String key, String value) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Key cannot be null or empty.");
        }
        properties.put(key, value);
    }

    @Override
    public String toString() {
        return properties.keySet().stream()
                         .map(key -> key + ": " + getProperty(key))
                         .collect(Collectors.joining(", ", "{", "}"));
    }

    public static double calculateMean(List<Cell> cells, String key) {
        if (cells.isEmpty()) {
            throw new IllegalArgumentException("Cell list cannot be empty.");
        }
        try {
            return cells.stream()
                        .mapToDouble(cell -> Double.parseDouble(cell.getProperty(key)))
                        .average()
                        .orElseThrow();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Non-numeric value present.", e);
        }
    }

    public static double calculateMedian(List<Cell> cells, String key) {
        if (cells.isEmpty()) {
            throw new IllegalArgumentException("Cell list cannot be empty.");
        }
        try {
            List<Double> values = cells.stream()
                                       .map(cell -> Double.parseDouble(cell.getProperty(key)))
                                       .sorted()
                                       .collect(Collectors.toList());
            int middle = values.size() / 2;
            if (values.size() % 2 == 1) {
                return values.get(middle);
            } else {
                return (values.get(middle-1) + values.get(middle)) / 2.0;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Non-numeric value present.", e);
        }
    }

    public static double calculateStandardDeviation(List<Cell> cells, String key) {
        double mean = calculateMean(cells, key);
        try {
            return Math.sqrt(cells.stream()
                                  .mapToDouble(cell -> Math.pow(Double.parseDouble(cell.getProperty(key)) - mean, 2))
                                  .average()
                                  .orElseThrow());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Non-numeric value present.", e);
        }
    }

    public static String findMode(List<Cell> cells, String key) {
        if (cells.isEmpty()) {
            throw new IllegalArgumentException("Cell list cannot be empty.");
        }
        try {
            Map<String, Long> frequencyMap = cells.stream()
                                                  .collect(Collectors.groupingBy(cell -> cell.getProperty(key), Collectors.counting()));
            return Collections.max(frequencyMap.entrySet(), Map.Entry.comparingByValue()).getKey();
        } catch (NoSuchElementException e) {
            throw new IllegalStateException("Error computing mode, possibly due to inconsistent data.", e);
        }
    }

    public static Set<String> uniqueValues(List<Cell> cells, String key) {
        if (!cells.isEmpty() && cells.get(0).properties.containsKey(key)) {
            return cells.stream()
                        .map(cell -> cell.getProperty(key))
                        .collect(Collectors.toSet());
        } else {
            throw new IllegalArgumentException("Invalid or non-existent key.");
        }
    }

    public static void addObject(List<Cell> cells, Cell newCell) {
        if (newCell == null || newCell.properties.isEmpty()) {
            throw new IllegalArgumentException("New cell is null or empty.");
        }
        cells.add(newCell);
    }

    public static boolean deleteObject(List<Cell> cells, Cell cellToDelete) {
        if (cellToDelete == null || !cells.contains(cellToDelete)) {
            throw new IllegalArgumentException("Cell to delete is null or does not exist in the list.");
        }
        return cells.remove(cellToDelete);
    }
}
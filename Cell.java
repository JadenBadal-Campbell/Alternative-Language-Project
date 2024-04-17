import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
        return properties.getOrDefault(key, "null");
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    @Override
    public String toString() {
        return properties.keySet().stream()
                         .map(key -> key + ": " + getProperty(key))
                         .collect(Collectors.joining(", ", "{", "}"));
    }

    public static double calculateMean(List<Cell> cells, String key) {
        return cells.stream()
                    .mapToDouble(cell -> Double.parseDouble(cell.getProperty(key)))
                    .average()
                    .orElse(Double.NaN);
    }

    public static double calculateMedian(List<Cell> cells, String key) {
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
    }

    public static double calculateStandardDeviation(List<Cell> cells, String key) {
        double mean = calculateMean(cells, key);
        return Math.sqrt(cells.stream()
                              .mapToDouble(cell -> Math.pow(Double.parseDouble(cell.getProperty(key)) - mean, 2))
                              .average()
                              .orElse(Double.NaN));
    }

    public static String findMode(List<Cell> cells, String key) {
        Map<String, Long> frequencyMap = cells.stream()
                                              .collect(Collectors.groupingBy(cell -> cell.getProperty(key), Collectors.counting()));
        return Collections.max(frequencyMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public static Set<String> uniqueValues(List<Cell> cells, String key) {
        return cells.stream()
                    .map(cell -> cell.getProperty(key))
                    .collect(Collectors.toSet());
    }

    public static void addObject(List<Cell> cells, Cell newCell) {
        cells.add(newCell);
    }

    public static boolean deleteObject(List<Cell> cells, Cell cellToDelete) {
        return cells.remove(cellToDelete);
    }

}
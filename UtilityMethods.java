import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityMethods {


     /* static String extractYear(String launchAnnounced) {
        Pattern pattern = Pattern.compile("\\b(19|20)\\d{2}\\b");
        Matcher matcher = pattern.matcher(launchAnnounced);
        return matcher.find() ? matcher.group(0) : "null";
    }
    
    static String extractWeight(String bodyWeight) {
        Pattern pattern = Pattern.compile("(\\d+) g");
        Matcher matcher = pattern.matcher(bodyWeight);
        return matcher.find() ? matcher.group(1) : "null";
    }

    static String cleanLaunchStatus(String launchStatus) {
        if (launchStatus.contains("Discontinued") || launchStatus.contains("Cancelled")) {
            return launchStatus;
        } else {
            return extractYear(launchStatus);
        }
    }
    
    static String cleanBodySim(String bodySim) {
        return bodySim.equals("No") || bodySim.equals("Yes") ? "null" : bodySim;
    }

    
    static String cleanPlatformOs(String platformOs) {
        if (platformOs == null || platformOs.isEmpty()) {
            return "null";
        }
        int commaIndex = platformOs.indexOf(',');
        return commaIndex != -1 ? platformOs.substring(0, commaIndex) : platformOs;
    } */

/**
     * Cleans and validates OEM and Model fields, returning 'null' if they are empty.
     */
    static String cleanString(String input) {
        if (input == null || input.trim().isEmpty() || input.equals("-")) {
            return "null";
        }
        return input.trim();
    }

    /**
     * Extracts the year from a string; returns 'null' if no valid year is found.
     */
    static String extractYear(String text) {
        if (text == null || text.isEmpty()) {
            return "null";
        }
        Pattern pattern = Pattern.compile("\\b(19|20)\\d{2}\\b");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return "null";
    }

    /**
     * Returns the input if it matches 'Discontinued' or 'Cancelled', 
     * otherwise tries to extract a year. If no year, returns 'null'.
     */
    static String cleanLaunchStatus(String status) {
        if (status == null || status.trim().isEmpty() || status.equals("-")) {
            return "null";
        }
        if (status.equals("Discontinued") || status.equals("Cancelled")) {
            return status;
        }
        return extractYear(status);
    }

    /**
     * Extracts the numeric weight in grams from a string, returns 'null' if the format is incorrect.
     */
    static String extractWeight(String weight) {
        if (weight == null || weight.isEmpty() || weight.equals("-")) {
            return "null";
        }
        Pattern pattern = Pattern.compile("(\\d+)\\s*g", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(weight);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "null";
    }

    /**
     * Validates the SIM type, returning 'null' for 'No' or 'Yes', otherwise returns the input.
     */
    static String cleanBodySim(String bodySim) {
        if (bodySim == null || bodySim.trim().isEmpty() || bodySim.equals("-")) {
            return "null";
        }
        if (bodySim.equalsIgnoreCase("No") || bodySim.equalsIgnoreCase("Yes")) {
            return "null";
        }
        return bodySim.trim();
    }

    /**
     * Extracts the display size in inches from a string, returns 'null' if the format is incorrect.
     */
    static String extractDisplaySize(String displaySize) {
        if (displaySize == null || displaySize.isEmpty() || displaySize.equals("-")) {
            return "null";
        }
        Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)\\s*inches", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(displaySize);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "null";
    }

    /**
     * Cleans the platform OS string to remove details after a comma.
     */
    static String cleanPlatformOS(String platformOs) {
        if (platformOs == null || platformOs.trim().isEmpty() || platformOs.equals("-")) {
            return "null";
        }
        int commaIndex = platformOs.indexOf(',');
        return commaIndex != -1 ? platformOs.substring(0, commaIndex).trim() : platformOs.trim();
    }
}


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityMethods {


     static String extractYear(String launchAnnounced) {
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
    }


}


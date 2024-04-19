import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
public class UtilityMethodsTest {
     @Test
    public void testExtractYearValid() {
        assertEquals(Integer.valueOf(2020), UtilityMethods.extractYear("Released in 2020"));
        assertNull(UtilityMethods.extractYear("Year V1"));
    }

    @Test
    public void testExtractYearInvalid() {
        assertNull(UtilityMethods.extractYear("No year"));
    }

    @Test
    public void testCleanLaunchStatus() {
        assertEquals("Discontinued", UtilityMethods.cleanLaunchStatus("Discontinued"));
        assertEquals("1999", UtilityMethods.cleanLaunchStatus("Released 1999"));
        assertEquals("null", UtilityMethods.cleanLaunchStatus("Unknown Status"));
    }

    @Test
    public void testExtractWeightValid() {
        assertEquals(Float.valueOf(118), UtilityMethods.extractWeight("118 g"));
    }

    @Test
    public void testExtractWeightInvalid() {
        assertNull(UtilityMethods.extractWeight("-"));
    }

    @Test
    public void testCleanBodySim() {
        assertEquals("null", UtilityMethods.cleanBodySim("No"));
        assertEquals("Nano-SIM", UtilityMethods.cleanBodySim("Nano-SIM"));
    }

    @Test
    public void testExtractDisplaySize() {
        assertEquals(Float.valueOf((float) 5.5), UtilityMethods.extractDisplaySize("5.5 inches"));
    }

    @Test
    public void testCleanPlatformOS() {
        assertEquals("Android 9.0 (Pie)", UtilityMethods.cleanPlatformOS("Android 9.0 (Pie), upgradable to Android 10"));
    }

    // Additional required tests

    @Test
    public void testFileNotEmpty() {
        assertTrue(new File("cells.csv").length() > 0);
    }

    @Test
        public void testFileContentStructure() {
    File inputFile = new File("cells.csv");
    assertTrue(inputFile.exists());  // Check that the file exists
    assertTrue(inputFile.isFile());  // Check that it's not a directory
    assertTrue(inputFile.length() > 0);  // Check the file is not empty

    // Optionally, verify the structure
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
        String headerLine = reader.readLine();
        assertNotNull(headerLine);
        // Check some expected columns are present, which are critical for the application
        List<String> expectedHeaders = Arrays.asList("oem", "model", "launch_announced");
        assertTrue(expectedHeaders.stream().allMatch(headerLine::contains));
    } catch (IOException e) {
        fail("Failed to read from the file: " + e.getMessage());
    }
    }
}
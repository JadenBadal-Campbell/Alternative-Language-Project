import java.io.File;
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

    
}

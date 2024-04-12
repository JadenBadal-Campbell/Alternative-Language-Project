import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

}
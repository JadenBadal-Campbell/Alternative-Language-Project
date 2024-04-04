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

           

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


 

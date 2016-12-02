package fi.riissanen.gwent.engine.files;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for file handling.
 * @author Daniel
 */
public class FileUtils {
    
    /**
     * Reads lines of a file into a list.
     * @param filename The name of the file
     * @return The lines in a list
     * @throws FileNotFoundException If the file cannot be found
     * @throws IOException If there occurs an error while reading the file
     */
    public List<String> readLines(String filename)
            throws FileNotFoundException, IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
}

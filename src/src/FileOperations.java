package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Inderpreet
 * @date Oct 8, 2014
 */
public class FileOperations {

    private BufferedReader reader;
    private BufferedWriter writer;

    /**
     * Reads text from a file
     *
     * @param file The file to read from
     * @return The text from the file
     */
    public String read(File file) {
        createReader(file);
        String toReturn = "";
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                toReturn += line;
            }
        } catch (IOException ex) {
            System.out.println("Could not read from file");
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                System.out.println("Resource not closed");
            }
        }
        return toReturn;
    }

    /**
     * Writes text to a file
     *
     * @param file The file to write to
     * @param text The text to write
     */
    public void write(File file, String text) {
        createWriter(file);
        try {
            writer.write(text);
        } catch (IOException ex) {
            System.out.println("Could not write text");
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                System.out.println("Resource not closed");
            }

        }
    }

    /**
     * Creates the reader object
     *
     * @param file The file for the reader
     */
    private void createReader(File file) {
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
    }

    /**
     * Creates the writer object
     *
     * @param file The file for the writer
     */
    private void createWriter(File file) {
        try {
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException ex) {
            System.out.println("File not found");
        }
    }
}

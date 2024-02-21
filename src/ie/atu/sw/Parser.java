package ie.atu.sw;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileInputStream;

public class Parser {
//	This method reads the text from a file and returns it as a string
	public String readTextFromFile(String fileName) {
		// Create a BufferedReader to read the lines from the file
		StringBuilder text = new StringBuilder();

		String line;

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
			// Read each line from the file using the BufferedReader
			while ((line = reader.readLine()) != null) {
				   // Append the current line to the text StringBuilder
				text.append(line);
			}
		} catch (Exception e) {
			 // If an exception occurs, print the error message and the file name
			System.out.println(e.toString());
			System.out.println("Could not find file: " + fileName);
		}
		// Convert the content of the StringBuilder to a string and return it
		return text.toString();
	}

}

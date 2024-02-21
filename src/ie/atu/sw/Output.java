package ie.atu.sw;

import java.io.*;

public class Output {
	// Writes the encrypted text to a specified file
	public void writeEncryptedTextToFile(String fileName, String encryptedText) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
			// Check if the encryptedText parameter is null
			if (encryptedText == null) {
				throw new IllegalArgumentException("Encrypted text is null");
			}
			// Write the encrypted text to the file and print a success message
			writer.println(encryptedText);
			System.out.println("Encrypted text has been written to " + fileName);
		} catch (Exception e) {
			// Print the exception message and indicate failure to write
			System.out.println(e.toString());
			System.out.println("Encrypted text hasn't been written to " + fileName);
		}
	}

	// Writes the decrypted text to a specified file
	public void writeDecryptedTextToFile(String fileName, String decryptedText) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
			if (decryptedText == null) {
				throw new IllegalArgumentException("Decrypted text is null");
			}
			writer.println(decryptedText);
			System.out.println("Decrypted text has been written to " + fileName);
		} catch (Exception e) {
			System.out.println(e.toString());
			System.out.println("Decrypted text hasn't been written to " + fileName);
		}
	}
}

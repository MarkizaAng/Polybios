package ie.atu.sw;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Decrypt {
	// Array to encrypt
	private static final char[][] square = { { ' ', 'A', 'D', 'F', 'G', 'V', 'X' },
			{ 'A', 'P', 'H', '0', 'Q', 'G', '6' }, { 'D', '4', 'M', 'E', 'A', '1', 'Y' },
			{ 'F', 'L', '2', 'N', 'O', 'F', 'D' }, { 'G', 'X', 'K', 'R', '3', 'C', 'V' },
			{ 'V', 'S', '5', 'Z', 'W', '7', 'B' }, { 'X', 'J', '9', 'U', 'T', 'I', '8' }, };

	// Performing decryption
	public void performDecryption() {
		Scanner scanner = new Scanner(System.in);
		// User input of file name for decryption
		System.out.print("Enter file name: ");
		String fileName = scanner.nextLine();
		// Reading text from the file, invoking class Parser
		Parser input = new Parser();
		String inputEncryptedText = input.readTextFromFile(fileName);

		// Checking file which user put, if it doesn't exist, program will show error
		if (!inputEncryptedText.isEmpty()) {
			System.out.print("Enter keyword: ");
			String key = scanner.nextLine();

			// Calculating the remainder when dividing the encrypted phrase by the length of
			// the keyword
			int clip = inputEncryptedText.length() % key.length();
			String removedPart = inputEncryptedText.substring(inputEncryptedText.length() - clip);
			inputEncryptedText = inputEncryptedText.substring(0, inputEncryptedText.length() - clip);

			// Creating array
			int cols = key.length();
			int rows = inputEncryptedText.length() / key.length() + 2;
			char[][] array = new char[rows][cols];

			int decryptedIndex = 0;
			int removedPartIndex = 0;

			// Getting keyword in the alphabet order
			String alphabetKeyword = rearrangeKeywordAlphabetically(key);
//			System.out.println("Original keyword: " + key);
//			System.out.println("Rearranged keyword: " + alphabetKeyword);

			// Recording keyword which is in alphabet order in the first row
			for (int col = 0; col < cols; col++) {
				array[0][col] = alphabetKeyword.charAt(col);
			}

			// Writing the remainder in the last row
			for (int col = 0; col < cols; col++) {
				if (removedPartIndex < removedPart.length()) {
					array[rows - 1][col] = removedPart.charAt(removedPartIndex);
					removedPartIndex++;
				}

			}

			// Writing characters to remaining strings
			for (int row = 1; row < rows + 1; row++) {
				for (int col = 0; col < cols; col++) {
					if (decryptedIndex < inputEncryptedText.length()) {
						array[row][col] = inputEncryptedText.charAt(decryptedIndex);
						decryptedIndex++;
					}
				}
			}

			// Printing an encrypted array with the keyword which is in alphabet order in
			// the first row
//			System.out.println("Decrypted Array:");
//			for (int row = 0; row < rows; row++) {
//				for (int col = 0; col < cols; col++) {
//					System.out.print(array[row][col] + " ");
//				}
//				System.out.println();
//			}

			rearrangedArray(array, key);
//			System.out.println("Reorganized Array:");
//			printArray(array);

			// Print array in a string
			String reorganizedString = arrayToString(array);
//			System.out.println("Reorganized Array as String:");
//			System.out.println(reorganizedString);
//		
			// Removing the keyword from the line
			if (reorganizedString.length() > key.length()) {
				reorganizedString = reorganizedString.substring(key.length());
			}

			String decryptRes = reorganizedString;
			// Removing leading and trailing whitespaces, all characters that are not
			// letters (non-alphabetic characters)
			// Converting the remaining characters to uppercase
			decryptRes = decryptRes.trim().replaceAll("[^a-zA-Z]", "").toUpperCase();
//			System.out.println("decryptRes: " + decryptRes);

			String decryptedText = decrypt(decryptRes, key);
//			System.out.println("Decrypted Text:");
//			System.out.println(decryptedText);

			// Invoking Output class and writing decryption text to the specific directory
			Output output = new Output();
			String decryptedFileName = "outputDecr.txt";
			output.writeDecryptedTextToFile(decryptedFileName, decryptedText);

		} else {
			System.out.println("Error reading text from the file. Choose another file.");
		}

	}

	public static void printArray(char[][] array) {
		for (char[] row : array) {
			for (char c : row) {
				System.out.print(c + " ");
			}
			System.out.println();
		}
	}

	public static int indexOf(char[] array, char target) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == target) {
				return i;
			}
		}
		return -1;
	}

	public static String rearrangeKeywordAlphabetically(String key) {
		// Convert the keyword into an array of characters and sort it
		char[] charArray = key.toCharArray();
		Arrays.sort(charArray);

		// Convert the sorted array of characters back into a string
		return new String(charArray);
	}

	public static void rearrangedArray(char[][] array, String key) {
		// Create a copy of the first row
		char[] keywordRow = Arrays.copyOf(array[0], array[0].length);
		keywordRow = key.toCharArray();

		// Create a mapping of characters in the copied first row to their corresponding
		// indices
		Map<Character, LinkedList<Integer>> charToIndexMap = new HashMap<>();
		for (int i = 0; i < keywordRow.length; i++) {
			LinkedList<Integer> list = charToIndexMap.get(keywordRow[i]);
			if (list == null) {
				list = new LinkedList<>();
				charToIndexMap.put(keywordRow[i], list);
			}

			list.add(i);
		}

		// Copy of elements of the array to prevent moves of original array during sort
		char[][] arrayCopy = Arrays.copyOf(array, array.length);
		for (int i = 0; i < array.length; i++) {
			array[i] = Arrays.copyOf(array[i], array[i].length);
		}

		for (int col = 0; col < arrayCopy[0].length; col++) {
			char currentChar = arrayCopy[0][col]; // Character in the first row
			List<Integer> list = charToIndexMap.get(currentChar);
			int targetCol = list.remove(0); // Target column based on the sorted order

			// Swap the columns
			for (int row = 0; row < array.length; row++) {
				array[row][targetCol] = arrayCopy[row][col];
			}
		}
	}

//	Converts a 2D array of characters into a single string
	public static String arrayToString(char[][] array) {
		StringBuilder result = new StringBuilder();

		for (char[] row : array) {
			for (char c : row) {
				result.append(c);
			}
		}

		return result.toString();
	}

//	Decrypts a given string using the ADFGVX cipher decryption technique
	public static String decrypt(String decryptRes, String key) {
		// Create a StringBuilder to store the decrypted text
		StringBuilder decryptedText = new StringBuilder();
		
		// Convert the encrypted text to uppercase and remove characters not in [ADFGVX]
		decryptRes = decryptRes.toUpperCase().replaceAll("[^ADFGVX]", "");
		// Iterate through the encrypted text in pairs of characters
		for (int i = 0; i < decryptRes.length(); i += 2) {

			char rowKey = decryptRes.charAt(i);
			char colKey = decryptRes.charAt(i + 1);
			
			int row = -1, col = -1;
			// Find the row index in the square matrix based on the row key
			for (int j = 1; j < square.length; j++) {
				if (square[j][0] == rowKey) {
					row = j;
					break;
				}
			}
			// Find the column index in the square matrix based on the column key
			for (int j = 1; j < square[0].length; j++) {
				if (square[0][j] == colKey) {
					col = j;
					break;
				}
			}
			// If both row and column indices are valid, append the decrypted character
			if (row != -1 && col != -1) {
				decryptedText.append(square[row][col]);
			}
		}
		 // Convert the content of the StringBuilder to a string and return the decrypted texts
		return decryptedText.toString();
	}

}

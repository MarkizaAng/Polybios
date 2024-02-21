package ie.atu.sw;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ADFGVXCipher {
	// Array to encrypt
	private static final char[][] square = { 
			{ ' ', 'A', 'D', 'F', 'G', 'V', 'X' },
			{ 'A', 'P', 'H', '0', 'Q', 'G', '6' }, 
			{ 'D', '4', 'M', 'E', 'A', '1', 'Y' },
			{ 'F', 'L', '2', 'N', 'O', 'F', 'D' }, 
			{ 'G', 'X', 'K', 'R', '3', 'C', 'V' },
			{ 'V', 'S', '5', 'Z', 'W', '7', 'B' },
			{ 'X', 'J', '9', 'U', 'T', 'I', '8' }, };

	// Performing encryption
	public void performEncryption() {
		Scanner scanner = new Scanner(System.in);
		// User input of file name
		System.out.print("Enter file name: ");
		String fileName = scanner.nextLine();
		// Reading text from the file
		Parser input = new Parser();
		String plainText = input.readTextFromFile(fileName);

		// Checking file which user put, if it doesn't exist, program will show error
		if (!plainText.isEmpty()) {
			System.out.print("Enter keyword: ");
			String key = scanner.nextLine();

			String encryptedText = encrypt(plainText, key);

			// Calculating the remainder when dividing the encrypted phrase by the length of
			// the keyword
			int clip = encryptedText.length() % key.length();
			String removedPart = encryptedText.substring(encryptedText.length() - clip);
			encryptedText = encryptedText.substring(0, encryptedText.length() - clip);

			// Creating array with keyword
			int cols = key.length();
			int rows = encryptedText.length() / key.length() + 2;
			char[][] array = new char[rows][cols];

			int encryptedIndex = 0;
			int removedPartIndex = 0;

			// Recording keyword in the first row
			for (int col = 0; col < cols; col++) {
				array[0][col] = key.charAt(col);
			}

			// Writing the remainder in the last row
			for (int col = 0; col < cols; col++) {
				if (removedPartIndex < removedPart.length()) {
					array[rows - 1][col] = removedPart.charAt(removedPartIndex);
					removedPartIndex++;
				}

			}

			// Writing encrypted characters to remaining strings
			for (int row = 1; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					if (encryptedIndex < encryptedText.length()) {
						array[row][col] = encryptedText.charAt(encryptedIndex);
						encryptedIndex++;
					}
				}
			}

			// Printing an encrypted array with the keyword in the first row
//			System.out.println("Encrypted Array:");
//			for (int row = 0; row < rows; row++) {
//				for (int col = 0; col < cols; col++) {
//					System.out.print(array[row][col] + " ");
//				}
//				System.out.println();
//			}

			// Invoke method rearrangedArray
			rearrangedArray(array);

			// Displaying the result in the console
//			System.out.println("Rearranged Array:");
//			for (int row = 0; row < array.length; row++) {
//				for (int col = 0; col < array[row].length; col++) {
//					System.out.print(array[row][col] + " ");
//				}
//				System.out.println();
//
//			}

			// Converting the array to a string
			String rearrangedArrayString = "";
			for (char[] row : array) {
				for (char c : row) {
					rearrangedArrayString += c;
				}
			}

			// Removing the keyword from the line
			if (rearrangedArrayString.length() > key.length()) {
				rearrangedArrayString = rearrangedArrayString.substring(key.length());
			}

			String encryptRes = rearrangedArrayString;
			// Removing leading and trailing whitespaces, all characters that are not
			// letters (non-alphabetic characters)
			// Converting the remaining characters to uppercase
			encryptRes = encryptRes.trim().replaceAll("[^a-zA-Z]", "").toUpperCase();
//			System.out.println("Encrypted text: " + encryptRes);
			
			// Invoking Output class and writing encryption text to the specific directory
			Output output = new Output();
			String encryptedFileName = "outputEncr.txt";
			output.writeEncryptedTextToFile(encryptedFileName, encryptRes);

		} else {
			System.out.println("Error reading text from the file. Choose another file.");
		}

	}

	// Printing array
	public static void printArray(char[][] array) {
		for (char[] row : array) {
			for (char c : row) {
				System.out.print(c + " ");
			}
			System.out.println();
		}
	}

	// Helper method to find the index of a specific character 'target' in the
	// 'array'
	public static int indexOf(char[] array, char target) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == target) {
				return i;
			}
		}
		return -1;
	}

	public static String encrypt(String plainText, String key) {
		StringBuilder encryptedText = new StringBuilder();
		// Convert the plain text to uppercase and remove non-alphanumeric characters
		// and spaces
		plainText = plainText.toUpperCase().replaceAll("[^A-Z0-9]", "").replaceAll("\\s+", "");
		// Convert the key to uppercase and remove non-alphabetic characters
		key = key.replaceAll("[^A-Za-z]", "").toUpperCase();

		for (char c : plainText.toCharArray()) {
			int row = -1, col = -1;
			for (int i = 1; i < square.length; i++) {
				for (int j = 1; j < square[i].length; j++) {
					if (square[i][j] == c) {
						row = i;
						col = j;
						break;
					}
				}
				if (row != -1 && col != -1) {
					break;
				}
			}
			if (row != -1 && col != -1) {
				// Append the substitution of 'c' to the encrypted text
				encryptedText.append(square[0][row]).append(square[col][0]);
			}
		}

		return encryptedText.toString(); // Return the encrypted text
	}

	// Rearrange the columns of the 2D 'array' based on the sorted order of
	// characters in the first row
	public static void rearrangedArray(char[][] array) {
		// Create a copy of the first row
		char[] firstRowCopy = Arrays.copyOf(array[0], array[0].length);

		// Sort the copied first row
		Arrays.sort(firstRowCopy);

		// Create a mapping of characters in the copied first row to their corresponding
		// indices
		Map<Character, LinkedList<Integer>> charToIndexMap = new HashMap<>();
		for (int i = 0; i < firstRowCopy.length; i++) {
			LinkedList<Integer> list = charToIndexMap.get(firstRowCopy[i]);
			if (list == null) {
				list = new LinkedList<>();
				charToIndexMap.put(firstRowCopy[i], list);
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

}

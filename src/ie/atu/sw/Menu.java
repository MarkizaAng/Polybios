package ie.atu.sw;

import java.util.Scanner;

//Class responsible for displaying and managing the menu
public class Menu {
	// Method to display the menu and handle user choices
	public void show() {
		Scanner scanner = new Scanner(System.in);
		int choice;

		// Display the menu options and process user input
		do {
			System.out.println("************************************************************");
			System.out.println("*       ATU - Dept. Computer Science & Applied Physics     *");
			System.out.println("*                                                          *");
			System.out.println("*                   ADFGVX File Encryption                 *");
			System.out.println("*                                                          *");
			System.out.println("************************************************************");
			System.out.println("(1) Encrypt");
			System.out.println("(2) Decrypt");
			System.out.println("(3) Quit");

			System.out.print("Select Option [1-3]>");
			System.out.println();

			choice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline

			switch (choice) {
			case 1:
				//Invoking method which does encryption
				ADFGVXCipher cipher = new ADFGVXCipher();
				cipher.performEncryption();
				break;
			case 2:
				//Invoking method which does encryption
				Decrypt orText = new Decrypt();
				orText.performDecryption();
				break;
			case 3:
				// Closing the application
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid choice. Try again.");
			}
		} while (choice != 3);

		scanner.close();
	}

}

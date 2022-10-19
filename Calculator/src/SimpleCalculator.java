import java.util.*;
import java.io.*;

public class SimpleCalculator {

	public static void main(String[] args) {
		
		// Initialized File, Formatter and Scanner so I may be able to use them in creating, writing and reading data in the text file.
		Formatter file = null;
		File outputData = null;
		Scanner data = null;
		
		// Using the try method to create the text file and read it.
		try {
			FileWriter output = new FileWriter("C:\\Users\\User\\Desktop\\Introduction to Programming\\Level 2\\Task 18\\Calculator\\src\\equations.txt", true);
			file = new Formatter(output);
			
			outputData = new File("C:\\Users\\User\\Desktop\\Introduction to Programming\\Level 2\\Task 18\\Calculator\\src\\equations.txt");
			data = new Scanner(outputData);
		
		// Initialized the variables that will hold the numbers inserted by the user and created a new scanner to take in data
		float firNum = 0;
		float secNum = 0; 
		float answer;
		Scanner input = new Scanner(System.in);
		// Initialized a string so that we can use a loop to do the calculations.
		String option = null;
		
		/* Using the Do While loop allows us to take in a calculation when the program is ran for the first time,
		 * and we also added to a try method when asking the user to input an integer or number of we may be able to catch
		 * an error when the user inserts a character that is not a number.
		 */
		do {
			try {
				System.out.println("Enter a number: ");
				input = new Scanner(System.in);
				firNum = input.nextFloat();
				System.out.println("Enter a second number: ");
				secNum = input.nextFloat();
			}
			catch (java.util.InputMismatchException e) {
				System.out.println("You did not enter a valid integer.");
				System.out.println("Retry \n");
			}
			System.out.println("Please enter an operator (+, -, *, /): ");
			char operator = input.next().charAt(0);
			
			/* Using the switch method allows us to use the case method in order to make different
			 * calculations based on the operator the user chooses.
			 * I used the if method on the devision case in order to check if the second number inserted is
			 * a 0 and if it is, the program will inform the user that you cannot divide by zero.
			 */
			switch (operator) {
			case '+':
				answer = firNum + secNum;
				System.out.println(firNum + " + " + secNum + " = " + answer);
				file.format("%s %s %s %s %s %s", firNum, "+", secNum, "=", answer, "\n");
				break;
			case '-':
				answer = firNum - secNum;
				System.out.println(firNum + " - " + secNum + " = " + answer);
				file.format("%s %s %s %s %s %s", firNum, "-", secNum, "=", answer, "\n");
				break;
			case '*':
				answer = firNum * secNum;
				System.out.println(firNum + " * " + secNum + " = " + answer);
				file.format("%s %s %s %s %s %s", firNum, "*", secNum, "=", answer, "\n");
				break;
			case '/':
				if (secNum == 0) {
					System.out.println("Cannot devide by zero.");
				}
				else {
					answer = firNum / secNum;
					System.out.println(firNum + " / " + secNum + " = " + answer);
					file.format("%s %s %s %s %s %s", firNum, "/", secNum, "=", answer, "\n");
				}
				break;
			}
			// The second part of the do while method that asks if the user wishes to do another calculation or not
			System.out.println("Please enter yes or no");
			System.out.println("Do you wish to do another calculation: ");
			input = new Scanner(System.in);
			option = input.nextLine();
			
			} while (option.equals("yes"));
			
			output.close();
			file.close();
			input.close();
		
			// The catch methods that print out error when the program crashes.
			}
			catch (FileNotFoundException e) {
				System.out.println("Error");
			}
		
			catch (Exception e) {
				System.out.println("Error!");
		}
		
		// When the user chooses not to make any more calculations, the program will print out all the calculations on file and then end.
		System.out.println("\nPlease see the calculations in the text file below: \n");
		while (data.hasNextLine()) {
			System.out.println(data.nextLine());
		}
	
		System.out.println("\nGoodbye");

	}

}

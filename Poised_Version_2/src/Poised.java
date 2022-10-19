import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;


public class Poised {
	// Declaring the list as static and outside the main method makes it easier to access it on multiple methods
	static ArrayList<Object> lst = new ArrayList<>();
	static Project project;
	static Customer consumer;
	static Architect architect;
	static Contractor contractor;

	public static void main(String[] args) {
		
		// Prints out all the projects on file, weather they are completed or not
		System.out.println("Projects available on file are below: \n");
		try {
			File x = new File("projects.txt");
			Scanner sc = new Scanner(x);
			while (sc.hasNext()) {
				String [] line = sc.nextLine().split(",");
				System.out.println("Project No.: " + line[0].replace("[", "") + ", Project Name: " + line[1]);
			}
			sc.close();
		} catch (Exception e) {
			System.out.println("File does not exist.");
		}
		
		// Declaring the variables that hold the data taken in allows the program to hold the data and not override it when going through the while loop
		Scanner input = new Scanner(System.in);
		String mainMenu = null;
		
		/* Using the do while loop allows the program to go into the menu so the user can select what they wish
		 * to do and also the loop allows the user to come back from to the menu to select a different option
		 */
		do {
			System.out.println("\nPlease select from the menu below: "
								+ "\ncnp - Create New Project"
								+ "\nep - Edit Project"
								+ "\nup - Uncompleted Projects"
								+ "\nop - Overdue Projects"
								+ "\ncp - Completed Projects"
								+ "\ne - Exit");
			String menu = input.nextLine();
			System.out.println("\n");
			
			// Creates new projects
			if (menu.equals("cnp")) {
				createProject(input);
			}
			// Edits the projects details within the array
			else if (menu.equals("ep")) {
				editProject(input, contractor, project);	
			}
			// Prints all uncompleted projects
			else if (menu.equals("up")) {
				uncompletedProjects();
			}
			// Prints all overdue projects
			else if (menu.equalsIgnoreCase("op")) {
				overdueProjects();
			}
			// Prints all completed projects
			else if (menu.equalsIgnoreCase("cp")) {
				completedProjects();
			}
			// An exit option given to the user to close the program from the main menu
			else if (menu.equals("e")) {
				System.out.println("Goodbye");
				break;
			}
			else {
				System.out.println("Invalid input. Please go back to the main menu and enter an appropriate input.");
			}
		input = new Scanner(System.in);	
		System.out.println("\nPlease enter yes or no");
		System.out.println("Do you wish to return to the main menu to create a new project? ");
		mainMenu = input.nextLine();
		System.out.println("\n");
		} while (mainMenu.equals("yes"));
		input.close();
		
		// When the the program closes, the project in the array will be written into the text file.
		if (mainMenu.equals("no")) {
			try {
				FileWriter y = new FileWriter("projects.txt", true);
				Formatter data = new Formatter(y);
				data.format("%s", ("\n" + lst));
				data.close();
			} catch (Exception e) {
				System.out.println("Error");
			}
		}
	}
	
	private static void completedProjects() {
		// Checks if the projects are completed and prints the ones that are completed.
		try {
			File x = new File("projects.txt");
			Scanner sc = new Scanner(x);
			while (sc.hasNext()) {
				String [] line = sc.nextLine().split(",");
				if (line[8].equalsIgnoreCase("yes"))
					System.out.println("Project No.: " + line[0].replace("[", "") + ", Project Name: " + line[1]);
			}
			sc.close();
		} catch (Exception e) {
			System.out.println("File does not exist.");
		}
	}

	private static void overdueProjects() {
		// Takes the due date on the text file for all projects, converts it and compares it to today's date to see weather the project is overdue or not
		LocalDate date = null;
		String [] line = {};
		try {
			File x = new File("projects.txt");
			Scanner sc = new Scanner(x);
			while (sc.hasNext()) {
				line = sc.nextLine().split(",");
				String duedate = line[7];
				
				DateTimeFormatter f = DateTimeFormatter.ofPattern("d MMMM yyyy");
				try {
					date = LocalDate.parse(duedate, f);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			sc.close();
		} catch (Exception e) {
			System.out.println("File does not exist.");
		}
		LocalDate today = java.time.LocalDate.now();
		
		if (today.isAfter(date)) {
			System.out.println("Project No.: " + line[0].replace("[", "") + ", Project Name: " + line[1]);
		}
	}

	private static void uncompletedProjects() {
		// Prints all the uncompleted projects
		try {
			File x = new File("projects.txt");
			Scanner sc = new Scanner(x);
			while (sc.hasNext()) {
				String [] line = sc.nextLine().split(",");
				if (line[8].equalsIgnoreCase("no"))
					System.out.println("Project No.: " + line[0].replace("[", "") + ", Project Name: " + line[1]);
			}
			sc.close();
		} catch (Exception e) {
			System.out.println("File does not exist.");
		}
	}
	
	private static void createProject(Scanner input) {
		// Takes in all the data and create a new project
		// Project details
		int projNum = 0;
		try {
			System.out.println("Please enter the project's number: ");
			projNum = input.nextInt();
			lst.add(projNum);
		} catch (Exception e) {
			System.out.println("The project number consist of only integers. \nPlease end the program and enter the details correctly.");
		}
		input = new Scanner(System.in);	
		System.out.println("Please enter the project's name: ");
		String projName = input.nextLine();
		lst.add(projName);
		System.out.println("Please enter the project's design type: ");
		String projDesign = input.nextLine();
		lst.add(projDesign);
		System.out.println("Please enter the project's physical adress: ");
		String projPhysical = input.nextLine();
		lst.add(projPhysical);
		System.out.println("Please enter the project's ERF number: ");
		String projErf = input.nextLine();
		lst.add(projErf);
		Float projTotalFee = null;
		Float projPaidFee = null;
		try {
			System.out.println("Please enter the project's total fee charged: ");
			projTotalFee = input.nextFloat();
			lst.add(projTotalFee);
			System.out.println("Please enter the project's total fee paid to date: ");
			projPaidFee = input.nextFloat();
			lst.add(projPaidFee);
		} catch (Exception e) {
			System.out.println("Please enter a number!");
		}
		input = new Scanner(System.in);	
		System.out.println("Please enter the date in this method \'01 January 2022\'");
		System.out.println("Please enter the project's due date: ");
		String projDueDate = input.nextLine();
		lst.add(projDueDate);
		System.out.println("Is the project complete? (Yes or No) ");
		String projStatus = input.nextLine();
		lst.add(projStatus);
		
		// Customer details input
		System.out.println("Please enter the customer's name: ");
		String custName = input.nextLine();
		lst.add(custName);
		System.out.println("Please enter the customer's telephone number: ");
		String custNumber = input.nextLine();
		lst.add(custNumber);
		System.out.println("Please enter the customer's email address: ");
		String custEmail = input.nextLine();
		lst.add(custEmail);
		System.out.println("Please enter the customer's physical adress: ");
		String custPhysical = input.nextLine();
		lst.add(custPhysical);
		
		consumer = new Customer(custName, custNumber, custEmail, custPhysical);
		
		// Architect details input
		System.out.println("Please enter the architect's name: ");
		String archName = input.nextLine();
		lst.add(archName);
		System.out.println("Please enter the architect's telephone number: ");
		String archNumber = input.nextLine();
		lst.add(archNumber);
		System.out.println("Please enter the architect's email address: ");
		String archEmail = input.nextLine();
		lst.add(archEmail);
		System.out.println("Please enter the architect's physical adress: ");
		String archPhysical = input.nextLine();
		lst.add(archPhysical);
		
		architect = new Architect(archName, archNumber, archEmail, archPhysical);
		
		// Contractor details input
		System.out.println("Please enter the contractor's name: ");
		String conName = input.nextLine();
		lst.add(conName);
		System.out.println("Please enter the contractor's telephone number: ");
		String conNumber = input.nextLine();
		lst.add(conNumber);
		System.out.println("Please enter the contractor's email address: ");
		String conEmail = input.nextLine();
		lst.add(conEmail);
		System.out.println("Please enter the contractor's physical adress: ");
		String conPhysical = input.nextLine();
		lst.add(conPhysical);
		
		contractor = new Contractor(conName, conNumber, conEmail, conPhysical);
		
		if (projName.equals("")) {
			projName = custName + projDesign;
		}
		
		project = new Project(projNum, projName, projDesign, projPhysical, projErf, projTotalFee, projPaidFee, projDueDate, projStatus, consumer, architect, contractor);
		
		System.out.println("\n" + project.toString());
		
	}

	private static void editProject(Scanner input, Contractor contractor, Project project) {
		/* Gives the user options on which data can they edit on the project after checking if there is a project to edit.
		 * The program will first check if weather the is data to edit or not.
		 */
		if (lst.size() < 1) {
			System.out.println("No project is available to edit");
		}
		else {
			System.out.println("Do wish to change the \"dd - Due Date\",\"ps - Project Status\",  \"ap - Amount Paid\" or \"cd - Contractor's Details\": ");
			String editOption = input.nextLine();
			if (editOption.equals("dd")) {
				System.out.println("Please enter the new due date: ");
				String newDueDate = input.nextLine();
				project.setDueDate(newDueDate);
				lst.add(7, newDueDate);
			}
			else if (editOption.equalsIgnoreCase("ps")) {
				System.out.println("Has the project been completed: ");
				String newProjectStatus = input.nextLine();
				project.setProjectStatus(newProjectStatus);
				lst.add(8, newProjectStatus);
			}
			else if (editOption.equals("ap")) {
				System.out.println("Please enter the new amount paid to date: ");
				Float newPaidFee = input.nextFloat();
				project.setFeePaid(newPaidFee);
				lst.add(6, newPaidFee);
			}
			else if (editOption.equals("cd")) {
				System.out.println("Please enter the contractor's new telephone number: ");
				String newConNumber = input.nextLine();
				contractor.setTelephoneNo(newConNumber);
				lst.add(18, newConNumber);
				System.out.println("Please enter the contractor's new email address: ");
				String newConEmail = input.nextLine();
				contractor.setEmailAdd(newConEmail);
				lst.add(19, newConEmail);
			}
			else {
				System.out.println("Invalid Input!");
			}
		}
		System.out.println(project.toString());
	}
	
}

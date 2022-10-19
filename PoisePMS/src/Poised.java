import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class Poised {
	// Defining these outside the main class and using a static on them so they can be access by other methods
	static Statement data = null;
	static ResultSet results;
	static int rows = 0;

	public static void main(String[] args) {
		// Connecting to the database
		try {
			Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/PoisePMS?useSSL=false&allowPublicKeyRetrieval=true",
                    "Sbusiso",
                    "swordfish"
                    );
			
			data = connection.createStatement();
			
			// Main menu of the program
            Scanner input = new Scanner(System.in);
        	boolean option = true;
            do {
            	System.out.println("\nPlease select from the menu below: "
						+ "\ncnp - Create New Project"
						+ "\nep - Edit Project"
						+ "\nup - Uncompleted Projects"
						+ "\nop - Overdue Projects"
						+ "\ncp - Completed Projects"
						+ "\nsp - Search for Project"
						+ "\nfp - Finalize Project"
						+ "\ne - Exit");
            	String menu = input.nextLine();
            	System.out.println("\n");
            	
            	// Using switch case instead of the if statement to choose options in the menu
            	switch(menu) {
            	case "cnp":
            		createProject(input);
            		break;
            		
            	case "ep":
            		editProject(input);
            		break;
            	
            	// Printing uncompleted tasks
            	case "up":
            		String UncompletedCommand = "SELECT * FROM Poised_Table WHERE Project_Status = \'No\'";
        			results = data.executeQuery(UncompletedCommand);
        			// Printing out the results of the command ran
        			if (results.next()) {
        				System.out.println(results.getInt("Project_Number") + ", " + results.getString("Project_Name") + ", " + results.getString("Project_Type") + ", " + 
        					results.getString("Project_Physical") + ", " + results.getString("Project_ERF") + ", " + results.getInt("Project_Fee") + ", " + results.getInt("Project_Paid_Fee")+ ", " 
        					+ results.getString("Due_Date") + ", " + results.getString("Project_Manager") + ", " + results.getString("Structural_Engineer") + ", " + 
        					results.getInt("Customer_Number") + ", " + results.getInt("Architect_Number") + ", " + results.getInt("Contractor_number") + ", " + results.getString("Project_Status"));
        			}
        			break;
        			
        		// Printing completed tasks
            	case "cp":
            		String completedCommand = "SELECT * FROM Poised_Table WHERE Project_Status = \'Yes\'";
        			results = data.executeQuery(completedCommand);
        			// Printing out the results of the command ran
        			if (results.next()) {
        				System.out.println(results.getInt("Project_Number") + ", " + results.getString("Project_Name") + ", " + results.getString("Project_Type") + ", " + 
            					results.getString("Project_Physical") + ", " + results.getString("Project_ERF") + ", " + results.getInt("Project_Fee") + ", " + results.getInt("Project_Paid_Fee")+ ", " 
            					+ results.getString("Due_Date") + ", " + results.getString("Project_Manager") + ", " + results.getString("Structural_Engineer") + ", " + 
            					results.getInt("Customer_Number") + ", " + results.getInt("Architect_Number") + ", " + results.getInt("Contractor_number") + ", " + results.getString("Project_Status"));
        			}
        			break;
        		
        		// Printing overdue tasks
            	case "op":
            		LocalDate today = java.time.LocalDate.now();
            		
            		String overdueCommand = "SELECT * FROM Poised_Table WHERE Due_Date < " + today;
        			results = data.executeQuery(overdueCommand);
            		break;
            	
            	// Allows the user to search by project name or project ID
            	case "sp":
            		System.out.println("Please enter 1 for ID or 2 for Name.");
            		System.out.println("Do you have a Project ID or project Name: ");
            		String choice = input.nextLine();
            		// If statements used to select the appropriate option based on the users choice.
            		if (choice.equals("1")) {
            			System.out.println("Please enter the Project ID: ");
            			int projectID = input.nextInt();
            			String searchCommand = "SELECT * FROM Poised_Table WHERE Project_Number = "+projectID;
            			results = data.executeQuery(searchCommand);
            		}
            		else if (choice.equals("2")) {
            			System.out.println("Please enter the Project Name: ");
            			String projectName = input.nextLine();
            			String searchCommand = "SELECT * FROM Poised_Table WHERE Project_Name = "+projectName;
            			results = data.executeQuery(searchCommand);
            		}
            		else {
            			System.out.println("Invalid Input!");
            			option = false;
            		}
            		break;
            	
            		// Finalizing the project.
            		// Program will check if the is an outstanding amount to decide weather to issue an invoice or not
            	case "fp":
            		System.out.println("Please enter the project id you wish to finalize: ");
            		int projectID = input.nextInt();
            		String feeDifference = "SELECT Project_Fee, Project_Paid_Fee, Project_Fee - Project_Paid_Fee as OutstandingFee FROM Poised_Table WHERE Project_Number = "+projectID;
            		results = data.executeQuery(feeDifference);
            		String customerDetails = "SELECT Customer_Table.Customer_Name, Customer_Table.Customer_Telephone FROM Poised_Table INNER JOIN Customer_Table ON Poised_Table.Customer_Number = "
            				+ "Customer_Table.Customer_ID WHERE Project_Number = "+projectID;
            		results = data.executeQuery(customerDetails);
            		String outstandingFee = null;
            		if (results.next())		
            			outstandingFee = results.getString("OutstandingFee");
            		
            		if (outstandingFee.equalsIgnoreCase("0")) {
            			System.out.println("The entire project amout has been paid, the project is finalized and no invoice will be issued.");
            			System.out.println("\nGoodbye");
            		}
            		else {
            			System.out.println("The project has an outstanding fee, please see the invoice before the project can be finalized:\n");
            			System.out.println("Customer Name: " + results.getString("Customer_Name"));
            			System.out.println("\nCustomer Telephone: " + results.getString("Customer_Telephone"));
            			System.out.println("Outstanding amount: " + outstandingFee);
            		}
            		break; 
            		
            	case "e":
            		option = false;
            	break;
            	
            	}
            
            } while(option);
            input.close();
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void editProject(Scanner input) throws SQLException {
		// Asking what data do they wish to edit, and updating the data in the database.
		System.out.println("Do wish to change the \"dd - Due Date\",\"ps - Project Status\",  \"ap - Amount Paid\" or \"cd - Contractor's Details\": ");
		String editOption = input.nextLine();
		if (editOption.equals("dd")) {
			System.out.println("Please enter the project ID you with to update: ");
			int projectID = input.nextInt();
			input = new Scanner(System.in);
			System.out.println("Please enter the new due date: ");
			String newDueDate = input.nextLine();
			
			String updateCommand = "UPDATE Poised_Table SET Due_Date = "+newDueDate+" WHERE Project_Number = "+projectID;
			rows = data.executeUpdate(updateCommand);
		}
		else if (editOption.equalsIgnoreCase("ps")) {
			System.out.println("Please enter the project ID you with to update: ");
			int projectID = input.nextInt();
			input = new Scanner(System.in);
			System.out.println("Has the project been completed: ");
			String newProjectStatus = input.nextLine();
			
			String updateCommand = "UPDATE Poised_Table SET Project_Status = "+newProjectStatus+" WHERE Project_Number = "+projectID;
			rows = data.executeUpdate(updateCommand);
		}
		else if (editOption.equals("ap")) {
			System.out.println("Please enter the project ID you with to update: ");
			int projectID = input.nextInt();
			input = new Scanner(System.in);
			System.out.println("Please enter the new amount paid to date: ");
			Float newPaidFee = input.nextFloat();
			
			String updateCommand = "UPDATE Poised_Table SET Project_Paid_Fee = "+newPaidFee+" WHERE Project_Number = "+projectID;
			rows = data.executeUpdate(updateCommand);
		}
		else if (editOption.equals("cd")) {
			System.out.println("Please enter the contractor ID you with to update: ");
			int contractorID = input.nextInt();
			
			input = new Scanner(System.in);
			System.out.println("Please enter the contractor's new telephone number: ");
			String newConNumber = input.nextLine();
			System.out.println("Please enter the contractor's new email address: ");
			String newConEmail = input.nextLine();
			
			String updateContactNumber = "UPDATE Contractor_Table SET Contractor_Telephone = "+newConNumber+" WHERE Contractor_ID = "+contractorID;
			rows = data.executeUpdate(updateContactNumber);
			String updateEmail = "UPDATE Contractor_Table SET Contractor_Email = "+newConEmail+" WHERE Contractor_ID = "+contractorID;
			rows = data.executeUpdate(updateEmail);
		}
		else {
			System.out.println("Invalid Input!");
		}
	}

	private static Scanner createProject(Scanner input) throws SQLException {
		// Customer details input and inputting it to the database.
		System.out.println("Please enter customer ID: ");
		int customerID = input.nextInt();
		input = new Scanner(System.in);	
		System.out.println("Please enter the customer's name: ");
		String custName = input.nextLine();
		System.out.println("Please enter the customer's telephone number: ");
		String custNumber = input.nextLine();
		System.out.println("Please enter the customer's email address: ");
		String custEmail = input.nextLine();
		System.out.println("Please enter the customer's physical adress: ");
		String custPhysical = input.nextLine();
		
		String insertCustomer = "INSERT INTO Customer_Table VALUES ("+customerID+", \'"+custName+"\', \'"+custNumber+"\', \'"+custEmail+"\', \'"+custPhysical+"\'" +")";
		rows = data.executeUpdate(insertCustomer);
		
		// Contractor details input and inputting it to the database.
		System.out.println("Please enter contractor ID: ");
		int contractorID = input.nextInt();
		input = new Scanner(System.in);	
		System.out.println("Please enter the contractor's name: ");
		String conName = input.nextLine();
		System.out.println("Please enter the contractor's telephone number: ");
		String conNumber = input.nextLine();
		System.out.println("Please enter the contractor's email address: ");
		String conEmail = input.nextLine();
		System.out.println("Please enter the contractor's physical adress: ");
		String conPhysical = input.nextLine();
		
		String insertContractor = "INSERT INTO Contractor_Table VALUES ("+contractorID+", \'"+conName+"\', \'"+conNumber+"\', \'"+conEmail+"\', \'"+conPhysical+"\'" +")";
		rows = data.executeUpdate(insertContractor);
		
		// Architect details input and inputting it to the database.
		System.out.println("Please enter architect ID: ");
		int architectID = input.nextInt();
		input = new Scanner(System.in);	
		System.out.println("Please enter the architect's name: ");
		String archName = input.nextLine();
		System.out.println("Please enter the architect's telephone number: ");
		String archNumber = input.nextLine();
		System.out.println("Please enter the architect's email address: ");
		String archEmail = input.nextLine();
		System.out.println("Please enter the architect's physical adress: ");
		String archPhysical = input.nextLine();
		
		String insertArchitect = "INSERT INTO Architect_Table VALUES("+architectID+", \'"+archName+"\', \'"+archNumber+"\', \'"+archEmail+"\', \'"+archPhysical+"\'" +")";
		rows = data.executeUpdate(insertArchitect);
				
		// Project details input and inputting it to the database.
		int projNum = 0;
		try {
			System.out.println("Please enter the project's number: ");
			projNum = input.nextInt();
		} catch (Exception e) {
			System.out.println("The project number consist of only integers. \nPlease end the program and enter the details correctly.");
		}
		input = new Scanner(System.in);	
		System.out.println("Please enter the project's name: ");
		String projName = input.nextLine();
		System.out.println("Please enter the project's design type: ");
		String projDesign = input.nextLine();
		System.out.println("Please enter the project's physical adress: ");
		String projPhysical = input.nextLine();
		System.out.println("Please enter the project's ERF number: ");
		String projErf = input.nextLine();
		Float projTotalFee = null;
		Float projPaidFee = null;
		try {
			System.out.println("Please enter the project's total fee charged: ");
			projTotalFee = input.nextFloat();
			System.out.println("Please enter the project's total fee paid to date: ");
			projPaidFee = input.nextFloat();
		} catch (Exception e) {
			System.out.println("Please enter a number!");
		}
		input = new Scanner(System.in);	
		System.out.println("Please enter the date in this method \'2022-12-25\'");
		System.out.println("Please enter the project's due date: ");
		String projDueDate = input.nextLine();
		System.out.println("Is the project complete? (Yes or No) ");
		String projStatus = input.nextLine();
		System.out.println("Please enter the Project Manager's Name: ");
		String projManager = input.nextLine();
		System.out.println("Please enter the Structural Engineer of the project: ");
		String strucEngineer =  input.nextLine();
		if (projName.equals("")) {
			projName = custName + projDesign;
		}
		
		String insertProject = "INSERT INTO Poised_Table VALUES("+projNum+", \'"+projName+"\', \'"+projDesign+"\', \'"+projPhysical+"\', \'"+projErf+"\', "+projTotalFee+
								", "+projPaidFee+", \'"+projDueDate+"\', \'"+projManager+"\', \'"+strucEngineer+"\', "+customerID+", "+architectID+", "+contractorID+", \'"+
								projStatus+"\'"+")";
		rows = data.executeUpdate(insertProject);
		return input;
	}
	
}

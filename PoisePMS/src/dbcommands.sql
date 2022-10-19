create if not exists PoisePMS;

use PoisePMS;

drop table if exists Poised_Table;

create table Customer_Table (int Customer_ID, Customer_Name varchar(50), Customer_Telephone varchar(50), Customer_Email varchar(50),
							Customer_Physical varchar(50), PRIMARY KEY (Customer_ID));
							
create table Architect_Table (int Architect_ID, Architect_Name varchar(50), Architect_Telephone varchar(50), Architect_Email varchar(50),
							Architect_Physical varchar(50), PRIMARY KEY (Architect_ID));
							
create table Contractor_Table (int Contractor_ID, Contractor_Name varchar(50), Contractor_Telephone varchar(50), Contractor_Email varchar(50),
							Contractor_Physical varchar(50), PRIMARY KEY (Contractor_ID));

create table Poised_Table (int Project_Number, Project_Name varchar(50), Project_Type varchar(50), Project_Physical varchar(50),
						Project_ERF varchar(50), int Project_Fee, int Project_Paid_Fee, Due_Date varchar(50), Project_Manager varchar(50),
						Structural_Engineer varchar(50), int Customer_Number, int Architect_Number, int Contractor_number, Project_Status varchar(50),
						PRIMARY KEY (Project_Number), FOREIGN KEY (Customer_Number) REFERENCES Customer_Table(Customer_ID),
						FOREIGN KEY (Architect_Number) REFERENCES Architect_Table(Architect_ID), FOREIGN KEY (Contractor_Number) REFERENCES Contractor_Table(Contractor_ID));
						
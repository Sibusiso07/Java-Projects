
public class Person {

	// Attributes
	public String name;
	public String telephoneNo;
	public String emailAdd;
	public String physicalAdd;
	
	// Contractors
	public Person (String name, String telephoneNo, String emailAdd, String physicalAdd) {
		this.name = name;
		this.telephoneNo = telephoneNo;
		this.emailAdd = emailAdd;
		this.physicalAdd = physicalAdd;
	}
	
	// Setters
	public void setName(String newName) {
		name = newName;
	}
	public void setTelephoneNo(String newTelephoneNo) {
		telephoneNo = newTelephoneNo;
	}
	public void setEmailAdd(String newEmailAdd) {
		emailAdd = newEmailAdd;
	}
	public void setPhysicalAdd(String newPhysicalAdd) {
		physicalAdd = newPhysicalAdd;
	}

	// Getters
	public String getName() {
		return name;
	}
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public String getEmailAdd() {
		return emailAdd;
	}
	public String getPhysicalAdd() {
		return physicalAdd;
	}
	
	//To String
	public String toString() {
		String architectDetails = "Name: " + name + "\n" + "Telephone Number: " + telephoneNo + "\n" + "Email Address: " + emailAdd + "\n" 
				+ "Physical Address: " + physicalAdd + "\n";
		return architectDetails;
	}
	
	public String write() {
		String details = name + "," +  telephoneNo + "," +  emailAdd + "," +  physicalAdd;
		return details;
	}
}

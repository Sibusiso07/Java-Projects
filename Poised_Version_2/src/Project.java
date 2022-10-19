
public class Project {
	// Attributes
	public int projectNum;
	public String projectName;
	public String designType;
	public String physicalAdd;
	public String erfNum;
	public Float totalFee;
	public Float feePaid;
	public String dueDate;
	public String projectStatus; 
	public Customer consumer;
	public Architect architect;
	public Contractor contractor;
	
	//Constructors
	public Project (int projectNum, String projectName, String designType, String physicalAdd, String erfNum, Float totalFee, Float feePaid, 
					String dueDate, String projectStatus, Customer consumer, Architect architect, Contractor contractor) {
		this.projectNum = projectNum;
		this.projectName = projectName;
		this.designType = designType;
		this.physicalAdd = physicalAdd;
		this.erfNum = erfNum;
		this.totalFee = totalFee;
		this.feePaid = feePaid;
		this.dueDate =  dueDate;
		this.projectStatus = projectStatus;
		this.consumer = consumer;
		this.architect = architect;
		this.contractor = contractor;
	}
	
	// Setters
	public void setNumber(int newNumber) {
		this.projectNum = newNumber;
	}
	public void setName(String newName) {
		this.projectName = newName;
	}
	public void setDesign(String newDesign) {
		this.designType = newDesign;
	}
	public void setAddress(String newAddress) {
		this.physicalAdd = newAddress;
	}
	public void setErf(String newErf) {
		this.erfNum = newErf;
	}
	public void setTotalFee(Float newTotalFee) {
		this.totalFee = newTotalFee;
	}
	public void setFeePaid(Float newFeePaid) {
		this.feePaid = newFeePaid;
	}
	public void setDueDate(String newDueDate) {
		this.dueDate = newDueDate;
	}
	public void setProjectStatus(String newProjectStatus) {
		this.projectStatus = newProjectStatus;
	}
	
	// Getters
	public int getProjectNum() {
		return projectNum;
	}
	public String getProjectName() {
		return projectName;
	}
	public String getDesignType() {
		return designType;
	}
	public String getPhysicalAdd() {
		return physicalAdd;
	}
	public String getErfNum() {
		return erfNum;
	}
	public Float getTotalFee() {
		return totalFee;
	}
	public Float getFeePaid() {
		return feePaid;
	}
	public String getDueDate() {
		return dueDate;
	}
	public String getProjectStatus() {
		return projectStatus;
	}
	
	// To String
	public String toString() {
		String projectDetails =  "Project Number: " + projectNum + "\n" + "Project Name: " + projectName + "\n" + "Design Type: " + designType + "\n" + "Physical Address: " 
								+ physicalAdd + "\n" + "ERF Number: " + erfNum + "\n" + "Total Fee: " + totalFee + "\n" + "Fee Paid to date: " + feePaid + "\n" + "Due Date: " 
								+ dueDate + "\n" + "Project Status Completion: " + projectStatus + "\n" + consumer.toString() + "\n" + architect.toString() + "\n" + contractor.toString() + "\n";
		return projectDetails;
	}
	public String write() {
		String details =  projectNum + "," + projectName + "," +  designType + "," +  physicalAdd + "," +  erfNum + "," +  totalFee + "," +  feePaid + "," +  dueDate 
						+ "," +  projectStatus + "," + consumer.write() + "," +  architect.write() + "," +  contractor.write();
return details;
	}
}

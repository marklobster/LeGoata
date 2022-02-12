package Engine.GameCharacter;

public abstract class GameCharacter {
	
	private String firstName;
	
	private String lastName;
	
	private StatManager statManager;
	
	private EquipmentManager equipment;
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getFullName() {
		if (lastName == null || lastName == "") {
			return firstName;
		}
		return String.join(" ", firstName, lastName);
	}
	
	public StatManager getStats() {
		return statManager;
	}
	
	public EquipmentManager getEquipment() {
		return equipment;
	}
}

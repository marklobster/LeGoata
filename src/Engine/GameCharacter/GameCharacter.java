package Engine.GameCharacter;

public class GameCharacter {
	
	private String firstName;
	
	private String lastName;
	
	private StatManager statManager;
	
	private EquipmentManager equipment;
	
	private ActionManager actionManager;
	
	public GameCharacter(CharacterInitializer initializer) {
		this.firstName = initializer.getFirstName();
		this.lastName = initializer.getLastName();
		this.statManager = initializer.getStatManager();
		this.equipment = initializer.getEquipment();
		this.actionManager = initializer.getActionManager();
	}
	
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
	
	public ActionManager getActionManager() {
		return actionManager;
	}
}

package Engine.GameCharacter;

public class CharacterInitializer {

	private String firstName;
	
	private String lastName;
	
	private StatManager statManager;
	
	private EquipmentManager equipment;

	private ActionManager actionManager;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public StatManager getStatManager() {
		return statManager;
	}

	public void setStatManager(StatManager statManager) {
		this.statManager = statManager;
	}

	public EquipmentManager getEquipment() {
		return equipment;
	}

	public void setEquipment(EquipmentManager equipment) {
		this.equipment = equipment;
	}

	public ActionManager getActionManager() {
		return actionManager;
	}

	public void setActionManager(ActionManager actionManager) {
		this.actionManager = actionManager;
	}
}

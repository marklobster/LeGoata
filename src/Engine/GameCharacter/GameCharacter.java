package Engine.GameCharacter;

public abstract class GameCharacter {
	
	// identity properties
	
	private String firstName;
	
	private String lastName;
	
	private StatManager statManager;
	
	private EquipmentManager equipment;
	
	
	// readiness properties
	
	private int delayState;
	
	
	// identity methods
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public StatManager getStats() {
		return statManager;
	}
	
	public EquipmentManager getEquipment() {
		return equipment;
	}
	
	// readiness methods
	
	public int getDelayState() {
		return delayState;
	}
	
	public int performAction(int cost) {
		delayState += cost;
		return delayState;
	}
	
	public int recoverFromAction(int time) {
		delayState -= time;
		if (delayState < 0) {
			delayState = 0;
		}
		return delayState;
	}
}

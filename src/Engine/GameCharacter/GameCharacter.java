package Engine.GameCharacter;

public abstract class GameCharacter {
	
	// identity properties
	
	private String firstName;
	
	private String lastName;
	
	// health properities
	
	private int health;
	
	private int maxHealth;
	
	// readiness properties
	
	private int delayState;
	
	// base stat properties
	
	private int strength;
	
	private int dexterity;
	
	private int initiative;
	
	private int agility;
	
	private int awareness;
	
	private int resolve;
	
	// identity methods
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	// health methods
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int addHealth(int points) {
		health += points;
		if (health > maxHealth) {
			health = maxHealth;
		}
		return health;
	}
	
	// misc stat methods
	
	public int getCarryingCapacity() {
		return strength * 3 + dexterity + resolve;
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

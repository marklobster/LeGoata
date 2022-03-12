package Engine.GameCharacter;

import java.util.ArrayList;

import Engine.Equipment.Equipment;
import Engine.Equipment.Shield;
import Engine.Equipment.Weapon;

public class GameCharacter {
	
	private String firstName;
	
	private String lastName;
	
	private ArrayList<Equipment> equipment;
	
	private Weapon weapon;
	
	private Shield shield;
	
	private int health;
	
	private int maxHealth;
	
	private int strength;
	
	private int dexterity;
	
	private int quickness;
	
	private int agility;
	
	private int awareness;
	
	private int resolve;
	
	public GameCharacter(CharacterInitializer initializer) {
		this.firstName = initializer.getFirstName();
		this.lastName = initializer.getLastName();
	}
	
	// Name
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getFullName() {
		return String.join(" ", firstName, lastName);
	}
	
	// Equipment	
	public boolean equipWeapon(Weapon weapon) {
		if (weapon.isTwoHanded() && shield != null) {
			return false;
		} else {
			this.weapon = weapon;
			return true;
		}
	}
	
	public Weapon unequipWeapon() {
		Weapon removal = weapon;
		weapon = null;
		return removal;
	}
	
	public boolean equipShield(Shield shield) {
		this.shield = shield;
		return true;
	}
	
	public Shield unequipShield() {
		Shield removal = shield;
		shield = null;
		return removal;
	}
	
	public boolean canCarry(Equipment item) {
		return getCarriedWeight() + item.getWeight() <= getCarryingCapacity();
	}
	
	public boolean addItem(Equipment item) {
		if (canCarry(item)) {
			equipment.add(item);
			return true;
		}
		return false;
	}
	
	public boolean removeItem(Equipment item) {
		boolean removed = equipment.remove(item);
		if (removed) {
			if (item == shield) {
				shield = null;
			} else if (item == weapon) {
				weapon = null;
			}
		}
		return removed;
	}
	
	// Stats
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int gainHealth(int points) {
		health += points;
		if (health > maxHealth) {
			health = maxHealth;
		}
		return health;
	}
	
	public int loseHealth(int points) {
		health -= points;
		if (health < 0) {
			health = 0;
		}
		return health;
	}
	
	public int getBaseStrength() {
		return strength;
	}
	
	public int getBaseDexterity() {
		return dexterity;
	}
	
	public int getBaseQuickness() {
		return quickness;
	}
	
	public int getBaseAgility() {
		return agility;
	}
	
	public int getBaseAwareness() {
		return awareness;
	}
	
	public int getBaseResolve() {
		return resolve;
	}
	
	public int getCarryingCapacity() {
		return strength * 3 + dexterity + resolve;
	}
	
	public int getCarriedWeight() {
		int weight = 0;
		for (Equipment item : equipment) {
			weight += item.getWeight();
		}
		return weight;
	}
	
	public int getInitiative() {
		return 0;
	}

	// Attempts
	public boolean attemptMeleeStrike() {
		return false;
	}
	public boolean attemptCriticalMeleeStrike() {
		return false;
	}
	public boolean attemptRangedStrike() {
		return false;
	}
	public boolean attemptCriticalRangedStrike() {
		return false;
	}
	public boolean attemptDodge() {
		return false;
	}
	public boolean attemptDeflection() {
		return false;
	}
}

package org.legoata.gamecharacter;

public class StatManager {
	
	private int health;
	
	private int maxHealth;
	
	private int strength;
	
	private int dexterity;
	
	private int quickness;
	
	private int agility;
	
	private int awareness;
	
	private int resolve;
	
	// health methods
	
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
	
	public int getInitiative() {
		return 0;
	}
	
}

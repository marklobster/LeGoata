package legoata.engine.gamecharacter;

import java.util.ArrayList;

import legoata.engine.equipment.Item;
import legoata.engine.equipment.Shield;
import legoata.engine.equipment.Weapon;

public class CharacterInitializer {
	
	private boolean isUser;

	private String firstName;
	
	private String lastName;
	
	private ArrayList<Item> item = new ArrayList<Item>();
	
	private Weapon weapon;
	
	private Weapon defaultWeapon;
	
	private Shield shield;
	
	private int maxHealth;
	
	private int strength;
	
	private int dexterity;
	
	private int quickness;
	
	private int agility;
	
	private int awareness;
	
	private int resolve;
	
	public boolean isUserControlled() {
		return this.isUser;
	}
	
	public void setUserControlled(boolean isUserControllered) {
		this.isUser = isUserControllered;
	}
	
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
	
	public ArrayList<Item> getEquipment(){
		return item;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public Weapon getDefaultWeapon() {
		return defaultWeapon;
	}
	
	public void setDefaultWeapon(Weapon weapon) {
		defaultWeapon = weapon;
	}

	public Shield getShield() {
		return shield;
	}

	public void setShield(Shield shield) {
		this.shield = shield;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public int getQuickness() {
		return quickness;
	}

	public void setQuickness(int quickness) {
		this.quickness = quickness;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getAwareness() {
		return awareness;
	}

	public void setAwareness(int awareness) {
		this.awareness = awareness;
	}

	public int getResolve() {
		return resolve;
	}

	public void setResolve(int resolve) {
		this.resolve = resolve;
	}

	
}

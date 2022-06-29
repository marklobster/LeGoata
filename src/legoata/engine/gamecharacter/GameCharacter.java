package legoata.engine.gamecharacter;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import legoata.engine.equipment.Item;
import legoata.engine.equipment.Shield;
import legoata.engine.equipment.Weapon;
import legoata.engine.utils.Utils;

public class GameCharacter {
	
	private boolean isUser;
	
	private String firstName;
	
	private String lastName;
	
	private ArrayList<Item> items;
	
	private Weapon weapon;
	
	private Weapon defaultWeapon;
	
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
		this.isUser = initializer.isUserControlled();
		this.firstName = initializer.getFirstName();
		this.lastName = initializer.getLastName();
		
		this.maxHealth = initializer.getMaxHealth();
		this.health = this.maxHealth;
		this.strength = initializer.getStrength();
		this.dexterity = initializer.getDexterity();
		this.quickness = initializer.getQuickness();
		this.agility = initializer.getAgility();
		this.awareness = initializer.getAwareness();
		this.resolve = initializer.getResolve();
		
		this.items = new ArrayList<Item>();
		for (Item item : initializer.getEquipment()) {
			this.addItem(item);
		}
		Weapon weapon = initializer.getWeapon();
		if (weapon != null) {
			if (!items.contains(weapon)) {
				this.addItem(weapon);
			}
			this.equipWeapon(weapon);
		}
		Shield shield = initializer.getShield();
		if (shield != null) {
			if (!items.contains(shield)) {
				this.addItem(shield);
			}
			this.equipShield(shield);
		}
		this.defaultWeapon = initializer.getDefaultWeapon();
	}
	
	// user or not
	public boolean isUserControlled() {
		return isUser;
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
	
	// Item
	public List<Item> getEquipment(){
		return Collections.unmodifiableList(items);
	}
	
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
	
	public Weapon getWeapon() {
		if (this.weapon == null) {
			return defaultWeapon;
		}
		return this.weapon;
	}
	
	public boolean equipShield(Shield shield) {
		if (weapon != null && weapon.isTwoHanded()) {
			return false;
		}
		this.shield = shield;
		return true;
	}
	
	public Shield unequipShield() {
		Shield removal = shield;
		shield = null;
		return removal;
	}
	
	public Shield getShield() {
		return this.shield;
	}
	
	public boolean canCarry(Item item) {
		return getCarriedWeight() + item.getWeight() <= getCarryingCapacity();
	}
	
	public boolean addItem(Item item) {
		if (canCarry(item)) {
			items.add(item);
			return true;
		}
		return false;
	}
	
	public boolean removeItem(Item item) {
		boolean removed = items.remove(item);
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
		if (points < 0) {
			points = 0;
		}
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
	
	// derived stats
	public boolean isFallen() {
		return health == 0;
	}
	
	public int getCarryingCapacity() {
		return strength * 3 + dexterity + resolve;
	}
	
	public int getCarriedWeight() {
		int weight = 0;
		for (Item item : items) {
			weight += item.getWeight();
		}
		return weight;
	}
	
	public int getInitiative() {
		int init = Utils.getRandom(-5, 5) + awareness;
		if (init < 0) {
			init = 0;
		}
		return init;
	}
	
	public int calculateDamageSent() {
		int damage = getWeapon().getPower() + strength + dexterity + Utils.getRandom(-3, 3);
		if (damage < 0) {
			damage = 0;
		}
		return damage;
	}
	
	public int calculateDamageReduction() {
		int reduction = Utils.getRandom(-4, 4) + strength + resolve / 2;
		if (reduction < 0) {
			reduction = 0;
		}
		return reduction;
	}

	// Attempts
	public boolean attemptMeleeStrike() {
		int strikeAttempt = Utils.getRandom(-4, 4) + dexterity;
		return strikeAttempt > 8;
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
		int dodgeAttempt = Utils.getRandom(-4, 4) + awareness + agility + quickness;
		return dodgeAttempt > 28;
	}
	public boolean attemptDeflection() {
		if (this.shield == null) {
			return false;
		}
		int deflectionAttempt = Utils.getRandom(-5, 5) + dexterity * 2 + awareness + agility + quickness;
		return deflectionAttempt > 42;
	}
	
	// Select target
	public GameCharacter selectTarget(ArrayList<GameCharacter> opponents) {
		ArrayList<GameCharacter> livingOpponents = new ArrayList<GameCharacter>();
		for (GameCharacter gc : opponents) {
			if (!gc.isFallen()) {
				livingOpponents.add(gc);
			}
		}
		return Utils.pickRandom(livingOpponents);
	}
	
	public void printStats(PrintStream stream) {
		stream.println(String.format("%s%s%s", "~", getFullName(), "~"));
		stream.println(String.format("Health: %d / %d", health, maxHealth));
		stream.println(String.format("Weapon: %s", getWeapon().getName()));
		stream.println(String.format("Shield: %s", shield == null ? "none" : getShield().getName()));
		stream.println(String.format("Strength: %d", strength));
		stream.println(String.format("Dexterity: %d", dexterity));
		stream.println(String.format("Quickness: %d", quickness));
		stream.println(String.format("Agility: %d", agility));
		stream.println(String.format("Awareness: %d", awareness));
		stream.println(String.format("Resolve: %d", resolve));
		stream.println(String.format("Rating: %d", strength + dexterity + quickness + agility + awareness + resolve));
		stream.println("");
	}
}

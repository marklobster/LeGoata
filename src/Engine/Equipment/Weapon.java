package Engine.Equipment;

public class Weapon extends Equipment {

	public Weapon(int power, boolean twoHanded, int weight, String name, String description) {
		super(weight, name, description);
		this.power = power;
	}
	
	private int power;
	private boolean twoHanded;
	
	public int getPower() {
		return power;
	}
	
	public boolean isTwoHanded() {
		return twoHanded;
	}
}

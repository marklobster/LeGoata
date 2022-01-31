package Engine.Equipment;

public class Weapon extends Equipment {

	public Weapon(int power, int weight, String name, String description) {
		super(weight, name, description);
		this.power = power;
	}
	
	private int power;
	
	public int getPower() {
		return power;
	}
}

package Engine.Equipment;

public abstract class Equipment {
	private int weight;
	private String name;
	private String description;
	
	public Equipment(int weight, String name, String description) {
		this.weight = weight;
		this.name = name;
		this.description = description;
	}
	
	public int getWeight() {
		return weight;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
}

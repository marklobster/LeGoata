package Engine.Equipment;

public class Shield extends Equipment {

	public Shield(int block, int weight, String name, String description) {
		super(weight, name, description);
		this.block = block;
	}
	
	private int block;
	
	public int getBlock() {
		return block;
	}
}

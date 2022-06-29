package legoata.engine.equipment;

import java.io.PrintStream;

import legoata.engine.actions.TargetType;
import legoata.engine.gamecharacter.GameCharacter;

public class Shield extends Item {

	public Shield(int block, int weight, String name, String description) {
		super(weight, name, description, TargetType.Self, false);
		this.block = block;
	}
	
	private int block;
	
	public int getBlock() {
		return block;
	}

	@Override
	public void useItem(GameCharacter user, GameCharacter target, PrintStream out) {
		if (!user.getEquipment().contains(this)) {
			throw new IllegalArgumentException("GameCharacter does not possess Shield.");
		}
		if (user.equipShield(this)) {
			out.println(this.getName() + " equipped!");
		} else {
			out.println("Unable to equip shield.");
		}
	}
}

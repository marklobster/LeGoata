package legoata.engine.equipment;

import java.io.PrintStream;

import legoata.engine.actions.TargetType;
import legoata.engine.gamecharacter.GameCharacter;

public class HealingItem extends Equipment {
	
	private int healthPoints;

	public HealingItem(int weight, String name, String description, int healthPoints) {
		super(weight, name, description, TargetType.Ally);
		this.healthPoints = healthPoints;
	}

	@Override
	public void useItem(GameCharacter user, GameCharacter target, PrintStream out) {
		out.println(String.format("%s gains %d health.", target.getFullName(), healthPoints));
		target.gainHealth(healthPoints);
	}

}

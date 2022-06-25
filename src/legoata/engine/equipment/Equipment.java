package legoata.engine.equipment;

import java.io.PrintStream;

import legoata.engine.actions.TargetType;
import legoata.engine.gamecharacter.GameCharacter;

public abstract class Equipment {
	private int weight;
	private String name;
	private String description;
	private TargetType targetType;
	
	public Equipment(int weight, String name, String description, TargetType targetType) {
		this.weight = weight;
		this.name = name;
		this.description = description;
		this.targetType = targetType;
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
	
	public TargetType getTargetType() {
		return targetType;
	}

	public abstract void useItem(GameCharacter user, GameCharacter target, PrintStream out);
}

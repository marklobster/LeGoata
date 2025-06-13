package org.legoata.equipment;

import java.io.PrintStream;

import org.legoata.gamecharacter.GameCharacter;

public abstract class Item {
	private int weight;
	private String name;
	private String description;
	private TargetType targetType;
	private boolean disposable;
	
	public Item(int weight, String name, String description, TargetType targetType, boolean disposeOnUse) {
		this.weight = weight;
		this.name = name;
		this.description = description;
		this.targetType = targetType;
		this.disposable = disposeOnUse;
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
	
	public boolean disposeOnUse() {
		return disposable;
	}

	public abstract void useItem(GameCharacter user, GameCharacter target, PrintStream out);
}

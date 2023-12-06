package legoata.engine.map.location;

import java.util.ArrayList;

import legoata.engine.equipment.Item;
import legoata.engine.gamecharacter.GameCharacter;

public class Location implements MapNode {
	
	private boolean isPath;
	private boolean isBarrier;
	private ArrayList<GameCharacter> occupants = new ArrayList<GameCharacter>();
	private ArrayList<Item> contents = new ArrayList<Item>();

	public Location(boolean isPath, boolean isBarrier) {
		this.isPath = isPath;
		this.isBarrier = isBarrier;
	}
	
	@Override
	public boolean isPath() {
		return this.isPath;
	}
	
	@Override
	public boolean isBarrier() {
		return this.isBarrier;
	}

	@Override
	public ArrayList<GameCharacter> getOccupants() {
		return occupants;
	}

	@Override
	public ArrayList<Item> getContents() {
		return contents;
	}

}

package legoata.engine.map.location;

import java.util.ArrayList;

import legoata.engine.equipment.Item;
import legoata.engine.gamecharacter.GameCharacter;

public interface MapNode {
	public boolean isPath();
	public boolean isBarrier();
	public ArrayList<GameCharacter> getOccupants();
	public ArrayList<Item> getContents();
}

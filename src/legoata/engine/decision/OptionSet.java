package legoata.engine.decision;

import java.util.ArrayList;

import legoata.engine.gamecharacter.GameCharacter;

public interface OptionSet {

	public OptionSet select(Decision decision, Option selection);
	
	public void undoSelection(Decision decision);
	
	public ArrayList<Option> getOptions(GameCharacter character);
	
	public String getPrompt();
	
	public String getEmptySetText();
	
}

package legoata.engine.decision;

import java.util.ArrayList;

import legoata.engine.gamecharacter.GameCharacter;

public interface OptionSet {

	public OptionSet select(Decision decision, Option selection, GameCharacter actor);
	
	public void undoSelection(Decision decision, GameCharacter character);
	
	public ArrayList<Option> getOptions(Decision decision, GameCharacter character);
	
	public String getPrompt();
	
	public String getEmptySetText();
	
}

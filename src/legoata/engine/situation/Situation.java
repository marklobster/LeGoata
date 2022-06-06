package legoata.engine.situation;

import java.io.PrintStream;
import java.util.ArrayList;

import legoata.engine.actions.CharacterAction;

public interface Situation {
	
	public abstract ArrayList<CharacterAction> getPossibleActions();

	public abstract void performAction(CharacterAction action, PrintStream stream);
}

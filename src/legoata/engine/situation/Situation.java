package legoata.engine.situation;

import java.io.PrintStream;
import java.util.ArrayList;

import legoata.engine.actions.CharacterAction;

/**
 * Provides infrastructure for characters to perform actions.
 * @author Mark
 *
 */
public interface Situation {
	
	public abstract ArrayList<CharacterAction> getPossibleActions();

	public abstract void performAction(CharacterAction action, PrintStream stream);
}

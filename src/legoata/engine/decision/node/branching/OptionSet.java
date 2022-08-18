package legoata.engine.decision.node.branching;

import java.io.PrintStream;
import java.util.ArrayList;

import legoata.engine.decision.Decision;
import legoata.engine.decision.node.DecisionBuilderNode;
import legoata.engine.gamecharacter.GameCharacter;

/**
 * A menu of selectable options.
 * @author Mark
 *
 */
public interface OptionSet extends DecisionBuilderNode {

	public DecisionBuilderNode select(Decision decision, Option selection, GameCharacter actor, PrintStream out);
	
	public void undoSelection(Decision decision, GameCharacter character);
	
	public ArrayList<Option> getOptions(Decision decision, GameCharacter character);
	
	public String getPrompt();
	
	public String getEmptySetText();
	
}

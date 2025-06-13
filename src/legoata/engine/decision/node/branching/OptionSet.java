package legoata.engine.decision.node.branching;

import java.io.PrintStream;
import java.util.ArrayList;

import legoata.engine.decision.Decision;
import legoata.engine.decision.node.DecisionBuilderNode;
import legoata.engine.gamecharacter.GameCharacter;
import legoata.engine.model.LGObject;

/**
 * A menu of selectable options.
 * @author Mark
 *
 */
public interface OptionSet extends DecisionBuilderNode {

	public DecisionBuilderNode select(Decision decision, Option selection, LGObject actor, PrintStream out);
	
	public void undoSelection(Decision decision, LGObject actor);
	
	public ArrayList<Option> getOptions(Decision decision, LGObject actor);
	
	public String getPrompt();
	
	public String getEmptySetText();
	
}

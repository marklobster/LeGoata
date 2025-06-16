package org.legoata.decision.node.branching;

import java.io.PrintStream;
import java.util.ArrayList;

import org.legoata.decision.Decision;
import org.legoata.decision.node.DecisionBuilderNode;
import org.legoata.gamecharacter.GameCharacter;
import org.legoata.model.LGObject;

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

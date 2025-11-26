package org.legoata.decision.node.branching;

import java.io.PrintStream;
import java.util.Scanner;

import org.legoata.decision.node.DecisionBuilderNode;
import org.legoata.model.LGObject;

public interface InputNode<T> extends DecisionBuilderNode {
	
	public DecisionBuilderNode getInput(LGObject actor, T decisionData, Scanner in, PrintStream out);
	
	public void undo(LGObject actor, T decisionData, PrintStream out);
	
}

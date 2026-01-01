package org.legoata.decision;

import java.io.PrintStream;
import java.util.Stack;

import org.legoata.decision.node.DecisionBuilderNode;
import org.legoata.decision.node.branching.InputNode;
import org.legoata.decision.node.nonbranching.DecisionComplete;
import org.legoata.decision.node.nonbranching.GoBack;
import org.legoata.decision.node.nonbranching.ReturnToRoot;
import org.legoata.execute.ClockControls;
import org.legoata.execute.ControlUnit;
import org.legoata.execute.GameControls;
import org.legoata.execute.RoundControls;
import org.legoata.execute.SchedulingControls;
import org.legoata.execute.TurnControls;

/**
 * Provides a means of navigating a menu with or without sub-menus which will ultimately 
 * result in the user building an decision.
 * @author Mark
 *
 */
public class DecisionBuilder<T> {

	private ControlUnit controls;
	private InputNode<T> rootNode;
	private String initialText;
	
	public DecisionBuilder(ControlUnit controls) {
		this.controls = controls;
	}
	
	public InputNode<T> getRootNode() {
		return this.rootNode;
	}
	
	public void setRootNode(InputNode<T> rootNode) {
		this.rootNode = rootNode;
	}
	
	public String getInitialText() {
		return this.initialText;
	}
	
	public void setInitialText(String text) {
		this.initialText = text;
	}
	
	/**
	 * Obtain input from a player who is a user by navigating the DecisionBuilder and building a Decision.
	 * @param decision
	 * @return
	 */
	public T getUserDecision(T decision) {
		
		PrintStream out = this.getGameControls().getOutStream();
		
		// put root level menu onto stack
		Stack<InputNode<T>> nodeStack = new Stack<InputNode<T>>();
		nodeStack.push(this.rootNode);
		boolean decisionComplete = false;
		
		// print initial text of menu tree
		if (this.initialText != null && this.initialText != "") {
			out.println(initialText);
		}
		
		do {
			// process current node
			InputNode<T> currentNode = nodeStack.peek();
			DecisionBuilderNode nextNode = currentNode.getInput(decision);
			
			// repeat same decision
			if (nextNode == null) {
				currentNode.undo(decision);
			}
			
			// decision completed
			else if (nextNode instanceof DecisionComplete) {
				decisionComplete = true;
			}
			
			// go back n number of nodes
			else if (nextNode instanceof GoBack) {
				GoBack goBackSignal = (GoBack)nextNode;
				nodeStack.peek().undo(decision);
				int counter = 0;
				while (counter++ < goBackSignal.getNumberOfStepsBack()
						&& nodeStack.size() > 1) {
					nodeStack.pop();
					nodeStack.peek().undo(decision);
				}
			}
			
			// return to menu root
			else if (nextNode instanceof ReturnToRoot) {
				nodeStack.peek().undo(decision);
				while (nodeStack.size() > 1) {
					nodeStack.pop();
					nodeStack.peek().undo(decision);
				}
			}
			
			// sub-menu
			else if (nextNode instanceof InputNode) {
				nodeStack.push((InputNode<T>)nextNode);
			}
			
			// unsupported DecisionBuilderNode type
			else {
				String err = String.format(
						"The org.legoata.decision.DecisionBuilderNode sub-class %s is not supported.",
						nextNode.getClass());
				throw new UnsupportedOperationException(err);
			}
			
		} while (!decisionComplete);
		
		return decision;
	}
	
	/**
	 * The whole set of controls.
	 * @return ControlSet
	 */
	protected ControlUnit getControls() {
		return this.controls;
	}
	
	/**
	 * Reference to the GameControls.
	 * @return GameControls
	 */
	protected GameControls getGameControls() {
		return this.controls.getGameControls();
	}
	
	/**
	 * Reference to the ClockControls.
	 * @return ClockControls
	 */
	protected ClockControls getClockControls() {
		return this.controls.getClockControls();
	}
	
	/**
	 * Reference to the SchedulingControls.
	 * @return SchedulingControls
	 */
	protected SchedulingControls getSchedulingControls() {
		return this.controls.getSchedulingControls();
	}
	
	/**
	 * Reference to the RoundControls.
	 * @return RoundControls
	 */
	protected RoundControls getRoundControls() {
		return this.controls.getRoundControls();
	}
	
	/**
	 * Reference to the TurnControls.
	 * @return TurnControls
	 */
	protected TurnControls getTurnControls() {
		return this.controls.getTurnControls();
	}
}

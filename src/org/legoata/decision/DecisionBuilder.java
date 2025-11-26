package org.legoata.decision;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.Stack;

import org.legoata.decision.node.DecisionBuilderNode;
import org.legoata.decision.node.branching.InputNode;
import org.legoata.decision.node.nonbranching.DecisionComplete;
import org.legoata.decision.node.nonbranching.GoBack;
import org.legoata.decision.node.nonbranching.ReturnToRoot;
import org.legoata.model.LGObject;

/**
 * Provides a means of navigating a menu with or without sub-menus which will ultimately 
 * result in the user building an decision.
 * @author Mark
 *
 */
public class DecisionBuilder<T> {

	private InputNode<T> rootNode;
	private String initialText;
	
	public DecisionBuilder() {
		
	}
	
	public DecisionBuilder(String initialText) {
		this.initialText = initialText;
	}
	
	public DecisionBuilder(InputNode<T> rootNode) {
		this.rootNode = rootNode;
	}
	
	public DecisionBuilder(InputNode<T> rootNode, String initialText) {
		this(rootNode);
		this.initialText = initialText;
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
	 * @param actor
	 * @param decision
	 * @param in
	 * @param out
	 * @return
	 */
	public T getUserDecision(LGObject actor, T decision, Scanner in, PrintStream out) {
		
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
			DecisionBuilderNode nextNode = currentNode.getInput(actor, decision, in, out);
			
			// repeat same decision
			if (nextNode == null) {
				currentNode.undo(actor, decision, out);
			}
			
			// decision completed
			else if (nextNode instanceof DecisionComplete) {
				decisionComplete = true;
			}
			
			// go back n number of nodes
			else if (nextNode instanceof GoBack) {
				GoBack goBackSignal = (GoBack)nextNode;
				nodeStack.peek().undo(actor, decision, out);
				int counter = 0;
				while (counter++ < goBackSignal.getNumberOfStepsBack()
						&& nodeStack.size() > 1) {
					nodeStack.pop();
					nodeStack.peek().undo(actor, decision, out);
				}
			}
			
			// return to menu root
			else if (nextNode instanceof ReturnToRoot) {
				nodeStack.peek().undo(actor, decision, out);
				while (nodeStack.size() > 1) {
					nodeStack.pop();
					nodeStack.peek().undo(actor, decision, out);
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
}

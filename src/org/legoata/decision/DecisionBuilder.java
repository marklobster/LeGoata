package org.legoata.decision;

import org.legoata.decision.node.branching.InputNode;

/**
 * Provides a means of navigating a menu with or without sub-menus which will ultimately 
 * result in the user building an decision.
 * @author Mark
 *
 */
public class DecisionBuilder {

	private Decision decision;
	private InputNode rootNode;
	private String initialText;
	
	public DecisionBuilder() {
		this.decision = new Decision();
	}
	
	public DecisionBuilder(String initialText) {
		this();
		this.initialText = initialText;
	}
	
	public DecisionBuilder(InputNode rootNode) {
		this();
		this.rootNode = rootNode;
	}
	
	public DecisionBuilder(InputNode rootNode, String initialText) {
		this(rootNode);
		this.initialText = initialText;
	}
	
	public Decision getDecision() {
		return this.decision;
	}
	
	public InputNode getRootNode() {
		return this.rootNode;
	}
	
	public void setRootNode(InputNode rootNode) {
		this.rootNode = rootNode;
	}
	
	public String getInitialText() {
		return this.initialText;
	}
	
	public void setInitialText(String text) {
		this.initialText = text;
	}
}

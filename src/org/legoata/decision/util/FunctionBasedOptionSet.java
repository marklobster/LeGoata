package org.legoata.decision.util;

import java.io.PrintStream;
import java.util.ArrayList;

import org.legoata.decision.Decision;
import org.legoata.decision.node.branching.Option;
import org.legoata.decision.node.branching.OptionSet;
import org.legoata.gamecharacter.GameCharacter;
import org.legoata.model.LGObject;

public class FunctionBasedOptionSet implements OptionSet {
	
	private String prompt;
	private OptionSelection onSelect;
	private UndoOptionSelection onUndo;
	private OptionProvider onGetOptions;
	private String emptySetText = null;
	
	public FunctionBasedOptionSet(String prompt, OptionSelection onSelect, OptionProvider onGetOptions) {
		this.prompt = prompt;
		this.onSelect = onSelect;
		this.onGetOptions = onGetOptions;
	}
	
	public FunctionBasedOptionSet(String prompt, OptionSelection onSelect, OptionProvider onGetOptions, UndoOptionSelection onUndo) {
		this(prompt, onSelect, onGetOptions);
		this.onUndo = onUndo;
	}
	
	public FunctionBasedOptionSet(String prompt, OptionSelection onSelect, OptionProvider onGetOptions, String emptySetText) {
		this(prompt, onSelect, onGetOptions);
		this.emptySetText = emptySetText;
	}
	
	public FunctionBasedOptionSet(String prompt, OptionSelection onSelect, OptionProvider onGetOptions, UndoOptionSelection onUndo, String emptySetText) {
		this(prompt, onSelect, onGetOptions, onUndo);
		this.emptySetText = emptySetText;
	}

	@Override
	public OptionSet select(Decision decision, Option selection, LGObject actor, PrintStream out) {
		return onSelect.select(decision, selection, actor, out);
	}

	@Override
	public void undoSelection(Decision decision, LGObject actor) {
		if (this.onUndo != null) {
			this.onUndo.undoSelection(decision, actor);
		}
	}

	@Override
	public ArrayList<Option> getOptions(Decision decision, LGObject actor) {
		return this.onGetOptions.getOptions(decision, actor);
	}

	@Override
	public String getPrompt() {
		return this.prompt;
	}

	@Override
	public String getEmptySetText() {
		return this.emptySetText;
	}

}

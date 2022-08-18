package legoata.engine.decision.util;

import java.io.PrintStream;
import java.util.ArrayList;

import legoata.engine.decision.Decision;
import legoata.engine.decision.node.branching.Option;
import legoata.engine.decision.node.branching.OptionSet;
import legoata.engine.gamecharacter.GameCharacter;

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
	public OptionSet select(Decision decision, Option selection, GameCharacter actor, PrintStream out) {
		return onSelect.select(decision, selection, actor, out);
	}

	@Override
	public void undoSelection(Decision decision, GameCharacter actor) {
		if (this.onUndo != null) {
			this.onUndo.undoSelection(decision, actor);
		}
	}

	@Override
	public ArrayList<Option> getOptions(Decision decision, GameCharacter actor) {
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

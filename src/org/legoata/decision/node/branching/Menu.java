package org.legoata.decision.node.branching;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.legoata.decision.Decision;
import org.legoata.decision.node.DecisionBuilderNode;
import org.legoata.execute.ControlSet;
import org.legoata.model.LGObject;

public abstract class Menu implements InputNode {
	
	private ListDisplayMode displayMode;
	private InputSpecificity specificity;
	private String separator = ") ";

	public Menu(ListDisplayMode displayOption, InputSpecificity specificityOption) {
		this.displayMode = displayOption;
		this.specificity = specificityOption;
	}

	@Override
	public DecisionBuilderNode getInput(ControlSet controls, Decision decision, LGObject actor) {
		final String NUMBERED_FORMAT = "%d%s%s%s";
		final String LABELED_FORMAT = "%s%s%s%s";
		PrintStream out = controls.getGameControls().getOutStream();
		Scanner in = controls.getGameControls().getScanner();
		Option selection = null;
		
		do {
			List<Option> options = getOptions(decision, actor);
			
			// show empty text message, if applicable
			String emptyText = this.getEmptySetText();
			if (options.isEmpty() && emptyText != null && emptyText != "") {
				out.println(emptyText);
			}

			// show prompt
			out.println(this.getPrompt());
			
			// display the options and build dictionary of options
			Map<String, Option> dictionary = new HashMap<String, Option>();
			switch (this.displayMode) {
			case LABELS:
				for (Option option : options) {
					String key = option.getLabel();
					out.printf(LABELED_FORMAT, key, separator, option.getTitle(), System.lineSeparator());
					dictionary.put(key, option);
				}
				break;
			case TITLES:
				for (Option option : options) {
					String title = option.getTitle();
					out.println(title);
					dictionary.put(title, option);
				}
				break;
			default:
				// handle counting from 0 or from 1
				int counter, limit;
				if (this.displayMode == ListDisplayMode.NUMBER_FROM_ZERO) {
					counter = 0;
					limit = options.size() - 1;
				} else {
					counter = 1;
					limit = options.size();
				}
				while (counter <= limit) {
					Option option = options.get(counter);
					out.printf(NUMBERED_FORMAT, counter, separator, option.getTitle(), System.lineSeparator());
					dictionary.put(Integer.toString(counter++), option);
				}
				break;
			}
			
			// check if input matches an option
			String input = in.nextLine();
			if (input != null) {
				selection = dictionary.get(input);
			}
			
		} while (selection == null);
		
		return this.select(decision, selection, actor, out);
	}
	
	public abstract DecisionBuilderNode select(Decision decision, Option selection, LGObject actor, PrintStream out);
	
	public abstract void undoSelection(Decision decision, LGObject actor);
	
	public abstract List<Option> getOptions(Decision decision, LGObject actor);
	
	public abstract String getPrompt();
	
	public String getEmptySetText() {
		return null;
	}

}

package org.legoata.decision.node.branching;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.legoata.decision.node.DecisionBuilderNode;
import org.legoata.decision.node.nonbranching.GoBack;
import org.legoata.model.LGObject;

public abstract class OptionSet<T> implements InputNode<T> {
	
	public final static String DEFAULT_SEPARATOR = ") ";
	
	private ListDisplayMode displayMode;
	private InputSpecificity specificity;
	boolean showBackOption;
	private String separator;

	public OptionSet(ListDisplayMode displayOption, InputSpecificity specificityOption) {
		this(displayOption, specificityOption, false, DEFAULT_SEPARATOR);
	}
	
	public OptionSet(ListDisplayMode displayOption, InputSpecificity specificityOption, boolean supplyBackOption) {
		this(displayOption, specificityOption, supplyBackOption, DEFAULT_SEPARATOR);
	}
	
	public OptionSet(ListDisplayMode displayOption, InputSpecificity specificityOption, String separator) {
		this(displayOption, specificityOption, false, separator);
	}
	
	public OptionSet(ListDisplayMode displayOption, InputSpecificity specificityOption, boolean supplyBackOption, String separator) {
		this.displayMode = displayOption;
		this.specificity = specificityOption;
		this.showBackOption = supplyBackOption;
		this.separator = separator;
	}

	@Override
	public DecisionBuilderNode getInput(LGObject actor, T decisionData, Scanner in, PrintStream out) {
		final String KEY_FORMAT = "%s%s";
		final String NUMBERED_FORMAT = "%d%s%s%s";
		final String TITLE_FORMAT = "%s%s%s%s";
		final String BACK_KEY = "b";
		final String BACK_TEXT = "Back";
		final String BACK_TEXT_KEYS_ONLY = "[B]ack";
		Option selection = null;
		Option backOption = null;
		
		do {
			List<Option> options = getOptions(decisionData, actor);
			
			// show prompt
			out.println(this.getPrompt());

			// show empty text message, if applicable
			if (options.isEmpty()) {
				String emptyText = this.getEmptySetText();
				if (emptyText != null && emptyText != "") {
					out.println(emptyText);
				}
			}

			// display the options and build map of options
			Map<String, Option> dictionary = new HashMap<String, Option>();
			switch (this.displayMode) {
			case KEYS_AND_TITLES:
				for (Option option : options) {
					String key = option.getKey();
					out.printf(TITLE_FORMAT, key, separator, option.getTitle(), System.lineSeparator());
					dictionary.put(key, option);
				}
				if (this.showBackOption) {
					backOption = new Option(BACK_TEXT, null, BACK_KEY);
					out.printf(TITLE_FORMAT, BACK_KEY, separator, BACK_TEXT, System.lineSeparator());
					dictionary.put(BACK_KEY, backOption);
				}
				break;
			case KEYS_ONLY:
				for (Option option : options) {
					String key = option.getKey();
					out.printf(KEY_FORMAT, key, System.lineSeparator());
					dictionary.put(key, option);
				}
				if (this.showBackOption) {
					backOption = new Option(BACK_TEXT_KEYS_ONLY, null, BACK_KEY);
					out.printf(KEY_FORMAT, BACK_TEXT_KEYS_ONLY, System.lineSeparator());
					dictionary.put(BACK_KEY, backOption);
				}
				break;
			default:
				// handle counting from 0 or from 1
				int optionNum = this.displayMode == ListDisplayMode.NUMBER_FROM_ZERO ? 0 : 1;
				for (int i = 0; i < options.size(); i++, optionNum++) {
					Option option = options.get(i);
					out.printf(NUMBERED_FORMAT, optionNum, separator, option.getTitle(), System.lineSeparator());
					dictionary.put(Integer.toString(optionNum), option);
				}
				if (this.showBackOption) {
					backOption = new Option(BACK_TEXT, null, BACK_KEY);
					out.printf(TITLE_FORMAT, BACK_KEY, separator, BACK_TEXT, System.lineSeparator());
					dictionary.put(BACK_KEY, backOption);
				}
			}
			
			// check if input matches an option
			String input = in.nextLine();
			if (input != null) {
				selection = dictionary.get(input);
			}
			
		} while (selection == null);
		
		return selection == backOption ?
				new GoBack() :
				this.select(actor, decisionData, selection, out);
	}
	
	public abstract DecisionBuilderNode select(LGObject actor, T decisionData, Option selection, PrintStream out);
	
	public abstract List<Option> getOptions(T decisionData, LGObject actor);
	
	public abstract String getPrompt();
	
	public String getEmptySetText() {
		return null;
	}

}

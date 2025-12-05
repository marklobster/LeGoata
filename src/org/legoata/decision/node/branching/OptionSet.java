package org.legoata.decision.node.branching;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.legoata.decision.node.DecisionBuilderNode;
import org.legoata.decision.node.nonbranching.GoBack;
import org.legoata.execute.ClockControls;
import org.legoata.execute.ControlSet;
import org.legoata.execute.GameControls;
import org.legoata.execute.RoundControls;
import org.legoata.execute.SchedulingControls;
import org.legoata.execute.TurnControls;

public abstract class OptionSet<T> implements InputNode<T> {
	
	public final static String DEFAULT_SEPARATOR = ") ";
	
	private ControlSet controls;
	private ListDisplayMode displayMode;
	private InputSpecificity specificity;
	boolean showBackOption;
	private String separator;

	public OptionSet(ControlSet controls, ListDisplayMode displayOption, InputSpecificity specificityOption) {
		this(controls, displayOption, specificityOption, false, DEFAULT_SEPARATOR);
	}
	
	public OptionSet(ControlSet controls, ListDisplayMode displayOption, InputSpecificity specificityOption, boolean supplyBackOption) {
		this(controls, displayOption, specificityOption, supplyBackOption, DEFAULT_SEPARATOR);
	}
	
	public OptionSet(ControlSet controls, ListDisplayMode displayOption, InputSpecificity specificityOption, String separator) {
		this(controls, displayOption, specificityOption, false, separator);
	}
	
	public OptionSet(ControlSet controls, ListDisplayMode displayOption, InputSpecificity specificityOption, boolean supplyBackOption, String separator) {
		this.controls = controls;
		this.displayMode = displayOption;
		this.specificity = specificityOption;
		this.showBackOption = supplyBackOption;
		this.separator = separator;
	}

	@Override
	public DecisionBuilderNode getInput(T decisionData) {
		final String KEY_FORMAT = "%s%s";
		final String NUMBERED_FORMAT = "%d%s%s%s";
		final String TITLE_FORMAT = "%s%s%s%s";
		final String BACK_KEY = "b";
		final String BACK_TEXT = "Back";
		final String BACK_TEXT_KEYS_ONLY = "[B]ack";
		Option selection = null;
		Option backOption = null;
		PrintStream out = this.getGameControls().getOutStream();
		Scanner in = this.getGameControls().getScanner();
		
		do {
			List<Option> options = getOptions(decisionData);
			
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
					dictionary.put(transformKey(key), option);
				}
				if (this.showBackOption) {
					backOption = new Option(BACK_TEXT, null, BACK_KEY);
					out.printf(TITLE_FORMAT, BACK_KEY, separator, BACK_TEXT, System.lineSeparator());
					dictionary.put(transformKey(BACK_KEY), backOption);
				}
				break;
			case KEYS_ONLY:
				for (Option option : options) {
					String key = option.getKey();
					out.printf(KEY_FORMAT, key, System.lineSeparator());
					dictionary.put(transformKey(key), option);
				}
				if (this.showBackOption) {
					backOption = new Option(BACK_TEXT_KEYS_ONLY, null, BACK_KEY);
					out.printf(KEY_FORMAT, BACK_TEXT_KEYS_ONLY, System.lineSeparator());
					dictionary.put(transformKey(BACK_KEY), backOption);
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
					dictionary.put(transformKey(BACK_KEY), backOption);
				}
			}
			
			// check if input matches an option
			String input = in.nextLine();
			if (input != null) {
				input = transformKey(input);
				if (this.specificity == InputSpecificity.CLOSEST_MATCH) {
					ArrayList<LikenessScore> scores = calculateScores(input, dictionary.keySet());
					String key = this.getTopScoringKey(scores);
					if (key != null) {
						selection = dictionary.get(key);
					}
				} else {
					selection = dictionary.get(input);
				}
			}
			
		} while (selection == null);
		
		return selection == backOption ?
				new GoBack() :
				this.select(decisionData, selection);
	}
	
	/**
	 * Process the selected option and return the appropriate DecisionBuilderNode.
	 * @param decisionData The decision object being built
	 * @param selection The selected option
	 * @return
	 */
	public abstract DecisionBuilderNode select(T decisionData, Option selection);
	
	/**
	 * Return the list of options to display.
	 * @param decisionData The decision object being built
	 * @return The list of options
	 */
	public abstract List<Option> getOptions(T decisionData);
	
	/**
	 * Text to display before listing the options.
	 * @return
	 */
	public abstract String getPrompt();
	
	/**
	 * Text to be displayed when there are no options to select.  Null by default.
	 * @return
	 */
	public String getEmptySetText() {
		return null;
	}
	
	/**
	 * The whole set of controls.
	 * @return ControlSet
	 */
	protected ControlSet getControls() {
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
	
	/**
	 * Converts key to lower-case, unless InputSpecificity is set to EXACT_MATCH.
	 * @param key
	 * @return
	 */
	private String transformKey(String key) {
		if (key != null && this.specificity != InputSpecificity.EXACT_MATCH) {
			key = key.toLowerCase();
		}
		return key;
	}
	
	/**
	 * Calculate how like the input is to each key and return all keys/scores in a list
	 * @param input
	 * @param keys
	 * @return
	 */
	private ArrayList<LikenessScore> calculateScores(String input, Set<String> keys) {
		/**
		 * For each consecutive character in a key that matches the input, starting from index 0, a point 
		 * is added.  A perfect match gets an extra point so that it does not tie with a key that has 
		 * more characters after the input.
		 */
		ArrayList<LikenessScore> scores = new ArrayList<LikenessScore>(keys.size());
		for (String key : keys) {
			int minLength = input.length() > key.length() ? key.length() : input.length();
			int score = 0;
			for (int j = 0; j < minLength; j++) {
				if (Character.toLowerCase(input.charAt(j)) == Character.toLowerCase(key.charAt(j))) {
					score++;
				} else {
					break;
				}
			}
			// add extra point if exact match
			if (input.equals(key)) {
				score++;
			}
			scores.add(new LikenessScore(key, score));
		}
		return scores;
	}
	
	/**
	 * Finds the key with the highest score, where score is at least 1 and there is not a tie for highest score.
	 * @param scores
	 * @return key with highest score, or null if all scores are 0 or there is a tie for highest
	 */
	private String getTopScoringKey(List<LikenessScore> scores) {
		
		// sort highest scores to end
		scores.sort(new Comparator<LikenessScore>() {

			@Override
			public int compare(OptionSet<T>.LikenessScore o1, OptionSet<T>.LikenessScore o2) {
				return Integer.compare(o1.score, o2.score);
			}
			
		});
		
		switch (scores.size()) {
		case 0:
			break;
		case 1:
			// make sure score is at least 1
			if (scores.get(0).score > 0) {
				return scores.get(0).key;
			}
			break;
		default:
			// check if score is at least 1 and make sure there is not a tie for highest score
			if (scores.getLast().score > 0 && scores.getLast().score != scores.get(scores.size() - 2).score) {
				return scores.getLast().key;
			}
		}
		return null;
	}
	
	/**
	 * Contains a key and its calculated likeness score in regards to the current input
	 */
	private class LikenessScore {
		private String key;
		private int score;
		private LikenessScore(String key, int score) {
			this.key = key;
			this.score = score;
		}
	}

}

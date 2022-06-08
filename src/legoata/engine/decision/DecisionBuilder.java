package legoata.engine.decision;

public class DecisionBuilder {

	private Decision decision;
	private OptionSet optionSet;
	private String initialText;
	
	public DecisionBuilder() {
		this.decision = new Decision();
	}
	
	public DecisionBuilder(String initialText) {
		this();
		this.initialText = initialText;
	}
	
	public DecisionBuilder(OptionSet optionSet) {
		this();
		this.optionSet = optionSet;
	}
	
	public DecisionBuilder(OptionSet optionSet, String initialText) {
		this(optionSet);
		this.initialText = initialText;
	}
	
	public Decision getDecision() {
		return this.decision;
	}
	
	public OptionSet getOptionSet() {
		return this.optionSet;
	}
	
	public void setOptionSet(OptionSet optionSet) {
		this.optionSet = optionSet;
	}
	
	public String getInitialText() {
		return this.initialText;
	}
	
	public void setInitialText(String text) {
		this.initialText = text;
	}
}
